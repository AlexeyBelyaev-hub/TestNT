

//Two-dimensional quadrangle
public class Quadrangle {
    private Point2D a;
    private Point2D b;
    private Point2D c;
    private Point2D d;

    public Quadrangle(Point2D a, Point2D b, Point2D c, Point2D d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;

    }

    public Point2D getA() {
        return a;
    }

    public void setA(Point2D a) {
        this.a = a;
    }

    public Point2D getB() {
        return b;
    }

    public void setB(Point2D b) {
        this.b = b;
    }

    public Point2D getC() {
        return c;
    }

    public void setC(Point2D c) {
        this.c = c;
    }

    public Point2D getD() {
        return d;
    }

    public void setD(Point2D d) {
        this.d = d;
    }

    @Override
    public String toString() {
        return "Quadrangle{" +
                "a=" + a +
                ", b=" + b +
                ", c=" + c +
                ", d=" + d +
                '}';
    }
}
