/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.core.system;

import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IPhysic;
import fr.rphstudio.ecs.core.ECSWorld;
import java.util.List;
import org.dyn4j.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author GRIGNON FAMILY
 */
public class PhysicSystem
{
    
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private ECSWorld EcsWorld;
    
    
    
    //================================================
    // CONSTRUCTORS
    //================================================
    public PhysicSystem(ECSWorld wrld) 
    {    
        // Store world reference
        this.EcsWorld = wrld;
    }

    
    
    //================================================
    // PRIVATE METHODS
    //================================================
    
    
    
    //================================================
    // PUBLIC METHODS
    //================================================
    public void update(int delta)
    {
        long startTime;
        long endTime;
        startTime = System.nanoTime();
       
        // Update all physic component information
        this.EcsWorld.updatePhy(delta);
        
        // Measure execution time
        endTime = System.nanoTime();
        // System.out.println("Physic Time = "+((endTime-startTime)/1000000.0)+" ms.");
    }
    
    
    
    //================================================
    // END OF CLASS
    //================================================    
}
