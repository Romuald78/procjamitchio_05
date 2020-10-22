/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.core.interf;


/**
 *
 * @author GRIGNON FAMILY
 */
public interface IComponent
{
    //================================================
    // PUBLIC METHODS
    //================================================ 
    // Retrieve Component ID
    public long getID();
    // Retrieve Component Name
    public String getName();    
    
    
    
    //================================================
    // END OF INTERFACE
    //================================================  
}
