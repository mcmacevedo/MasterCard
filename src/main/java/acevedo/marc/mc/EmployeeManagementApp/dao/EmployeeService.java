package acevedo.marc.mc.EmployeeManagementApp.dao;

import acevedo.marc.mc.EmployeeManagementApp.exception.EmployeeNotFoundException;
import acevedo.marc.mc.EmployeeManagementApp.exception.InvalidQueryException;
import acevedo.marc.mc.EmployeeManagementApp.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.List;
import static java.lang.Boolean.TRUE;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public Employee getEmployeeById(Long id) throws EmployeeNotFoundException {
        if (repository.findById(id).isEmpty()) {
            EmployeeNotFoundException exception = new EmployeeNotFoundException("Could not find Employee in In-Memory database. No Action Performed.");
            log.warn(exception.getMessage(), exception);
            throw exception;
        }
        return repository.findById(id).get();
    }

    public void addNewEmployee(Employee employee) {
        repository.save(employee);
    }

    public Boolean deleteEmployeeById(Long id) {
        if (repository.findById(id).isEmpty()) {
            EmployeeNotFoundException exception = new EmployeeNotFoundException("Could not find Employee in In-Memory database. No Action Performed.");
            log.warn(exception.getMessage(), exception);
            throw exception;
        }
        repository.deleteById(id);
        return TRUE;
    }

    public Employee updateEmployeeInformation(Employee employee) {
        addNewEmployee(employee);
        return repository.findById(employee.getId()).get();
    }

    public List<Employee> searchEmployeesBySalary(BigInteger floor, BigInteger ceiling) {
        if (floor.compareTo(ceiling) >= 0) {
            InvalidQueryException exception = new InvalidQueryException("Salary floor cannot equal or exceed salary ceiling. No Action Executed");
            log.warn(exception.getMessage(), exception);
            throw exception;
        }
        return repository.findBySalaryGreaterThanAndSalaryLessThanEqual(floor, ceiling);
    }
}
