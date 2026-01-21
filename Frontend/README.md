## CampusFlow Frontend

React frontend for **CampusFlow**, providing role‑based dashboards and UI for admins, teachers, and students.

- **Framework**: React 18 (Vite)  
- **UI**: Ant Design, Tailwind CSS  
- **Routing**: `react-router-dom`  
- **HTTP**: Axios  
- **Auth**: JWT stored in `localStorage`, decoded on the client

The frontend consumes the Spring Boot backend APIs documented in `../Backend/README.md`.

---

### 1. Project Structure (Frontend)

Top‑level:

- `src/`
  - `pages/`
    - `auth/`
      - `LoginPage.jsx`
      - `SignInPage.jsx`
    - `admin/`
      - `AdminDashboard.jsx`
      - `ManageStudents.jsx`
      - `ManageTeachers.jsx`
      - `ManageCourses.jsx`
      - `ManageSubjects.jsx`
      - `AssignStudentToCourse.jsx`
      - `AssignTeacherToSubjects.jsx`
      - `AssignSubjectsToCourse.jsx`
    - `teacher/`
      - `TeacherDashboard.jsx`
    - `student/`
      - `StudentDashboard.jsx`
  - `components/`
    - `layout/`
      - `Layout.jsx` – main shell with `Header` + `Sidebar`
      - `Header.jsx`
      - `Sidebar.jsx`
    - `auth/`
      - `ProtectedRoute.jsx` – route guard based on role
  - `routes/`
    - `AppRoutes.jsx` – route definitions
  - `services/`
    - `api.js` – Axios instance with interceptors
    - `authService.js` – login/sign‑up + token storage
    - `adminService.js` – admin APIs
    - `teacherService.js` – teacher APIs
    - `studentService.js` – student APIs
  - `hooks/`
    - `useAuth.js` – auth state (role, userId, isAuthenticated)
  - `utils/`
    - `constants.js` – API endpoints, roles, routes, storage keys
    - `jwt.js` – simple JWT decode helpers
    - `helpers.js` – misc helpers (attendance percentage, etc.)
  - `index.css` – Tailwind + global styles
  - `App.jsx` – Ant Design `ConfigProvider`, `BrowserRouter`, `AppRoutes`

---

### 2. Environment & Configuration

Frontend talks to the backend via the base URL:

- Defined in `src/utils/constants.js`:

```js
export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1';
```

#### 2.1 Environment Variable

Create `.env` in `Frontend`:

```env
VITE_API_BASE_URL=http://localhost:8080/api/v1
```

---

### 3. Install & Run (Development)

From the `Frontend` directory:

```bash
cd Frontend

# Install dependencies
npm install

# Start dev server
npm run dev
```

Default Vite dev server:

- `http://localhost:5173`

Make sure the backend is running (usually `http://localhost:8080`) and CORS is configured correctly (already wired in the backend).

---

### 4. Build for Production

From `Frontend`:

```bash
npm run build
```

This generates static assets in:

- `dist/`

To preview the production build locally:

```bash
npm run preview
```

You can serve `dist/` via any static file server or reverse proxy (Nginx, etc.).

---

### 5. Authentication Flow

#### 5.1 Login

1. User opens `/login`
2. `LoginPage` posts credentials to:
   - `POST /api/v1/auth/login`
3. Backend returns a JWT:
   - `authService.login` stores:
     - `campusflow_token`
     - `campusflow_role`
     - `campusflow_user_id`
   - Role and userId are extracted from token using `jwt.js`
4. `useAuth` hook reads from `localStorage` and exposes:
   - `isAuthenticated`
   - `userRole` (`ADMIN`, `TEACHER`, `STUDENT`)
   - `userId`
5. `LoginPage` decodes role and navigates:
   - `ADMIN`   → `/admin`
   - `TEACHER` → `/teacher`
   - `STUDENT` → `/student`

#### 5.2 Sign‑Up (Student Only)

1. User opens `/signin`
2. `SignInPage` posts to:
   - `POST /api/v1/auth/signin`
3. Backend always creates a **STUDENT** user (role is not selectable on frontend)
4. On success, user is redirected to `/login`

Admins and teachers are created by an existing admin via the Admin dashboard (see backend README).

#### 5.3 Protected Routes

- `ProtectedRoute` wraps routes under `/admin`, `/teacher`, `/student`
- Checks `useAuth().userRole` against allowed roles
- Redirects to `/login` if not authenticated or if role mismatch

