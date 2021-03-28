import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BiFunction;


public class Task4 {
    static final LocalTime OPEN_HOUR = LocalTime.of(8,00);
    static final LocalTime CLOSE_HOUR = LocalTime.of(20,00);

    //uses to count how many times periods intercepts
    static int rang=0;


    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))){

            List<Interval> intervals = constructIntervalList(reader);
            Collections.sort(intervals);

            System.out.println(intervals);

            List<Interval> interceptions = getMaxVisitorsIntervals(intervals);
            System.out.println(interceptions);
            List<Interval> unitedIntervals =  uniteIntervals(interceptions);
            System.out.println("United intervals: ");
            for (Interval interval: unitedIntervals) {
                System.out.println(interval);
            }
           // System.out.println("Rang - "+rang);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static List<Interval> uniteIntervals(List<Interval> interceptions) {
        List<Interval> unitedIntervals = new ArrayList<>();
        for (int i=0; i<interceptions.size()-1; ){

            if (interceptions.get(i).getEnd().equals(interceptions.get(i+1).getStart())
            || (interceptions.get(i).getStart().equals(interceptions.get(i+1).getStart())
                    && interceptions.get(i).getEnd().equals(interceptions.get(i+1).getEnd()))){
                //start uniting
                int startsFromIndex = i;
                do {
                    i++;
                }while (
                        (i<interceptions.size()-1) &&
                                (interceptions.get(i).getEnd().equals(interceptions.get(i+1).getStart()) ||
                                        (interceptions.get(i).getStart().equals(interceptions.get(i+1).getStart())
                                                && interceptions.get(i).getEnd().equals(interceptions.get(i+1).getEnd())))

                                );

                unitedIntervals.add(new Interval(interceptions.get(startsFromIndex).getStart(), interceptions.get(i).getEnd()));
                if (i!=interceptions.size()-2) i++;

            }else {
                unitedIntervals.add(new Interval(interceptions.get(i+1)));
                i++;
            }
        }
        //unitedIntervals.add(new Interval(interceptions.get(interceptions.size()-1)));
        return unitedIntervals;
    }

    // read data from file, construct interval list.
    // Do checks:
    // Interval: start >= end
    // start >= openHour
    // end <= closeHour
    static List<Interval> constructIntervalList(BufferedReader bufferedReader) throws IOException {
        String line;
        List<Interval> intervals = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            if (!line.isEmpty()) {
                intervals.add(parseLine(line));
            }
        }

        if (intervals.isEmpty()) {
            throw new IllegalArgumentException("No intervals detected");
        }

        return intervals;
    }


    // parse line with interval with " " delimiter
    // expected 2 values: start, end. Otherwise IllegalArgumentException is thrown.
    static Interval parseLine(String line) {
        line = line.trim().replace("\\n", "");
        String[] intervals = line.split(" ");
        if (intervals.length < 1 || intervals.length > 2) {
            throw new IllegalArgumentException("ERROR: expected 2 values for time interval, but got " + intervals.length + " " + intervals.toString());
        }

        String [] start = intervals[0].split(":");
        String [] end = intervals[1].split(":");

        int startHour = Integer.valueOf(start[0]);
        int startMinute = Integer.valueOf(start[1]);
        int endHour = Integer.valueOf(end[0]);
        int endMinute = Integer.valueOf(end[1]);

        LocalTime startTime = LocalTime.of(startHour,startMinute);
        LocalTime endTime = LocalTime.of(endHour, endMinute);

        // Check that in work hours
        if (startTime.isBefore(OPEN_HOUR)||endTime.isAfter(CLOSE_HOUR)){
            throw new IllegalArgumentException("ERROR: interval {"+startTime+","+endTime+"} is out of bank working hours: {"+ OPEN_HOUR +","+ CLOSE_HOUR +"}");
        }

        return new Interval(startTime,endTime);
    }

    private static boolean isEquals(List<Interval> intervals, List<Interval> interceptions){
        if (intervals.size()!=interceptions.size()){
            return false;
        }
        for (int i = 0; i<intervals.size(); i++){
            if (!interceptions.get(i).equals(intervals.get(i))){
                return false;
            }
        }
        return true;
    }

    private static List<Interval> getMaxVisitorsIntervals(List<Interval> intervals){
        rang++;
        Interval[] array = new Interval[intervals.size()];
        intervals.toArray(array);
        List<Interval> interceptions = new ArrayList<>();
        //System.out.println("INTERCEPTIONS: ");
        for (int i = 0; i<array.length; i++){
            for (int j=i+1; j<array.length; j++){
                if(
                        array[i].getStart().isBefore(array[j].getEnd()) && array[i].getEnd().isAfter(array[j].getStart())
//                                &&
//                                !(array[i].getStart().equals(array[j].getStart())
//                                        && array[i].getEnd().equals(array[j].getEnd()))
                ){
                    LocalTime startTime = array[j].getStart();
                    LocalTime endTime = array[i].getEnd().isBefore(array[j].getEnd())?array[i].getEnd():array[j].getEnd();
                    Interval interval = new Interval(startTime, endTime);
                    //System.out.println(interval);
                    interceptions.add(interval);
                }
            }
        }

        if(interceptions.isEmpty()||isEquals(intervals,interceptions)){
            return intervals;
        } else {
           return getMaxVisitorsIntervals(interceptions);
        }

    }



}
