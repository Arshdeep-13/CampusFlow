## CampusFlow

CampusFlow is a full‑stack campus management system built with:

- **Backend**: Spring Boot, PostgreSQL  
- **Frontend**: React (Vite), Ant Design, Tailwind CSS

The project is split into two independent applications:

- `Backend/` – Spring Boot REST API, security, persistence, Docker image  
- `Frontend/` – React, routing, role‑based dashboards, API integration

This root `README` gives a high‑level overview and points you to the detailed docs for each part.

---

### Backend (Spring Boot)

All backend documentation (setup, configuration, running locally, Docker, deployment) is in:

- `Backend/README.md`

It covers:

- Project structure and main modules (auth, admin, teacher, student)
- Environment configuration (PostgreSQL, JWT secret, ports)
- How to run with Maven / Docker
- Role model (ADMIN / TEACHER / STUDENT) and signup/login flow
- Key REST endpoints

---

### Frontend (React + Ant Design + Tailwind)

All frontend documentation (setup, scripts, environment variables, routing, role‑based UI) is in:

- `Frontend/README.md`

It covers:

- Project structure (pages, components, services, hooks, utils)
- How to run the Vite dev server and build for production
- Auth flow (login/sign‑up), route protection, and dashboards for each role
- How the frontend talks to the backend API

