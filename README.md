**English** | [Italiano](README.it.md)

# 🎬 Adastra Cinema — Back-End

Back-end of the **Adastra Cinema** web application, a management system for a cinema chain: it exposes the APIs used by the front-end to browse cinemas, films and showtimes, book seats, buy tickets and manage users.

> 🔗 Front-End Repository: [FE-AdAdstra-Capstone-EPICODE](https://github.com/giannibussoletti/FE-AdAdstra-Capstone-EPICODE)

---

## 🛠️ Tech Stack

- **Java**
- **Spring (Spring Boot)**
- **PostgreSQL** (database)
- **Maven** (dependency management, integrated in IntelliJ)
- **IntelliJ IDEA** (recommended IDE for running the server locally)
- **Cloudinary** for image storage

---

## 📋 Prerequisites

| Tool               | Notes                                            | Download                                                       |
| ------------------ | ------------------------------------------------ | -------------------------------------------------------------- |
| JDK                | version 17 or higher (compatible with Spring Boot 3) | [Eclipse Temurin](https://adoptium.net/)                   |
| IntelliJ IDEA      | Community Edition is enough                      | [jetbrains.com/idea](https://www.jetbrains.com/idea/download/) |
| PostgreSQL         | to create the database                           | [postgresql.org](https://www.postgresql.org/download/)         |
| Cloudinary account | to manage images (e.g. user avatars)             | [cloudinary.com](https://cloudinary.com/)                      |

> ℹ️ **What is Cloudinary?** It's a cloud service that handles storage, optimization and delivery of images and other media files. Adastra Cinema uses it so that images are kept on a dedicated external storage instead of being saved directly on the server.

> Maven doesn't require a separate installation: IntelliJ IDEA manages it automatically and downloads the required packages when the project is opened.

---

## ⚙️ Environment Variables

Before starting the project, the following variables must be configured manually (for example in the `application.properties`/`application.yml` file or in the environment variables of the IntelliJ run configuration):

```
PORT=5555
DB_URL=
DB_USERNAME=
DB_PASSWORD=
JWT_SECRET=

CLOUDINARY_API_KEY=
CLOUDINARY_SECRET=
CLOUDINARY_NAME=
```

The `CLOUDINARY_*` values are obtained by creating a free account on [Cloudinary](https://cloudinary.com/) and copying them from the dashboard.

---

## 🚀 Installation and Startup

1. **Clone the repository**

   ```bash
   git clone https://github.com/giannibussoletti/BE-AdAdstra-Capstone-EPICODE.git
   ```

2. **Create the database** in PostgreSQL that will be referenced in `DB_URL`.

3. **Open the project in IntelliJ IDEA**: Maven will automatically download the required dependencies.

4. **Configure the environment variables** listed above (through an `env.properties` file saved in the project root).

5. **Start the application** by running the main class of the Spring Boot project.

6. The server will be available at `http://localhost:PORT` (`5555` by default, depending on the value you set).

> 🔗 To use the full application, also start the [front-end](https://github.com/giannibussoletti/FE-AdAdstra-Capstone-EPICODE).

> P.S. The project already includes a `data.sql` file that creates the data needed to explore the project on startup.

---

## 🔐 Authentication and Roles

Authentication is handled through **JWT**, sent in requests as the `Authorization: Bearer <token>` header.

There are three user roles:

| Role           | Description                                                             |
| -------------- | ----------------------------------------------------------------------- |
| **USER**       | Regular customer of the website                                         |
| **ADMIN**      | Site manager, responsible for adding showtimes                          |
| **SUPERADMIN** | General data management: creating cities, cinemas and film information |

---

## 📡 API Endpoints

### Public (no authentication required)

| Method | Endpoint                  | Description                                                                   |
| ------ | ------------------------- | ----------------------------------------------------------------------------- |
| GET    | `/public/cinemas`         | List of cinemas                                                               |
| GET    | `/public/screening-times` | List of showtimes                                                             |
| GET    | `/public/seats`           | List of seats used to build the theater map                                   |
| GET    | `/public/tickets`         | List of already booked tickets (by showtime id), without sensitive attributes |
| POST   | `/public/bookings`        | Save a booking without an account                                             |

### User Management and Authentication

| Method | Endpoint                  | Description                                          |
| ------ | ------------------------- | ---------------------------------------------------- |
| POST   | `/auth/registration`      | User sign-up                                         |
| POST   | `/auth/login`             | User login                                           |
| GET    | `/auth/token`             | Token check when the user opens a new session        |
| POST   | `/bookings`               | Save bookings for a logged-in account                |
| GET    | `/tickets/user-movies`    | List of films watched by the user, shown on the profile |
| PATCH  | `/user/profile/password`  | Update password                                      |
| PATCH  | `/user/profile/new-email` | Update email                                         |
| PATCH  | `/user/profile/avatar`    | Update avatar                                        |

---

## 🗺️ Project Roadmap

- ✅ Creating PDF tickets and sending them by email (release [tag 0.3.0](https://github.com/giannibussoletti/BE-AdAdstra-Capstone-EPICODE/releases/tag/0.3.0))
- ❌ Advance ticket sales for upcoming films
- ❌ Seat map management for seats reserved for people with disabilities
- ❌ Ticket validation check, to mark a ticket as used and prevent it from being reused
- ❌ Creation and management of administrative endpoints
- ❌ Loyalty card management for registered users

---

## 🔗 Useful Links

- [Front-End Repository](https://github.com/giannibussoletti/FE-AdAdstra-Capstone-EPICODE)
- [Download JDK (Eclipse Temurin)](https://adoptium.net/)
- [Download IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- [Download PostgreSQL](https://www.postgresql.org/download/)
- [Cloudinary](https://cloudinary.com/)
