/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.script;

import fr.rphstudio.ecs.component.common.Position;
import fr.rphstudio.ecs.component.control.AnalogPadHandler;
import fr.rphstudio.ecs.component.render.RenderAnimations;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IScript;
import fr.rphstudio.subway.launcher.Common;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author GRIGNON FAMILY
 */
public class MovePositionWithPad implements IComponent, IScript
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
    private int   move;
    private float step;
    private boolean horizontalMode;
    private boolean verticalMode;
    private AnalogPadHandler cmpPad = null;
    private Position         cmpPos = null;
    private RenderAnimations render = null;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    private void init(AnalogPadHandler pad, Position pos, RenderAnimations rend, float moveStep)
    {
        // Init Local variables
        this.cmpPad = pad;
        this.cmpPos = pos;
        this.render = rend;
        // Init moving variable
        this.move = 0;
        this.step = moveStep;
        // By default, all axis are handled
        this.horizontalMode = true;
        this.verticalMode   = true;
    }
    public MovePositionWithPad(AnalogPadHandler pad, Position pos, RenderAnimations rend, float moveStep)
    {
        // Store name
        this.name = "Class_Move4Dirs";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(pad, pos, rend, moveStep);
    }    
    public MovePositionWithPad(AnalogPadHandler pad, Position pos, RenderAnimations rend, float moveStep, String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(pad, pos, rend, moveStep);
    }
    
    
    
    //================================================
    // PRIVATE METHODS
    //================================================
    private void modifyPosition(int delta)
    {
        // Do not perform if delta is zero
        if(delta <= 0)
        {
            return;
        }
        // Get update value
        float updateStep = this.step * delta;
        // Get direction values (analog)
        float dX = 0;
        float dY = 0;
        if(this.cmpPad != null)
        {
            if(this.horizontalMode)
            {
                dX = this.cmpPad.getDirectionX();
            }
            if(this.verticalMode)
            {
                dY = this.cmpPad.getDirectionY();
            }
        }
        if(this.cmpPos != null)
        {
            if(Math.abs(dX) > 0.1f)
            {
                this.cmpPos.increaseXPosition(updateStep*dX);
                this.cmpPos.increaseXPosition(updateStep*dX);
            }
            if(Math.abs(dY) > 0.1f)
            {
                this.cmpPos.increaseYPosition(updateStep*dY);
                this.cmpPos.increaseYPosition(updateStep*dY);
            }
        }
        // In all cases, update rendering position with Position value
        if(this.render != null && this.cmpPos != null)
        {
            this.render.setPosition( new Vector2f(this.cmpPos.getXPosition()*Common.NB_PIXELS_PER_METER,this.cmpPos.getYPosition()*Common.NB_PIXELS_PER_METER) );
        }
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
        // According to move variable, update position value
        this.modifyPosition(delta);
    }
    
    
    
    //================================================
    // SETTERS
    //================================================
    public void setHorizontalMode(boolean mode)
    {
        this.horizontalMode = mode;
    }
    public void setVerticalMode(boolean mode)
    {
        this.verticalMode = mode;
    }
    
    
    
    //================================================
    // GETTERS
    //================================================
    public boolean getHorizontalMode()
    {
        return this.horizontalMode;
    }
    public boolean getVerticalMode()
    {
        return this.verticalMode;
    }
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}
