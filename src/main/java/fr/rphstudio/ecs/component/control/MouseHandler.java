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
public class MouseHandler implements IComponent
{   
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private final long                id;
    private final String              name;
    private       List<ControlAction> buttons;
    private       int                 xPos;
    private       int                 yPos;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public MouseHandler()
    {
        // Store name
        this.name = "Class_KeyboardHandler";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init properties
        this.buttons = new ArrayList<ControlAction>();
        this.xPos = 0;
        this.yPos = 0;
    }    
    public MouseHandler(String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init properties
        this.buttons = new ArrayList<ControlAction>();
        this.xPos = 0;
        this.yPos = 0;
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
    public void addActionKey(String nam, int k)
    {
        this.addActionKey(nam, k, false, false);
    }
    public void addActionKey(String nam, int k, boolean strActiv, boolean strInactiv)
    {
        // Check if this action name is not alreay in the list
        if(!this.isActionRegistered(nam) && !this.isActionRegistered(k))
        {
            this.buttons.add(0,new ControlAction(nam, k));
        }
        else
        {
            throw new Error("[ERROR] Action '"+nam+"' is already registered with Button #"+Integer.toString(k));
        }
    }
    public void removeActionKey(String nam)
    {
        // Get action index
        int idx = this.getActionIndex(nam);
        if(idx != -1)
        {
            this.buttons.remove(idx);
        }
        else
        {
            throw new Error("[ERROR][removeActionKey][String] Action '"+nam+"' is not registered");
        }
    }
    public void removeActionKey(int k)
    {
        // Get action index
        int idx = this.getActionIndex(k);
        if(idx != -1)
        {
            this.buttons.remove(idx);
        }
        else
        {
            throw new Error("[ERROR][removeActionKey][Input] Action '"+Integer.toString(k)+"' is not registered");
        }
    }
    public void setActive(String nam, boolean flg)
    {
        int idx = this.getActionIndex(nam);
        if(idx != -1)
        {
            this.buttons.get(idx).setStatus(flg);
        }
        else
        {
            throw new Error("[ERROR][setActive][String] Action '"+nam+"' is not registered");
        }
    }
    public void setActive(int k, boolean flg)
    {
        int idx = this.getActionIndex(k);
        if(idx != -1)
        {
            this.buttons.get(idx).setStatus(flg);
        }
        else
        {
            throw new Error("[ERROR][setActive][Input] Action '"+Integer.toString(k)+"' is not registered");
        }
    }
    public void updatePosition(int newPosX, int newPosY)
    {
        this.xPos = newPosX;
        this.yPos = newPosY;
    }
    
    
    
    //================================================
    // GETTERS
    //================================================
    public boolean isActionRegistered(String nam)
    {
        // Get action index
        int idx = this.getActionIndex(nam);
        // Return result
        return (idx != -1);
    }
    public boolean isActionRegistered(int k)
    {
        // Get action index
        int idx = this.getActionIndex(k);
        // Return result
        return (idx != -1);
    }
    public int getActionIndex(String nam)
    {
        // Iit result
        int res = -1;
        // Loop for each action registered
        for(int i=0;i<this.buttons.size();i++)
        {
            // check if this name is already in the list
            if( this.buttons.get(i).getActionName().equals(nam) )
            {
                res = i;
                break;
            }
        }
        // return result
        return res;
    }
    public int getActionIndex(int k)
    {
        // Iit result
        int res = -1;
        // Loop for each action registered
        for(int i=0;i<this.buttons.size();i++)
        {
            // check if this name is already in the list
            if(this.buttons.get(i).getActionKey() == k)
            {
                res = i;
                break;
            }
        }
        // return result
        return res;
    }
    public boolean isActive(String nam)
    {
        // Get index for this action
        int idx = this.getActionIndex(nam);
        if(idx != -1)
        {
            return this.buttons.get(idx).isActive();
        }
        else
        {
            throw new Error("[ERROR][isActive][String] Action '"+nam+"' is not registered");
        }
    }
    public boolean isActive(int k)
    {
        // Get index for this action
        int idx = this.getActionIndex(k);
        if(idx != -1)
        {
            return this.buttons.get(idx).isActive();
        }
        else
        {
            throw new Error("[ERROR][isActive][Input] Action '"+Integer.toString(k)+"' is not registered");
        }
    }
    public int getPositionX()
    {
        return this.xPos;
    }
    public int getPositionY()
    {
        return this.yPos;
    }
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}


