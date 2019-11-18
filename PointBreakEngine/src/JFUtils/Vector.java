/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package JFUtils;

import JFUtils.dVector;

/**
 *
 * @author Jonnelafin
 */
public class Vector implements java.io.Serializable{
    public float x;
    public float y;
    public Vector(float nx, float ny){
        this.x = nx;
        this.y = ny;
    }
    public static Vector add(Vector one, Vector two){
        float nx = one.x + two.x;
        float ny = one.y + two.y;
        return(new Vector(nx, ny));
    }
    public static Vector subtract(Vector o, Vector t){
        return(new Vector(o.x - t.x, o.y - t.y));
    }
    public static Vector multiply(Vector one, Vector two){
        return(new Vector(one.x * two.x, one.y * two.y));
    }
    public static Vector divide(Vector one, Vector two){
        return(new Vector(one.x / two.x, one.y / two.y));
    }
    public String represent(){
        return(this.x + ", " + this.y);
    }
    public static Vector round(Vector in){
        return(new Vector(Math.round(in.x), Math.round(in.y)));
    }
    public static Vector norm(int x, int y){
        return new Vector(x/(x^2 + y^2)^(1/2), y/(x^2 + y^2)^(1/2));
    }
    public static dVector[] dir(){
            dVector[] dirs = new dVector[8];
            dirs[0] = new dVector(0.0,1.0);
            dirs[1] = new dVector(1.0,1.0);
            dirs[2] = new dVector(1.0,0.0);
            dirs[3] = new dVector(-1.0,-1.0);
            dirs[4] = new dVector(0,-1.0);
            dirs[5] = new dVector(-1.0,-1.0);
            dirs[6] = new dVector(-1.0,0);
            dirs[7] = new dVector(-1.0,1);
            return dirs;
    }
}
class Vector3{
    public float x;
    public float y;
    public float z;
    public Vector3(float nx, float ny, float nz){
        this.x = nx;
        this.y = ny;
        this.z = nz;
    }
}
