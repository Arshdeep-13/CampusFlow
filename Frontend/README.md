# CampusFlow Frontend

Frontend application for CampusFlow - A comprehensive campus management system.

## Features

### 🔐 Authentication
- Login page for existing users
- Sign up page for new users
- JWT token-based authentication
- Role-based access control

### 👨‍💼 Admin Dashboard
- **Manage Students**: Create, Edit, Delete students
- **Manage Teachers**: Create, Edit, Delete teachers
- **Manage Courses**: Create, Edit, Delete courses
- **Manage Subjects**: Create, Edit, Delete subjects
- **Assignments**:
  - Assign students to courses
  - Assign teachers to subjects

### 👨‍🏫 Teacher Dashboard
- View assigned subjects
- View students for each subject
- Mark student attendance
- Enter student marks

### 👨‍🎓 Student Dashboard
- View own attendance records
- View own marks
- Attendance percentage calculation
- Exam eligibility check (75% attendance required)

## Tech Stack

- **React 19** - UI library
- **Vite** - Build tool and dev server
- **Tailwind CSS v4** - Utility-first CSS framework
- **Ant Design** - React UI component library
- **React Router** - Client-side routing
- **Axios** - HTTP client

## Project Structure

```
Frontend/
├── src/
│   ├── components/
│   │   ├── auth/
│   │   │   └── ProtectedRoute.jsx
│   │   └── layout/
│   │       ├── Header.jsx
│   │       ├── Layout.jsx
│   │       └── Sidebar.jsx
│   ├── pages/
│   │   ├── admin/
│   │   │   ├── AdminDashboard.jsx
│   │   │   ├── ManageStudents.jsx
│   │   │   ├── ManageTeachers.jsx
│   │   │   ├── ManageCourses.jsx
│   │   │   ├── ManageSubjects.jsx
│   │   │   ├── AssignStudentToCourse.jsx
│   │   │   └── AssignTeacherToSubjects.jsx
│   │   ├── auth/
│   │   │   ├── LoginPage.jsx
│   │   │   └── SignInPage.jsx
│   │   ├── teacher/
│   │   │   └── TeacherDashboard.jsx
│   │   └── student/
│   │       └── StudentDashboard.jsx
│   ├── routes/
│   │   └── AppRoutes.jsx
│   ├── services/
│   │   ├── api.js
│   │   ├── authService.js
│   │   ├── adminService.js
│   │   ├── teacherService.js
│   │   └── studentService.js
│   ├── hooks/
│   │   └── useAuth.js
│   ├── utils/
│   │   ├── constants.js
│   │   ├── helpers.js
│   │   └── jwt.js
│   ├── App.jsx
│   └── main.jsx
```

## Setup

1. **Install dependencies:**
```bash
cd Frontend
npm install
```

2. **Create a `.env` file:**
```bash
VITE_API_BASE_URL=http://localhost:8080/api/v1
```

3. **Start the development server:**
```bash
npm run dev
```

4. **Build for production:**
```bash
npm run build
```

## API Integration

The frontend communicates with the backend API at `http://localhost:8080/api/v1`. All API calls are handled through service files in `src/services/`.

### Authentication Flow
1. User signs up or logs in
2. JWT token is received and stored in localStorage
3. Token is decoded to extract role and userId
4. User is redirected to their role-specific dashboard

### Protected Routes
- Routes are protected based on user roles (ADMIN, TEACHER, STUDENT)
- Unauthorized access attempts redirect to login
- Token expiration is handled automatically

## Role-Based Access

### Admin
- Full access to all CRUD operations
- Can manage students, teachers, courses, and subjects
- Can assign students to courses and teachers to subjects

### Teacher
- Can view only assigned subjects
- Can view students of assigned subjects
- Can mark attendance and enter marks

### Student
- Can view only own attendance and marks
- Cannot access other students' data
- Sees exam eligibility status

## Business Rules Implemented

1. **Attendance Eligibility**: Students with < 75% attendance see "Not Eligible for Exam" warning
2. **Role-based Access**: Each role can only access their designated screens
3. **Teacher Subject Access**: Teachers can only see and manage their assigned subjects

## Notes

- JWT tokens are decoded client-side to extract role and userId
- All API calls include the JWT token in the Authorization header
- Token expiration is handled by axios interceptors
- The application uses Ant Design components for consistent UI
- Tailwind CSS is used for additional styling and layout
