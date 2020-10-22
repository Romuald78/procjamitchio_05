/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.render;

import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IRender;
import fr.rphstudio.ecs.core.interf.IScript;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author GRIGNON FAMILY
 */
public class RenderAnimations implements IComponent, IRender, IScript
{
    //================================================
    // PRIVATE STRUCTURE
    //================================================
    private static class AnimData
    {
        private String    name;
        private Animation anim;
        private int       offsetX;       // used to center an animation frame on X-axis
        private int       offsetY;       // used to center an animation frame on Y-axis
        private Color     color;
        
        public AnimData(Animation animat, String nam, Color clr,int offX, int offY)
        {
            this.name    = nam;
            this.anim    = animat;
            this.offsetX = offX;
            this.offsetY = offY;
            this.color   = clr;
        }
        public String getAnimationName()
        {
            return this.name;
        }
        public Animation getAnimationObject()
        {
            return this.anim;
        }
        public int getOffsetX()
        {
            return this.offsetX;
        }
        public int getOffsetY()
        {
            return this.offsetY;
        }
        public Color getColor()
        {
            return this.color;
        }
        public void setColor(Color clr)
        {
            this.color = clr;
        }
    }
    
    
    
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
    private List<AnimData>   anims;
    private AnimData         currentAnimData;
    private Vector2f         currentPos;
    private float            zOrder;
    private boolean          isDebugDisplay;
    private boolean          isDisplayed;
    private float            angle;
    private float            scale;

    //================================================
    // CONSTRUCTOR
    //================================================
    private void init(int z)
    {
        // Init list of animations
        this.anims = new ArrayList<AnimData>();
        // Init current animation ref
        this.currentAnimData = null;
        // Init current position
        this.currentPos = new Vector2f(0,0);
        // Init Z position
        this.zOrder = z;
        // angle is zero by default
        this.angle = 0;
        // Scale by default is 1
        this.scale = 1;
        // Init debug display
        this.isDebugDisplay = false;
        // Display is always on by default
        this.isDisplayed    = true;
    }
    public RenderAnimations(int z)
    {
        // Store name
        this.name = "Class_RenderAnimations";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(z);
    }    
    public RenderAnimations(String nam, int z)
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
    public void update(int delta)
    {
        if(this.currentAnimData != null)
        {
            this.currentAnimData.getAnimationObject().update(delta);
        }   
    }

    public void drawCurrentFrame(Graphics g)
    {
        float xPos = 0;
        float yPos = 0;
        if(this.currentPos != null)
        {
            xPos = this.currentPos.x;
            yPos = this.currentPos.y;
        }
        // Put animation frame in an Image object
        Image imgTmp = this.currentAnimData.getAnimationObject().getCurrentFrame().getScaledCopy(this.scale);
        imgTmp.setCenterOfRotation(imgTmp.getWidth()/2, imgTmp.getHeight()/2);
        imgTmp.setRotation(this.angle);
        xPos -= this.currentAnimData.getOffsetX()*this.scale;
        yPos -= this.currentAnimData.getOffsetY()*this.scale;
        //if a color has been set, use it for FLASH draw
        if(this.currentAnimData.color == null)
        {
            g.drawImage(imgTmp, xPos, yPos);
        }
        else
        {
            g.drawImage(imgTmp, xPos, yPos, this.currentAnimData.color);
        }
    }

    public void drawCurrentFrameFlash(Graphics g,Color color){
        float xPos = 0;
        float yPos = 0;
        if(this.currentPos != null)
        {
            xPos = this.currentPos.x;
            yPos = this.currentPos.y;
        }
        // Put animation frame in an Image object
        Image imgTmp = this.currentAnimData.getAnimationObject().getImage(0).getScaledCopy(this.scale);
        imgTmp.setCenterOfRotation(imgTmp.getWidth()/2, imgTmp.getHeight()/2);
        imgTmp.setRotation(this.angle);
        xPos -= this.currentAnimData.getOffsetX()*this.scale;
        yPos -= this.currentAnimData.getOffsetY()*this.scale;
        g.setDrawMode(Graphics.MODE_NORMAL);
        imgTmp.drawFlash(xPos, yPos,imgTmp.getWidth(),imgTmp.getHeight(),color);
        //g.drawImage(imgTmp, xPos, yPos);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        if(this.currentAnimData != null)
        {
            // Draw current image of the animation using the optional color filter
            drawCurrentFrame(g);
            
            // Display offset center (debug)
            if(this.isDebugDisplay)
            {
                g.setColor(Color.black);
                g.fillOval(this.currentPos.x-2, this.currentPos.y-2, 5, 5);
            }
            /*
            // Display Z position for debug
            g.setColor(Color.yellow);
            g.drawString(Integer.toString((int)this.zOrder), xPos, yPos);
            //*/
        }
    }
    
    
    
