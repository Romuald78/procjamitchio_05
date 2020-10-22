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
public class Score implements IComponent
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private final long     id;
    private final String   name;
    private       float    value;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public Score()
    {
        // Store name
        this.name = "Class_Life";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init life
        this.value = 0;
    }    
    public Score(String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init life
        this.value = 0;
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
    public void setValue(float S)
    {
        this.value = S;
    }
    public void increaseValue(float dS)
    {
        this.value += dS;
    }
    
    
    
    //================================================
    // GETTERS
    //================================================
    public float getValue()
    {
        return this.value;
    }
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}
