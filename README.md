# blibli-experience

This is a backend project for accomplishing Blibli Future Program 4.0 phase 2. Using Spring Boot, Asynchronous Java Framework - 
  Project Reactor and Command Pattern provided by Blibli.com. 

#### How to use it:
1. Clone the project,
2. Run BlibliExperienceApplication,
3. Check the docs in [swagger-ui](http://localhost:8080/experience/swagger-ui.html#)

#### How to get user Id for accessing other method:
1. Post a user on **registerUser** endpoint
OR
2. Login a user on **loginUser** endpoint

#### Bug list (please add bug in this list if you found a bug):
| Method Affected | Description | Date Added | Status | Last Updated |
| --------------- |:-----------:|:----------:|:------:|:------------:| 
| updateUserPassword | method returned "User password updated successfully." even if the old password is wrong. | 18-02-2020 | Closed | 20-02-2020 | 
| postProduct | method error when 1 id post multiple product due to indexed email | 20-02-2020 | Open | 20-02-2020
#### Bug Status (please refer to this when adding or modifying a bug)
| Status | Description | Example |
| ------ |:-----------:|:-------:|
| **Open** | Use this status when adding a new but not vital bug. | Wrong status code returned, wrong response from endpoints. |
| **Critical** | Use this status when adding a vital bug that need to solved immediately. | Login method error, unexpected NPE |  
| **In Development** | Use this status when bug is on progress to be solved | |
| **In Test** | Use this status if bug is already solved but not yet tested. | |
| **Closed** | Use this status if bug is already fixed and tested. | |
| **Rejected** | Use this status if bug is a false alarm or not a bug. | |
| **Re-Open** | Use this status if bug that already **Closed** give same behavior as before. | |