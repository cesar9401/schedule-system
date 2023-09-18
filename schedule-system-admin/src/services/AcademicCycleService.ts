import type { AcademicCycle } from '@/model/schedule.model';
import { http } from '@/services/HttpClient';
import type { AxiosResponse } from 'axios';

class AcademicCycleService {

  private URL: string = '/academic-cycles';

  findAll(): Promise<AxiosResponse<AcademicCycle[]>> {
    return http.get(this.URL);
  }

  findById(id: number): Promise<AxiosResponse<AcademicCycle>> {
    return http.get(`${this.URL}/${id}`);
  }

  save(academicCycle: AcademicCycle) {
    return http.post(this.URL, academicCycle);
  }
}

export default new AcademicCycleService();
