// API Configuration
export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1';

// API Endpoints
export const API_ENDPOINTS = {
  AUTH: {
    SIGNIN: '/auth/signin',
    LOGIN: '/auth/login',
  },
  ADMIN: {
    CREATE_STUDENT: '/admin/create/student',
    EDIT_STUDENT: '/admin/edit/student',
    DELETE_STUDENT: '/admin/delete/student',
    CREATE_TEACHER: '/admin/create/teacher',
    EDIT_TEACHER: '/admin/edit/teacher',
    DELETE_TEACHER: '/admin/delete/teacher',
    CREATE_ADMIN: '/admin/create/admin',
    CREATE_SUBJECT: '/admin/create/subject',
    EDIT_SUBJECT: '/admin/edit/subject',
    DELETE_SUBJECT: '/admin/delete/subject',
    CREATE_COURSE: '/admin/create/course',
    EDIT_COURSE: '/admin/edit/course',
    DELETE_COURSE: '/admin/delete/course',
    ASSIGN_STUDENT_TO_COURSE: '/admin/assign/student',
    ASSIGN_TEACHER_TO_SUBJECTS: '/admin/assign/teacher',
    ASSIGN_SUBJECTS_TO_COURSE: '/admin/assign/course',
    GET_COURSES: '/admin/courses',
    GET_SUBJECTS: '/admin/subjects',
    GET_COURSES_COUNT: '/admin/courses/count',
    GET_SUBJECTS_COUNT: '/admin/subjects/count',
  },
  STUDENT: {
    GET_STUDENTS: '/student',
    GET_STUDENTS_COUNT: '/student/count',
    ATTENDANCE: '/student/attendance',
    MARKS: '/student/marks',
  },
  TEACHER: {
    GET_TEACHERS: '/teacher',
    GET_TEACHERS_COUNT: '/teacher/count',
    SUBJECTS: '/teacher',
    SUBJECT_STUDENTS: '/teacher',
    MARK_ATTENDANCE: '/teacher',
    MARK_MARKS: '/teacher',
  }
};

// Local Storage Keys
export const STORAGE_KEYS = {
  TOKEN: 'campusflow_token',
  USER: 'campusflow_user',
  ROLE: 'campusflow_role',
  USER_ID: 'campusflow_user_id',
};

// User Roles
export const ROLES = {
  ADMIN: 'ADMIN',
  TEACHER: 'TEACHER',
  STUDENT: 'STUDENT',
};

// Routes
export const ROUTES = {
  LOGIN: '/login',
  SIGNIN: '/signin',
  ADMIN: '/admin',
  TEACHER: '/teacher',
  STUDENT: '/student',
};
