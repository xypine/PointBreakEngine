/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

/**
 *
 * @author Jonnelafin
 */
class Vector {

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
