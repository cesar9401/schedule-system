import type { AcCyScheduleModel } from '@/model/schedule.model';
import { http } from '@/services/HttpClient';
import type { AxiosResponse } from 'axios';

class AcCyScheduleModelService {

  private URL: string = '/ac-cy-schedule-models';

  save(academicCycleId: number, acCyScheduleModel: AcCyScheduleModel) {
    return http.post(`${this.URL}/${academicCycleId}`, acCyScheduleModel);
  }

  findAllByAcCy(academicCycleId: number): Promise<AxiosResponse<AcCyScheduleModel[]>> {
    return http.get(`${this.URL}/${academicCycleId}`);
  }
}

export default new AcCyScheduleModelService();
