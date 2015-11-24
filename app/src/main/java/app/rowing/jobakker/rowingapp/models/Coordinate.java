package app.rowing.jobakker.rowingapp.models;

public class Coordinate {
    private final long x;
    private final long y;

    public Coordinate(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }
}
