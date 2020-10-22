/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.common;

import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author GRIGNON FAMILY
 */
public class Life implements IComponent
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private final long     id;
    private final String   name;
    private       float    curLife;
    private       float    maxLife;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public Life()
    {
        // Store name
        this.name = "Class_Life";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init life
        this.maxLife = 1;
        this.curLife = 1;
    }    
    public Life(String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init life
        this.maxLife = 1;
        this.curLife = 1;
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
    
    
    //================================================
    // PRIVATE METHODS
    //================================================
    private void checkLife()
    {
        // Saturate life between 0 and maxLife
        this.curLife = Math.max( Math.min(this.curLife, this.maxLife), 0);
    }
    
    
    //================================================
    // SETTERS
    //================================================
    public void setMaxLife(float maxL)
    {
        this.maxLife = maxL;
        this.checkLife();
    }
    public void setLife(float L)
    {
        this.curLife = L;
        this.checkLife();
    }
    public void increaseLife(float dL)
    {
        this.curLife += dL;
        this.checkLife();
    }
    
    
    
    //================================================
    // GETTERS
    //================================================
    public float getLife()
    {
        return this.curLife;
    }
    public float getLifePercent()
    {
        return (this.curLife/this.maxLife);
    }
    public float getMaxLife()
    {
        return this.maxLife;
    }
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}
