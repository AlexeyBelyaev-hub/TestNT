import java.time.LocalTime;
import java.util.Objects;

public class Interval implements Comparable<Interval> {
    private final LocalTime start;
    private final LocalTime end;

    public Interval(Interval interval) {
        this.start = interval.getStart();
        this.end = interval.getEnd();
    }

    public Interval(LocalTime start, LocalTime end) throws IllegalArgumentException {
        if (end.isBefore(start)){
            throw new IllegalArgumentException("ERROR: Incorrect interval: end ("+end+") is before start("+start+")");
        }
        this.start = start;
        this.end = end;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    @Override
    public int compareTo(Interval other) {

        return this.getStart().compareTo(other.getStart());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interval)) return false;
        Interval interval = (Interval) o;
        return start.equals(interval.start) &&
                end.equals(interval.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return start +
                " " + end;
    }
}
