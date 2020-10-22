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
public class Energy implements IComponent
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private final long     id;
    private final String   name;
    private       float    value;
    private       float    maxValue;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public Energy()
    {
        // Store name
        this.name = "Class_Life";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init life
        this.value    = 0;
        this.maxValue = 1;
    }    
    public Energy(String nam)
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
    public void setMaxValue(float S)
    {
        this.maxValue = S;
    }
    public void setValue(float S)
    {
        this.value = S;
        this.value = Math.min(this.maxValue, Math.max(0, this.value));
    }
    public void increaseValue(float dS)
    {
        this.value += dS;
        this.value  = Math.min(this.maxValue, Math.max(0, this.value));
    }
    
    
    
    //================================================
    // GETTERS
    //================================================
    public float getValue()
    {
        return this.value;
    }
    public float getMaxValue()
    {
        return this.maxValue;
    }
    public float getPercentValue()
    {
        return 100*(this.value/this.maxValue);
    }
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}
