/*
 * The MIT License
 *
 * Copyright 2019 Elias Eskelinen.
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

package PBEngine.gameObjects;

import JFUtils.point.Point2D;
import PBEngine.Rendering.core.renderType;
import PBEngine.Supervisor;
import java.util.LinkedList;

/**
 *
 * @author Jonnelafin
 */
public class objectContainer extends gameObject{
    public LinkedList<gameObject> objects;

    public objectContainer(LinkedList<gameObject> objects, Point2D location, int size, double mass, renderType shape, Supervisor master, int ID) {
        super(location, size, mass, shape, master, ID);
        this.objects = objects;
        general();
    }
    //public objectContainer(Point2D location, int size, double mass, renderType shape, Supervisor master, int ID) {
    //    super(location, size, mass, shape, master, ID);
    //    this.objects = new LinkedList<>();
    //    general();
    //}
    private void general(){
        this.collisions = false;
        this.hidden = true;
        this.tag.add("nocoll");
    }
    @Override
    public void setEnabled(boolean val){
        this.objects.forEach(l -> l.setEnabled(val));
    }
    @Override
    public void setHidden(boolean val){
        this.objects.forEach(l -> l.setHidden(val));
    }
}
