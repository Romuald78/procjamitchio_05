/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.physic.utils;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.joint.Joint;

/**
 *
 * @author GRIGNON FAMILY
 */
public class PhysicJointInfo
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private String  name;
    private Joint   joint;
    
    
    //================================================
    // CONSTRUCTORS
    //================================================
    public PhysicJointInfo(Joint jnt, String nam)
    {
        this.joint = jnt;
        this.name  = nam;
    }

    
    //================================================
    // GETTERS
    //================================================
    public String getJointName()
    {
        return this.name;
    }
    public Joint getJoint()
    {
        return this.joint;
    }
    

    //================================================
    // SETTERS
    //================================================
        
        
    
    //================================================
    // END OF CLASS
    //================================================
}