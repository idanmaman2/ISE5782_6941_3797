package lightning;

import primitives.*;

/**
 *LightSource
 *
 * @author Idan and Eliyahu
 */
public interface LightSource {
    public Color getIntensity(Point p);
    public Vector getL(Point p);
    public double distanceSquared(Point p);

}