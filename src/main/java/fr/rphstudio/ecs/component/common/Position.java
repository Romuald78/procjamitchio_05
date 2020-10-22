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
public class Position implements IComponent
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private final long     id;
    private final String   name;
    private       Vector2f position;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public Position()
    {
        // Store name
        this.name = "Class_Position";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init position
        this.position = new Vector2f(0,0);
    }    
    public Position(String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init position
        this.position = new Vector2f(0,0);
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
    public void setPosition(Vector2f newPos)
    {
        this.position.x = newPos.x;
        this.position.y = newPos.y;
    }
    public void setXPosition(float newXPos)
    {
        this.position.x = newXPos;
    }
    public void setYPosition(float newYPos)
    {
        this.position.y = newYPos;
    }
    public void increaseXPosition(float dX)
    {
        this.position.x += dX;
    }
    public void increaseYPosition(float dY)
    {
        this.position.y += dY;
    }
    
    
    
    //================================================
    // GETTERS
    //================================================
    public Vector2f getPosition()
    {
        Vector2f res = new Vector2f(this.position.x, this.position.y);
        return res;
    }
    public float getXPosition()
    {
        return this.position.x;
    }
    public float getYPosition()
    {
        return this.position.y;
    }
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}
