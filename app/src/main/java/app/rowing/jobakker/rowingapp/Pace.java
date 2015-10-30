package app.rowing.jobakker.rowingapp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JOBAKKER on 30-6-2015.
 */
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
