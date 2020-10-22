/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.subway.launcher;

import org.newdawn.slick.Color;

/**
 *
 * @author GRIGNON FAMILY
 */
public class Common
{
    //---------------------------------------------------------
    // PROGRAM CONSTANTS
    //---------------------------------------------------------
    // Screen default dimensions (automatically resized, if the screen has a different resolution)
    public static final float   SCREEN_WIDTH   = 1920.0f;
    public static final float   SCREEN_HEIGHT  = 1080.0f;    
    // Colors used during game state transitions
    public static final Color   COLOR_FADE_IN  = Color.orange;
    public static final Color   COLOR_FADE_OUT = Color.yellow;
    // Timings used during game state transitions
    public static final int     TIME_FADE_IN   = 125*2;
    public static final int     TIME_FADE_OUT  = 125*2;
  
    // Door opening Time
    public final static int     DOOR_OPENING_TIME_MS   = 1000;
    public final static long    MAZE_GENERATION_PERIOD = 5000;
    
    // Physic-Display relationships
    public final static float   NB_PIXELS_PER_METER         = 64.0f;
    public final static float   SPRITE_PHYSIC_DISPLAY_RATIO = 0.75f; 
    
    // RENDER LAYERS
    public final static int     RENDER_LAYER_WALLS   = 1000;
    public final static int     RENDER_LAYER_DOORS   =  500;
    public final static int     RENDER_LAYER_PLAYERS =  400;
    public final static int     RENDER_LAYER_FLOOR   =  250;
    
}