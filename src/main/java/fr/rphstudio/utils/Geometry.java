package fr.rphstudio.utils;

import org.newdawn.slick.geom.Vector2f;

public class Geometry {

    public static float getDistance(Vector2f A, Vector2f B){
        float dx = A.x - B.x;
        float dy = A.y - B.y;
        float dist = (dx*dx) + (dy*dy);
        dist = (float)(Math.sqrt(dist));
        return dist;
    }

    public static float getAngle(Vector2f src, Vector2f dst){
        float dx = dst.x - src.x;
        float dy = dst.y - src.y;
        float angle = (float)Math.atan2(dy,dx);
        return angle;
    }
}
