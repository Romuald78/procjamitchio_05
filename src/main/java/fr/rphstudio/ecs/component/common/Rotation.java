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
public class Rotation implements IComponent
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private final long     id;
    private final String   name;
    private       float    angle;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public Rotation()
    {
        // Store name
        this.name = "Class_Position";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init position
        this.angle = 0;
    }    
    public Rotation(String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init position
        this.angle = 0;
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
    // SETTERS
    //================================================
    public void setAngle(float newAngle)
    {
        this.angle = newAngle;
    }
    public void increaseAngle(float dX)
    {
        this.angle += dX;
    }
    
    
    
    //================================================
    // GETTERS
    //================================================
    public float getAngle()
    {
        return this.angle;
    }
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}
