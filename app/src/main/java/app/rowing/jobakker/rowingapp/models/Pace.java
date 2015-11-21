package app.rowing.jobakker.rowingapp.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Pace extends Date {
    private SimpleDateFormat paceformat;

    Pace() {
        super();
        paceformat = new SimpleDateFormat("mm:ss");
    }

    public String getPace() {
        return paceformat.format(this);
    }
}
