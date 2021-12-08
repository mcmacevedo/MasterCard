package acevedo.marc.mc.EmployeeManagementApp.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.math.BigInteger;
import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "role", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Manager.class, name = "MANAGER"),
        @JsonSubTypes.Type(value = QATester.class, name = "QA"),
        @JsonSubTypes.Type(value = Developer.class, name = "DEV")
})
@Entity(name = "Employee")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "ROLE")
public abstract class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    @Column(unique = true, name = "Email", nullable = false)
    @Email
    private String email;

    @Column(name = "Name", nullable = false)
    @NotEmpty
    private String name;

    @Column(name = "DOB", nullable = false)
    private LocalDate dob;

    @Column( name = "Salary", nullable = false)
    @Positive
    private BigInteger salary;

    @Column(name = "Manager_Email")
    @Email
    private String managerEmail;

    public Employee() {}

    public Employee(String name, String email, LocalDate dob, BigInteger salary, String managerEmail) {
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.salary = salary;
        this.managerEmail = managerEmail;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public BigInteger getSalary() {
        return salary;
    }

    public void setSalary(BigInteger salary) {
        this.salary = salary;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", salary=" + salary +
                ", managerEmail='" + managerEmail + '\'' +
                '}';
    }
}
