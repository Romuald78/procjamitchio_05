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
public class RectangleBox implements IComponent
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private final long     id;
    private final String   name;
    private       Vector2f topLeft;
    private       Vector2f bottomRight;
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public RectangleBox(Vector2f tl, Vector2f br)
    {
        // Store name
        this.name = "Class_Life";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init coords
        this.topLeft     = tl;
        this.bottomRight = br;
    }    
    public RectangleBox(Vector2f tl, Vector2f br, String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init coords
        this.topLeft     = tl;
        this.bottomRight = br;
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
   
    
    
    //================================================
    // SETTERS
    //================================================
    
    
    
    
    //================================================
    // GETTERS
    //================================================
    public float getLeftLimit()
    {
        return this.topLeft.x;
    }
    public float getTopLimit()
    {
        return this.topLeft.y;
    }
    public float getRightLimit()
    {
        return this.bottomRight.x;
    }
    public float getBottomLimit()
    {
        return this.bottomRight.y;
    }
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}
