package fr.rphstudio.subway.process;

import fr.rphstudio.ecs.component.render.RenderFont;
import fr.rphstudio.ecs.component.render.font.SubwaySpriteFont;
import fr.rphstudio.utils.Geometry;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Station {

    // Attributes
    private String      name;
    private Vector2f    position;
    private Color       color;
    private Color       color2;
    private List<Railway> parents;
    private RenderFont  renName;

    // Constructor
    public Station(String n, Vector2f pos){
        this.name     = n;
        this.position = pos;
        this.color  = Color.black;
        this.color2 = Color.black;
        this.parents = new ArrayList<>();

        try {
            Image img = new Image("./sprites/fonts/SubwaySpriteFont.png");
            this.renName = new RenderFont(this.name, img, SubwaySpriteFont.getCharInfo(), 0, 0.2f, Color.white);
        }
        catch(Exception ex){
            throw new Error("Impossible to create RenderFont in Station.java");
        }
    }

    public void addParent(Railway r){
        this.parents.add(r);
    }
    public void removeParent(Railway r){
        this.parents.remove(r);
    }
    public int getNbParents(){
        return this.parents.size();
    }

    // Getters
    public String getName(){
        return this.name;
    }
    public Vector2f getPosition(){
        return this.position.copy();
    }
    public float getX(){
        return this.position.x;
    }
    public float getY(){
        return this.position.y;
    }

    public float getDistance(Vector2f pos){
        return Geometry.getDistance(pos, this.position);
    }

    public void changeColor(Color newClr){
        this.color  = newClr;
        this.color2 = new Color(newClr);
        this.color.a = 1.0f;
    }

    public void render(Graphics g) {
        this.render(g, false, false, null);
    }
    public void render(Graphics g, boolean isFirst, boolean isLast, String railName){
        Color darkColor  = new Color(this.color );
        Color darkColor2 = new Color(this.color2);
        darkColor.r  /= 3;
        darkColor.g  /= 3;
        darkColor.b  /= 3;
        darkColor.a  *=0.8;
        darkColor2.r /= 2;
        darkColor2.g /= 2;
        darkColor2.b /= 2;
        darkColor2.a *=0.9;
        // FIRST station
        if(isFirst || isLast){
            // color OVAL (big)
            g.setColor(darkColor);
            g.fillOval(this.position.x-20,this.position.y-20,40,40);
            // color OVAL (big)
            g.setColor(darkColor2);
            g.fillOval(this.position.x-14,this.position.y-14,28,28);
        }
        // if several parents
        if(this.parents.size()>1) {
            float radius  = 4+4*this.parents.size();
            float radius2 = radius *0.6f;

            // OVAL (small black)
            g.setColor(Color.black);
            g.fillOval(this.position.x-radius,this.position.y-radius,2*radius,2*radius);
            // OVAL (xsmal white)
            g.setColor(Color.white);
            g.fillOval(this.position.x-radius2,this.position.y-radius2,2*radius2,2*radius2);
        }
        // Normal station (just color small circle)
        else if(!isFirst && !isLast){
            // color OVAL (small)
            g.setColor(this.color);
            g.fillOval(this.position.x-8,this.position.y-8,16,16);
            g.setColor(Color.white);
            g.fillOval(this.position.x-5,this.position.y-5,10,10);
        }
        if(isFirst || isLast){
            // Line name/number
            String displayName = railName;
            if(isFirst){
                displayName += "1";
            }
            if(isLast){
                displayName += "2";
            }
            float deltaY = this.parents.size()>1 ? -40 : -3;
            this.renName.setPosition(this.position.x, this.position.y+deltaY);
            this.renName.setMessage(displayName);
            this.renName.render(null, null, g);

        }
    }
}
