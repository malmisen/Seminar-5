package exceptions;

public class CouldNotConnectToDatabaseException extends Exception {
    public CouldNotConnectToDatabaseException(String message) {
        super(message);
    }
}
