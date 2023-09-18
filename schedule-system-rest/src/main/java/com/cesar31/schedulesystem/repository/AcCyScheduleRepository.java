package com.cesar31.schedulesystem.repository;

import com.cesar31.schedulesystem.dto.AcCySchedSubjDto;
import com.cesar31.schedulesystem.dto.PeriodDto;
import com.cesar31.schedulesystem.exception.ScheduleSysException;
import com.cesar31.schedulesystem.export.DataColumn;
import com.cesar31.schedulesystem.export.DataType;
import com.cesar31.schedulesystem.export.ExcelFileExporter;
import com.cesar31.schedulesystem.model.AcCySchedule;
import com.cesar31.schedulesystem.util.enums.CategoryEnum;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.xmlbeans.ThreadLocalUtil;
import org.slf4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequestScoped
@Repository(forEntity = AcCySchedule.class)
public abstract class AcCyScheduleRepository extends AbstractEntityRepository<AcCySchedule, Long> {

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    Logger logger;

    public byte[] exportById(Long acCyScheduleId) throws ScheduleSysException {
        var acCySchedule = this.findBy(acCyScheduleId);
        if (acCySchedule == null)
            throw new ScheduleSysException("schedule_not_found")
                    .status(Response.Status.NOT_FOUND);

        var schedSubjects = acCySchedule.getAcCySchedSubjs();

        var catDays = categoryRepository.findByParentInternalId(CategoryEnum.DAY_OF_WEEK.internalId);

        // periods grouping by day
        var periods = schedSubjects
                .stream()
                .map(sched -> new PeriodDto(sched.getCatDay(), sched.getStartTime(), sched.getEndTime()))
                .collect(Collectors.groupingBy(PeriodDto::getCatDay, Collectors.toSet()));

        periods.forEach((day, period) -> {
            logger.info("day: {}, period: {}", day.getDescription(), period);
        });

        // group by classroom and periods
        var schedByClassrooms = schedSubjects
                .stream()
                .map(AcCySchedSubjDto::new)
                .collect(Collectors.groupingBy(AcCySchedSubjDto::getClassroom, Collectors.groupingBy(AcCySchedSubjDto::getPeriod, Collectors.collectingAndThen(Collectors.toList(), list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException("repeated_period_for_classroom");
                    }
                    return list.get(0);
                }))));

        // get a list of the hours (just start and end time)
        var periodsInOneDay = new ArrayList<>(periods.
                values()
                .stream()
                .findFirst()
                .orElse(Set.of()))
                .stream()
                .sorted()
                .collect(Collectors.toList());

        var fileData = new LinkedHashMap<DataColumn, List<?>>();

        // times
        var periodsStr = periodsInOneDay
                .stream()
                .map(PeriodDto::getTimeStr)
                .collect(Collectors.toList());
        fileData.put(new DataColumn("Schedule/Classroom-Day", DataType.STRING), periodsStr);

        // schedule for every classroom
        for (var classroom : schedByClassrooms.keySet()) {
            logger.info("Looking periods for classroom: {}", classroom.getName());
            var periodsInClass = schedByClassrooms.get(classroom);

            // for day
            for (var day : catDays) {
                logger.info("looking for day: {}", day.getDescription());
                if (!periods.containsKey(day)) continue;
                var periodsClassDayStr = periods.get(day)
                        .stream()
                        .sorted()
                        .map(periodsInClass::get)
                        .map(AcCySchedSubjDto::schedSubjNoClassroomStr)
                        .collect(Collectors.toList());

                if (!periodsClassDayStr.isEmpty()) {
                    fileData.put(new DataColumn(classroom.getName().concat(" - ").concat(day.getDescription()), DataType.STRING), periodsClassDayStr);
                }
            }
        }

        try {
            var xlsxFile = ExcelFileExporter
                    .aExcelFileExporter(fileData)
                    .export();

            ThreadLocalUtil.clearAllThreadLocals();
            return xlsxFile.toByteArray();
        } catch (IOException e) {
            throw new ScheduleSysException("error_exporting_to_xlsx");
        }
    }
}
