/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.core;

import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.component.control.KeyboardHandler;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author GRIGNON FAMILY
 */
public class Entity implements IComponent
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    // Identifier of the Entity instance
    private final long       entityID;
    // Name of the Entity instance
    private final String     entityName;
    // List of Components
    private List<IComponent> components;
    // Destroy flag (Ask for a clean destroy)
    private boolean          destroyFlag;
    private int              destroyCount;
    // Store world instance
    private ECSWorld         world;
    
    
    //================================================
    // CONSTRUCTORS
    //================================================
    public Entity(ECSWorld wrld)
    {
        this.entityName = "Class_Entity";
        this.entityID   = CoreUtils.getNewID();
        // Init keys
        this.components = new ArrayList<IComponent>();
        // Init destroy flag
        this.destroyFlag  = false;
        this.destroyCount = 0;
        // Store world instance
        this.world = wrld;
    }
    public Entity(ECSWorld wrld, String nam)
    {
        this.entityName = nam;
        this.entityID   = CoreUtils.getNewID();
        // Init keys
        this.components = new ArrayList<IComponent>();
        // Init destroy flag
        this.destroyFlag = false;
        // Store world instance
        this.world = wrld;
    }
    
    
    //================================================
    // GETTERS
    //================================================
    //---------------------------
    // Personal information
    //---------------------------
    public long getID()
    {
        return this.entityID;
    }
    public String getName()
    {
        return this.entityName;
    }
    public boolean getDestroyRequest()
    {
        return this.destroyFlag;
    }
    public int getDestroyCount()
    {
        // First decrease counter and saturate to zero
        this.destroyCount--;
        this.destroyCount = Math.max(0, this.destroyCount);
        // then return new value of counter
        return this.destroyCount;
    }
    //---------------------------    
    // Components
    //---------------------------    
    public List<IComponent> getAllComponents()
    {
    	// Init return list (empty)
        List<IComponent> res = new ArrayList<IComponent>();
        // For each component of this entity
        for(int i=0;i<this.components.size();i++)
        {
            // Look deeper into Sub Entities
            if( this.components.get(i) instanceof Entity )
            {
                res.add(this.components.get(i));
                res.addAll(((Entity)(this.components.get(i))).getAllComponents() );
            }
            else
            {
	            // Add this component to the return list
	            res.add(this.components.get(i));
            }
        }
        // Return result
        return res;
    }
    public List<IComponent> getComponent(Class<?> classType)
    {
        // Init return list (empty)
        List<IComponent> res = new ArrayList<IComponent>();
        // For each component of this entity
        for(int i=0;i<this.components.size();i++)
        {
        	// Look deeper into Sub Entities
            if( this.components.get(i).getClass() == Entity.class )
            {
            	res.add(this.components.get(i));
                res.addAll( ((Entity)(this.components.get(i))).getComponent(classType) );
            }
            else
            {
	            // Check if this component class matches
	            if( this.components.get(i).getClass() == classType )
	            {
	                // Add this component to the return list
	                res.add(this.components.get(i));
	            }
            }            
        }
        // Return result
        return res;
    }
    public List<IComponent> getComponent(long id)
    {
    	// Init return list (empty)
        List<IComponent> res = new ArrayList<IComponent>();
        // For each component of this entity
        for(int i=0;i<this.components.size();i++)
        {
            // Add entoty itself if id is same
            if(this.components.get(i).getID() == id)
            {
                res.add(this.components.get(i));
            }
            // Look deeper into Sub Entities
            if( this.components.get(i).getClass() == Entity.class )
            {
                // Add sub components
                res.addAll( ((Entity)(this.components.get(i))).getComponent(id) );
            }
            else
            {
	            // Check if this component id matches
	            if( this.components.get(i).getID() == id )
	            {
	                // Add this component to the return list
	                res.add(this.components.get(i));
	            }
            }
            
        }
        // Return result
        return res;
    }
    public List<IComponent> getComponent(String nam)
    {
    	// Init return list (empty)
        List<IComponent> res = new ArrayList<IComponent>();
        // For each component of this entity
        for(int i=0;i<this.components.size();i++)
        {
            // Check if this component name matches
            if( this.components.get(i).getName() == nam )
            {
                // Add this component to the return list
                res.add(this.components.get(i));
            }            
            // Look deeper into Sub Entities
            if( this.components.get(i).getClass() == Entity.class )
            {
            	res.addAll( ((Entity)(this.components.get(i))).getComponent(nam) );
            }
        }
        // Return result
        return res;
    }
    
    
    
    //================================================
    // SETTERS
    //================================================
    public void setDestroyRequest(int count)
    {
        this.destroyFlag  = true;
        this.destroyCount = count;
    }
    public void setDestroyRequest()
    {
        this.setDestroyRequest(0);
    }
    //---------------------------    
    // Add Components
    //---------------------------    
    public void addComponent(IComponent comp)
    {
        if(comp != null)
        {
            this.components.add(comp);
        }
    }
    public void addComponents(List<IComponent> compList)
    {
        if(compList != null)
        {
            this.components.addAll(compList);
        }    
    }
    //---------------------------    
    // Remove Components
    //---------------------------    
    public void removeAllComponents()
    {
        this.components.clear();
    }
    public void removeComponents(Class<?> classType)
    {
        // Init deletion list
        List<IComponent> delList = new ArrayList<IComponent>();
        delList = this.getComponent(classType);
        this.components.removeAll(delList);
    }
    public void removeComponents(long compID)
    {
        // Init deletion list
        List<IComponent> delList = new ArrayList<IComponent>();
        delList = this.getComponent(compID);
        //System.out.println("Number of elements to remove "+delList.size());
        //System.out.println("                   compID "+compID);
        this.components.removeAll(delList);
    }
    
    
    //================================================
    // END OF CLASS
    //================================================
}


