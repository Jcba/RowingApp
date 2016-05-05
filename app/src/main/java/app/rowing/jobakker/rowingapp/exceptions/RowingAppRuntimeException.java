package app.rowing.jobakker.rowingapp.exceptions;

public class RowingAppRuntimeException extends RuntimeException {
    public RowingAppRuntimeException() {
    }

    public RowingAppRuntimeException(String detailMessage) {
        super(detailMessage);
    }

    public RowingAppRuntimeException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public RowingAppRuntimeException(Throwable throwable) {
        super(throwable);
    }
}
