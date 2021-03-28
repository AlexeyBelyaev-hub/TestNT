import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class Task1 {
    public static void main(String[] args) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]))) {

            List<Short> list = new ArrayList<>();

            String line;
            int lineQuantity=0;

            while ((line = bufferedReader.readLine()) != null) {
                if(!line.isEmpty()) {
                    if (lineQuantity>=1000){
                        System.out.println("WARNING: File got more then 1000 lines - some data ignored!");
                        break;
                    }
                    list.add(Short.valueOf(line));
                    lineQuantity++;
                }
            }

            if (list.isEmpty()){
                System.out.println("File is empty!");
                return;
            }

            DecimalFormat formatter = new DecimalFormat("#0.00");
            System.out.println(formatter.format(calculatePercentile(list,  90)));
            System.out.println(formatter.format(calculateMedium(list)));
            System.out.println(formatter.format(getMax(list)));
            System.out.println(formatter.format(getMin(list)));
            System.out.println(formatter.format(getAverage(list)));

        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("File not found. Please check the path");
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    //parameters: list of Shorts, 0 <= percentile <= 100.
    private static double calculatePercentile(List<Short> list, int percentile) throws RuntimeException{
        if (percentile<0||percentile>100) {
            throw new IllegalArgumentException("Percentile is not in the range 0-100");
        }
        Short [] array = new Short[list.size()];
        list.toArray(array);
        Arrays.sort(array);
        double nd =(percentile/100.00*(array.length-1)+1);
        String nString = String.valueOf(nd);
        int indexDecimal = nString.indexOf(".");
        int ni = Integer.valueOf(nString.substring(0,indexDecimal));
        double x = Double.valueOf(nString.substring(indexDecimal));
        double v = array[ni-1]+x*(array[ni]-array[ni-1]);
        return v;
    }

    private static double calculateMedium(List<Short> list){
        Short [] array = new Short[list.size()];
        list.toArray(array);
        Arrays.sort(array);
        int length = array.length;

        if (length%2 == 0){
            return (array[length/2]+array[length/2+1])/2;
        }else {
            return array[length+1/2];
        }
    }

    private static double getMax(List<Short> list){
        return  list.stream().max(Comparator.naturalOrder()).get();
    }

    private static double getMin(List<Short> list){
        Short max = Short.MIN_VALUE;
        return  list.stream().min(Comparator.naturalOrder()).get();
    }

    private static double getAverage(List<Short> list){
        Short max = Short.MIN_VALUE;
        return  list.stream().mapToDouble(Short::doubleValue).average().getAsDouble();
    }
}
