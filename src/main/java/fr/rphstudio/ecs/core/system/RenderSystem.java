/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.core.system;

import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IRender;
import fr.rphstudio.ecs.core.ECSWorld;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author GRIGNON FAMILY
 */
public class RenderSystem
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private ECSWorld world;
    
    
    
    //================================================
    // CONSTRUCTORS
    //================================================
    public RenderSystem(ECSWorld wrld) 
    {    
        // Store world reference
        this.world = wrld;
    }

    
    
    //================================================
    // STATIC METHODS
    //================================================
    static Comparator<IRender> compareZ()
    {
        return new Comparator<IRender>()
        {
            // compare using attribute 1
            public int compare(IRender render1, IRender render2)
            {
                // Ascendant order (lower Y position is rendered first)
                float A = render1.getZOrder();
                float B = render2.getZOrder();
                if(A>B)
                {
                    return 1;
                }
                else if(A<B)
                {
                    return -1;
                }
                else
                {
                    return 0;
                }
            }
        };
    }
    
    
    
    //================================================
    // PUBLIC METHODS
    //================================================
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        // Debug display
        //System.out.println("[RENDER_SYS]------------------------- START ------------------");
        long startTime;
        long mid1Time;
        long mid2Time;
        long endTime;
        startTime = System.nanoTime();
        // Create rendering list
        List<IRender> renderList = new ArrayList<IRender>();
        // Browse all entities for this world
        List<Entity> entList = this.world.getAllEntities();
        for(int entIdx=0;entIdx<entList.size();entIdx++)
        {
            // Get component list for this entity
            List<IComponent> cmpList = entList.get(entIdx).getAllComponents();
            for(int cmpIdx=0;cmpIdx<cmpList.size();cmpIdx++)
            {
                // Check if this component is related to rendering (means it has an render method in it)
                if( cmpList.get(cmpIdx) instanceof IRender )
                {
                    // Debug display
                    // System.out.println("[RENDER_SYS] a component has been found for update, call it");
                    // Add target into list
                    if( ((IRender)(cmpList.get(cmpIdx))).isShown() )
                    {
                        renderList.add(((IRender)(cmpList.get(cmpIdx))));
                    }
                }
            }
        }
        mid1Time = System.nanoTime();
        // Now list has been filled : sort it by Z-order
        Collections.sort(renderList, RenderSystem.compareZ());
        // Now process all rendering
        mid2Time = System.nanoTime();
        for(int loop=0;loop<renderList.size();loop++)
        {
            renderList.get(loop).render(container, game, g);
        }
        // Measure execution time
        endTime = System.nanoTime();
        /*
        System.out.println("Render List  = "+((mid1Time-startTime)/1000000.0)+" ms.");
        System.out.println("Render Sort  = "+((mid2Time-mid1Time)/1000000.0)+" ms.");
        System.out.println("Render Blit  = "+((endTime-mid2Time)/1000000.0)+" ms.");
        System.out.println("Render total = "+((endTime-startTime)/1000000.0)+" ms.");
        //*/
        //System.out.println("[RENDER_SYS]------------------------- END ------------------");
    }

    
    
    //================================================
    // END OF CLASS
    //================================================    
}
