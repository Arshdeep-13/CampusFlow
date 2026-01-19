import api from './api';
import { API_ENDPOINTS } from '../utils/constants';

export const teacherService = {
  getSubjects: async (teacherId) => {
    const response = await api.get(`${API_ENDPOINTS.TEACHER.SUBJECTS}/${teacherId}/subjects`);
    return response.data;
  },

  getSubjectStudents: async (teacherId, subjectId) => {
    const response = await api.get(
      `${API_ENDPOINTS.TEACHER.SUBJECT_STUDENTS}/${teacherId}/subject/${subjectId}/students`
    );
    return response.data;
  },

  markAttendance: async (teacherId, subjectId, studentId, present) => {
    const response = await api.post(
      `${API_ENDPOINTS.TEACHER.MARK_ATTENDANCE}/${teacherId}/subject/${subjectId}/student/${studentId}/attendance`,
      { present }
    );
    return response.data;
  },

  markMarks: async (teacherId, subjectId, studentId, marks) => {
    const response = await api.post(
      `${API_ENDPOINTS.TEACHER.MARK_MARKS}/${teacherId}/subject/${subjectId}/student/${studentId}/marks`,
      { marks }
    );
    return response.data;
  },
};
