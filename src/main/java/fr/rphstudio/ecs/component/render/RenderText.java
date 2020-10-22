/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.render;

import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IRender;
import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author GRIGNON FAMILY
 */
public class RenderText implements IComponent, IRender
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    //----------------------------
    // Personal properties
    //----------------------------
    private final long       id;
    private final String     name;
    //----------------------------
    // Process properties
    //----------------------------
    private Vector2f         currentPos;
    private float            zOrder;
    private boolean          isDebugDisplay;
    private String           text;
    private Color            clr;

    private TrueTypeFont ttf;
      
    
    //================================================
    // CONSTRUCTOR
    //================================================
    private void init(int z)
    {
        // Init current position
        this.currentPos = new Vector2f(0,0);
        // Init Z position
        this.zOrder = z;
        // Init debug display
        this.isDebugDisplay = false;
        // Text and color
        this.text = "";
        this.clr  = Color.white;
        // Fonts
        Font font = new Font("./sprites/menus/ARACNE.OTF", Font.BOLD, 32);
        this.ttf  = new TrueTypeFont(font, true);
    
    }
    public RenderText(int z)
    {
        // Store name
        this.name = "Class_RenderText";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(z);
    }    
    public RenderText(String nam, int z)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(z);
    }
    
    
    
    //================================================
    // INTERFACE METHODS
    //================================================
    @Override
    public long getID()
    {
        return this.id;
    }
    @Override
    public String getName()
    {
        return this.name;
    }
    @Override
    public float getZOrder()
    {
        return this.zOrder;
    }
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        // display text
        //g.setColor(this.clr);
        //g.drawString(this.text, this.currentPos.x, this.currentPos.y);
        this.ttf.drawString(this.currentPos.x, this.currentPos.y, this.text, Color.blue);

        // Display offset center (debug)
        if(this.isDebugDisplay)
        {
            g.setColor(Color.yellow);
            g.drawOval(this.currentPos.x-3, this.currentPos.y-3, 6, 6);
        }
    }
    
    
    
    //================================================
    // SETTERS
    //================================================
    public void setPosition(Vector2f newPos)
    {
        if(newPos != null)
        {
            this.currentPos.x = newPos.x;
            this.currentPos.y = newPos.y;
        }
    }
    public void setPosition(int eltIndex, Vector2f newPos)
    {
        throw new Error("[ERROR] setPosition method is not used in RenderText class !");
    }
    public void setZOrder(float z)
    {
        this.zOrder = z;
    }
    public void setColor(Color newClr)
    {
        this.clr = newClr;
    }
    public void setDebugDisplay(boolean dd)
    {
        this.isDebugDisplay = dd;
    }
    public void setText(String newText)
    {
        this.text = newText;
    }
    
    
    
    //================================================
    // GETTERS
    //================================================
    public Vector2f getPosition()
    {
        Vector2f res = new Vector2f(this.currentPos.x, this.currentPos.y);
        return res;
    }
    public String getText()
    {
        return this.text;
    }
    public boolean isShown()
    {
        return true;
    }
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}
