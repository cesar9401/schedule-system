import { AcCyScheduleModelDto, Category } from '@/model/schedule.model';
import type { AcCyScheduleModel } from '@/model/schedule.model';
import { http } from '@/services/HttpClient';
import type { AxiosResponse } from 'axios';

class AcCyScheduleModelService {

  private URL: string = '/ac-cy-schedule-models';

  save(
    academicCycleId: number,
    subjectOrder: Category | undefined,
    acCyScheduleModel: AcCyScheduleModel
  ) {
    const params = {};
    if (subjectOrder) {
      params['subject-order'] = subjectOrder.internalId;
    }

    console.log(params);
    return http.post(`${this.URL}/${academicCycleId}`, acCyScheduleModel, { params: params });
  }

  findAllByAcCy(academicCycleId: number): Promise<AxiosResponse<AcCyScheduleModelDto[]>> {
    return http.get(`${this.URL}/${academicCycleId}`);
  }
}

export default new AcCyScheduleModelService();
