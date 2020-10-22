/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.script;

import fr.rphstudio.ecs.component.common.Position;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IRender;
import fr.rphstudio.ecs.core.interf.IScript;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author GRIGNON FAMILY
 */
public class MoveRenderToPosition implements IComponent, IScript
{
    //================================================
    // CONSTANTS
    //================================================
    private final static int MOVE_UP         = 0x1;
    private final static int MOVE_DOWN       = 0x2;
    private final static int MOVE_LEFT       = 0x4;
    private final static int MOVE_RIGHT      = 0x8;
    private final static int MOVE_HORIZONTAL = MOVE_LEFT | MOVE_RIGHT;
    private final static int MOVE_VERTICAL   = MOVE_UP | MOVE_DOWN;
    
    
    
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    //----------------------------
    // Personal properties
    //----------------------------
    private final long   id;
    private final String name;
    private Position     cmpPos = null;
    private IRender      render = null;
    private float        xCoef = 1f;
    private float        yCoef = 1f;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    private void init(Position pos, IRender render)
    {
        // Store components
        this.cmpPos   = pos;
        this.render   = render;
    }
    public MoveRenderToPosition(Position pos, IRender render)
    {
        // Store name
        this.name = "ScriptMoveRenderToPosition";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(pos, render);
    }
    public MoveRenderToPosition(Position pos, IRender render, String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(pos, render);
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
        if(this.cmpPos != null && this.render != null)
        {
            // Get position
            float x = this.cmpPos.getXPosition();
            float y = this.cmpPos.getYPosition();
            // Set position
            this.render.setPosition( new Vector2f(x*this.xCoef, y*this.yCoef) );
        }
    }
  
    
    
    //================================================
    // SETTERS
    //================================================
    public void setCoefX(float xc)
    {
        this.xCoef = xc;
    }
    public void setCoefY(float yc)
    {
        this.yCoef = yc;
    }
    
    
    
    //================================================
    // GETTERS
    //================================================
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}
