
# 🧠 Employee Recommendation Engine

## 📌 Short Description

The **Employee Recommendation Engine** is a backend system that helps organizations automatically identify the most suitable employees for a project. It analyzes employee profiles—including **skills, experience, availability, and seniority**—and compares them with **project requirements**.

The system calculates a **suitability score**, ranks employees accordingly, and provides **clear explanations** for why they are recommended.

All features are exposed through **REST APIs**, making the system **scalable, modular, and easy to integrate** with other enterprise applications.

---

# 🚀 Key Features

### 1️⃣ Employee Management

* Add, update, delete, and view employee profiles.
* Store employee details such as **skills, experience, availability, and seniority**.

### 2️⃣ Project Management

* Create and manage project requirements.
* Define required **skills, proficiency levels, experience, and seniority**.

### 3️⃣ Employee Search & Filtering

* Search employees by **skills, experience, availability, or domain expertise**.

### 4️⃣ Smart Employee Recommendation

* Automatically identifies the most suitable employees for a project.

### 5️⃣ Suitability Score Calculation

* Calculates a score based on:

  * Skill match
  * Skill proficiency
  * Experience relevance
  * Availability

---

# 🛠 Technical Stack

| Technology            | Purpose                                                            |
| --------------------- | ------------------------------------------------------------------ |
| **Java 17**           | Core programming language used for backend development             |
| **Spring Boot**       | Framework used to build REST APIs and simplify backend development |
| **Maven**             | Build and dependency management tool                               |
| **MySQL**             | Relational database used to store application data                 |
| **Spring Data JPA**   | ORM framework used to map Java entities to database tables         |
| **Lombok**            | Reduces boilerplate code (getters, setters, constructors)          |
| **Swagger / OpenAPI** | API documentation and testing tool                                 |

---

# 👨‍💼 Employee Management APIs

| Method | Endpoint                       | Service Method             | Description                                               |
| ------ | ------------------------------ | -------------------------- | --------------------------------------------------------- |
| POST   | `/api/employees`               | createEmployee()           | Creates a new employee with skills and proficiency levels |
| GET    | `/api/employees`               | getAllEmployees()          | Retrieves all employees with their skills                 |
| GET    | `/api/employees/grouped`       | getEmployeeSkillsGrouped() | Fetches employee skills grouped by employee ID            |
| GET    | `/api/employees/{id}`          | getEmployeeById()          | Retrieves a specific employee by ID                       |
| PUT    | `/api/employees/{id}`          | updateEmployee()           | Updates employee details and skill list                   |
| DELETE | `/api/employees/{id}`          | deleteEmployee()           | Deletes an employee and related skills                    |
| GET    | `/api/employees/search/{name}` | searchEmployeeByName()     | Searches employees by first name or surname               |

---

# 🧠 Recommendation Engine APIs

| Method | Endpoint                       | Controller Method  | Purpose                     | Description                                                |
| ------ | ------------------------------ | ------------------ | --------------------------- | ---------------------------------------------------------- |
| POST   | `/v2/add/emp`                  | addEmpSkills       | Add Employee                | Creates a new employee with skills and proficiency levels  |
| POST   | `/v2/add/project`              | addProjectSkills   | Add Project                 | Creates a project with required skills                     |
| POST   | `/v2/add/requirement/{id}`     | addRecommendation  | Generate Recommendation     | Runs evaluation algorithm and stores recommendation scores |
| POST   | `/v2/add/skill`                | addSkill           | Add Skill                   | Adds a new skill to the system                             |
| GET    | `/v2/get/recommendations/{id}` | getRecommendations | Get Project Recommendations | Returns ranked employee recommendations                    |

---

# 📂 Project Management APIs

| Method | Endpoint             | API Name          | Description                                | Request Body       | Response         |
| ------ | -------------------- | ----------------- | ------------------------------------------ | ------------------ | ---------------- |
| POST   | `/api/projects`      | Create Project    | Creates a new project with required skills | ProjectRequest     | Created Project  |
| GET    | `/api/projects`      | Get All Projects  | Retrieves all projects                     | None               | List of Projects |
| GET    | `/api/projects/{id}` | Get Project By ID | Retrieves a specific project               | Path Variable `id` | Project Object   |
| PUT    | `/api/projects/{id}` | Update Project    | Updates project details                    | ProjectRequest     | Updated Project  |
| DELETE | `/api/projects/{id}` | Delete Project    | Deletes a project                          | Path Variable `id` | Success Message  |

---

# 🗄 Database Tables

## Employees

