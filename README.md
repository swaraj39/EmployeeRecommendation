# Employee Recommendation Engine

## 📌 Project Description

The **Employee Recommendation Engine** is a backend system that helps
organizations automatically identify and recommend the most suitable
employees for a project.\
It analyzes employee profiles such as **skills, experience,
availability, and expertise** and compares them with **project
requirements** to generate a **ranked list of candidates**.

The system uses a **data‑driven scoring mechanism** to evaluate how well
each employee matches project needs and provides clear explanations for
recommendations.

All features are exposed through **REST APIs**, enabling efficient
employee management, project management, search functionality, and
intelligent resource allocation.

------------------------------------------------------------------------

# 📘 API Documentation

## 👨‍💼 Employee Management APIs

  -----------------------------------------------------------------------------------------------
  Method            Endpoint                       Service Method               Description
  ----------------- ------------------------------ ---------------------------- -----------------
  POST              /api/employees                 createEmployee()             Creates a new
                                                                                employee along
                                                                                with their
                                                                                associated skills
                                                                                and proficiency
                                                                                levels.

  GET               /api/employees                 getAllEmployees()            Retrieves all
                                                                                employees along
                                                                                with their skill
                                                                                records.

  GET               /api/employees/grouped         getEmployeeSkillsGrouped()   Fetches all
                                                                                employee skills
                                                                                grouped by
                                                                                employee ID.

  GET               /api/employees/{id}            getEmployeeById()            Retrieves details
                                                                                of a specific
                                                                                employee
                                                                                including their
                                                                                associated
                                                                                skills.

  PUT               /api/employees/{id}            updateEmployee()             Updates employee
                                                                                details and
                                                                                replaces existing
                                                                                skills with new
                                                                                ones.

  DELETE            /api/employees/{id}            deleteEmployee()             Deletes an
                                                                                employee and all
                                                                                associated skill
                                                                                records.

  GET               /api/employees/search/{name}   searchEmployeeByName()       Searches
                                                                                employees by
                                                                                matching first
                                                                                name or surname.
  -----------------------------------------------------------------------------------------------

------------------------------------------------------------------------

## 🧠 Recommendation Engine APIs

  -----------------------------------------------------------------------------------------------------
  Method         Endpoint                       Controller Method    Purpose           Description
  -------------- ------------------------------ -------------------- ----------------- ----------------
  POST           /v2/add/emp                    addEmpSkills         Add Employee      Creates a new
                                                                                       employee and
                                                                                       stores their
                                                                                       skills and
                                                                                       proficiency
                                                                                       levels.

  POST           /v2/add/project                addProjectSkills     Add Project       Creates a new
                                                                                       project and
                                                                                       stores required
                                                                                       skills and
                                                                                       proficiency
                                                                                       levels.

  POST           /v2/add/requirement/{id}       addRecommendation    Generate          Runs the
                                                                     Recommendation    evaluation
                                                                                       algorithm to
                                                                                       match employees
                                                                                       with the
                                                                                       specified
                                                                                       project and
                                                                                       stores
                                                                                       recommendation
                                                                                       scores.

  POST           /v2/add/skill                  addSkill             Add Skill         Adds a new skill
                                                                                       to the system if
                                                                                       it does not
                                                                                       already exist.

  GET            /v2/get/recommendations/{id}   getRecommendations   Get Project       Returns a ranked
                                                                     Recommendations   list of
                                                                                       employees
                                                                                       recommended for
                                                                                       the given
                                                                                       project.
  -----------------------------------------------------------------------------------------------------

------------------------------------------------------------------------

## 📂 Project Management APIs

  ---------------------------------------------------------------------------------------
  Method      Endpoint             API Name    Description   Request Body     Response
  ----------- -------------------- ----------- ------------- ---------------- -----------
  POST        /api/projects        Create      Creates a new ProjectRequest   Created
                                   Project     project and                    Project
                                               assigns                        object
                                               required                       
                                               skills with                    
                                               proficiency                    
                                               levels.                        

  GET         /api/projects        Get All     Retrieves all None             List of
                                   Projects    projects                       Project
                                               available in                   
                                               the system.                    

  GET         /api/projects/{id}   Get Project Fetches       Path variable id Project
                                   By ID       project                        object
                                               details using                  
                                               its unique                     
                                               ID.                            

  PUT         /api/projects/{id}   Update      Updates       ProjectRequest   Updated
                                   Project     project                        Project
                                               details and                    
                                               replaces                       
                                               required                       
                                               skills.                        

  DELETE      /api/projects/{id}   Delete      Deletes a     Path variable id Success
                                   Project     project from                   message
                                               the system.                    
  ---------------------------------------------------------------------------------------

------------------------------------------------------------------------

# 🗄 Database Schema (Overview)

Main Entities: - **Employee** - **Skill** - **EmployeeSkill** -
**Project** - **ProjectSkill** - **Recommendation**

These tables store employee details, skill proficiency, project
requirements, and recommendation scores.

------------------------------------------------------------------------

# ⚙ Setup Instructions

### 1️⃣ Clone the Repository

``` bash
git clone https://github.com/your-username/employee-recommendation-engine.git
cd employee-recommendation-engine
```

### 2️⃣ Configure Database

Update your database credentials in:

    application.properties

Example:

    spring.datasource.url=jdbc:mysql://localhost:3306/employeedb
    spring.datasource.username=root
    spring.datasource.password=password

### 3️⃣ Run the Application

``` bash
mvn spring-boot:run
```

or run the main class in your IDE.

------------------------------------------------------------------------

# 🚀 Features

-   Employee Management
-   Project Management
-   Skill Tracking
-   Intelligent Recommendation Engine
-   RESTful API Design
-   Ranked Employee Matching

------------------------------------------------------------------------

# 🛠 Technologies Used

-   Java
-   Spring Boot
-   Spring Data JPA
-   MySQL
-   Maven
-   REST APIs

------------------------------------------------------------------------

# 📊 Recommendation Logic

Employees are ranked based on:

-   Skill match with project requirements
-   Skill proficiency levels
-   Relevant experience
-   Availability
-   Overall suitability score

The system calculates a **dynamic score** and returns the **top matching
employees**.

------------------------------------------------------------------------

# 👨‍💻 Author

Developed as part of an **Employee Recommendation System Backend
Assignment**.
