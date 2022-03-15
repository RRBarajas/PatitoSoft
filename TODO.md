# PatitoSoft

## Issues/Errors
The following is a list of bugs that remain pending on the project, in no particular order of priority:

1. Bean validation is not throwing the errors, verify why.
2. On the same line, add more bean validation to the DTOs.
3. Controllers should have Tests to validate the Input/Output data, as well as the responses that should be expected (for instance, above's Bean validation issue should've come up there).
4. Consider using a version column to handle optimistic locking.
5. The Employee-Address relationship can be disputed, but for the purpose of this exercise it should be kept as 1-1.
6. Employee create/replace are currently ignoring the Employment history field, should we fix that?
7. Employees per position should also show positions that don't have employees assigned.
8. In the searchByCriteria we should use the Position ID instead of the Position Name, making the search easier. Remember, this is a backend service and the UI should handle the IDs properly.
9. All searches that return a List can break the system when dealing with lots of data, we should use Pagination instead.
10. Why are we using the Transactional annotation in the fire/rehire repository method? It's odd.

## Improvements
While the following are things that could help improve the overall project:
1. Add security to avoid having multiple repeated endpoints for Basic/Admin users.
2. Improve the Swagger documentation by adding the proper annotations to the Controllers/DTOs.
3. Entities and DTOs should have their custom equals and hashcode method if we'll use them in hashing operations/comparisons, but they should be properly defined first.
4. Deleted flag is used to indicate soft-delete, but a better approach may be to use a STATUS column, since it gives more flexibility to extend its functionality.
5. Add a Position node to the EmployeeDTO containing the current position; employment history should instead keep old positions.
6. Current flag in employment history is quite rudimentary, it may be better to use the from/to date combination to determinate the current position and perform the necessary validations.