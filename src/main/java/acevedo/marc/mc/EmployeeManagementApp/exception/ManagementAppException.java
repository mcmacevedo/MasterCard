package acevedo.marc.mc.EmployeeManagementApp.exception;

public class ManagementAppException extends RuntimeException {
    private String message;
    public ManagementAppException(String message) {
        super(message);
        this.message = message;
    }
    public ManagementAppException() {
    }
}
