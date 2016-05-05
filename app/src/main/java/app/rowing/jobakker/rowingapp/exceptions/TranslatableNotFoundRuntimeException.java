package app.rowing.jobakker.rowingapp.exceptions;

public class TranslatableNotFoundRuntimeException extends RuntimeException {

    public TranslatableNotFoundRuntimeException() {
    }

    public TranslatableNotFoundRuntimeException(String detailMessage) {
        super(detailMessage);
    }

    public TranslatableNotFoundRuntimeException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public TranslatableNotFoundRuntimeException(Throwable throwable) {
        super(throwable);
    }
}
