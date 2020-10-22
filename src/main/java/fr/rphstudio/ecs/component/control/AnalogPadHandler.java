/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.control;

import fr.rphstudio.ecs.component.control.utils.ControlAction;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author GRIGNON FAMILY
 */
public class AnalogPadHandler implements IComponent
{   
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private final long                id;
    private final String              name;
    private       float               xDir;
    private       float               yDir;
    private       int                 ctrlID;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public AnalogPadHandler(int controller)
    {
        // Store name
        this.name   = "Class_KeyboardHandler";
        // Get unique ID
        this.id     = CoreUtils.getNewID();
        // Init properties
        this.xDir   = 0;
        this.yDir   = 0;
        this.ctrlID = controller;
    }    
    public AnalogPadHandler(String nam, int controller)
    {
        // Store name
        this.name   = nam;
        // Get unique ID
        this.id     = CoreUtils.getNewID();
        // Init properties
        this.xDir   = 0;
        this.yDir   = 0;
        this.ctrlID = controller;
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
    public void updatePosition(float newPosX, float newPosY)
    {
        //System.out.println(newPosX+" "+newPosY);
        this.xDir = newPosX;
        this.yDir = newPosY;
    }
    
    
    
    //================================================
    // GETTERS
    //================================================
    public float getDirectionX()
    {
        return this.xDir;
    }
    public float getDirectionY()
    {
        return this.yDir;
    }
    public int getControllerID()
    {
        return this.ctrlID;
    }
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}


