/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.script;

import fr.rphstudio.ecs.component.common.Position;
import fr.rphstudio.ecs.component.control.MouseHandler;
import fr.rphstudio.ecs.component.render.RenderAnimations;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IRender;
import fr.rphstudio.ecs.core.interf.IScript;
import java.util.List;

/**
 *
 * @author GRIGNON FAMILY
 */
public class MoveTowardAngle implements IComponent, IScript
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    //----------------------------
    // Personal properties
    //----------------------------
    private final long       id;
    private final String     name;
    private Position         sourcePos = null;
    //----------------------------
    // Process properties
    //----------------------------
    private double angle;
    private float  step;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    private void init(Position pos, double ang, float moveStep)
    {
        this.sourcePos = pos;
        this.angle     = ang;
        this.step      = moveStep;
    }
    public MoveTowardAngle(Position pos, double angle, float moveStep)
    {
        // Store name
        this.name = "Comp_MoveTowardAngle";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(pos, angle, moveStep);
    }
    public MoveTowardAngle(Position pos, double angle, float moveStep, String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(pos, angle, moveStep);
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
        float updateStep = this.step * delta;
        this.sourcePos.increaseXPosition((float)(updateStep*Math.cos(this.angle)));
        this.sourcePos.increaseYPosition((float)(updateStep*Math.sin(this.angle)));
    }
  
    
    
    //================================================
    // SETTERS
    //================================================
    
    
    
    //================================================
    // GETTERS
    //================================================
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}
