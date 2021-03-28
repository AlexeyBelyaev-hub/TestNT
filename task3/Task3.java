import java.io.*;

public class Task3 {
    public static void main(String[] args) {
        int numberOfCashes = 5;
        int numberOfIntervals = 16;

        if (args.length != 1) {
            System.out.println("ERROR: 1 arguments expected:\n " +
                    "1) folder path\n " +
                    "but found arguments: " + args.length + ".");
            return;
        }

        File dir = new File(args[0]);
        File[] files = dir.listFiles((File folder, String name) -> name.toLowerCase().matches("cash\\d.txt"));


        if (files.length != numberOfCashes) {
            System.out.println("ERROR: " + numberOfCashes + " cashes expected:\n " +
                    "but found: " + files.length + ".");
            return;
        }

        try {
            double[][] intervals = loadIntervals(files, numberOfIntervals);
            System.out.println(getIndexOfMaxInterval(intervals, numberOfIntervals));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        } catch (IOException e) {
            e.getMessage();
        }


    }

    // load intervals from files,
    // if intervals in file MORE then numberOfIntervals then ArrayIndexOutOfBoundsException thrown up
    // if intervals in file LESS then numberOfIntervals then it will be initialise ba default by 0.0.
    private static double[][] loadIntervals(File[] files, int numberOfIntervals) throws ArrayIndexOutOfBoundsException, FileNotFoundException {
        double[][] intervals = new double[files.length][numberOfIntervals];

        for (int i = 0; i < files.length; i++) {
            try (BufferedReader reader = new BufferedReader(new FileReader(files[i]))) {
                String line;
                int j = 0;
                while ((line = reader.readLine()) != null) {
                    if (!line.isEmpty()) {
                        line = line.trim().replace("\\n", "");
                        intervals[i][j] = Double.valueOf(line);
                        j++;
                    }
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ArrayIndexOutOfBoundsException("ERROR: " + numberOfIntervals + " interval expected. " +
                        "Extra intervals in " + files[i].getName());
            } catch (FileNotFoundException fileNotFoundException) {
                throw fileNotFoundException;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return intervals;
    }

    //get index Of max interval from sum of all cashes
    private static int getIndexOfMaxInterval(double[][] intervals, int numberOfIntervals) {
        //get total values of all cashes for each interval
        double totalIntervals[] = new double[numberOfIntervals];
        for (int i = 0; i < numberOfIntervals; i++) {
            double iSum = 0;
            for (int j = 0; j < intervals.length; j++) {
                iSum += intervals[j][i];
            }
            totalIntervals[i] += iSum;
        }

        //find max
        double max = Double.MIN_VALUE;
        int maxIndex = -1;
        for (int i = 0; i < totalIntervals.length; i++) {
            if (totalIntervals[i] > max) {
                max = totalIntervals[i];
                maxIndex = i;
            }
        }
        return maxIndex + 1;
    }
}

