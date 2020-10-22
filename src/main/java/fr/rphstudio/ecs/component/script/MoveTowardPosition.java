/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.script;

import fr.rphstudio.ecs.component.common.Position;
import fr.rphstudio.ecs.component.physic.Physic2D;
import fr.rphstudio.ecs.component.render.RenderAnimations;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IScript;
import java.util.List;

/**
 *
 * @author GRIGNON FAMILY
 */
public class MoveTowardPosition implements IComponent, IScript
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
    private final long       id;
    private final String     name;
    private Position         sourcePos = null;
    private Position         targetPos = null;
    private RenderAnimations render    = null;
    private Physic2D         physic2d  = null;
    //----------------------------
    // Process properties
    //----------------------------
    private int   move;
    private float step;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    private void init(Entity entMover, Entity entFollower, float moveStep)
    {
        List<IComponent> compList;
        // Store positions and renders components
        compList = entMover.getComponent(Position.class);
        if(compList.size() > 0)
        {
            this.targetPos = (Position)(compList.get(0));
        }
        compList = entFollower.getComponent(Position.class);
        if(compList.size() > 0)
        {
            this.sourcePos = (Position)(compList.get(0));
        }
        compList = entFollower.getComponent(Physic2D.class);
        if(compList.size() > 0)
        {
            this.physic2d = (Physic2D)(compList.get(0));
        }
        compList = entFollower.getComponent(RenderAnimations.class);
        if(compList.size() > 0)
        {
            this.render = (RenderAnimations)(compList.get(0));
        }
        // Init moving variable
        this.move      = 0;
        this.step      = moveStep;
    }
    public MoveTowardPosition(Entity entMover, Entity entFollower, float moveStep, String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(entMover, entFollower, moveStep);
    }
    
    
    
    //================================================
    // PRIVATE METHODS
    //================================================
    private void checkMove()
    {
        if(this.targetPos != null)
        {
            // New position
            float newX = this.targetPos.getXPosition();
            float newY = this.targetPos.getYPosition();
            // Current position
            if(this.sourcePos != null)
            {
                int diffX = (int)(newX-this.sourcePos.getXPosition());
                int diffY = (int)(newY-this.sourcePos.getYPosition());
                // Get angle
                double angle = (180.0 * Math.atan2(diffY, diffX))/Math.PI;
                // Check  direciton
                move = 0;
                if(Math.abs(angle) < 60)
                {
                    // RIGHT
                    move |= MOVE_RIGHT;
                }
                else if(Math.abs(angle) > 120)
                {
                    // LEFT
                    move |= MOVE_LEFT;
                }
                if(angle < -30 && angle > -150)
                {
                    // UP
                    move |= MOVE_UP;
                }
                if(angle < 150 && angle > 30)
                {
                    // DOWN
                    move |= MOVE_DOWN;
                }
            }   
        }
    }
    private void modifyPosition(int delta)
    {
        // Local variables
        boolean left  = (move & MOVE_HORIZONTAL)==MOVE_LEFT;
        boolean right = (move & MOVE_HORIZONTAL)==MOVE_RIGHT;
        boolean top   = (move & MOVE_VERTICAL  )==MOVE_UP;
        boolean down  = (move & MOVE_VERTICAL  )==MOVE_DOWN;
        // Prepare position step
        float updateStep = this.step * delta;
        // Remove opposite directions
        if(top && down)
        {
            top  = false;
            down = false;
        }
        if(left && right)
        {
            left  = false;
            right = false;
        }
        // Check directions and update rendering
        if(this.render != null)
        {
            if(left && top)
            {
                this.render.selectAnimation("walkUPLEFT");
            }
            else if(left && down)
            {
                this.render.selectAnimation("walkDOWNLEFT");
            }
            else if(right && top)
            {
                this.render.selectAnimation("walkUPRIGHT");
            }
            else if(right && down)
            {
                this.render.selectAnimation("walkDOWNRIGHT");
            }
            else if(left)
            {
                this.render.selectAnimation("walkLEFT");
            }
            else if(right)
            {
                this.render.selectAnimation("walkRIGHT");
            }
            else if(top)
            {
                this.render.selectAnimation("walkUP");
            }
            else if(down)
            {
                this.render.selectAnimation("walkDOWN");
            }
        }
        if(this.sourcePos != null)
        {
            // Do without physic : LEFT/RIGHT/UP/DOWN
            if(this.physic2d == null)
            {
                if(left)
                {
                    this.sourcePos.increaseXPosition(-updateStep);
                }
                else if(right)
                {
                    this.sourcePos.increaseXPosition(updateStep);
                }
                if(top)
                {
                    this.sourcePos.increaseYPosition(-updateStep);
                }
                else if(down)
                {
                    this.sourcePos.increaseYPosition(updateStep);
                }
            }
            else
            {
                float dx = 0;
                float dy = 0;
                
                if(left)
                {
                    dx = -50;
                }
                else if(right)
                {
                    dx = 50;
                }
                if(top)
                {
                    dy = -50;
                }
                else if(down)
                {
                    dy = 50;
                }
                // Set speed modifier
                this.physic2d.setSpeed( dx, dy );
            }
        }
        // In all cases, update rendering position with Position value and Z order with Y value
        this.render.setZOrder(this.sourcePos.getYPosition());
        this.render.setPosition(this.sourcePos.getPosition());
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
        // update move variable
        this.checkMove();
        // According to move variable, update position value
        this.modifyPosition(delta);
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
