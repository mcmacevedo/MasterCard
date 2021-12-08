package acevedo.marc.mc.EmployeeManagementApp.dao;

import acevedo.marc.mc.EmployeeManagementApp.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    public List<Employee> findBySalaryGreaterThanAndSalaryLessThanEqual(BigInteger floor, BigInteger ceiling);
}
