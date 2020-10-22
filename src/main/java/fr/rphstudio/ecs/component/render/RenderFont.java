//--------------------------------------------------------------------
// IMPORTS
//--------------------------------------------------------------------
package fr.rphstudio.ecs.component.render;

import fr.rphstudio.ecs.component.render.font.FontCharInfo;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IRender;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;


//--------------------------------------------------------------------
// CLASS
//--------------------------------------------------------------------

/**
 * Class used to blit some font sprites on the screen, using a string as input.
 * @author Romuald GRIGNON
 */
public class RenderFont implements IComponent, IRender
{
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private final long       id;
    private final String     name;
    
    private       float      zOrder;
    private       Vector2f   currentPos;
    private       String     message;
    private       float      ratio;
    private       Color      color;
    private       boolean    isMiddleAlign;
    private       boolean    isVerticalAlign;
    
    /**
     * Letter array, containing all information about the character area in the spritesheet.
     */
    private FontCharInfo[] letters;
    
    /**
     * Private field containing a reference to the spritesheet
     */
    private Image font;
 
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    /**
     * Constructor of the font manager.
     * It just loads a spritesheet into the memory, waiting for the public methods calls to use it.
     */
    public RenderFont(String msg, Image ssFont, FontCharInfo[] fontLetters, float z, float rat, Color clr)
    {
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Set name
        this.name = "RenderFont component";
        // Store elements
        this.message    = msg;
        this.zOrder     = z;
        this.font       = ssFont;
        this.ratio      = rat;
        this.color      = clr;
        this.currentPos = new Vector2f(0,0);
        this.letters    = fontLetters;
        this.isMiddleAlign   = true;
        this.isVerticalAlign = true;
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
    public void setZOrder(float z)
    {
        this.zOrder = z;
    }
    @Override
    public boolean isShown()
    {
        return true;
    }
    @Override 
    public void setPosition(Vector2f newPos)
    {
        if(newPos != null)
        {
            this.currentPos.x = newPos.x;
            this.currentPos.y = newPos.y;
        }
    }
    public void setPosition(float x, float y)
    {
        this.currentPos.x = x;
        this.currentPos.y = y;
    }
    @Override 
    public void setPosition(int eltIndex, Vector2f newPos)
    {
        throw new Error("[ERROR] setPosition method is not used in RenderFont class !");
    }
    public void setAngle(float a)
    {
            throw new Error("[ERROR] setAngle method is not used in RenderFont class !");
    }


    //--------------------------------------------------------------------
    // PUBLIC METHODS
    //--------------------------------------------------------------------
    
    public void setMessage(String msg)
    {
        this.message = msg;
    }
    public void setRatio(float r)
    {
        this.ratio = r;
    }
    public void setColor(Color c)
    {
        this.color = c;
    }
    
    
    /**
     * Blits a string message on the screen, using the message input and the spritesheet private property.
     * @param g reference of the graphic buffer object used to blit things on the screen.
     */
    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
    {
        float offsetMid  = 0;
        float offsetVert = 0;
        
        try{
            for (int i = 0; i < this.message.length(); i++) {
                // Get letter
                char c = this.message.charAt(i);
                // Get entry from the 'letters' array
                for (FontCharInfo li : this.letters) {
                    if (li.getChar() == c) {
                        if(this.isMiddleAlign)
                        {
                            offsetMid += li.getW() * this.ratio;
                        }
                        if(this.isVerticalAlign)
                        {
                            offsetVert = Math.max(offsetVert, li.getH() * this.ratio);
                        }
                    }
                }
            }
            offsetMid  /= 2;
            offsetVert /= 2;
        }catch (RuntimeException ex){
            System.err.println(ex);
        }
        
        try {
            int offsetX = 0;
            // Loop for each character from the message
            for (int i = 0; i < this.message.length(); i++) {
                // Get letter
                char c = this.message.charAt(i);
                // Get entry from the 'letters' array
                for (FontCharInfo li : this.letters) {
                    if (li.getChar() == c) {
                        // Draw character at the correct offset
                        g.drawImage(this.font.getSubImage(li.getX(), li.getY(), li.getW(), li.getH()).getScaledCopy(this.ratio),
                                this.currentPos.x + offsetX - offsetMid,
                                this.currentPos.y           - offsetVert,
                                this.color);
                        offsetX += this.ratio * li.getW();
                        // Go to next message character
                        break;
                    }
                }
            }
        }catch (RuntimeException ex){
            System.err.println(ex);
        }
    }
    
    public void setMiddleAlign(boolean b)
    {
        this.isMiddleAlign = b;
    }
    public void setVerticalAlign(boolean b)
    {
        this.isVerticalAlign = b;
    }

    
    
    //--------------------------------------------------------------------
    // END OF CLASS
    //--------------------------------------------------------------------
}
