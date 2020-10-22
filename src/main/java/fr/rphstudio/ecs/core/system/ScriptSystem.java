/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.core.system;

import fr.rphstudio.ecs.component.script.LimitPosition;
import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IScript;
import fr.rphstudio.ecs.core.ECSWorld;
import java.util.List;
import org.newdawn.slick.SlickException;

/**
 *
 * @author GRIGNON FAMILY
 */
public class ScriptSystem
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private ECSWorld world;
    
    
    
    //================================================
    // CONSTRUCTORS
    //================================================
    public ScriptSystem(ECSWorld wrld) 
    {    
        // Store world reference
        this.world = wrld;
    }

    
    
    //================================================
    // PUBLIC METHODS
    //================================================
    public void update(int delta) throws SlickException
    {
        // Debug display
        //System.out.println("[SCRIPT_SYS]------------------------- START ------------------");
        long startTime;
        long endTime;
        startTime = System.nanoTime();
       
        // Browse all entities for this world
        List<Entity> entList = this.world.getAllEntities();
        for(int entIdx=0;entIdx<entList.size();entIdx++)
        {
            // Get component list for this entity
            List<IComponent> cmpList = entList.get(entIdx).getAllComponents();
            for(int cmpIdx=0;cmpIdx<cmpList.size();cmpIdx++)
            {
                // Check if this component is related to scripts (means it has an update method in it
                if( cmpList.get(cmpIdx) instanceof IScript )
                {
                    // Debug display
                    //System.out.println("[SCRIPT_SYS] a component has been found for update, call it");
                    // Call update method for this script
                    if(cmpList.get(cmpIdx).getClass() == LimitPosition.class)
                    {
                        //System.out.println("Call update methode for script "+cmpList.get(cmpIdx).getID());
                    }
                    ((IScript)(cmpList.get(cmpIdx))).update(delta);
                }
            }
        }
        // Measure execution time
        endTime = System.nanoTime();
        // System.out.println("Scripts Time = "+((endTime-startTime)/1000000.0)+" ms.");
        //System.out.println("[SCRIPT_SYS]------------------------- END ------------------");
    }

    
    
    //================================================
    // END OF CLASS
    //================================================    
}
