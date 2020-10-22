/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.core.system;

import fr.rphstudio.ecs.component.control.AnalogPadHandler;
import fr.rphstudio.ecs.component.control.KeyboardHandler;
import fr.rphstudio.ecs.component.control.MouseHandler;
import fr.rphstudio.ecs.component.control.PadHandler;
import fr.rphstudio.ecs.core.Entity;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.ECSWorld;
import fr.rphstudio.ecs.core.utils.ControllerButtons;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author GRIGNON FAMILY
 */
public class ControlSystem
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private ECSWorld world;
    
    
    
    //================================================
    // CONSTRUCTORS
    //================================================
    public ControlSystem(ECSWorld wrld) 
    {    
        // Store world reference
        this.world = wrld;
    }

    
    
    //================================================
    // PRIVATE METHODS
    //================================================
    private void updateKeyOrMouseButtons(int key, boolean status)
    {
        // Debug display
        // System.out.println("[CTRL_SYS] key:"+Integer.toString(key)+" = "+Boolean.toString(status));
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
                // Check if this component is related to Keyboard control
                if(cmpList.get(cmpIdx).getClass() == KeyboardHandler.class)
                {
                    // System.out.println("[CTRL_SYS] a KeyboardHandling component has been found");
                    KeyboardHandler kbh = ((KeyboardHandler)(cmpList.get(cmpIdx)));
                    if( kbh.isKeyRegistered(key) )
                    {
                        // Debug display
                        // System.out.println("[CTRL_SYS] key has been registered in this component");
                        // Set status for this key/action
                        kbh.setKeyActive(key, status);
                    }
                }    
                else if(cmpList.get(cmpIdx).getClass() == MouseHandler.class)
                {
                    // System.out.println("[CTRL_SYS] a MouseHandling component has been found");
                    MouseHandler msh = ((MouseHandler)(cmpList.get(cmpIdx)));
                    if( msh.isActionRegistered(key) )
                    {
                        // Debug display
                        // System.out.println("[CTRL_SYS] key has been registered in this component");
                        // Set status for this key/action
                        msh.setActive(key, status);
                    }
                }
            }
        }
        // Measure execution time
        endTime = System.nanoTime();
        // System.out.println("Keys/Mousebuttons time = "+((endTime-startTime)/1000000.0)+" ms.");
    }
    private void updateAnalogPadDirection(GameContainer container)
    {
        long startTime;
        long endTime;
        startTime = System.nanoTime();
        // Get input object
        Input in = container.getInput();
        // System.out.println("[CTRL_SYS] key:"+Integer.toString(key)+" = "+Boolean.toString(status));
        // Browse all entities for this world
        List<Entity> entList = this.world.getAllEntities();
        for(int entIdx=0;entIdx<entList.size();entIdx++)
        {
            // Get component list for this entity
            List<IComponent> cmpList = entList.get(entIdx).getAllComponents();
            for(int cmpIdx=0;cmpIdx<cmpList.size();cmpIdx++)
            {
                // Check if this component is related to Mouse control
                if(cmpList.get(cmpIdx).getClass() == AnalogPadHandler.class)
                {
                    // Debug display
                    // System.out.println("[CTRL_SYS] a KeyboardHandling component has been found")
                    AnalogPadHandler pad = ((AnalogPadHandler)(cmpList.get(cmpIdx)));
                    // Get controller ID
                    int ctrlID = pad.getControllerID();
                    // Update pad position
                    int idAxisX = ControllerButtons.AXIS_ANALOG_X;
                    int idAxisY = ControllerButtons.AXIS_ANALOG_Y;
                    pad.updatePosition( in.getAxisValue(ctrlID, idAxisX), in.getAxisValue(ctrlID, idAxisY) );
                }                
            }
        }
        // Measure execution time
        endTime = System.nanoTime();
        // System.out.println("Analog Pad Time = "+((endTime-startTime)/1000000.0)+" ms.");
    }
    private void updateMousePosition(GameContainer container)
    {
        long startTime;
        long endTime;
        startTime = System.nanoTime();
        // Get mouse position
        int xMouse = container.getInput().getAbsoluteMouseX();
        int yMouse = container.getInput().getAbsoluteMouseY();
        // System.out.println("[CTRL_SYS] key:"+Integer.toString(key)+" = "+Boolean.toString(status));
        // Browse all entities for this world
        List<Entity> entList = this.world.getAllEntities();
        for(int entIdx=0;entIdx<entList.size();entIdx++)
        {
            // Get component list for this entity
            List<IComponent> cmpList = entList.get(entIdx).getAllComponents();
            for(int cmpIdx=0;cmpIdx<cmpList.size();cmpIdx++)
            {
                // Check if this component is related to Mouse control
                if(cmpList.get(cmpIdx).getClass() == MouseHandler.class)
                {
                    // Debug display
                    // System.out.println("[CTRL_SYS] a KeyboardHandling component has been found");
                    MouseHandler msh = ((MouseHandler)(cmpList.get(cmpIdx)));
                    msh.updatePosition(xMouse, yMouse);
                }                
            }
        }
        // Measure execution time
        endTime = System.nanoTime();
        // System.out.println("Mouse Position Time = "+((endTime-startTime)/1000000.0)+" ms.");
    }
    private void updatePadControls(int ctrlID, int buttonID, boolean status)
    {
        // Debug display
        // System.out.println("[CTRL_SYS] Pad:"+Integer.toString(ctrlID)+"/Button:"+Integer.toString(buttonID)+"="+Boolean.toString(status));
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
                // Check if this component is related to Keyboard control
                if(cmpList.get(cmpIdx).getClass() == PadHandler.class)
                {
                    // System.out.println("[CTRL_SYS] a PadHandling component has been found");
                    PadHandler pdh = ((PadHandler)(cmpList.get(cmpIdx)));
                    // check controller ID
                    if(pdh.getControllerID() == ctrlID)
                    {
                        if( (buttonID & ECSWorld.PAD_DIR_MASK)==ECSWorld.PAD_DIR_MASK )
                        {
                            // PAD Directions
                            if( pdh.isDirectionRegistered(buttonID) )
                            {
                                // Debug display
                                // System.out.println("[CTRL_SYS] direction has been registered in this component");
                                // Set status for this key/action
                                pdh.setDirectionActive(buttonID, status);
                            }
                        }
                        else
                        {
                            // PAD Buttons
                            if( pdh.isButtonRegistered(buttonID) )
                            {
                                // Debug display
                                // System.out.println("[CTRL_SYS] button has been registered in this component");
                                // Set status for this key/action
                                pdh.setButtonActive(buttonID, status);
                            }
                        }
                    }
                }
            }
        }
        // Measure execution time
        endTime = System.nanoTime();
        // System.out.println("Logic Pad Time = "+((endTime-startTime)/1000000.0)+" ms.");
    }
    

    
    //================================================
    // PUBLIC METHODS
    //================================================
    public void keyPressed(int key)
    {
        this.updateKeyOrMouseButtons(key, true);
    }
    public void keyReleased(int key)
    {
        this.updateKeyOrMouseButtons(key, false);
    }
    public void mouseMoved(GameContainer container, StateBasedGame game, int delta)
    {
        this.updateMousePosition(container);
    }
    public void controllerButtonPressed(int controller, int button)
    {
        this.updatePadControls(controller, button, true);
    }
    public void controllerButtonReleased(int controller, int button)
    {   
        this.updatePadControls(controller, button, false);
    }
    public void controllerDirectionPressed(int controller, int button)
    {
        this.updatePadControls(controller, button, true);
    }
    public void controllerDirectionReleased(int controller, int button)
    {   
        this.updatePadControls(controller, button, false);
    }
    public void analogPadMoved(GameContainer container, StateBasedGame game, int delta)
    {
        this.updateAnalogPadDirection(container);
    }
    
    
    //================================================
    // END OF CLASS
    //================================================    
}
