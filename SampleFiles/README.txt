$ curl -v POST --data @PatientExample.json "http://localhost:8301/healthcare/categories/surgery/reserve" --header "Content-Type:application/json" -w "\n"

//Data services:

GetEmployeeDetails
$ curl -v GET http://localhost:8301/services/RDBMSDataService.HTTPEndpoint/Employee/3 -w "\n"

GetAllEmployeesDetails
$ curl -v GET http://localhost:8301/services/RDBMSDataService.HTTPEndpoint/Employee --header "Accept:application/json" -w "\n"


curl -v POST --data @AddDoctorExample.json "http://localhost:8290/hospitalservice/registerDoctor" --header "Content-Type:application/json" -w "\n"
