package app.rowing.jobakker.rowingapp.exceptions;

public class TranslatableNotFoundException extends RuntimeException {

    public TranslatableNotFoundException() {
    }

    public TranslatableNotFoundException(String detailMessage) {
        super(detailMessage);
    }

    public TranslatableNotFoundException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public TranslatableNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