Example (`AppRoutes.jsx`):

- `/admin` – allowed roles: `ADMIN`
- `/teacher` – allowed roles: `TEACHER`
- `/student` – allowed roles: `STUDENT`

---

### 6. Layout & Navigation

#### 6.1 Layout

- `Layout.jsx` uses Ant Design `Layout` with:
  - `Header` – shows current role + logout button
  - `Sidebar` – role‑based navigation menu
  - Main content – React Router `Outlet`

#### 6.2 Sidebar

- Items depend on role:
  - **Admin**
    - Dashboard
    - Students, Teachers, Courses, Subjects
    - Assignments:
      - Student → Course
      - Teacher → Subjects
      - Subjects → Course
  - **Teacher**
    - Dashboard
  - **Student**
    - Dashboard

Clicking items navigates using `react-router-dom` and syncs `selectedKeys` with `location.pathname`.

---

### 7. Pages Overview

#### 7.1 Admin

- `AdminDashboard.jsx`
  - Displays counts for:
    - Students, Teachers, Courses, Subjects
  - Quick actions:
    - Assign Student → Course
    - Assign Teacher → Subjects
    - Assign Subjects → Course
    - Create Admin
  - Uses `adminService` for count endpoints.

- `ManageStudents.jsx`
  - List of students with Ant Design `Table`
  - Create/edit/delete via modal forms

- `ManageTeachers.jsx`
  - Similar to ManageStudents (for teachers)

- `ManageCourses.jsx`
  - Manage courses (name, code)

- `ManageSubjects.jsx`
  - Manage subjects (name, course, teacher)

- `AssignStudentToCourse.jsx`
  - Select student + course, calls:
    - `POST /api/v1/admin/assign/student/{studentId}/course/{courseId}`

- `AssignTeacherToSubjects.jsx`
  - Select teacher + multiple subjects, calls:
    - `POST /api/v1/admin/assign/teacher/{teacherId}/subjects`

- `AssignSubjectsToCourse.jsx`
  - Select course + multiple subjects, calls:
    - `POST /api/v1/admin/assign/course/{courseId}/subjects`

#### 7.2 Teacher

- `TeacherDashboard.jsx`
  - Statistics:
    - Number of subjects
    - Students count (for selected subject)
    - Courses taught
  - "My Subjects" table:
    - Lists teacher’s subjects with course info
    - Button to view students per subject
  - "Students" table for selected subject:
    - Actions:
      - Mark Attendance (modal → calls teacher attendance endpoint)
      - Enter Marks (modal → calls marks endpoint)

#### 7.3 Student

- `StudentDashboard.jsx`
  - Statistics:
    - Attendance percentage (calculated in `helpers.js`)
    - Subjects count (from attendance/marks)
    - Average marks
    - Present days vs total
  - Warning banner if attendance < 75%
  - "My Attendance" table:
    - Shows subject, date, status (Present/Absent)
  - "My Marks" table:
    - Shows subject, marks, date

---

### 8. Services & API Calls

All API calls share an Axios instance in `services/api.js`:

- Base URL from `API_BASE_URL`
- Attaches `Authorization: Bearer <token>` header if token is present
- Handles 401/403 errors to redirect to login as needed (if implemented)

#### 8.1 Auth Service

- `authService.signIn(signInData)` → `POST /auth/signin`
- `authService.login(loginData)` → `POST /auth/login`
  - Stores token, role, userId in `localStorage`
- `authService.logout()` → clears stored auth data
- `authService.getToken()`, `getRole()`, `getUserId()`

#### 8.2 Admin Service

Wraps all admin endpoints:

- Students: list/create/edit/delete/count
- Teachers: list/create/edit/delete/count
- Courses: list/create/edit/delete/count
- Subjects: list/create/edit/delete/count
- Assignments:
  - Student → Course
  - Teacher → Subjects
  - Subjects → Course
- `createAdmin` – create new admin user

#### 8.3 Teacher & Student Services

- `teacherService` – subjects, subject students, mark attendance, mark marks
- `studentService` – attendance + marks for a given student

---

### 9. Styling & UI

- **Ant Design** for components (`Table`, `Card`, `Form`, `Input`, `Button`, `Modal`, `Statistic`, etc.)
- **Tailwind CSS** for utility classes:
  - Layout spacing (`p-4`, `mb-6`, `flex`, `justify-center`, etc.)
  - Typography and colors (as needed)
- `index.css` imports Tailwind base, components, utilities.



