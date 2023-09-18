import type { AcCyScheduleDto } from '@/model/schedule.model';
import { http } from '@/services/HttpClient';
import type { AxiosResponse } from 'axios';

class AcCyScheduleService {
  private URL: string = '/ac-cy-schedules';

  findByAcCyScheduleModelId(acCyScheduleModelId: number): Promise<AxiosResponse<AcCyScheduleDto>> {
    return http.get(`${this.URL}/by-schedule-model/${acCyScheduleModelId}`);
  }

  exportByAcCyScheduleId(acCyScheduleId: number) {
    return http.get(`${this.URL}/export/${acCyScheduleId}`, { responseType: 'blob' });
  }
}

export default new AcCyScheduleService();
