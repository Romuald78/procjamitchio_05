/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.rphstudio.ecs.component.physic;

import fr.rphstudio.ecs.component.physic.utils.PhysicBodyInfo;
import fr.rphstudio.ecs.component.physic.utils.PhysicJointInfo;
import fr.rphstudio.ecs.core.utils.CoreUtils;
import fr.rphstudio.ecs.core.interf.IComponent;
import fr.rphstudio.ecs.core.interf.IPhysic;
import java.util.ArrayList;
import java.util.List;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.joint.Joint;
import org.dyn4j.dynamics.joint.WeldJoint;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.geom.Vector2f;

/**
 *
 * @author GRIGNON FAMILY
 */
public class Physic2D implements IComponent, IPhysic
{
    public final static double MASS_RATIO = 1;
    
    //================================================
    // PRIVATE PROPERTIES
    //================================================
    private final long            id;
    private final String          name;
    private List<PhysicBodyInfo>  bodyInfoList;
    private List<PhysicJointInfo> jointInfoList;
    
    
    //================================================
    // PRIVATE METHODS
    //================================================
    private void createCommonBody(Body body, String name, BodyFixture fixture, Vector2f initPos, boolean isStatic, double massRatio, double linearDamping)
    {
        // Init local var
        Vector2     initPosition;
        // Set physic parameters
        fixture.setRestitution(0.9);
        fixture.setDensity(massRatio);
        body.setLinearDamping(linearDamping);
        body.addFixture(fixture);
        // Set mass of the object
        body.setMass(MassType.NORMAL);
        if(isStatic)
        {
            body.setMass(MassType.INFINITE);    
        }
        // Set initial position
        initPosition = new Vector2(initPos.x, initPos.y );
        body.shift( initPosition );
        // Store the body info in the list
        this.bodyInfoList.add( new PhysicBodyInfo(body, name) );
    }
    
    
    //================================================
    // CONSTRUCTOR
    //================================================
    // constructor used to create an empty component
    public Physic2D()
    {
        // Store name
        this.name = "PHYSIC2D_Component";
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init private lists
        this.bodyInfoList  = new ArrayList<PhysicBodyInfo>();
        this.jointInfoList = new ArrayList<PhysicJointInfo>();
    }
    public Physic2D(String nam)
    {
        // Store name
        this.name = nam;
        // Get unique ID
        this.id = CoreUtils.getNewID();
        // Init private lists
        this.bodyInfoList  = new ArrayList<PhysicBodyInfo>();
        this.jointInfoList = new ArrayList<PhysicJointInfo>();
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
    // SETTERS (Creation)
    //================================================
    public void addCircleBody(String name, Vector2f initPos, float rad, boolean isStatic, double massRatio, double linearDamping)
    {
        // Local variables
        BodyFixture fixture;
        Circle      cirShape;
        Body        body;
        // Instanciate body
        body = new Body();
        body.getContacts(true);
        // Create shape and elastic collision behavior
        cirShape = new Circle(rad);
        fixture  = new BodyFixture(cirShape);
        // Call common init
        this.createCommonBody(body, name, fixture, initPos, isStatic, massRatio, linearDamping);
    }
    public void addSquareBody(String name, Vector2f initPos, float width, float height, boolean isStatic, double massRatio, double linearDamping)
    {
        // Local variables
        BodyFixture fixture;
        Rectangle   rectShape;
        Body        body;
        // Instanciate body
        body = new Body();
        body.getContacts(true);
        // Create shape and elastic collision behavior
        rectShape = new Rectangle(width,height);
        fixture   = new BodyFixture(rectShape);
        // Call common init
        this.createCommonBody(body, name, fixture, initPos, isStatic, massRatio, linearDamping);
    }
    public void addJoint(String nam, Body bdy1, Body bdy2, Vector2f jointPos)
    {
        WeldJoint joint = new WeldJoint(bdy1, bdy2, new Vector2(jointPos.x, jointPos.y) );
        this.jointInfoList.add( new PhysicJointInfo(joint, name) );
    }
    
    //================================================
    // SETTERS (Creation)
    //================================================
    public void setSpeed( float dx, float dy)
    {
        this.bodyInfoList.get(0).getBody().setLinearVelocity( dx, dy);
    }
    public void setForce( float dx, float dy)
    {
        this.bodyInfoList.get(0).getBody().applyImpulse(new Vector2(dx,dy));
    }
    public void incSpeed( float dx, float dy)
    {
        this.bodyInfoList.get(0).getBody().applyImpulse(new Vector2(dx,dy));
    }
    public void setAngle(float angle)
    {
        this.bodyInfoList.get(0).getBody().getTransform().setRotation( angle*Math.PI /180.0);
    }
    
    
    //================================================
    // GETTERS (Bodies)
    //================================================
    // Get the number of bodies in the current component
    @Override
    public int getBodyCount()
    {
        return this.bodyInfoList.size();
    }
    // Gett all the bodies in this component
    @Override
    public List<Body> getAllBodies()
    {
        // Init list
        List<Body> list = new ArrayList<Body>();
        // Loo, for all bodies in this component
        for(PhysicBodyInfo info: this.bodyInfoList )
        {
            list.add(info.getBody());
        }
        // return list of all bodies
        return list;
    }
    // Get body object
    @Override
    public Body getBody(int index)
    {
        // Check index is correct
        if( (index < 0) || (index >= this.bodyInfoList.size()) )
        {
            throw new Error("[ERROR] body index is not correct !");
        }
        // Return body from the list
        return this.bodyInfoList.get(index).getBody();
    }
    @Override
    public Body getBody(String name)
    {
        // Look for all the body info and try to find the requested name
        for(PhysicBodyInfo info: this.bodyInfoList )
        {
            // If we have found a name that is equal, return linked body
            if(info.getBodyName().equalsIgnoreCase(name))
            {
                return info.getBody();
            }
        }
        // We haven(t found the requested body
        throw new Error("[ERROR] body name is not correct !");
    }
    // Get horizontal position
    public double getXPosition(int index)
    {
        return this.getBody(index).getTransform().getTranslationX();
    }
    public double getXPosition(String name)
    {
        return this.getBody(name).getTransform().getTranslationX();
    }
    // Get vertical position
    public double getYPosition(int index)
    {
        return this.getBody(index).getTransform().getTranslationY();
    }   
    public double getYPosition(String name)
    {
        return this.getBody(name).getTransform().getTranslationY();
    }
    // Get body rotation
    public float getDirectionAngle(int index)
    {
        double a = this.getBody(index).getTransform().getRotation();
        a = a*180/Math.PI;
        return (float)a;
    }
    public float getDirectionAngle(String name)
    {
        double a = this.getBody(name).getTransform().getRotation();
        a = a*180/Math.PI;
        return (float)a;
    }
    
    
    //================================================
    // GETTERS (Joints)
    //================================================
    // get the number of joints in this component
    public int getJointCount()
    {
        return this.jointInfoList.size();
    }
    // Get all the joints in this component
    public List<Joint> getAllJoints()
    {
        // Init list
        List<Joint> list = new ArrayList<Joint>();
        // Loo, for all bodies in this component
        for(PhysicJointInfo info: this.jointInfoList )
        {
            list.add(info.getJoint());
        }
        // return list of all bodies
        return list;
    }
    // Get jointobject
    public Joint getJoint(int index)
    {
        // Check index is correct
        if( (index < 0) || (index >= this.jointInfoList.size()) )
        {
            throw new Error("[ERROR] joint index is not correct !");
        }
        // Return body from the list
        return this.jointInfoList.get(index).getJoint();
    }
    public Joint getJoint(String name)
    {
        // Look for all the joint info and try to find the requested name
        for(PhysicJointInfo info: this.jointInfoList )
        {
            // If we have found a name that is equal, return linked joint
            if(info.getJointName().equalsIgnoreCase(name))
            {
                return info.getJoint();
            }
        }
        // We haven(t found the requested joint
        throw new Error("[ERROR] jont name is not correct !");
    }
    
    
    
    //================================================
    // GETTERS (Simple use : when a component has only one body, just use these methods)
    //================================================
    @Override
    public Body getBody()
    {
        return this.getBody(0);
    }
    public double getXPosition()
    {
        return this.getXPosition(0);
    }
    public double getYPosition()
    {
        return this.getYPosition(0);
    }    
    public float getDirectionAngle()
    {
        return this.getDirectionAngle(0);
    }
    
    //================================================
    // SETTERS (Simple use : when a component has only one body, just use these methods)
    //================================================
    public void setXPosition(float posX)
    {
        this.getBody(0).getTransform().setTranslationX(posX);
    }
    public void setYPosition(float posY)
    {
        this.getBody(0).getTransform().setTranslationY(posY);
    }
    
    
    //================================================
    // END OF CLASS
    //================================================
}
