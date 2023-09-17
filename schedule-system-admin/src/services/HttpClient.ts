import { environment } from '@/config/environment';
import axios from 'axios';

export const http = axios.create({
  baseURL: environment.SCHEDULE_REST_URL
});
