package acevedo.marc.mc.EmployeeManagementApp.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

@Entity(name = "Manager")
public class Manager extends Employee {
    @OneToMany
    private List<Employee> managedEmployees;

    public Manager(String name, String email, LocalDate dob, BigInteger salary, String managerEmail, List<Employee> managedEmployees) {
        super(name, email, dob, salary, managerEmail);
        this.managedEmployees = managedEmployees;
    }

    public Manager() {
    }

    public List<Employee> getManagedEmployees() {
        return managedEmployees;
    }

    public void setManagedEmployees(List<Employee> managedEmployees) {
        this.managedEmployees = managedEmployees;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Manager.class.getSimpleName() + "[", "]")
                .add("managedEmployees=" + managedEmployees)
                .toString();
    }
}
