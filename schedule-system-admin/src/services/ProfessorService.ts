import type { Professor } from '@/model/schedule.model';
import { http } from '@/services/HttpClient';
import type { AxiosResponse } from 'axios';

class ProfessorService {

  private URL = '/professors';

  findAll(): Promise<AxiosResponse<Professor[]>> {
    return http.get(this.URL);
  }

  findById(id: number): Promise<AxiosResponse<Professor>> {
    return http.get(`${this.URL}/${id}`);
  }

  save (professor: Professor) {
    if (professor.professorId) {
      return http.put(`${this.URL}/${professor.professorId}`, professor);
    }

    return http.post(this.URL, professor);
  }
}

export default new ProfessorService();
