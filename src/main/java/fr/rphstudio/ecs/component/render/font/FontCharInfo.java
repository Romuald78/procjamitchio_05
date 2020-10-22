//--------------------------------------------------------------------
// IMPORTS
//--------------------------------------------------------------------
package fr.rphstudio.ecs.component.render.font;

import fr.rphstudio.utils.font.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


//--------------------------------------------------------------------
// CLASS
//--------------------------------------------------------------------

/**
 * Class used to store some information about a character from a spritesheet.
 * @author Romuald GRIGNON
 */
public class FontCharInfo
{
    //--------------------------------------------------------------------
    // PROPERTIES
    //--------------------------------------------------------------------

        private char letter;
        private int  posX;
        private int  posY;
        private int  width;
        private int  height;
 
    
    //--------------------------------------------------------------------
    // CONSTRUCTOR
    //--------------------------------------------------------------------
    
    /**
     * Constructor of the font char information class.
     */
    public FontCharInfo(char c, int x, int y)
    {
        this(c,x,y,0,0);
    }
    public FontCharInfo(char c, int x, int y, int w, int h)
    {
        this.letter = c;
        this.posX   = x;
        this.posY   = y;
        this.width  = w;
        this.height = h;
    }
     
    
    //--------------------------------------------------------------------
    // PUBLIC METHODS
    //--------------------------------------------------------------------
    public char getChar()
    {
        return this.letter;
    }
    public int getX()
    {
        return this.posX;
    }
    public int getY()
    {
        return this.posY;
    }
    public int getW()
    {
        return this.width;
    }
    public int getH()
    {
        return this.height;
    }
    
    
    //--------------------------------------------------------------------
    // END OF CLASS
    //--------------------------------------------------------------------
}
