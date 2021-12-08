package acevedo.marc.mc.EmployeeManagementApp.exception;

public class EmployeeNotFoundException extends ManagementAppException {
    private String message;
    public EmployeeNotFoundException(String message) {
        super(message);
        this.message = message;
    }
    public EmployeeNotFoundException() {
    }
}
