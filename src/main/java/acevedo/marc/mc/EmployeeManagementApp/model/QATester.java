package acevedo.marc.mc.EmployeeManagementApp.model;

import javax.persistence.Entity;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity(name = "QATester")
public class QATester extends Employee{
    public QATester() {}

    public QATester(String name, String email, LocalDate dob, BigInteger salary, String managerEmail) {
        super(name, email, dob, salary, managerEmail);
    }
}
