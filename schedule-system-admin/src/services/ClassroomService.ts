import type { Classroom } from '@/model/schedule.model';
import { http } from '@/services/HttpClient';
import type { AxiosResponse } from 'axios';

class ClassroomService {

  private URL = '/classrooms';

  findAll(): Promise<AxiosResponse<Classroom[]>> {
    return http.get(this.URL);
  }

  findById(id: number): Promise<AxiosResponse<Classroom>> {
    return http.get(`${this.URL}/${id}`);
  }

  save(classroom: Classroom) {
    return http.post(this.URL, classroom);
  }
}

export default new ClassroomService();