| Column       | Type    | Key | Description           |
| ------------ | ------- | --- | --------------------- |
| id           | BIGINT  | PK  | Unique employee ID    |
| name         | VARCHAR |     | Employee first name   |
| surname      | VARCHAR |     | Employee last name    |
| email        | VARCHAR |     | Employee email        |
| phone        | BIGINT  |     | Contact number        |
| experience   | BIGINT  |     | Years of experience   |
| availability | BOOLEAN |     | Employee availability |
| seniority    | VARCHAR |     | Seniority level       |

---

## Skills

| Column | Type    | Key | Description |
| ------ | ------- | --- | ----------- |
| id     | BIGINT  | PK  | Skill ID    |
| name   | VARCHAR |     | Skill name  |

---

## EmployeeSkill

| Column      | Type    | Key | Description              |
| ----------- | ------- | --- | ------------------------ |
| id          | BIGINT  | PK  | Unique employee skill ID |
| employee_id | BIGINT  | FK  | Reference to Employees   |
| skill_id    | BIGINT  | FK  | Reference to Skills      |
| proficiency | VARCHAR |     | Skill proficiency        |

---

## Project

| Column              | Type      | Key | Description           |
| ------------------- | --------- | --- | --------------------- |
| id                  | BIGINT    | PK  | Project ID            |
| project_name        | VARCHAR   |     | Project name          |
| project_description | VARCHAR   |     | Description           |
| experience          | BIGINT    |     | Required experience   |
| availability        | BOOLEAN   |     | Required availability |
| seniority           | VARCHAR   |     | Required seniority    |
| creation_date       | TIMESTAMP |     | Created date          |
| update_date         | TIMESTAMP |     | Last updated date     |

---

## ProjectSkill

| Column               | Type    | Key | Description          |
| -------------------- | ------- | --- | -------------------- |
| id                   | BIGINT  | PK  | Project skill ID     |
| project_id           | BIGINT  | FK  | Reference to Project |
| skill_id             | BIGINT  | FK  | Reference to Skills  |
| required_proficiency | VARCHAR |     | Required proficiency |

---

## ProjectRecommendation

| Column      | Type   | Key | Description        |
| ----------- | ------ | --- | ------------------ |
| id          | BIGINT | PK  | Recommendation ID  |
| employee_id | BIGINT | FK  | Employee reference |
| project_id  | BIGINT | FK  | Project reference  |
| score       | BIGINT |     | Suitability score  |

---

# 📂 Project Structure

```
src
└── main
    └── java
        └── com
            └── example
                └── demo
                    ├── Controllers
                    │   ├── EmployeeController.java
                    │   ├── FrontController.java
                    │   ├── ProjectController.java
                    │   └── SkillsController.java
                    │
                    ├── DTOs
                    │   ├── DisplayEmployee.java
                    │   ├── EmployeeRequest.java
                    │   ├── EMPRecommendDTO.java
                    │   ├── ProjectRequest.java
                    │   ├── ProjectSkillProficiency.java
                    │   └── SkillProficiency.java
                    │
                    ├── Models
                    │   ├── Employees.java
                    │   ├── EmployeeSkill.java
                    │   ├── Project.java
                    │   ├── ProjectRecommendation.java
                    │   ├── ProjectSkill.java
                    │   └── Skills.java
                    │
                    ├── Repository
                    │   ├── EmployeeRepo.java
                    │   ├── EmployeeSkillRepo.java
                    │   ├── ProjectRepo.java
                    │   ├── ProjectRecommendationRepo.java
                    │   ├── ProjectSkillRequRepo.java
                    │   └── SkillsRepo.java
                    │
                    ├── Services
                    │   ├── EmployeeService.java
                    │   ├── EvaluationService.java
                    │   ├── FrontService.java
                    │   └── ProjectService.java
                    │
                    └── EmployeeRecommendationApplication.java
```

---

# 🧠 Recommendation Criteria

| Factor            | Max Score | Description                                       |
| ----------------- | --------- | ------------------------------------------------- |
| Skill Match       | 50        | Employee has required project skills              |
| Skill Proficiency | 20        | Compares employee skill level with required level |
| Experience        | 20        | Employee experience vs project requirement        |
| Availability      | 10        | Employee availability                             |

Total Score = **100**

---

# 📊 Proficiency Evaluation

| Condition           | Score |
| ------------------- | ----- |
| Exact Match         | 100%  |
| Employee > Required | 80%   |
| Slightly Lower      | 50%   |
| Much Lower          | 30%   |

---

# ⚙ Setup Instructions

## 1️⃣ Clone Repository

```bash
git clone https://github.com/swaraj39/employee-recommendation-engine.git
cd employee-recommendation-engine
```

---

## 2️⃣ Configure Database

Edit **application.properties**

```
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=root
spring.datasource.password=password
```

---

## 3️⃣ Run Application

```
mvn spring-boot:run
```

or run

```
EmployeeRecommendationApplication.java
```

from your IDE.

