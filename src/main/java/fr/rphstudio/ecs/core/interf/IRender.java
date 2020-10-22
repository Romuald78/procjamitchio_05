/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.core.interf;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


/**
 *
 * @author GRIGNON FAMILY
 */
public interface IRender
{
    //================================================
    // PUBLIC METHODS
    //================================================ 
    // Render method
    public void render(GameContainer container, StateBasedGame game, Graphics g);    
    // Method to get Z axis value
    public float getZOrder();
    // Method to set position (for almost all components that have only one position to handle)
    public void setPosition(Vector2f newPos);
    // Method to set position of one of the subelements for this component (e.g.:particle system)
    public void setPosition(int eltIndex, Vector2f newPos);
    // Method to know if this is displayed or not
    public boolean isShown();
    // Set Z-order
    public void setZOrder(float val);
    
    
    
    //================================================
    // END OF INTERFACE
    //================================================  
}
