/*
 * The MIT License
 *
 * Copyright 2019 eliase.
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
package PBEngine.Rendering.core;

import java.util.LinkedList;

/**
 *
 * @author Elias Eskelinen (Jonnelafin)e
 */
/*public class vectorLayer{
int lastUpdated = 0;
LinkedList<Vector> points = new LinkedList<>();
LinkedList<String> images = new LinkedList<>();
LinkedList<Color> colors = new LinkedList<>();
LinkedList<Integer> sizes = new LinkedList<>();
public int blur;
float x = 15.34F;
float y = 22.48F;
float factor = 2F;
public int w = 0;
public int h = 0;
public String title = "";
public void init(String title, int blur){
this.title = title;
}

public void update(LinkedList<Vector> p,LinkedList<Color> c, LinkedList<String> images, LinkedList<Integer> sizes, float factor, int tick){
if(lastUpdated > tick){
return;
}
lastUpdated = tick;
this.points = p;
this.colors = c;
this.images = images;
this.factor = factor;
this.sizes = sizes;
}
}*/
public class newVectorLayer{
    public int lastUpdated = 0;
    public LinkedList<renderContainer> containers = new LinkedList<>();
    public int blur;
    public float x = 15.34F;
    public float y = 22.48F;
    public float factor = 2F;
    public int w = 0;
    public int h = 0;
    public String title = "";
    public void init(String title, int blur){
        this.title = title;
    }
    
    public void update(LinkedList<renderContainer> containers, int tick){
        if(lastUpdated > tick){
            return;
        }
        this.containers = containers;
    }
}
