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

/**
 *
 * @author GRIGNON FAMILY
 */
public class SetZOrderFromYPosition implements IComponent, IScript
{
    
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
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    private void init(Position pos, IRender render)
    {
        // Store components
        this.cmpPos   = pos;
        this.render   = render;
    }
    public SetZOrderFromYPosition(Position pos, IRender render)
    {
        // Store name
        this.name = "ScriptMoveRenderToPosition";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(pos, render);
    }
    public SetZOrderFromYPosition(Position pos, IRender render, String nam)
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
            // Set position
            this.render.setZOrder( this.cmpPos.getYPosition() );
        }
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
