/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.sfx;

import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 *
 * @author GRIGNON FAMILY
 */
public class MusicFX implements IComponent
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private       long     id;
    private       String   name;
    private       Music    music;
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    private void init(String nam, String musicPath) throws SlickException
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Open sound file
        this.music = new Music(musicPath);
    }
    public MusicFX(String musicPath) throws SlickException
    {
        this.init("MusicFX component", musicPath);
    }    
    public MusicFX(String nam, String musicPath) throws SlickException
    {
        this.init(nam, musicPath);
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
    public void playMusic()
    {
        this.music.play();
    }
    public void stopMusic()
    {
        this.music.stop();
    }
    public void pauseMusic()
    {
        this.music.pause();
    }
    public void resumeMusic()
    {
        this.music.resume();
    }
    
    
    //================================================
    // END OF CLASS
    //================================================
}
