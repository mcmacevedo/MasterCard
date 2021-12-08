package acevedo.marc.mc.EmployeeManagementApp.model;

import javax.persistence.Entity;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity(name = "Developer")
public class Developer extends Employee {
    public Developer() {
    }

    public Developer(String name, String email, LocalDate dob, BigInteger salary, String managerEmail) {
        super(name, email, dob, salary, managerEmail);
    }
}
