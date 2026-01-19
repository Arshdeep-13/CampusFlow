import api from './api';
import { API_ENDPOINTS } from '../utils/constants';

export const adminService = {
  // Student operations
  getStudents: async () => {
    const response = await api.get(API_ENDPOINTS.STUDENT.GET_STUDENTS);
    return response.data;
  },

  createStudent: async (data) => {
    const response = await api.post(API_ENDPOINTS.ADMIN.CREATE_STUDENT, data);
    return response.data;
  },

  editStudent: async (data) => {
    const response = await api.post(API_ENDPOINTS.ADMIN.EDIT_STUDENT, data);
    return response.data;
  },

  deleteStudent: async (studentId) => {
    const response = await api.post(`${API_ENDPOINTS.ADMIN.DELETE_STUDENT}/${studentId}`);
    return response.data;
  },

  getStudentsCount: async () => {
    const response = await api.get(`${API_ENDPOINTS.STUDENT.GET_STUDENTS_COUNT}`);
    return response.data;
  },

  // Teacher operations
  getTeachers: async () => {
    const response = await api.get(API_ENDPOINTS.TEACHER.GET_TEACHERS);
    return response.data;
  },

  createTeacher: async (data) => {
    const response = await api.post(API_ENDPOINTS.ADMIN.CREATE_TEACHER, data);
    return response.data;
  },

  createAdmin: async (data) => {
    const response = await api.post(API_ENDPOINTS.ADMIN.CREATE_ADMIN, data);
    return response.data;
  },

  editTeacher: async (teacherId, data) => {
    const response = await api.post(`${API_ENDPOINTS.ADMIN.EDIT_TEACHER}/${teacherId}`, data);
    return response.data;
  },

  deleteTeacher: async (teacherId) => {
    const response = await api.post(`${API_ENDPOINTS.ADMIN.DELETE_TEACHER}/${teacherId}`);
    return response.data;
  },

  getTeachersCount: async () => {
    const response = await api.get(`${API_ENDPOINTS.TEACHER.GET_TEACHERS_COUNT}`);
    return response.data;
  },

  // Subject operations
  getSubjects: async () => {
    const response = await api.get(API_ENDPOINTS.ADMIN.GET_SUBJECTS);
    return response.data;
  },

  createSubject: async (data) => {
    const response = await api.post(API_ENDPOINTS.ADMIN.CREATE_SUBJECT, data);
    return response.data;
  },

  editSubject: async (subjectId, data) => {
    const response = await api.post(`${API_ENDPOINTS.ADMIN.EDIT_SUBJECT}/${subjectId}`, data);
    return response.data;
  },

  deleteSubject: async (subjectId) => {
    const response = await api.post(`${API_ENDPOINTS.ADMIN.DELETE_SUBJECT}/${subjectId}`);
    return response.data;
  },

  getSubjectsCount: async () => {
    const response = await api.get(`${API_ENDPOINTS.ADMIN.GET_SUBJECTS_COUNT}`);
    return response.data;
  },

  // Course operations
  getCourses: async () => {
    const response = await api.get(API_ENDPOINTS.ADMIN.GET_COURSES);
    return response.data;
  },

  createCourse: async (data) => {
    const response = await api.post(API_ENDPOINTS.ADMIN.CREATE_COURSE, data);
    return response.data;
  },

  editCourse: async (courseId, data) => {
    const response = await api.post(`${API_ENDPOINTS.ADMIN.EDIT_COURSE}/${courseId}`, data);
    return response.data;
  },

  deleteCourse: async (courseId) => {
    const response = await api.post(`${API_ENDPOINTS.ADMIN.DELETE_COURSE}/${courseId}`);
    return response.data;
  },

  getCoursesCount: async () => {
    const response = await api.get(`${API_ENDPOINTS.ADMIN.GET_COURSES_COUNT}`);
    return response.data;
  },

  // Assignment operations
  assignStudentToCourse: async (studentId, courseId) => {
    const response = await api.post(
      `${API_ENDPOINTS.ADMIN.ASSIGN_STUDENT_TO_COURSE}/${studentId}/course/${courseId}`
    );
    return response.data;
  },

  assignTeacherToSubjects: async (teacherId, subjectIds) => {
    const response = await api.post(
      `${API_ENDPOINTS.ADMIN.ASSIGN_TEACHER_TO_SUBJECTS}/${teacherId}/subjects`,
      { subjectIds }
    );
    return response.data;
  },

  assignSubjectsToCourse: async (courseId, subjectIds) => {
    const response = await api.post(
      `${API_ENDPOINTS.ADMIN.ASSIGN_SUBJECTS_TO_COURSE}/${courseId}/subjects`,
      { subjectIds }
    );
    return response.data;
  },
};
