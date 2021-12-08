package acevedo.marc.mc.EmployeeManagementApp.controller;

import acevedo.marc.mc.EmployeeManagementApp.dao.EmployeeService;
import acevedo.marc.mc.EmployeeManagementApp.exception.ManagementAppException;
import acevedo.marc.mc.EmployeeManagementApp.model.Employee;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.math.BigInteger;
import java.util.List;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/mastercard/api/v1")
@Validated
public class Controller {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/getEmployee")
    public ResponseEntity<Employee> getEmployee(@RequestHeader @Positive Long id) {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), OK);
    }

    @PostMapping(value = "/createEmployee")
    public ResponseEntity<Employee> createNewEmployee(@RequestBody @Valid Employee employee) {
        employeeService.addNewEmployee(employee);
        return new ResponseEntity<>(employeeService.getEmployeeById(employee.getId()), OK);
    }

    @DeleteMapping(value = "/deleteEmployee")
    public ResponseEntity<Boolean> deleteEmployee(@RequestHeader @Positive Long id) {
        return new ResponseEntity<>(employeeService.deleteEmployeeById(id), OK);
    }

    @PutMapping(value = "/updateEmployee")
    public ResponseEntity<Employee> updateEmployee(@RequestBody @Valid Employee employee) {
        return new ResponseEntity<>(employeeService.updateEmployeeInformation(employee), OK);
    }

    @GetMapping(value = "/searchEmployeesBySalary")
    public ResponseEntity<List<Employee>> searchEmployeesBySalary(@RequestHeader(required = false) @Positive BigInteger floor, @RequestHeader @Positive BigInteger ceiling) {
        return new ResponseEntity<>(employeeService.searchEmployeesBySalary(floor, ceiling), OK);
    }

    @ExceptionHandler(value = ManagementAppException.class)
    public ResponseEntity handleManagementAppException(ManagementAppException managementAppException) {
        return new ResponseEntity(managementAppException.getMessage() + "mangement", CONFLICT);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity handleConstraintViolationException(ConstraintViolationException constraintViolationException) {
        return new ResponseEntity("Constraint failure on payload. Please check data quality", CONFLICT);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity handleMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        return new ResponseEntity(httpMessageNotReadableException.getMessage() + "http", CONFLICT);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        return new ResponseEntity(methodArgumentNotValidException.getMessage() , CONFLICT);
    }
}
