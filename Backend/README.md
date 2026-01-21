## CampusFlow Backend

Spring Boot backend for **CampusFlow**, providing REST APIs, authentication/authorization, and persistence.

- **Language**: Java 17  
- **Framework**: Spring Boot, Spring Web MVC, Spring Security, Spring Data JPA  
- **Database**: PostgreSQL  
- **Auth**: JWT (JSON Web Token) with role‑based access (ADMIN / TEACHER / STUDENT)  
- **Build Tool**: Maven  
- **Container**: Docker (optional)

---

### 1. Project Structure (Backend)

Key packages:

- `com.arshdeep.campusflow`
  - `configuration/`
    - `WebSecurityConfig` – Spring Security, CORS
    - `ModelMapperConfig` – DTO mapping
  - `controller/`
    - `AuthController` – `/api/v1/auth` (sign‑up, login)
    - `AdminController` – `/api/v1/admin` (admin operations)
    - `TeacherController` – `/api/v1/teacher` (teacher operations)
    - `StudentController` – `/api/v1/student` (student operations)
  - `dto/`
    - `request/` – request payloads (sign‑up, create/edit student/teacher/course/subject, etc.)
    - `response/` – response payloads (login, counts, attendance, marks, subjects, students)
  - `entity/`
    - `User` – core auth user, implements `UserDetails` with `RoleType`
    - `Student`, `Teacher`, `Course`, `Subject`, `Attendance`, `Marks`
  - `repository/` – Spring Data JPA repositories
  - `security/`
    - `JwtFilter`, `AuthUtil`, `CustomUserDetailsService`
  - `service/`
    - `AuthService` – sign‑up + login
    - `AdminService` – all admin CRUD and assignments
    - `TeacherService` – teacher‑side actions (attendance, marks, subject students)
    - `StudentService` – student‑side aggregate data

---

### 2. Configuration

Backend configuration is in:

- `src/main/resources/application.properties`

#### 2.1 Recommended Local Setup

For local development, it’s safer to:

1. Run PostgreSQL locally (Docker example):
   ```bash
   docker run --name campusflow-db \
     -e POSTGRES_DB=campusflow \
     -e POSTGRES_USER=campusflow \
     -e POSTGRES_PASSWORD=campusflow \
     -p 5432:5432 \
     -d postgres:15
   ```

2. Configure `application.properties` for local DB:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/campusflow
   spring.datasource.username=campusflow
   spring.datasource.password=campusflow
   ```

3. Use a strong `jwt.secret` value (environment variable in production).

---

### 3. Run Backend Locally

From the `Backend` directory:

```bash
# Build
mvn clean package

# Run
java -jar target/campusflow-0.0.1-SNAPSHOT.jar
```

By default, the app runs on:

- `http://localhost:8080`

---

### 4. Authentication & Roles

#### 4.1 Roles

Defined in `RoleType`:

- `ADMIN`
- `TEACHER`
- `STUDENT`

#### 4.2 Sign‑Up (Student Only)

Sign‑up only creates **students**; teachers and admins are created by existing admins.

- **Endpoint**: `POST /api/v1/auth/signin`
- **Request body**:

```json
{
  "username": "student1",
  "password": "password123"
}
```

This:
- Creates a `User` with role `STUDENT`
- Creates a corresponding `Student` entity

#### 4.3 Login

- **Endpoint**: `POST /api/v1/auth/login`
- **Request body**:

```json
{
  "username": "student1",
  "password": "password123"
}
```

- **Response**:
  - `token` – JWT (used as `Authorization: Bearer <token>`)
  - `message`, `success`

The token encodes the role and userId, which the frontend decodes to route users and authorize requests.

---

### 5. Admin Features

All Admin endpoints are prefixed with `/api/v1/admin` and require `ROLE_ADMIN`.

Key capabilities:

- **Student Management**
  - Create/edit/delete students
  - Assign student to course
- **Teacher Management**
  - Create/edit/delete teachers
  - Assign teacher to subjects
- **Course & Subject Management**
  - Create/edit/delete courses
  - Create/edit/delete subjects
  - Assign subjects to courses
- **Counts / Dashboard**
  - Get total students, teachers, courses, subjects

Example endpoints (non‑exhaustive):

- `POST /api/v1/admin/create/student`
- `POST /api/v1/admin/edit/student`
- `POST /api/v1/admin/delete/student/{id}`
- `POST /api/v1/admin/create/teacher`
- `POST /api/v1/admin/delete/teacher/{id}`
- `POST /api/v1/admin/create/course`
- `POST /api/v1/admin/create/subject`
- `POST /api/v1/admin/assign/student/{studentId}/course/{courseId}`
- `POST /api/v1/admin/assign/teacher/{teacherId}/subjects`
- `POST /api/v1/admin/assign/course/{courseId}/subjects`
- `GET  /api/v1/admin/students/count`
- `GET  /api/v1/admin/teachers/count`
- `GET  /api/v1/admin/courses/count`
- `GET  /api/v1/admin/subjects/count`

#### 5.1 Admin Creation

Admins are created via:

- `POST /api/v1/admin/create/admin`

Request:

```json
{
  "username": "admin2",
  "password": "strongPassword!"
}
```

This creates a `User` with role `ADMIN`. Only existing admins can call this endpoint.

---

### 6. Teacher Features

Endpoints under `/api/v1/teacher` (requires `ROLE_TEACHER`):

- `GET  /api/v1/teacher/{teacherId}/subjects`
  - Returns subjects with associated course and students
- `GET  /api/v1/teacher/{teacherId}/subject/{subjectId}/students`
  - Returns students for a specific subject
- `POST /api/v1/teacher/{teacherId}/subject/{subjectId}/student/{studentId}/attendance`
  - Body: `{ "present": true }`
- `POST /api/v1/teacher/{teacherId}/subject/{subjectId}/student/{studentId}/marks`
  - Body: `{ "marks": 85 }`

---

### 7. Student Features

Endpoints under `/api/v1/student` (requires `ROLE_STUDENT`):

- `GET /api/v1/student/attendance/{studentId}`
  - Returns a list of `AttendanceResponse`
- `GET /api/v1/student/marks/{studentId}`
  - Returns a list of `MarksResponse`
- `GET /api/v1/student/count`
  - Student count (for admin dashboard)

DTOs:

- `AttendanceResponse` – id, present, subjectName, studentName, timestamps
- `MarksResponse` – id, marks, subjectName, studentName, timestamps

---

### 8. Building & Running with Docker

Dockerfile is located at `Backend/Dockerfile`:

```dockerfile
FROM amazoncorretto:17-alpine

WORKDIR /app

COPY target/campusflow-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
```

#### 8.1 Build JAR

```bash
cd Backend
mvn clean package
```

#### 8.2 Build Docker Image

```bash
docker build --platform linux/amd64 -t your-docker-username/campusflow-backend:latest .
```

#### 8.3 Run Container

```bash
docker run -p 8080:8080 --name campusflow-backend your-docker-username/campusflow-backend:latest
```

Ensure that your database is reachable from inside the container (update `spring.datasource.url` accordingly, often using a hostname like `host.docker.internal` for local DBs on Docker Desktop).

