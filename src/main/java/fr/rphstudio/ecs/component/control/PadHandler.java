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
public class PadHandler implements IComponent
{
    //================================================
    // PUBLIC CONSTANTS
    //================================================
    public final static int ANY_BUTTON = -999;
    
    
    
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private final long                id;
    private final String              name;
    private       List<ControlAction> buttons;
    private       List<ControlAction> directions;
    private       int                 ctrlID;
    
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    public PadHandler(int ctrl)
    {
        // Store name
        this.name = "Class_PadHandler";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Store controller ID
        this.ctrlID = ctrl;
        // Init keys
        this.buttons    = new ArrayList<ControlAction>();
        this.directions = new ArrayList<ControlAction>();
    }    
    public PadHandler(String nam, int ctrl)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Store controller ID
        this.ctrlID = ctrl;
        // Init keys
        this.buttons    = new ArrayList<ControlAction>();
        this.directions = new ArrayList<ControlAction>();
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
    // ADD
    public void addButton(String nam, int k)
    {
        // Check if this action name is not alreay in the list
        if(!this.isButtonRegistered(nam) && !this.isButtonRegistered(k))
        {
            this.buttons.add(0,new ControlAction(nam, k));
        }
        else
        {
            throw new Error("[ERROR] Button '"+nam+"' is already registered with Input #"+Integer.toString(k));
        }
    }
    public void addDirection(String nam, int k)
    {
        this.addDirection(nam, k, false, false);
    }
    public void addDirection(String nam, int k, boolean strActiv, boolean strInactiv)
    {
        // Check if this action name is not alreay in the list
        if(!this.isDirectionRegistered(nam) && !this.isDirectionRegistered(k))
        {
            this.directions.add(0,new ControlAction(nam, k));
        }
        else
        {
            throw new Error("[ERROR] Direction '"+nam+"' is already registered with Input #"+Integer.toString(k));
        }
    }
    // REMOVE
    public void removeButton(String nam)
    {
        // Get action index
        int idx = this.getButtonIndex(nam);
        if(idx != -1)
        {
            this.buttons.remove(idx);
        }
        else
        {
            throw new Error("[ERROR][removeButton][String] Action '"+nam+"' is not registered");
        }
    }
    public void removeButton(int k)
    {
        // Get action index
        int idx = this.getButtonIndex(k);
        if(idx != -1)
        {
            this.buttons.remove(idx);
        }
        else
        {
            throw new Error("[ERROR][removeButton][Input] Action '"+Integer.toString(k)+"' is not registered");
        }
    }
    public void removeDirection(String nam)
    {
        // Get action index
        int idx = this.getDirectionIndex(nam);
        if(idx != -1)
        {
            this.directions.remove(idx);
        }
        else
        {
            throw new Error("[ERROR][removeDirection][String] Action '"+nam+"' is not registered");
        }
    }
    public void removeDirection(int k)
    {
        // Get action index
        int idx = this.getDirectionIndex(k);
        if(idx != -1)
        {
            this.directions.remove(idx);
        }
        else
        {
            throw new Error("[ERROR][removeDirection][Input] Action '"+Integer.toString(k)+"' is not registered");
        }
    }
    // SET STATUS
    public void setButtonActive(String nam, boolean flg)
    {
        int idx = this.getButtonIndex(nam);
        if(idx != -1)
        {
            this.buttons.get(idx).setStatus(flg);
        }
        else
        {
            throw new Error("[ERROR][setButtonActive][String] Action '"+nam+"' is not registered");
        }
    }
    public void setButtonActive(int k, boolean flg)
    {
        int idx = this.getButtonIndex(k);
        if(idx != -1)
        {
            this.buttons.get(idx).setStatus(flg);
        }
        else
        {
            throw new Error("[ERROR][setButtonActive][Input] Action '"+Integer.toString(k)+"' is not registered");
        }
    }
    public void setDirectionActive(String nam, boolean flg)
    {
        int idx = this.getDirectionIndex(nam);
        if(idx != -1)
        {
            this.directions.get(idx).setStatus(flg);
        }
        else
        {
            throw new Error("[ERROR][setDirectionActive][String] Action '"+nam+"' is not registered");
        }
    }
    public void setDirectionActive(int k, boolean flg)
    {
        int idx = this.getDirectionIndex(k);
        if(idx != -1)
        {
            this.directions.get(idx).setStatus(flg);
        }
        else
        {
            throw new Error("[ERROR][setDirectionActive][Input] Action '"+Integer.toString(k)+"' is not registered");
        }
    }
    
    
    
