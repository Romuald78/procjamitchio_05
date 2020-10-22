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
public class KeyboardHandler implements IComponent
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private final long                id;
    private final String              name;
    private       List<ControlAction> keys;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public KeyboardHandler()
    {
        // Store name
        this.name = "Class_KeyboardHandler";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init keys
        this.keys = new ArrayList<ControlAction>();
    }    
    public KeyboardHandler(String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init keys
        this.keys = new ArrayList<ControlAction>();
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
        if(!this.isKeyRegistered(nam) && !this.isKeyRegistered(k))
        {
            this.keys.add(0,new ControlAction(nam, k));
        }
        else
        {
            throw new Error("[ERROR] Action '"+nam+"' is already registered with Input #"+Integer.toString(k));
        }
    }
    public void removeActionKey(String nam)
    {
        // Get action index
        int idx = this.getKeyIndex(nam);
        if(idx != -1)
        {
            this.keys.remove(idx);
        }
        else
        {
            throw new Error("[ERROR][removeActionKey][String] Action '"+nam+"' is not registered");
        }
    }
    public void removeActionKey(int k)
    {
        // Get action index
        int idx = this.getKeyIndex(k);
        if(idx != -1)
        {
            this.keys.remove(idx);
        }
        else
        {
            throw new Error("[ERROR][removeActionKey][Input] Action '"+Integer.toString(k)+"' is not registered");
        }
    }
    public void setKeyActive(String nam, boolean flg)
    {
        int idx = this.getKeyIndex(nam);
        if(idx != -1)
        {
            this.keys.get(idx).setStatus(flg);
        }
        else
        {
            throw new Error("[ERROR][setActive][String] Action '"+nam+"' is not registered");
        }
    }
    public void setKeyActive(int k, boolean flg)
    {
        int idx = this.getKeyIndex(k);
        if(idx != -1)
        {
            this.keys.get(idx).setStatus(flg);
        }
        else
        {
            throw new Error("[ERROR][setActive][Input] Action '"+Integer.toString(k)+"' is not registered");
        }
    }
    
    
    
    //================================================
    // GETTERS
    //================================================
    public boolean isKeyRegistered(String nam)
    {
        // Get action index
        int idx = this.getKeyIndex(nam);
        // Return result
        return (idx != -1);
    }
    public boolean isKeyRegistered(int k)
    {
        // Get action index
        int idx = this.getKeyIndex(k);
        // Return result
        return (idx != -1);
    }
    public int getKeyIndex(String nam)
    {
        // Iit result
        int res = -1;
        // Loop for each action registered
        for(int i=0;i<this.keys.size();i++)
        {
            // check if this name is already in the list
            if( this.keys.get(i).getActionName().equals(nam) )
            {
                res = i;
                break;
            }
        }
        // return result
        return res;
    }
    public int getKeyIndex(int k)
    {
        // Iit result
        int res = -1;
        // Loop for each action registered
        for(int i=0;i<this.keys.size();i++)
        {
            // check if this name is already in the list
            if(this.keys.get(i).getActionKey() == k)
            {
                res = i;
                break;
            }
        }
        // return result
        return res;
    }
    public boolean isKeyActive(String nam)
    {
        // Get index for this action
        int idx = this.getKeyIndex(nam);
        if(idx != -1)
        {
            return this.keys.get(idx).isActive();
        }
        else
        {
            throw new Error("[ERROR][isActive][String] Action '"+nam+"' is not registered");
        }
    }
    public boolean isKeyActive(int k)
    {
        // Get index for this action
        int idx = this.getKeyIndex(k);
        if(idx != -1)
        {
            return this.keys.get(idx).isActive();
        }
        else
        {
            throw new Error("[ERROR][isActive][Input] Action '"+Integer.toString(k)+"' is not registered");
        }
    }
    
    // EDGES
    public boolean isKeyRisingEdge(String nam)
    {
        // Get index for this action
        int idx = this.getKeyIndex(nam);
        if(idx != -1)
        {
            return this.keys.get(idx).isRisingEdge();
        }
        else
        {
            throw new Error("[ERROR][isButtonRisingEdge][String] Action '"+nam+"' is not registered");
        }
    }
    public boolean isKeyRisingEdge(int k)
    {
        // Get index for this action
        int idx = this.getKeyIndex(k);
        if(idx != -1)
        {
            return this.keys.get(idx).isRisingEdge();
        }
        else
        {
            throw new Error("[ERROR][isButtonRisingEdge][String] Action '"+k+"' is not registered");
        }
    }
    public boolean isKeyFallingEdge(String nam)
    {
        // Get index for this action
        int idx = this.getKeyIndex(nam);
        if(idx != -1)
        {
            return this.keys.get(idx).isFallingEdge();
        }
        else
        {
            throw new Error("[ERROR][isButtonFallingEdge][String] Action '"+nam+"' is not registered");
        }
    }
    public boolean isKeyFallingEdge(int k)
    {
        // Get index for this action
        int idx = this.getKeyIndex(k);
        if(idx != -1)
        {
            return this.keys.get(idx).isFallingEdge();
        }
        else
        {
            throw new Error("[ERROR][isButtonFallingEdge][String] Action '"+k+"' is not registered");
        }
    }
    
    
    //================================================
    // END OF CLASS
    //================================================
}