    //================================================
    // SETTERS
    //================================================
    public void addAnimation(Animation anm, String nam)
    {
        this.addAnimation(anm, nam, null, 0, 0);
    }
    public void addAnimation(Animation anm, String nam, Color clr)
    {
        this.addAnimation(anm, nam, clr, 0, 0);
    }
    public void addAnimation(Animation anm, String nam, int offX, int offY)
    {
        this.addAnimation(anm, nam, null, offX, offY);
    }
    public void addAnimation(Animation anm, String nam, Color clr, int offX, int offY)
    {
        // Set autoupdate to false : update are performed by the UPDATE methods
        anm.setAutoUpdate(false);
        // Add animation (first place in list)
        this.anims.add(0,new AnimData(anm, nam, clr, offX, offY));
        // first animation is the default selected one
        if(this.currentAnimData == null)
        {
            this.selectAnimation(nam);
        }
    }
    public void setPosition(Vector2f newPos)
    {
        if(newPos != null)
        {
            this.currentPos.x = newPos.x;
            this.currentPos.y = newPos.y;
        }
    }
    public void setPosition(float x, float y)
    {
        this.currentPos.x = x;
        this.currentPos.y = y;
    }
    public void setPosition(int eltIndex, Vector2f newPos)
    {
        throw new Error("[ERROR] setPosition method is not used in RenderAnimations class !");
    }
    public void selectAnimation(int index)
    {
        this.currentAnimData = this.anims.get(index);
    }
    public void selectAnimation(String nam)
    {
        // Select animation first
        int idx = this.getAnimIndex(nam);
        if(idx != -1)
        {
            this.selectAnimation(idx);
        }
        else
        {
            throw new Error("[ERROR][selectAnimation] Animation '"+nam+"' does not exist");
        }
    }
    public void setZOrder(float z)
    {
        this.zOrder = z;
    }
    public void setAllColor(Color clr)
    {
        for(AnimData anm : this.anims)
        {
            anm.setColor(clr);
        }
    }
    public void setColor(Color clr)
    {
        this.currentAnimData.setColor(clr);
    }
    public void setAngle(float a)
    {
        this.angle = (a+360)%360;
    }
    public void addAngle(float dA)
    {
        this.angle = (this.angle+360+dA)%360;
    }
    public void setScale(float sca)
    {
        this.scale = sca;
    }
    public void increaseScale(float incCoef)
    {
        this.scale *= incCoef;
    }
    /*
    public void decrease 
    */
    public void setDebugDisplay(boolean dd)
    {
        this.isDebugDisplay = dd;
    }
    public void hide()
    {
        this.isDisplayed = false;
    }
    public void show()
    {
        this.isDisplayed = true;
    }
    
    
    
    //================================================
    // GETTERS
    //================================================
    public int getAnimIndex(String nam)
    {
        // Iit result
        int res = -1;
        // Loop for each action registered
        for(int i=0;i<this.anims.size();i++)
        {
            // check if this name is already in the list
            if( this.anims.get(i).getAnimationName().equals(nam) )
            {
                res = i;
                break;
            }
        }
        // return result
        return res;
    }
    public Animation getAnimation(String nam)
    {
        // Iit result
        Animation res = null;
        // Loop for each action registered
        for(int i=0;i<this.anims.size();i++)
        {
            // check if this name is already in the list
            if( this.anims.get(i).getAnimationName().equals(nam) )
            {
                res = this.anims.get(i).getAnimationObject();
                break;
            }
        }
        // return result
        return res;
    }
    public Image getCurrentImage()
    {
        return this.currentAnimData.getAnimationObject().getCurrentFrame().getScaledCopy(this.scale);
    }
    public int getCurrentAnimIndex()
    {
        return this.getAnimIndex(this.currentAnimData.name);
    }
    public Vector2f getPosition()
    {
        Vector2f res = new Vector2f(this.currentPos.x, this.currentPos.y);
        return res;
    }
    public float getAngle()
    {
        return this.angle;
    }
    public boolean isCurrentAnimationEnded()
    {
        return this.currentAnimData.anim.isStopped();
    }
    public boolean isShown()
    {
        return this.isDisplayed;
    }
    public float getScale()
    {
        return this.scale;
    }
    
    
    //================================================
    // END OF CLASS
    //================================================
}
