
import java.io.*;
import java.util.List;

public class Task2 {
    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("ERROR: 2 arguments expected:\n " +
                    "1) file name of quadrangle coordinates\n " +
                    "2) file name of points coordinates\n" +
                    "but found arguments: " + args.length + ".");
            return;
        }

        try (BufferedReader quadrangleReader = new BufferedReader(new FileReader(args[0]));
             BufferedReader pointsReader = new BufferedReader(new FileReader(args[1]))) {


            QuadrangleConvex quadrangle = Util.constructQuadrangle(quadrangleReader);

            List<Point2D> pointList = Util.constructPointList(pointsReader);
            for (Point2D point : pointList) {
                System.out.println(Util.pointAtQuadrangle(quadrangle, point));
            }

        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.getMessage());
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        } catch (IOException e) {
            e.getMessage();
        }
    }
}