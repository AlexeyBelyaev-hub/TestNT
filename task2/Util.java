
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {

    // -- Construct list of point2D --
    // construct and return List<Point2D> from bufferedReader
    // expected 1-100 points with values: x,y. Otherwise IllegalArgumentException is thrown.
    static List<Point2D> constructPointList(BufferedReader bufferedReader) throws IOException {
        String line;
        List<Point2D> pointList = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            if (!line.isEmpty()) {
                pointList.add(parseLine(line));
            }
        }

        int size = pointList.size();
        if (size < 1 || size >= 100) {
            throw new IllegalArgumentException("ERROR: 1 - 100 points expected, but got " + size);
        }

        return pointList;
    }

    // -- Construct QuadrangleConvex --
    // construct and return QuadrangleConvex from bufferedReader
    // expected 4 points with values: x,y. Otherwise IllegalArgumentException is thrown.
    static QuadrangleConvex constructQuadrangle(BufferedReader bufferedReader) throws IOException{
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }

        int size = lines.size();
        if (size < 1 || size > 4) {
            throw new IllegalArgumentException("ERROR: 4 vertex expected, but got " + size);
        }

        return new QuadrangleConvex(
                parseLine(lines.get(0)),
                parseLine(lines.get(1)),
                parseLine(lines.get(2)),
                parseLine(lines.get(3))
        );

    }

    // parse line with coordinates with " " delimiter
    // expected 2 values: x,y. Otherwise IllegalArgumentException is thrown.
    static Point2D parseLine(String line) {
        line = line.trim().replace("\\n", "");
        String[] coordinates = line.split(" ");
        if (coordinates.length < 1 || coordinates.length > 2) {
            throw new IllegalArgumentException("ERROR: expected 2 values for point2D, but got " + coordinates.length + " " + coordinates.toString());
        }
        double x = Double.valueOf(coordinates[0]);
        double y = Double.valueOf(coordinates[1]);
        return new Point2D(x, y);
    }

    // -- Check point position relatively quadrangle--
    // parameters: QuadrangleConvex quadrangle, Point2D point
    // return values:
    // 0 - point is one of the vertex,
    // 1 - point is on a edge of the quadrangle,
    // 2 - point inside of the quadrangle,
    // 3 - point outside of the quadrangle.
    static int pointAtQuadrangle(QuadrangleConvex quadrangle, Point2D point) {

        // prepare variables for concise
        Point2D a = quadrangle.getA();
        Point2D b = quadrangle.getB();
        Point2D c = quadrangle.getC();
        Point2D d = quadrangle.getD();

        //check if point is one of the vertex
        if (a.equals(point) || b.equals(point)
                || c.equals(point) || d.equals(point) ){
            return 0;
        }

        // Check if point locate from left side each edge:
        // if result > 0 then point on the left side
        // if result < 0 then point on the right side
        // if result = 0 then point on the edge
        // Each edge should be checked in revert order

        // check ab
        double result1 = (a.getX()-b.getX()) * (point.getY()-b.getY())
                - (point.getX()-b.getX()) * (a.getY()-b.getY());
        //System.out.println(result1);
        if (result1<0) return 3;

        // check bc
        double result2 = (b.getX()-c.getX()) * (point.getY()-c.getY())
                - (point.getX()-c.getX()) * (b.getY()-c.getY());
        //System.out.println(result2);
        if (result2<0) return 3;

        // check cd
        double result3 = (c.getX()-d.getX()) * (point.getY()-d.getY())
                - (point.getX()-d.getX()) * (c.getY()-d.getY());
        //System.out.println(result3);
        if (result3<0) return 3;

        // check da
        double result4 = (d.getX()-a.getX()) * (point.getY()-d.getY())
                - (point.getX()-a.getX()) * (d.getY()-a.getY());
        //System.out.println(result4);
        if (result4<0) return 3;

        //check if point on edge
        //we do not need to check if results>0 because of previous checks
        if (result1==0||result2==0||result3==0||result4==0){
            return 1;
        } else {
            return 2;
        }

    }
}
