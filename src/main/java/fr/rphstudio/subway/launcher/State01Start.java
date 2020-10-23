/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.subway.launcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import fr.rphstudio.ecs.component.render.RenderFont;
import fr.rphstudio.ecs.component.render.font.SubwaySpriteFont;
import fr.rphstudio.subway.process.*;
import fr.rphstudio.utils.rng.Prng;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import static java.lang.Math.cos;

public class State01Start extends BasicGameState
{   
    //------------------------------------------------
    // PUBLIC CONSTANTS
    //------------------------------------------------
    public static final int ID = 100;


    //------------------------------------------------
    // PRIVATE PARAMETERS
    //------------------------------------------------
    private final float ANGLE_DIFF = (float)(Math.PI/4);
    private final float DIST_DIFF  = 175.0f;
    private final float MIN_X = MainLauncher.WIDTH/2 - MainLauncher.HEIGHT/2;
    private final float MAX_X = MainLauncher.WIDTH/2 + MainLauncher.HEIGHT/2;
    private final float MIN_Y = MainLauncher.HEIGHT*0.1f;
    private final float MAX_Y = MainLauncher.HEIGHT*0.9f;


    //------------------------------------------------
    // PRIVATE PROPERTIES
    //------------------------------------------------
    private StateBasedGame  gameObject;
    private GameContainer   container;
    private String          version;
    
    private Image           backGround;
    private RenderFont      textCityNum;
    private Prng            prng;

    private int             cityNum;
    private int             nbRails;
    private List<Railway>   railways;
    private List<Train>     trains;
    private int             selectedRail;
    private List<Info>      infos;
    private RenderFont      title;
    private HUD hud;


    //------------------------------------------------
    // PRIVATE Structures
    //------------------------------------------------


    //------------------------------------------------
    // PRIVATE METHODS
    //------------------------------------------------
    // Get current program version string from file
    private void getVersion()
    {
        // Get display version
        BufferedReader br = null;
        try
        {
            this.version = "";
            br = new BufferedReader(new FileReader("info/version.txt"));
            String line;
            line = br.readLine();
            while(line != null)
            {
                this.version = this.version + line + "\n";
                line = br.readLine();
            }
            if (br != null)
            {
                br.close();
            }
        }
        catch (IOException e)
        {
            throw new Error(e);
        }
        finally
        {
            try
            {
                if (br != null)
                {
                    br.close();
                }
            }
            catch (IOException ex)
            {
                throw new Error(ex);
            }
        }
    }
    // Quit game
    private void quitGame()
    {
        this.container.exit();
    }
    // change number of rails
    public void increaseNbRails(){
        this.nbRails++;
        if(this.nbRails>20){
            this.nbRails = 20;
        }
        this.init();
    }
    public void decreaseNbRails(){
        this.nbRails--;
        if(this.nbRails<1){
            this.nbRails = 1;
        }
        this.init();
    }
    // Change city numbers
    private void decreaseCity(){
        this.cityNum--;
        if(this.cityNum < 1){
            this.cityNum = 1000000;
        }
        this.init();
    }
    private void increaseCity(){
        this.cityNum++;
        if(this.cityNum > 1000000){
            this.cityNum = 1;
        }
        this.init();
    }
    private Station findNearStation(Vector2f pos){
        float minDist = 1000000000f;
        Station result = null;
        for(Railway r:this.railways){
            for(int i=0; i<r.getNbStations();i++){
                Station s = r.getStation(i);
                float tmpDist = s.getDistance(pos);
                if( (tmpDist < DIST_DIFF/2) && (tmpDist < minDist) ){
                    minDist = tmpDist;
                    result = s;
                }
            }
        }
        return result;
    }

    private Color getRandomColor(){
        Color clr = null;
        while(clr == null){
            double r = this.prng.random()*254 + 10;
            double g = this.prng.random()*254 + 10;
            double b = this.prng.random()*254 + 10;
            double y = 0.299*r + 0.587*g + 0.114*b;
            while( y < 160 ){
                r = Math.min(255, r*1.1);g = Math.min(255, g*1.1);
                b = Math.min(255, b*1.1);
                y = 0.299*r + 0.587*g + 0.114*b;
            }
            clr = new Color((int)r,(int)g,(int)b);
        }
        return clr;
    }

