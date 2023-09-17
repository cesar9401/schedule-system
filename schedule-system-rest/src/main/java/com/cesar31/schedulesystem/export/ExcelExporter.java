package com.cesar31.schedulesystem.export;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.cesar31.schedulesystem.export.FileExporterUtils.verifyFileData;
import static com.cesar31.schedulesystem.export.FileExporterUtils.getFileDataRowCount;
import static java.util.stream.IntStream.range;
import static org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER;

final class ExcelExporter {

    private ExcelExporter() {
        // no-op
    }

    static ByteArrayOutputStream export(final String title, final LinkedHashMap<String, String> filters, final LinkedHashMap<DataColumn, List<?>> fileData) throws IOException {
        verifyFileData(fileData);

        final var outputStream = new ByteArrayOutputStream();
        try (var workbook = new XSSFWorkbook()) {
            final var sheet = workbook.createSheet("Resultados");
            final var headerKeys = new ArrayList<>(fileData.keySet());
            final var headerStyle = getHeaderStyle(workbook);

            final var rowIndex = new AtomicInteger();

            addTitle(title, workbook, sheet, rowIndex, fileData.size());
            addFilters(filters, sheet, headerStyle, rowIndex);
            addHeaders(sheet, headerKeys, headerStyle, rowIndex);
            addData(fileData, sheet, headerKeys, rowIndex);

            for (int i = 0; i < headerKeys.size(); i++) {
                sheet.autoSizeColumn(i);
            }
            workbook.write(outputStream);
        }

        return outputStream;
    }

    private static void addTitle(final String title, final Workbook workbook, final XSSFSheet sheet, final AtomicInteger rowIndex, final int columnSize) {
        if (title != null && !title.isEmpty()) {
            final var font = workbook.createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints((short) 16);
            font.setBold(true);
            final var titleCellStyle = sheet.getWorkbook().createCellStyle();
            titleCellStyle.setAlignment(CENTER);
            titleCellStyle.setFont(font);

            final var titleRow = sheet.createRow(rowIndex.getAndAdd(1));
            var titleCell = titleRow.createCell(0);
            titleCell.setCellValue(title);
            titleCell.setCellStyle(titleCellStyle);

            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnSize - 1));
            sheet.createRow(rowIndex.getAndAdd(1));
        }
    }

    private static void addFilters(final LinkedHashMap<String, String> filters, final XSSFSheet sheet, final CellStyle headerStyle, final AtomicInteger rowIndex) {
        if (filters != null && !filters.isEmpty()) {
            var filterIndex = 0;
            var columnIndex = 0;
            Row filterRow = null;
            final var filterKeys = new ArrayList<>(filters.keySet());

            for (final String filterKey : filterKeys) {
                if (filterIndex % 2 == 0) {
                    filterRow = sheet.createRow(rowIndex.getAndAdd(1));
                    columnIndex = 0;
                } else {
                    columnIndex = 3;
                }
                var filterCell = filterRow.createCell(columnIndex);
                filterCell.setCellStyle(headerStyle);
                filterCell.setCellValue(filterKey);

                filterCell = filterRow.createCell(columnIndex + 1);
                filterCell.setCellValue(filters.get(filterKey));

                filterRow.createCell(columnIndex + 2);
                filterIndex++;
            }
            sheet.createRow(rowIndex.getAndAdd(1));
        }
    }

    private static void addHeaders(final XSSFSheet sheet, final List<DataColumn> headerKeys, final CellStyle headerStyle, final AtomicInteger rowIndex) {
        final var header = sheet.createRow(rowIndex.get());
        for (int i = 0; i < headerKeys.size(); i++) {
            final var headerCell = header.createCell(i);
            headerCell.setCellValue(headerKeys.get(i).getName());
            headerCell.setCellStyle(headerStyle);
        }
    }

    private static void addData(final LinkedHashMap<DataColumn, List<?>> fileData, final XSSFSheet sheet, final List<DataColumn> headerKeys, final AtomicInteger filterIndex) {
        final var stringCellStyle = sheet.getWorkbook().createCellStyle();
        stringCellStyle.setWrapText(true);

        final var dateCellStyle = sheet.getWorkbook().createCellStyle();
        dateCellStyle.setDataFormat((short) 14);

        final var dateTimeCellStyle = sheet.getWorkbook().createCellStyle();
        dateTimeCellStyle.setDataFormat((short) 22);

        range(0, getFileDataRowCount(fileData)).forEach(dataRowIndex -> {
            final var row = sheet.createRow(filterIndex.get() + dataRowIndex + 1);
            for (int i = 0; i < headerKeys.size(); i++) {
                final var columnHeader = headerKeys.get(i);
                final var cell = row.createCell(i);
                final var cellValue = fileData.get(columnHeader).get(dataRowIndex).toString();

                switch (columnHeader.getDataType()) {
                    case NUMBER:
                        cell.setCellValue(Double.parseDouble(cellValue));
                        break;
                    case BOOLEAN:
                        cell.setCellValue(Boolean.parseBoolean(cellValue));
                        break;
                    case LOCAL_DATE:
                        cell.setCellValue(LocalDate.parse(cellValue));
                        cell.setCellStyle(dateCellStyle);
                        break;
                    case LOCAL_DATE_TIME:
                        cell.setCellValue(LocalDateTime.parse(cellValue));
                        cell.setCellStyle(dateTimeCellStyle);
                        break;
                    case STRING:
                        cell.setCellValue(cellValue);
                        cell.setCellStyle(stringCellStyle);
                        break;
                }
            }
        });
    }

    private static CellStyle getHeaderStyle(final XSSFWorkbook workbook) {
        final var font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(true);

        final var headerStyle = workbook.createCellStyle();
        headerStyle.setFont(font);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.FINE_DOTS);

        return headerStyle;
    }

}
