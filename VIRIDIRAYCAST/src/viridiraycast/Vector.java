/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiraycast;

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
    public String represent(){
        return(this.x + ", " + this.y);
    }
    public static Vector round(Vector in){
        return(new Vector(Math.round(in.x), Math.round(in.y)));
    }
    public static Vector norm(int x, int y){
        if(x == 0){
            x = 1;
        }
        if(y == 0){
            y = 1;
        }
        return new Vector(x/(x^2 + y^2)^(1/2), y/(x^2 + y^2)^(1/2));
    }
    public static dVector[] dir(){
            dVector[] dirs = new dVector[8];
            dirs[0] = new dVector(0.0F,1.0F);
            dirs[1] = new dVector(1.0F,1.0F);
            dirs[2] = new dVector(1.0F,0.0F);
            dirs[3] = new dVector(-1.0F,-1.0F);
            dirs[4] = new dVector(0F,-1.0F);
            dirs[5] = new dVector(-1.0F,-1.0F);
            dirs[6] = new dVector(-1.0F,0F);
            dirs[7] = new dVector(-1.0F,1F);
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
class dVector{
    public double x;
    public double y;
    public dVector(double nx, double ny){
        this.x = nx;
        this.y = ny;
    }
    public static dVector add(dVector one, dVector two){
        double nx = one.x + two.x;
        double ny = one.y + two.y;
        return(new dVector(nx, ny));
    }
    public static dVector subtract(dVector o, dVector t){
        return(new dVector(o.x - t.x, o.y - t.y));
    }
    public static dVector multiply(dVector one, dVector two){
        return(new dVector(one.x * two.x, one.y * two.y));
    }
    public static dVector divide(dVector one, dVector two){
        return(new dVector(one.x / two.x, one.y / two.y));
    }
    public String represent(){
        return(this.x + ", " + this.y);
    }
    public static dVector round(dVector in){
        return(new dVector(Math.round(in.x), Math.round(in.y)));
    }
}