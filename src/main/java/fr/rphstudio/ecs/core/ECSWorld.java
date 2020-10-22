/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.core;

import fr.rphstudio.ecs.component.physic.Physic2D;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.system.ControlSystem;
import fr.rphstudio.ecs.core.system.PhysicSystem;
import fr.rphstudio.ecs.core.system.RenderSystem;
import fr.rphstudio.ecs.core.system.ScriptSystem;
import java.util.ArrayList;
import java.util.List;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.Joint;
import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author GRIGNON FAMILY
 */
public class ECSWorld
{
    //================================================
    // PUBLIC PROPERTIES
    //================================================
    public final static int PAD_DIR_MASK  = 0x7FFFFF00;
    public final static int PAD_DIR_LEFT  = 0 | PAD_DIR_MASK;
    public final static int PAD_DIR_RIGHT = 1 | PAD_DIR_MASK;
    public final static int PAD_DIR_UP    = 2 | PAD_DIR_MASK;
    public final static int PAD_DIR_DOWN  = 3 | PAD_DIR_MASK;
    
    
    
    //================================================
    // STATIC 
    //================================================
    // Unique identifier for all World classes
    static private long lastID = 0;
    static public  long getNewID()
    {
        ECSWorld.lastID++;
        return ECSWorld.lastID;
    }
    
    
    
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    // Identifier of the World instance
    private long          worldID;
    // Name of the World instance
    private String        worldName;
    // List of entities
    private List<Entity>  entities; 
    // Physical instance
    private World         phyWorld;
    // System properties
    private RenderSystem  renderSys;
    private ScriptSystem  scriptSys;
    private ControlSystem ctrlSys;
    private PhysicSystem  physicSys;
    
    
    //================================================
    // PRIVATE METHODS (physic object management)
    //================================================
    private void registerPhysic(IComponent phyCmp)
    {
        if( phyCmp.getClass() == Physic2D.class )
        {
            // For all bodies of this component
            List<Body> bdyList = ((Physic2D)phyCmp).getAllBodies();
            for(Body bdy:bdyList)
            {
                // Check if this component is not already registered
                if(!this.phyWorld.containsBody(bdy))
                {
                    this.phyWorld.addBody(bdy);
                }
            }
            // For all joints
            List<Joint> jntList = ((Physic2D)phyCmp).getAllJoints();
            for(Joint jnt:jntList)
            {
                // Check if this component is not already registered
                if(!this.phyWorld.containsJoint(jnt))
                {
                    this.phyWorld.addJoint(jnt);
                }
            }
        }
    }
    private void unregisterPhysic(IComponent phyCmp)
    {
        if( phyCmp.getClass() == Physic2D.class )
        {       
            // First unregister all joints
            List<Joint> jntList = ((Physic2D)phyCmp).getAllJoints();
            for(Joint jnt:jntList)
            {
                // Check if this component is well registered
                if(this.phyWorld.containsJoint(jnt))
                {
                    this.phyWorld.removeJoint(jnt);
                }
            }
            // Then unregister all bodies
            List<Body> bdyList = ((Physic2D)phyCmp).getAllBodies();
            for(Body bdy:bdyList)
            {
                // Check if this component is well registered
                if(this.phyWorld.containsBody(bdy))
                {
                    this.phyWorld.removeBody(bdy);
                }
            }
        }
    }
    private void checkForPhysicComponent(Entity ent, boolean isRegister)
    {
        List<IComponent> compList = ent.getAllComponents();
        for(IComponent c : compList)
        {
            if(isRegister)
            {
                this.registerPhysic(c);
            }
            else
            {
                this.unregisterPhysic(c);
            }
        }
    }
    private void checkForPhysicComponent(List<Entity> entList, boolean isRegister)
    {
        for(Entity e : entList)
        {
            this.checkForPhysicComponent(e, isRegister);
        }
    }
    
    
    //================================================
    // PRIVATE METHODS (entity destruction)
    //================================================
    private void removeEntitiesToDestroy()
    {
        // Init list to destroy
        List<Entity> destroyList = new ArrayList<Entity>();
        // Check for each entity
        for(Entity ent : this.entities)
        {
            // If the destroy request is enabled
            if( ent.getDestroyRequest() )
            {
                // Check if counter of destruction has reached the end (automatically decreased when method is called)
                if( ent.getDestroyCount() <= 0)
                {   
                    destroyList.add(ent);
                }
            }
        }
        // Remove entities
        for(Entity ent : destroyList)
        {
            // First check if there some physic components to unregister in this entity
            this.checkForPhysicComponent(ent, false);
            // then just remove entity
            this.entities.remove(ent);
        }
    }
    
    
    //================================================
    // CONSTRUCTORS
    //================================================
    private void setNewWorld(String nam, long id)
    {
        // Store parameters
        this.worldID   = id;
        this.worldName = nam;
        // Init entity list
        this.entities = new ArrayList<Entity>();
        // Instanciate systems
        this.renderSys = new RenderSystem (this);
        this.scriptSys = new ScriptSystem (this);
        this.ctrlSys   = new ControlSystem(this); 
        this.physicSys = new PhysicSystem(this); 
        // Instanciate new physical world (default is no gravity)
        this.phyWorld = new World();
        this.phyWorld.setGravity( new Vector2(0,0) );
    }
    public ECSWorld()
    {
        this.setNewWorld("NO_NAME", ECSWorld.getNewID());
    }
    public ECSWorld(String nam)
    {
        this.setNewWorld(nam, ECSWorld.getNewID());
    }
    
    
    //================================================
    // GETTERS
    //================================================
    //---------------------------
    // Personal information
    //---------------------------
    public long getID()
    {
        return this.worldID;
    }
    public String getName()
    {
        return this.worldName;
    }
    //---------------------------    
    // Entities
    //---------------------------    
    public List<Entity> getAllEntities()
    {
        // Init return list (copy of full list)
        List<Entity> res = new ArrayList<Entity>(this.entities);
        // Return result
        return res;
    }
    public List<Entity> getEntity(Class classType)
    {
        // Init return list (empty)
        List<Entity> res = new ArrayList<Entity>();
        // For each sub entity of this entity
        for(int i=0;i<this.entities.size();i++)
        {
            // Check if id is the same
            if( this.entities.get(i).getClass() == classType )
            {
                // Add this component to the return list
                res.add(this.entities.get(i));
            }
        }
        // Return result
        return res;
    }
    public List<Entity> getEntity(long id)
    {
        // Init return list (empty)
        List<Entity> res = new ArrayList<Entity>();
        // For each sub entity of this entity
        for(int i=0;i<this.entities.size();i++)
        {
            // Check if id is the same
            if(this.entities.get(i).getID() == id)
            {
                // Add this component to the return list
                res.add(this.entities.get(i));
            }
        }
        // Return result
        return res;
    }
    public List<Entity> getEntity(String nam)
    {
        // Init return list (empty)
        List<Entity> res = new ArrayList<Entity>();
        // For each sub entity of this entity
        for(int i=0;i<this.entities.size();i++)
        {
            // Check if id is the same
            if( this.entities.get(i).getName().equals(nam) )
            {
                // Add this component to the return list
                res.add(this.entities.get(i));
            }
        }
        // Return result
        return res;
    }
    public World getPhysicalWorld()
    {
        return this.phyWorld;
    }
    
    
    //================================================
    // SETTERS
    //================================================    
    public void addEntity(Entity ent)
    {
        if(ent != null)
        {
            this.entities.add(ent);
            // Now the entity has been added, check for its Physic component and add them to the physic system
            this.checkForPhysicComponent(ent,true);
        }
    }
    public void addEntities(List<Entity> entList)
    {
        if(entList != null)
        {
            this.entities.addAll(entList);
            // Now the entities have been added, check for its Physic component and add them to the physic system
            this.checkForPhysicComponent(entList,true);
        }
    }
    public void setPhyGravity(Vector2f gravVector)
    {
        Vector2 vg = new Vector2( gravVector.x, gravVector.y );
        this.phyWorld.setGravity( vg );
    }
    
    
    //================================================
    // SYSTEMS
    //================================================    
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException
    {
        // Call all render components and execute them
        this.renderSys.render(container, game, g);

        // Debug display (content of the world)
        //this.displayContent(g, 250, 200);
    }
    public void updatePhy(int delta)
    {
        this.phyWorld.update( delta/1000.0 );
    }
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
    {   
        long startTime;
        long endTime;
        startTime = System.nanoTime();
        
        // Check if we have to remove some entities from the world
        this.removeEntitiesToDestroy();
        
        // Run InputControlSystem
        this.ctrlSys.mouseMoved(container, game, delta);
        this.ctrlSys.analogPadMoved(container, game, delta);
        
        // Call physic components
        this.physicSys.update(delta);
        
        // Call all script components
        this.scriptSys.update(delta);
        
        // Measure execution time
        endTime = System.nanoTime();
        // System.out.println("TOTAL time = "+((endTime-startTime)/1000000.0)+" ms.");
    }
    public void keyPressed(int key)
    {
        // Run InputControlSystem
        this.ctrlSys.keyPressed(key);
    }
    public void keyReleased(int key)
    {
        // Run InputControlSystem
        this.ctrlSys.keyReleased(key);
    }
    public void controllerButtonPressed(int controller, int button)
    {
        this.ctrlSys.controllerButtonPressed(controller, button);
    }
    public void controllerButtonReleased(int controller, int button)
    {
        this.ctrlSys.controllerButtonReleased(controller, button);
    }
    public void controllerDirectionPressed(int controller, int direction)
    {
        this.ctrlSys.controllerDirectionPressed(controller, direction);
    }
    public void controllerDirectionReleased(int controller, int direction)
    {
        this.ctrlSys.controllerDirectionReleased(controller, direction);
    }
    
    
    
