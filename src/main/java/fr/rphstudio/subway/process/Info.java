package fr.rphstudio.subway.process;

import fr.rphstudio.ecs.component.render.RenderFont;
import fr.rphstudio.ecs.component.render.font.SubwaySpriteFont;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

public class Info {

    private RenderFont railName;
    private RenderFont nbStations;
    private RenderFont nbTrains;

    public Info(Railway r, Vector2f initPos, int nbTrains){
        // Create render fonts from railway object
        try {
            Image img = new Image("./sprites/fonts/SubwaySpriteFont.png");
            this.railName   = new RenderFont(r.getName(), img, SubwaySpriteFont.getCharInfo(), 0, 0.2f, r.getColor());
            this.nbStations = new RenderFont(""+r.getNbStations(), img, SubwaySpriteFont.getCharInfo(), 0, 0.17f, r.getColor());
            this.nbTrains   = new RenderFont(""+nbTrains, img, SubwaySpriteFont.getCharInfo(), 0, 0.17f, r.getColor());
            this.railName.setPosition(initPos);
            initPos.x += 120;
            initPos.y += 3;
            this.nbStations.setPosition(initPos);
            initPos.x += 120;
            this.nbTrains.setPosition(initPos);

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void render(GameContainer container, StateBasedGame game, Graphics g){

        this.railName.render(container, game, g);
        this.nbStations.render(container, game, g);
        this.nbTrains.render(container, game, g);

    }


}
