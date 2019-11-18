/*
 * The MIT License
 *
 * Copyright 2019 Elias.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package JFUtils;

/**
 *
 * @author Jonnelafin
 */
public class dVector3 implements java.io.Serializable{
    public double x;
    public double y;
    public double z;
    public double intX(){
        return Math.round(x);
    }
    public double intY(){
        return Math.round(y);
    }
    public double intZ(){
        return Math.round(z);
    }
    public dVector3(double nx, double ny, double nz){
        this.x = nx;
        this.y = ny;
        this.z = nz;
    }
    public static dVector3 add(dVector3 one, dVector3 two){
        double nx = one.x + two.x;
        double ny = one.y + two.y;
        double nz = one.z + two.z;
        return(new dVector3(nx, ny, nz));
    }
    public static dVector3 subtract(dVector3 o, dVector3 t){
        return(new dVector3(o.x - t.x, o.y - t.y, o.z - t.z));
    }
    public static dVector3 multiply(dVector3 one, dVector3 two){
        return(new dVector3(one.x * two.x, one.y * two.y, one.z * two.z));
    }
    public static dVector3 divide(dVector3 one, dVector3 two){
        return(new dVector3(one.x / two.x, one.y / two.y, one.z / two.z));
    }
    public String represent(){
        return(this.x + ", " + this.y + ", " + this.z);
    }
    public static dVector3 round(dVector3 in){
        return(new dVector3(Math.round(in.x), Math.round(in.y), Math.round(in.z)));
    }
    public static dVector3 clone(dVector3 source){
        return new dVector3(source.x, source.y, source.z);
    }
    @Override
    public dVector3 clone(){
        return new dVector3(x, y, z);
    }
}