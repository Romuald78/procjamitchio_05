/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.sfx;

import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * @author GRIGNON FAMILY
 */
public class SoundFX implements IComponent
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private       long     id;
    private       String   name;
    private       Sound    sound;
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    private void init(String nam, String soundPath) throws SlickException
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Open sound file
        this.sound = new Sound(soundPath);
    }
    public SoundFX(String soundPath) throws SlickException
    {
        this.init("SoundFX component", soundPath);
    }    
    public SoundFX(String nam, String soundPath) throws SlickException
    {
        this.init(nam, soundPath);
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
    
    
    //================================================
    // PUBLIC METHODS
    //================================================
    public void playSound(float pitch, float volume)
    {
        this.sound.play(pitch,volume);
    }
    public void playSound()
    {
        this.sound.play();
    }
    
    
    
    //================================================
    // END OF CLASS
    //================================================
}
