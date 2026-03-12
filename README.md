### Short Description

The **Employee Recommendation Engine** is a backend system that helps organizations automatically find the most suitable employees for a project. It analyzes employee profiles (skills, experience, availability, and seniority) and compares them with project requirements. The system then calculates a **suitability score**, ranks employees, and provides **clear explanations** for why they are recommended. All functionalities are available through **REST APIs**, making the system scalable and easy to integrate with other applications.

---

### Key Features (Simple)

1. **Employee Management**

   * Add, update, delete, and view employee profiles.
   * Store details like skills, experience, availability, and seniority.

2. **Project Management**

   * Create and manage project requirements.
   * Define required skills, proficiency levels, timelines, and priority.

3. **Employee Search & Filtering**

   * Search employees by skills, experience, availability, or domain expertise.

4. **Smart Employee Recommendation**

   * Automatically finds the best employees for a project.

5. **Suitability Score Calculation**

   * Calculates a score based on skill match, experience relevance, and availability.

## 🛠 Technical Stack

| Technology | Purpose |
|------------|---------|
| Java 17 | Core programming language used to implement the backend logic of the application. |
| Spring Boot | Framework used to build REST APIs and simplify backend application development. |
| Maven | Build and dependency management tool used to manage project libraries and build lifecycle. |
| MySQL | Relational database used to store employee data, project requirements, skills, and recommendations. |
| JPA (Spring Data JPA) | ORM framework used to map Java entities to database tables and handle database operations. |
| Lombok | Library used to reduce boilerplate code by automatically generating getters, setters, constructors, and builders. |
| Swagger (OpenAPI) | API documentation tool used to visualize, test, and document REST endpoints. |

## 👨‍💼 Employee Management APIs

| Method | Endpoint | Service Method | Description |
|-------|---------|---------------|-------------|
| POST | /api/employees | createEmployee() | Creates a new employee along with their associated skills and proficiency levels. |
| GET | /api/employees | getAllEmployees() | Retrieves all employees along with their skill records. |
| GET | /api/employees/grouped | getEmployeeSkillsGrouped() | Fetches all employee skills grouped by employee ID. |
| GET | /api/employees/{id} | getEmployeeById() | Retrieves details of a specific employee including their associated skills. |
| PUT | /api/employees/{id} | updateEmployee() | Updates an employee’s details and replaces existing skills with the new skill list. |
| DELETE | /api/employees/{id} | deleteEmployee() | Deletes an employee and all associated skill records. |
| GET | /api/employees/search/{name} | searchEmployeeByName() | Searches employees by matching first name or surname. |

## 🧠 Recommendation Engine APIs

| Method | Endpoint | Controller Method | Purpose | Description |
|-------|---------|------------------|--------|-------------|
| POST | /v2/add/emp | addEmpSkills | Add Employee | Creates a new employee and stores their personal details along with their skills and proficiency levels. |
| POST | /v2/add/project | addProjectSkills | Add Project | Creates a new project and stores required skills and proficiency levels needed for the project. |
| POST | /v2/add/requirement/{id} | addRecommendation | Generate Recommendation | Runs the evaluation algorithm to match employees with the specified project and stores recommendation scores. |
| POST | /v2/add/skill | addSkill | Add Skill | Adds a new skill to the system if it does not already exist in the skills database. |
| GET | /v2/get/recommendations/{id} | getRecommendations | Get Project Recommendations | Returns a ranked list of employees recommended for the given project based on score, experience, and skill matching. |

## 📂 Project Management APIs

| Method | Endpoint | API Name | Description | Request Body | Response |
|-------|---------|---------|-------------|-------------|----------|
| POST | /api/projects | Create Project | Creates a new project and assigns required skills with proficiency levels. | ProjectRequest | Created Project object |
| GET | /api/projects | Get All Projects | Retrieves a list of all projects available in the system. | None | List of Project |
| GET | /api/projects/{id} | Get Project By ID | Fetches details of a specific project using its unique ID. | Path variable id | Project object |
| PUT | /api/projects/{id} | Update Project | Updates project details and replaces its required skills with new ones. | ProjectRequest | Updated Project |
| DELETE | /api/projects/{id} | Delete Project | Deletes a project from the system using its ID. | Path variable id | Success message |

##🗄 Database Tables
## 👨‍💼 Employees Table

| Column | Type | Key | Description |
|-------|------|-----|-------------|
| id | BIGINT | PK | Unique identifier for employee |
| name | VARCHAR | | Employee first name |
| surname | VARCHAR | | Employee last name |
| email | VARCHAR | | Employee email address |
| phone | BIGINT | | Employee contact number |
| experience | BIGINT | | Years of experience |
| avaibility | BOOLEAN | | Employee availability for projects |
| seniority | VARCHAR | | Seniority level (Junior / Mid / Senior) |

## 🧠 Skills Table

| Column | Type | Key | Description |
|-------|------|-----|-------------|
| id | BIGINT | PK | Unique identifier for skill |
| name | VARCHAR | | Name of the skill |

## 👨‍💻 EmployeeSkill Table

| Column | Type | Key | Description |
|-------|------|-----|-------------|
| id | BIGINT | PK | Unique identifier for employee skill |
| employee_id | BIGINT | FK → Employees(id) | Reference to employee |
| skill_id | BIGINT | FK → Skills(id) | Reference to skill |
| proficiency | VARCHAR | | Employee proficiency level |

## 📂 Project Table

| Column | Type | Key | Description |
|-------|------|-----|-------------|
| id | BIGINT | PK | Unique identifier for project |
| project_name | VARCHAR | | Name of the project |
| project_description | VARCHAR | | Description of the project |
| experience | BIGINT | | Required experience |
| avaibility | BOOLEAN | | Required employee availability |
| seniority | VARCHAR | | Required seniority level |
| creation_date | TIMESTAMP | | Project creation date |
| update_date | TIMESTAMP | | Last updated date |

## 🧩 ProjectSkill Table

| Column | Type | Key | Description |
|-------|------|-----|-------------|
| id | BIGINT | PK | Unique identifier for project skill |
| project_id | BIGINT | FK → Project(id) | Reference to project |
| skill_id | BIGINT | FK → Skills(id) | Reference to skill |
| required_proficiency | VARCHAR | | Required proficiency level |

## ⭐ ProjectRecommendation Table

| Column | Type | Key | Description |
|-------|------|-----|-------------|
| id | BIGINT | PK | Unique identifier for recommendation |
| employee_id | BIGINT | FK → Employees(id) | Recommended employee |
| project_id | BIGINT | FK → Project(id) | Related project |
| score | BIGINT | | Suitability score for recommendation |

📂 Project Structure

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
## Recommendation Criteria

| Factor            | Max Score | Description                                                           |
| ----------------- | --------- | --------------------------------------------------------------------- |
| Skill Match       | 50        | Checks whether the employee has the required project skills           |
| Skill Proficiency | 20        | Compares the employee's proficiency level with the required level     |
| Experience        | 20        | Evaluates whether the employee meets the required years of experience |
| Availability      | 10        | Checks if the employee is currently available for the project         |

4️⃣ Proficiency Evaluation
| Condition           | Score Given                |
| ------------------- | -------------------------- |
| Exact Match         | 100% of proficiency weight |
| Employee > Required | 80%                        |
| Slightly Lower      | 50%                        |
| Much Lower          | 30%                        |
