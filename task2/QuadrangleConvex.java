

public class QuadrangleConvex extends Quadrangle {


    public QuadrangleConvex(Point2D a, Point2D b, Point2D c, Point2D d) {
        super(a, b, c, d);
        if(!isConvex()){
            throw new IllegalArgumentException("Quadrangle is not convex: "+a+" "+b+" "+c+" "+d);
        }
    }

    private boolean isConvex() {
        //System.out.println(p1.distance(p2));
        Point2D a = super.getA();
        Point2D b = super.getB();
        Point2D c = super.getC();
        Point2D d = super.getD();
        Point2D ab = new Point2D(a.getX() - b.getX(), a.getY() - b.getY());
        Point2D bc = new Point2D(b.getX() - c.getX(), b.getY() - c.getY());
        Point2D cd = new Point2D(c.getX() - d.getX(), c.getY() - d.getY());
        Point2D da = new Point2D(d.getX() - a.getX(), d.getY() - a.getY());

        double product1 = ab.getX() * bc.getY() - ab.getY() * bc.getX();
        double product2 = bc.getX() * cd.getY() - bc.getY() * cd.getX();
        double product3 = cd.getX() * da.getY() - cd.getY() * da.getX();
        double product4 = da.getX() * ab.getY() - da.getY() * ab.getX();

        return product1 < 0 && product2 < 0 && product3 < 0 && product4 < 0 ? true : false;
    }


    }