    private boolean createNewRailway(String name, int index){
        float radiusX = MainLauncher.HEIGHT/2.0f;
        float radiusY = MainLauncher.HEIGHT/2.0f;

        // Create Color (Luminance must be always the same)
        Color clr = this.getRandomColor();


        // create railway object
        Railway rail = new Railway(""+name, clr);

        // Create START STATION
        float x0 = 0;
        float y0 = 0;
        float startAngle = 0;
        int countMaxi = 100;
        do {
            // Random angle
            startAngle = (float) (this.prng.random() * Math.PI * 2);
            x0 = (radiusX * (float) Math.cos(startAngle)) + MainLauncher.WIDTH/2;
            y0 = (radiusY * (float) Math.sin(startAngle)) + MainLauncher.HEIGHT/2;
            // Saturate position around the screen
            x0 = Math.min(x0, MAX_X);
            x0 = Math.max(x0, MIN_X);
            y0 = Math.min(y0, MAX_Y);
            y0 = Math.max(y0, MIN_Y);
            // Get out of infinite loop
            countMaxi--;
            if(countMaxi <= 0){
                return false;
            }
        }
        while( this.findNearStation(new Vector2f(x0,y0)) != null );


        // NEXT STATIONS
        int stationCount = 1;
        startAngle += Math.PI;
        Station lastCrossStation = null;
        boolean first = true;
        countMaxi = 100;
        do{
            // Check if this position is close to any other station
            Station nearStation = this.findNearStation(new Vector2f(x0,y0));
            if(nearStation != null){
                // Cross  station
                if(lastCrossStation == null) {
                    rail.addStation(nearStation, first);
                    nearStation.addParent(rail);
                    lastCrossStation = nearStation;
                }
                else{
                    // check for all railways that lastCrossStation is not in here
                    boolean foundAgain = false;
                    for(Railway r:this.railways){
                        if (r.contains(lastCrossStation) && r.contains(nearStation)){
                            foundAgain = true;
                        }
                    }
                    if(!foundAgain){
                        // ok add it
                        rail.addStation(nearStation, first);
                        nearStation.addParent(rail);
                        lastCrossStation = nearStation;
                    }
                    else{
                        // Retry once with previous station
                        x0 = rail.getStation(rail.getNbStations()-1).getPosition().x;
                        y0 = rail.getStation(rail.getNbStations()-1).getPosition().y;
                    }
                }
            }
            else{
                // Add new station
                Station newStation = new Station(rail.getName() + stationCount, new Vector2f(x0, y0) );
                rail.addStation( newStation, first );
                newStation.addParent(rail);
                stationCount++;
                first = false;
                // update start angle
                startAngle += (this.prng.random()*ANGLE_DIFF)-(ANGLE_DIFF/2);
                lastCrossStation = null;
            }

            // Get out of infinite loop
            countMaxi--;
            if(countMaxi <= 0){
                break;
            }

            // Compute distance
            float dist = (float)(this.prng.random()*DIST_DIFF/2) + (DIST_DIFF/2);
            // New position
            x0 += dist * Math.cos(startAngle);
            y0 += dist * Math.sin(startAngle);

        }
        while(x0>MIN_X && y0>MIN_Y && x0<MAX_X && y0<MAX_Y);

        // Remove last added Station in case it has several parent TERMINUS
        boolean removed = false;
        do {
            removed = false;
            Station lastAddedStation = rail.getStation(rail.getNbStations() - 1);
            for(Railway r:this.railways){
                if( r.getStation(0)==lastAddedStation || r.getStation(r.getNbStations()-1)==lastAddedStation ){
                    rail.removeStation(lastAddedStation);
                    lastAddedStation.removeParent(rail);
                    removed = true;
                }
            }
        }
        while(removed);

        // Put rail color to last station
        Station lastAddedStation = rail.getStation(rail.getNbStations() - 1);
        lastAddedStation.changeColor(rail.getColor());

        // add rail only if several stations
        if(rail.getNbStations() > 1) {
            this.railways.add(rail);
            // Create trains
            int nbT = this.createTrains(rail);
            // Create infos
            this.infos.add( new Info(rail, new Vector2f(1615,85+index*30), nbT) );
            return true;
        }
        else{
            return false;
        }

    }

    public int createTrains(Railway r){
        int nbTrains = (int)(this.prng.random()*r.getNbStations()) + 1;
        float speed = (float)(this.prng.random()*15) + 8f;
        float wait = (float)(this.prng.random()*500) + 350;
        long  initTime = (long)(this.prng.random()*100000);
        for(int i=0;i<nbTrains;i++){
            long initFull = initTime + (long)( (2*i*speed*r.getLength())/nbTrains );
            Train tr = new Train("@", speed, wait, initFull, r);
            this.trains.add(tr);
        }
        return nbTrains;
    }

