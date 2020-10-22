/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.control.utils;

/**
 *
 * @author GRIGNON FAMILY
 */
public class ControlAction
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private String  actionName;
    private int     key;
    private boolean activeFlag;
    private boolean risingEdge;
    private boolean fallingEdge;

    
    
    //================================================
    // CONSTRUCTORS
    //================================================
    public ControlAction(String nam, int k)
    {
        this.actionName  = nam;
        this.key         = k;
        this.activeFlag  = false;
        this.risingEdge  = false;
        this.fallingEdge = false;
    }


    
    //================================================
    // GETTERS
    //================================================
    public String getActionName()
    {
        return this.actionName;
    }
    public int getActionKey()
    {
        return this.key;
    }
    public boolean isActive()
    {
        boolean result = this.activeFlag;
        // process stored active status
        //Send result
        return result;
    }
    public boolean isInactive()
    {
        boolean result = !(this.activeFlag);
        //Send result
        return result;
    }
    public boolean isRisingEdge()
    {
        // Read rising edge information
        boolean res = this.risingEdge;
        // Reset rising edge information
        // WARNING : as this information can be read only once per edge,
        //           it is important not to plug several components on
        //           this action because only one will get the "edge" information correctly (the first)
        this.risingEdge = false;
        // Send status back
        return res;
    }
    public boolean isFallingEdge()
    {
        // Read rising edge information
        boolean res = this.fallingEdge;
        // Reset rising edge information
        // WARNING : as this information can be read only once per edge,
        //           it is important not to plug several components on
        //           this action because only one will get the "edge" information correctly (the first)
        this.fallingEdge = false;
        // Send status back
        return res;
    }
    
    

    //================================================
    // SETTERS
    //================================================
    public void setStatus(boolean stat)
    {
        // Debug display
        // System.out.println("[KBH] action:"+this.actionName+" / key:"+Integer.toString(this.key)+" = "+Boolean.toString(stat));
        // Set status for this key/action
        boolean oldFlag = this.activeFlag;
        this.activeFlag = stat;
        // Check rising and falling edges
        if(this.activeFlag && !oldFlag)
        {
            this.risingEdge = true;
        }
        if(!this.activeFlag && oldFlag)
        {
            this.fallingEdge = true;
        }
    }
        
        
    
    //================================================
    // END OF CLASS
    //================================================
}