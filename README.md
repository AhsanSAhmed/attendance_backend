# 📝 Attendance Management System (Backend)

A role-based attendance management backend built using Java, Spring Boot, and MySQL. Designed for schools or institutions to track student attendance with admin and teacher roles.

---

## 🚀 Features

- 👥 **User Roles**: Admins and Teachers
- 🔐 **Authentication**:
  - Username-password login
  - **Passwords are securely hashed before storage**
  - (JWT authentication planned)
- 👤 **API capabilites**:
  - Create user accounts (admin or teacher - a default admin account is created if none exist)
  - Create grades (e.g., Grade 1, Grade 2)
  - Add/remove students to/from specific grades
  - Delete grades and students
  - View all students by grade
  - Mark attendance in batches or singularly
  - Fetch attendance records by date
  - Fetch attendance records by grade
  - get a summary of absences/presence/total days of a grade