    //------------------------------------------------
    // CONSTRUCTOR
    //------------------------------------------------
    public State01Start()
    {
        this.selectedRail = 10000000;
    }
    
    
    //------------------------------------------------
    // INIT METHOD
    //------------------------------------------------
    public void init(GameContainer container, StateBasedGame sbGame) throws SlickException
    {
        // Init fields
        this.container  = container;
        this.gameObject = sbGame;
        
        // Get version string
        this.getVersion();

        // Load background image
        this.backGround  = new Image("sprites/bgnd.png");

        // Load TITLE component
        Image img = new Image("./sprites/fonts/SubwaySpriteFont.png");
        // Create font objects
        this.textCityNum = new RenderFont("---test---", img, SubwaySpriteFont.getCharInfo(), 0, 0.75f, new Color(255,255,255,192));
        this.title       = new RenderFont("Line - Stations - Trains", img, SubwaySpriteFont.getCharInfo(), 0, 0.23f, Color.white);
        this.title.setPosition(1750, 50);

        this.hud = new HUD();


        // City number
        this.cityNum = 1;
        this.nbRails = 1;

        // PRNG
        this.prng = new Prng();
        this.init();

    }

    //------------------------------------------------
    // INIT METHOD
    //------------------------------------------------
    public void init()
    {
        // PRNG
        this.prng.setSeed(this.cityNum+123456);

        // CITY NUM TEXT
        this.textCityNum.setPosition(25,1080-50);
        this.textCityNum.setMiddleAlign(false);

        // Create train list
        this.trains = new ArrayList<Train>();

        // Create info list
        this.infos = new ArrayList<Info>();

        // Create railways
        this.railways = new ArrayList<Railway>();
        int railIndex = 0;
        int nbLoop = 1000;
        while( railIndex<this.nbRails && nbLoop>0 ){
            String name = Character.toString((char)(railIndex+'A'));
            if( this.createNewRailway(name, railIndex) ){
                railIndex++;
            }
            nbLoop--;
        }

    }

    //------------------------------------------------
    // RENDER METHOD
    //-------²-----------------------------------------
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
    {
        // Fit Screen
        MainLauncher.fitScreen(container, g);

        // Render BACKGROUND
        g.drawImage(this.backGround, 0, 0);

        // Render TEXTS
        this.textCityNum.render(container, game, g );

        // Render railways
        for(int i=0;i<this.railways.size();i++){
            this.railways.get(i).render(g, i == this.selectedRail);
        }

        // Render trains
        for(Train t:this.trains){
            t.render(g);
        }

        // Render stations
        for(int i=0;i<this.railways.size();i++){
            this.railways.get(i).renderStations(g);
        }
        for(int i=0;i<this.railways.size();i++){
            this.railways.get(i).renderStationNames(g);
        }

        // Render infos
        this.title.render(container, game, g);
        for(Info inf:this.infos){
            inf.render(container, game, g);
        }
        this.hud.render(container, game, g);

        // Render version number
        g.setColor( Color.white );
        g.drawString(this.version, 1300, 1080-30);
    }

    
    //------------------------------------------------
    // UPDATE METHOD
    //------------------------------------------------
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
    {   
        // CITY NUM TEXT
        this.textCityNum.setMessage("City #"+this.cityNum);
        for(Train t:this.trains){
            t.update(delta);
        }
    }
    
    
    //------------------------------------------------
    // KEYBOARD METHODS
    //------------------------------------------------
    @Override
    public void keyPressed(int key, char c)
    {
        switch(key)
        {
            // Quit game by pressing escape
            case Input.KEY_ESCAPE:
                this.quitGame();
                break;
            case Input.KEY_LEFT:
                this.decreaseCity();
                break;
            case Input.KEY_RIGHT:
                this.increaseCity();
                break;
            case Input.KEY_UP:
                this.increaseNbRails();
                break;
            case Input.KEY_DOWN:
                this.decreaseNbRails();
                break;
            case Input.KEY_F11:
                try {
                    this.container.setFullscreen(!this.container.isFullscreen());
                }catch(SlickException se){}
                break;
            // go to game
            // all other keys have no effect
            default :
                int index = 100000000;
                if( (int)c >= 'a' && (int)c <= 'z' ){
                    index = c - 'a';
                }
                if( (int)c >= 'A' && (int)c <= 'Z' ){
                    index = c - 'A';
                }
                if(index < this.railways.size()){
                    this.selectedRail = index;
                }
                else{
                    this.selectedRail = -1;
                }
                break;
        }
    }
    
    
    //------------------------------------------------
    // STATE ID METHOD
    //------------------------------------------------
    @Override
    public int getID()
    {
          return this.ID;
    }




    //------------------------------------------------
    // END OF STATE
    //------------------------------------------------
}