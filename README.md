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
