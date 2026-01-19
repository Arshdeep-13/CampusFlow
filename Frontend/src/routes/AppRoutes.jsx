import { Routes, Route, Navigate } from 'react-router-dom';
import ProtectedRoute from '../components/auth/ProtectedRoute';
import Layout from '../components/layout/Layout';
import LoginPage from '../pages/auth/LoginPage';
import SignInPage from '../pages/auth/SignInPage';
import AdminDashboard from '../pages/admin/AdminDashboard';
import ManageStudents from '../pages/admin/ManageStudents';
import ManageTeachers from '../pages/admin/ManageTeachers';
import ManageCourses from '../pages/admin/ManageCourses';
import ManageSubjects from '../pages/admin/ManageSubjects';
import AssignStudentToCourse from '../pages/admin/AssignStudentToCourse';
import AssignTeacherToSubjects from '../pages/admin/AssignTeacherToSubjects';
import AssignSubjectsToCourse from '../pages/admin/AssignSubjectsToCourse';
import TeacherDashboard from '../pages/teacher/TeacherDashboard';
import StudentDashboard from '../pages/student/StudentDashboard';
import { ROUTES, ROLES } from '../utils/constants';

const AppRoutes = () => {
  return (
    <Routes>
      {/* Public Routes */}
      <Route path="/login" element={<LoginPage />} />
      <Route path="/signin" element={<SignInPage />} />
      
      {/* Protected Admin Routes */}
      <Route
        path="/admin"
        element={
          <ProtectedRoute allowedRoles={[ROLES.ADMIN]}>
            <Layout />
          </ProtectedRoute>
        }
      >
        <Route index element={<AdminDashboard />} />
        <Route path="students" element={<ManageStudents />} />
        <Route path="teachers" element={<ManageTeachers />} />
        <Route path="courses" element={<ManageCourses />} />
        <Route path="subjects" element={<ManageSubjects />} />
        <Route path="assign/student-course" element={<AssignStudentToCourse />} />
        <Route path="assign/teacher-subject" element={<AssignTeacherToSubjects />} />
        <Route path="assign/subjects-course" element={<AssignSubjectsToCourse />} />
      </Route>

      {/* Protected Teacher Routes */}
      <Route
        path="/teacher"
        element={
          <ProtectedRoute allowedRoles={[ROLES.TEACHER]}>
            <Layout />
          </ProtectedRoute>
        }
      >
        <Route index element={<TeacherDashboard />} />
      </Route>

      {/* Protected Student Routes */}
      <Route
        path="/student"
        element={
          <ProtectedRoute allowedRoles={[ROLES.STUDENT]}>
            <Layout />
          </ProtectedRoute>
        }
      >
        <Route index element={<StudentDashboard />} />
      </Route>

      {/* Default redirect */}
      <Route path="/" element={<Navigate to="/login" replace />} />
      <Route path="*" element={<Navigate to="/login" replace />} />
    </Routes>
  );
};

export default AppRoutes;
