package fr.rphstudio.subway.process;

import fr.rphstudio.ecs.component.render.RenderFont;
import fr.rphstudio.ecs.component.render.font.SubwaySpriteFont;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class HUD {

    private RenderFont arrows1;
    private RenderFont arrows2;
    private RenderFont letterKeys;
    private RenderFont title;

    private long startTime;
    private Color startColor;
    private Color endColor;

    public HUD(){
        // Create render fonts from railway object
        try {
            Image img = new Image("./sprites/fonts/SubwaySpriteFont.png");

            this.arrows1 = new RenderFont("Press LEFT/RIGHT arrows to change City", img, SubwaySpriteFont.getCharInfo(), 0, 0.20f, new Color(255,255,255,160));
            this.arrows2 = new RenderFont("Press UP/DOWN arrows to add/remove tracks", img, SubwaySpriteFont.getCharInfo(), 0, 0.20f, new Color(255,255,255,160));
            this.letterKeys = new RenderFont("Press any LETTER KEY to highlight a track", img, SubwaySpriteFont.getCharInfo(), 0, 0.20f, new Color(255,255,255,160));
            this.title = new RenderFont("Subway Generator", img, SubwaySpriteFont.getCharInfo(), 0, 0.50f, Color.white);
            float xRef = 15;
            float yRef = 25;
            this.arrows1.setMiddleAlign(false);
            this.arrows2.setMiddleAlign(false);
            this.letterKeys.setMiddleAlign(false);
            this.title.setMiddleAlign(false);
            this.arrows1.setVerticalAlign(false);
            this.arrows2.setVerticalAlign(false);
            this.letterKeys.setVerticalAlign(false);
            this.title.setVerticalAlign(false);
            this.arrows1.setPosition(xRef,yRef);
            this.arrows2.setPosition(xRef,yRef+30);
            this.letterKeys.setPosition(xRef,yRef+60);
            this.title.setPosition(1300,975) ;

            this.startTime = 0;
            this.startColor = new Color(255,255,255,255);
            this.endColor = new Color(255,255,255,255);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g){
        // Set color for title
        long currTime = System.currentTimeMillis();
        long diff = currTime - this.startTime;
        while(diff > 2000){
            this.startTime = currTime;
            this.startColor = endColor;
            this.endColor = new Color((int)(Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
            diff = 0;
        }
        float dt = (float)(diff/2000.0);
        Color clr = new Color(this.startColor.r*(1-dt)+this.endColor.r*dt,this.startColor.g*(1-dt)+this.endColor.g*dt,this.startColor.b*(1-dt)+this.endColor.b*dt);
        this.title.setColor(clr);

        this.arrows1.render(container, game, g);
        this.arrows2.render(container, game, g);
        this.letterKeys.render(container, game, g);
        this.title.render(container, game, g);

    }


}
