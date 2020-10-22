/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.core.interf;

import java.util.List;
import org.dyn4j.dynamics.Body;


/**
 *
 * @author GRIGNON FAMILY
 */
public interface IPhysic
{
    //================================================
    // PUBLIC METHODS
    //================================================ 
    public int getBodyCount();
    public List<Body> getAllBodies();
    public Body getBody();
    public Body getBody(int index);
    public Body getBody(String name);
    
    
    
    //================================================
    // END OF INTERFACE
    //================================================  
}
