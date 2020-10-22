/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.script;

import fr.rphstudio.ecs.component.common.Position;
import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IScript;

/**
 *
 * @author GRIGNON FAMILY
 */
public class LimitPosition implements IComponent, IScript
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
    private float     xMin;
    private float     xMax;
    private float     yMin;
    private float     yMax;
    private Position  cmpPos      = null;
    private boolean   destroyFlag = false;
    private Entity    parentEnt   = null;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    private void init(Entity parent, Position pos, float minx, float maxx, float miny, float maxy)
    {
        // store properties
        this.xMin   = minx;
        this.xMax   = maxx;
        this.yMin   = miny;
        this.yMax   = maxy;
        this.cmpPos = pos;
        this.parentEnt = parent;
        this.destroyFlag = false;
    }
    public LimitPosition(Entity parent, Position pos, float minx, float maxx, float miny, float maxy)
    {
        // Store name
        this.name = "Class_LimitPosition";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(parent, pos, minx, maxx, miny, maxy);
    }    
    public LimitPosition(Entity parent, Position pos, float minx, float maxx, float miny, float maxy, String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(parent, pos, minx, maxx, miny, maxy);
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
    public void update(int delta)
    {
        // Get position
        float x = this.cmpPos.getXPosition();
        float y = this.cmpPos.getYPosition();
        // Check for destroy mode
        if(this.destroyFlag)
        {
            if( (x<this.xMin)||(x>this.xMax)||(y<this.yMin)||(y>this.yMax) )
            {
                //System.out.println("Send destroy request "+this.parentEnt.getID());
                this.parentEnt.setDestroyRequest();
            }
        }
        // saturate x
        x = Math.min(x, this.xMax);
        x = Math.max(x, this.xMin);
        // Saturate y
        y = Math.min(y, this.yMax);
        y = Math.max(y, this.yMin);
        // Set position back
        this.cmpPos.setXPosition(x);
        this.cmpPos.setYPosition(y);
    }
    
    
    
    //================================================
    // SETTERS
    //================================================
    public void setDestroyMode(boolean status)
    {
        this.destroyFlag = status;
    }
    
    
    
    //================================================
    // GETTERS
    //================================================
    public boolean getDestroyMode()
    {
        return this.destroyFlag;
    }
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}
