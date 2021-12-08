package acevedo.marc.mc.EmployeeManagementApp.exception;

public class InvalidQueryException extends ManagementAppException {
    private String message;
    public InvalidQueryException(String message) {
        super(message);
        this.message = message;
    }
    public InvalidQueryException() {
    }
}