    //================================================
    // GETTERS
    //================================================
    // CONTROLLER ID
    public int getControllerID()
    {
        return this.ctrlID;
    }
    // IS REGISTERED
    public boolean isButtonRegistered(String nam)
    {
        // Get action index
        int idx = this.getButtonIndex(nam);
        // Return result
        return (idx != -1);
    }
    public boolean isButtonRegistered(int k)
    {
        // Get action index
        int idx = this.getButtonIndex(k);
        // Return result
        return (idx != -1);
    }
    public boolean isDirectionRegistered(String nam)
    {
        // Get action index
        int idx = this.getDirectionIndex(nam);
        // Return result
        return (idx != -1);
    }
    public boolean isDirectionRegistered(int k)
    {
        // Get action index
        int idx = this.getDirectionIndex(k);
        // Return result
        return (idx != -1);
    }
    // GET INDEX
    public int getButtonIndex(String nam)
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
    public int getButtonIndex(int k)
    {
        // Iit result
        int res = -1;
        // Loop for each action registered
        for(int i=0;i<this.buttons.size();i++)
        {
            // check if this name is already in the list
            if( (this.buttons.get(i).getActionKey() == k)  )
            {
                res = i;
                break;
            }
        }
        // return result
        return res;
    }
    public int getDirectionIndex(String nam)
    {
        // Iit result
        int res = -1;
        // Loop for each action registered
        for(int i=0;i<this.directions.size();i++)
        {
            // check if this name is already in the list
            if( this.directions.get(i).getActionName().equals(nam) )
            {
                res = i;
                break;
            }
        }
        // return result
        return res;
    }
    public int getDirectionIndex(int k)
    {
        // Iit result
        int res = -1;
        // Loop for each action registered
        for(int i=0;i<this.directions.size();i++)
        {
            // check if this name is already in the list
            if(this.directions.get(i).getActionKey() == k)
            {
                res = i;
                break;
            }
        }
        // return result
        return res;
    }
    
    // IS ACTIVE
    public boolean isButtonActive(String nam)
    {
        // Get index for this action
        int idx = this.getButtonIndex(nam);
        if(idx != -1)
        {
            return this.buttons.get(idx).isActive();
        }
        else
        {
            throw new Error("[ERROR][isButtonActive][String] Action '"+nam+"' is not registered");
        }
    }
    public boolean isButtonActive(int k)
    {
        // Get index for this action
        int idx = this.getButtonIndex(k);
        if(idx != -1)
        {
            return this.buttons.get(idx).isActive();
        }
        else
        {
            throw new Error("[ERROR][isButtonActive][Input] Action '"+Integer.toString(k)+"' is not registered");
        }
    }
    // EDGES
    public boolean isButtonRisingEdge(String nam)
    {
        // Get index for this action
        int idx = this.getButtonIndex(nam);
        if(idx != -1)
        {
            return this.buttons.get(idx).isRisingEdge();
        }
        else
        {
            throw new Error("[ERROR][isButtonRisingEdge][String] Action '"+nam+"' is not registered");
        }
    }
    public boolean isButtonRisingEdge(int k)
    {
        // Get index for this action
        int idx = this.getButtonIndex(k);
        if(idx != -1)
        {
            return this.buttons.get(idx).isRisingEdge();
        }
        else
        {
            throw new Error("[ERROR][isButtonRisingEdge][String] Action '"+k+"' is not registered");
        }
    }
    public boolean isButtonFallingEdge(String nam)
    {
        // Get index for this action
        int idx = this.getButtonIndex(nam);
        if(idx != -1)
        {
            return this.buttons.get(idx).isFallingEdge();
        }
        else
        {
            throw new Error("[ERROR][isButtonFallingEdge][String] Action '"+nam+"' is not registered");
        }
    }
    public boolean isButtonFallingEdge(int k)
    {
        // Get index for this action
        int idx = this.getButtonIndex(k);
        if(idx != -1)
        {
            return this.buttons.get(idx).isFallingEdge();
        }
        else
        {
            throw new Error("[ERROR][isButtonFallingEdge][String] Action '"+k+"' is not registered");
        }
    }
    
    public boolean isDirectionActive(String nam)
    {
        // Get index for this action
        int idx = this.getDirectionIndex(nam);
        if(idx != -1)
        {
            return this.directions.get(idx).isActive();
        }
        else
        {
            throw new Error("[ERROR][isDirectionActive][String] Action '"+nam+"' is not registered");
        }
    }
    public boolean isDirectionActive(int k)
    {
        // Get index for this action
        int idx = this.getDirectionIndex(k);
        if(idx != -1)
        {
            return this.directions.get(idx).isActive();
        }
        else
        {
            throw new Error("[ERROR][isDirectionActive][Input] Action '"+Integer.toString(k)+"' is not registered");
        }
    }
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}


