package pl.suzuyo;

public class NotFoundService extends RuntimeException {

    public NotFoundService() {
        super();
    }

    public NotFoundService(String message) {
        super(message);
    }

    public NotFoundService(String message, Throwable cause) {
        super(message, cause);
    }
}
