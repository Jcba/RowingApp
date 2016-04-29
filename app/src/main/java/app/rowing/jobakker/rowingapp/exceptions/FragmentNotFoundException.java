package app.rowing.jobakker.rowingapp.exceptions;

public class FragmentNotFoundException extends RuntimeException {

    public FragmentNotFoundException() {
    }

    public FragmentNotFoundException(String detailMessage) {
        super(detailMessage);
    }

    public FragmentNotFoundException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public FragmentNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
