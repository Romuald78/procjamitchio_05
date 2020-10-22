/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.physic.utils;

import org.dyn4j.dynamics.Body;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author GRIGNON FAMILY
 */
public class PhysicBodyInfo
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private String  name;
    private Body    body;
    
    
    //================================================
    // CONSTRUCTORS
    //================================================
    public PhysicBodyInfo(Body bdy, String nam)
    {
        this.body = bdy;
        this.name = nam;
    }

    
    //================================================
    // GETTERS
    //================================================
    public String getBodyName()
    {
        return this.name;
    }
    public Body getBody()
    {
        return this.body;
    }
    

    //================================================
    // SETTERS
    //================================================
        
        
    
    //================================================
    // END OF CLASS
    //================================================
}