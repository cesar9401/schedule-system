import type { Professor } from '@/model/schedule.model';
import { http } from '@/services/HttpClient';

class ProfessorService {

  private URL = '/professors';

  findAll() {
    return http.get(this.URL);
  }

  findById(id: number) {
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
