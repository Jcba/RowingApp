package app.rowing.jobakker.rowingapp.exceptions;

public class FragmentNotFoundRuntimeException extends RuntimeException {

    public FragmentNotFoundRuntimeException() {
    }

    public FragmentNotFoundRuntimeException(String detailMessage) {
        super(detailMessage);
    }

    public FragmentNotFoundRuntimeException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public FragmentNotFoundRuntimeException(Throwable throwable) {
        super(throwable);
    }
}
