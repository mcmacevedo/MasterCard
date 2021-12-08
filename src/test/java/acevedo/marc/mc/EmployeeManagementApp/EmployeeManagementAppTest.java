package acevedo.marc.mc.EmployeeManagementApp;

import acevedo.marc.mc.EmployeeManagementApp.dao.EmployeeRepository;
import acevedo.marc.mc.EmployeeManagementApp.model.Developer;
import acevedo.marc.mc.EmployeeManagementApp.model.Employee;
import acevedo.marc.mc.EmployeeManagementApp.model.Manager;
import acevedo.marc.mc.EmployeeManagementApp.model.QATester;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeManagementAppTest {
	private static ValidatorFactory validatorFactory;
	private static Validator validator;

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private EmployeeRepository repository;

	@BeforeClass
	public static void createValidator() {
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	public void shouldFailConstraintViolations() {
		QATester qaTester = new QATester("Marc", "bad_email_format", LocalDate.now(), BigInteger.valueOf(0L), null);
		Set<ConstraintViolation<QATester>> violations = validator.validate(qaTester);
		assertTrue(!violations.stream()
				.filter(violation -> violation.getInvalidValue().toString().equalsIgnoreCase("bad_email_format"))
				.collect(Collectors.toList())
				.isEmpty()
		);
	}

	@Test
	public void shouldPassConstraintValidation() {
		Set<ConstraintViolation<Employee>> violations;
		QATester qaTester = new QATester("Marc Acevedo", "mcmacevedo@gmail.com", LocalDate.now(), BigInteger.valueOf(10L), "notmyboss@mom.com");
		Manager director = new Manager("Marc Acevedo", "mcmacevedo@gmail.com", LocalDate.now(), BigInteger.valueOf(10L), null, Collections.singletonList(qaTester));
		Manager vp = new Manager("Tim Acevedo", "tacevedo@gmail.com", LocalDate.now(), BigInteger.valueOf(10L), "mcmacevedo@gmail.com", null);
		Developer developer = new Developer("Marc Acevedo", "mcmacevedo@gmail.com", LocalDate.now(), BigInteger.valueOf(10L), "notmyboss@mom.com");
		violations = validator.validate(qaTester);
		assertTrue(violations.size() == 0);
		violations = validator.validate(director);
		assertTrue(violations.size() == 0);
		violations = validator.validate(vp);
		assertTrue(violations.size() == 0);
		violations = validator.validate(developer);
		assertTrue(violations.size() == 0);
	}

	@Test
	public void shouldCreateEmployees() {
		QATester qaTester = new QATester("Marc Acevedo", "mcmacevedo@gmail.com", LocalDate.now(), BigInteger.valueOf(10L), "notmyboss@mom.com");
		entityManager.persist(qaTester);
		assertTrue(repository.findAll().size() == 1);
	}

	@Test
	public void shouldDeleteEmployees() {
		QATester qaTester = new QATester("Marc Acevedo", "mcmacevedo@gmail.com", LocalDate.now(), BigInteger.valueOf(10L), "notmyboss@mom.com");
		Manager vp = new Manager("Tim Acevedo", "tacevedo@gmail.com", LocalDate.now(), BigInteger.valueOf(10L), "mcmacevedo@gmail.com", null);
		entityManager.persist(qaTester);
		entityManager.persist(vp);
		repository.deleteById(vp.getId());
		assertTrue(repository.findAll().size() == 1);
		assertTrue(repository.findById(vp.getId()).orElse(null) == null);
	}

	@Test
	public void shouldListEmployeesGivenASalaryRange() {
		QATester qaTester = new QATester("Marc Acevedo", "mcmacevedo@gmail.com", LocalDate.now(), BigInteger.valueOf(99L), "notmyboss@mom.com");
		Manager vp = new Manager("Tim Acevedo", "tacevedo@gmail.com", LocalDate.now(), BigInteger.valueOf(100000L), "mcmacevedo@gmail.com", null);
		entityManager.persist(qaTester);
		entityManager.persist(vp);
		List<Employee> employeesInSalaryRange = repository.findBySalaryGreaterThanAndSalaryLessThanEqual(BigInteger.valueOf(100L), BigInteger.valueOf(100000000L));
		assertTrue(employeesInSalaryRange.size() == 1);
	}
}