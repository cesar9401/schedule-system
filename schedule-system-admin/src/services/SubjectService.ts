import type { Subject } from '@/model/schedule';
import { http } from '@/services/HttpClient';
import type { AxiosResponse } from 'axios';

class SubjectService {

  private URL = '/subjects';

  findAll(): Promise<AxiosResponse<Subject[]>> {
    return http.get(this.URL);
  }

  findById(id: number): Promise<AxiosResponse<Subject>> {
    return http.get(`${this.URL}/${id}`);
  }

  save(subject: Subject) {
    if (subject.subjectId) {
      return http.put(`${this.URL}/${subject.subjectId}`, subject);
    }
    return http.post(this.URL, subject);
  }
}

export default new SubjectService();
