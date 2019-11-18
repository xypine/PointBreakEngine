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
public class dVector implements java.io.Serializable{
    public double x;
    public double y;
    public double intX(){
        return Math.round(x);
    }
    public double intY(){
        return Math.round(y);
    }
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
    public static dVector clone(dVector source){
        return new dVector(source.x, source.y);
    }
    @Override
    public dVector clone(){
        return new dVector(x, y);
    }
}