/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.core.interf;

import org.newdawn.slick.SlickException;


/**
 *
 * @author GRIGNON FAMILY
 */
public interface IScript
{
    //================================================
    // PUBLIC METHODS
    //================================================ 
    // Update method
    public void update(int delta) throws SlickException;    
    
    
    
    //================================================
    // END OF INTERFACE
    //================================================  
}