    //================================================
    // DEBUG
    //================================================
    public void displayContent(Graphics g, float x, float y)
    {
        int zOf7 = 0;
        // Display world name
        g.setColor(new Color(255,255,255,64));
        g.drawString(this.getName(),x,y+zOf7);
        zOf7 += 20;
        // Get entity list for this world
        List<Entity> entList = this.getAllEntities();
        for(int entIdx=0;entIdx<entList.size();entIdx++)
        {
            // Display each world entity name
            g.setColor(Color.blue);
            g.drawString(entList.get(entIdx).getName(),x+20,y+zOf7);
            zOf7 += 20;            
            // Get component list for this entity
            List<IComponent> cmpList = entList.get(entIdx).getAllComponents();
            for(int cmpIdx=0;cmpIdx<cmpList.size();cmpIdx++)
            {
                // Display each component name
                g.setColor(Color.green);
                if( cmpList.get(cmpIdx) instanceof Entity )
                {
                    g.setColor(Color.blue);
                }
                g.drawString(cmpList.get(cmpIdx).getName(),x+40,y+zOf7);
                zOf7 += 20;
                // If this component is an entity, display sub components
                if( cmpList.get(cmpIdx) instanceof Entity )
                {
                    List<IComponent> subCmpList = ((Entity)(cmpList.get(cmpIdx))).getAllComponents();
                    for(int cmpIdx2=0;cmpIdx2<subCmpList.size();cmpIdx2++)
                    {
                        // Display each component name
                        g.setColor(Color.red);
                        g.drawString(subCmpList.get(cmpIdx2).getName(),x+60,y+zOf7);
                        zOf7 += 20;
                    }        
                }
            }
        }
    }
    
    
    
    //================================================
    // END OF CLASS
    //================================================    
}
