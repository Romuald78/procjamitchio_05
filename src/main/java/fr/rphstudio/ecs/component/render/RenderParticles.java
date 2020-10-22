/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.render;

import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IRender;
import fr.rphstudio.ecs.core.interf.IScript;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author GRIGNON FAMILY
 */
public class RenderParticles implements IComponent, IRender, IScript
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    //----------------------------
    // Personal properties
    //----------------------------
    private final long       id;
    private final String     name;
    //----------------------------
    // Process properties
    //----------------------------
    private float            zOrder;
    private boolean          isDebugDisplay;

    private ParticleSystem   system;
    private List<ConfigurableEmitter> emitters;
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    private void init(int z, String imgPath, int maxParticles, int blendMode, boolean isRemoveCompletedEmitters) throws SlickException
    {
        // Init Z position
        this.zOrder = z;
        // Init debug display
        this.isDebugDisplay = false;
        // open image used for this particle system, and load it
        Image image    = new Image(imgPath, false);
        this.system = new ParticleSystem(image,maxParticles);    // e.g.: 1000
        // Set some parameters
        this.system.setBlendingMode(blendMode);                  // e.g.: ParticleSystem.BLEND_ADDITIVE
        this.system.setRemoveCompletedEmitters(isRemoveCompletedEmitters);
        // Init emitter list
        this.emitters = new ArrayList<ConfigurableEmitter>();
    }
    public RenderParticles(int z, String imgPath, int maxParticles, int blendMode, boolean isRemoveCompletedEmitters) throws SlickException
    {
        // Store name
        this.name = "Class_RenderText";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(z,imgPath,maxParticles,blendMode, isRemoveCompletedEmitters);
    }    
    public RenderParticles(String nam, int z, String imgPath,int maxParticles, int blendMode, boolean isRemoveCompletedEmitters) throws SlickException
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Common process
        this.init(z,imgPath,maxParticles,blendMode, isRemoveCompletedEmitters);
    }
   
    
    //================================================
    // INTERFACE METHODS
    //================================================
    @Override
    public long getID()
    {
        return this.id;
    }
    @Override
    public String getName()
    {
        return this.name;
    }
    @Override
    public float getZOrder()
    {
        return this.zOrder;
    }
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        // display particles
        this.system.render();
        // Display specific infos(debug)
        if(this.isDebugDisplay)
        {
            g.setColor(Color.blue);
            g.drawOval(this.system.getPositionX()-11, this.system.getPositionY()-11, 22, 22);
        }
    }
    @Override
    public void update(int deltaMs)
    {
        // Update particle positions, sizes and colors
        this.system.update(deltaMs);
    }
    
    
    //================================================
    // SETTERS
    //================================================
    public void setPosition(Vector2f newPos)
    {
        throw new Error("[ERROR] setPosition method is not used in RenderParticles class !");
    }
    public void setPosition(int emitterIndex, Vector2f newPos)
    {
        if(newPos != null)
        {
            if(emitterIndex < this.emitters.size())
            {
                this.emitters.get(emitterIndex).setPosition(newPos.x, newPos.y, false);
            }
            else
            {
                throw new Error("[ERROR] particle emitter index does not exist !");
            }
        }
        else
        {
            throw new Error("[ERROR] position object is null !");
        }
    }
    public void setZOrder(float z)
    {
        this.zOrder = z;
    }
    public void setDebugDisplay(boolean dd)
    {
        this.isDebugDisplay = dd;
    }
    public void addEmitter(String emitterXMLPath, Vector2f newPos) throws IOException
    {
        ConfigurableEmitter emit;
        File xmlFile = new File(emitterXMLPath);
        emit = ParticleIO.loadEmitter(xmlFile);
        emit.setPosition( newPos.x, newPos.y, false);
        this.system.addEmitter(emit);
        this.emitters.add(emit);
    }
    public void removeAllEmitters()
    {
        this.emitters.clear();
        this.system.removeAllEmitters();
    }
    
    
    //================================================
    // GETTERS
    //================================================
    public ConfigurableEmitter getEmitter(int index)
    {
        return this.emitters.get(index);
    }
    public Vector2f getPosition(int emitterIndex)
    {
        // Init position
        Vector2f res = new Vector2f(0, 0);
        // Check index is correct
        if(emitterIndex<this.emitters.size())
        {
            res.x = this.emitters.get(emitterIndex).getX();
            res.y = this.emitters.get(emitterIndex).getY();
        }
        else
        {
            throw new Error("[ERROR] particle emitter index is not correct !");
        }
        // Return position
        return res;
    }
    public boolean isShown()
    {
        return true;
    }
    

    //================================================
    // END OF CLASS
    //================================================    
}


/*

        ConfigurableEmitter emit;
                        try
                        {
                            File xmlFile = new File(emitterXMLPath);
                            emit = ParticleIO.loadEmitter(xmlFile);
                        }
                        catch(Exception e){throw new Error(e);};
                        emit.setPosition( this.icons.get(ic1).getPosition().x, this.icons.get(ic1).getPosition().y, false);
                        this.coldSystem.addEmitter(emit);

        
//*/
