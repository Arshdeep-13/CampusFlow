import api from './api';
import { API_ENDPOINTS } from '../utils/constants';

export const studentService = {
  getAttendance: async (studentId) => {
    const response = await api.get(`${API_ENDPOINTS.STUDENT.ATTENDANCE}/${studentId}`);
    return response.data;
  },

  getMarks: async (studentId) => {
    const response = await api.get(`${API_ENDPOINTS.STUDENT.MARKS}/${studentId}`);
    return response.data;
  },
};
