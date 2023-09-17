import type { Category } from '@/model/schedule';
import { http } from '@/services/HttpClient';
import type { AxiosResponse } from 'axios';

class CategoryService {
  private URL = '/categories';

  findByParentInternalId(id: number): Promise<AxiosResponse<Category[]>> {
    return http.get(`${this.URL}/by-parent-internal-id/${id}`);
  }
}
