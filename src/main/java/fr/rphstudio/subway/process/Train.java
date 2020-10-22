package fr.rphstudio.subway.process;

import fr.rphstudio.utils.Geometry;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Train {

    private float    speed;        // sec / pixels
    private float    wait;        // sec
    private Railway  track;
    private Vector2f position;
    private long     startTime;
    private String   trainID;
    private Color    color;

    public Train(String id, float spd, float wt, long initTime, Railway parent){
        this.trainID = id;
        this.track = parent;
        this.speed = spd;
        this.wait = wt;
        this.startTime = initTime;
        this.position = this.track.getStation(0).getPosition().copy();
        this.color = parent.getColor();
        this.color.a = 0.75f;
    }

    public void update(int deltaMs){
        double totalWait  = (this.track.getNbStations()-1)*2*this.wait;
        double totalRun   = this.track.getLength()*2*this.speed;
        double totalTime  = totalWait+totalRun;
        double duration  = (double)System.currentTimeMillis() - this.startTime;

        // Get duration in one cycle
        duration = duration % totalTime;

        // Get duration in the first half of cycle
        if(duration >= totalTime/2){
            duration = totalTime - duration + this.wait;
        }

        // Now we have the real duration in one half cycle
        // check each segment
        double progress = 0;
        for(int i=0;i<this.track.getNbStations()-1;i++){
            // Get station positions
            Vector2f prev = this.track.getStation(i).getPosition();
            Vector2f next = this.track.getStation(i+1).getPosition();
            // Get segment length
            double segLength = Geometry.getDistance(prev, next);
            // Increase progress with wait and seg length
            progress += this.wait;
            // check the current position is in the wait step
            if(progress >= duration) {
                // position of the train is the prev position
                this.position = prev.copy();
                break;
            }
            else{
                double segDuration = segLength*this.speed;
                progress += segDuration;
                if(progress >= duration) {
                    // position of the train is the move step
                    // Here we have found the segment where the train is
                    // Compute the real position on this segment
                    double ratio = (progress-duration)/(segDuration);
                    this.position.x = (float)((prev.x*ratio)+(next.x*(1-ratio)));
                    this.position.y = (float)((prev.y*ratio)+(next.y*(1-ratio)));
                    // Exit
                    break;
                }
            }
        }
    }

    public void render(Graphics g){
        g.setColor(this.color);
        g.fillOval(this.position.x-6, this.position.y-6,12, 12);
        g.setColor(Color.black);
        g.setLineWidth(2);
        g.drawOval(this.position.x-6, this.position.y-6,12, 12);
        g.setLineWidth(1);
    }


}


