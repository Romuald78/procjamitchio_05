package fr.rphstudio.subway.process;

import fr.rphstudio.utils.Geometry;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Railway {

    // Attributes
    private String          name;
    private List<Station>   stations;
    private Color           color;

    // Constructor
    public Railway(String n, Color clr){
        this.name     = n;
        this.color    = clr;
        this.stations = new ArrayList<Station>();
    }

    // Getters
    public String getName(){
        return this.name;
    }
    public int getNbStations(){
        return this.stations.size();
    }
    public Station getStation(int index){
        return this.stations.get(index);
    }
    public boolean contains(Station s){
        return this.stations.contains(s);
    }
    public Color getColor(){
        return new Color(this.color);
    }

    public float getLength(){
        float distance = 0.0f;
        for(int i=0; i<this.stations.size()-1;i++){
            // Get position for 'i' and 'i+1' stations
            Vector2f pos0 = this.stations.get(i  ).getPosition();
            Vector2f pos1 = this.stations.get(i+1).getPosition();
            // Compute real distance
            float tmpDist = Geometry.getDistance(pos0, pos1);
            distance += tmpDist;
        }
        return distance;
    }


    // Setters
    public void addStation(Station newSta, boolean updateColor){
        if(updateColor || newSta.getNbParents()==0) {
            newSta.changeColor(this.color);
        }
        this.stations.add(newSta);
    }
    public void removeStation(Station sta){
        this.stations.remove(sta);
    }
    public void removeStation(int index){
        this.stations.remove(index);
    }

    public void render(Graphics g, boolean isHighLighted){
        // Draw lines
        for (int i=1; i<this.stations.size();i++) {
            Vector2f pos0 = this.stations.get(i-1).getPosition();
            Vector2f pos1 = this.stations.get(i  ).getPosition();
            float dx = Math.abs(pos0.x - pos1.x);
            float dy = Math.abs(pos0.y - pos1.y);

            if(isHighLighted){
                int period = 600;
                int time = (int)(System.currentTimeMillis()%period);
                time = Math.abs(period/2-time);
                time = 2*(time *255)/period;
                Color glow = new Color(255,255,255,time);

                g.setLineWidth(14);
                g.setColor(glow);
                g.drawLine(pos0.x,pos0.y,pos1.x,pos1.y);
                g.setLineWidth(7);
                g.setColor(this.color);
                g.drawLine(pos0.x,pos0.y,pos1.x,pos1.y);
            }
            else{
                g.setLineWidth(5);
                g.setColor(this.color);
                g.drawLine(pos0.x,pos0.y,pos1.x,pos1.y);
            }
            g.setLineWidth(1);

        }
    }

    public void renderStations(Graphics g){
        // Draw stations
        for (int i=0; i<this.stations.size();i++){
            if(i==0 || i==this.stations.size()-1){
                this.stations.get(i).render(g, i==0, i==this.stations.size()-1, this.getName());
            }
            else {
                this.stations.get(i).render(g);
            }
        }
    }

}
