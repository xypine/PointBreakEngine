/*
 * The MIT License
 *
 * Copyright 2019 elias.
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
package PBEngine.Editor2;

import PBEngine.*;
import java.awt.Color;

/**
 *
 * @author elias
 */
public class Cursor extends Player{
    public Cursor(int ypos, int xpos, int size, String tag, String ap, float mas, Color cot, int ID, kick master) {
        super(ypos, xpos, size, tag, ap, mas, cot, ID, master);
        this.imageName = "";
    }
    
    @Override
    public void checkInput(Input in){
        //System.out.println("input check");
        if(in.mouseX() != in.lX && in.mouseY() != in.lY){
            this.x = (in.mouseX() / this.masterParent.Logic.Vrenderer.factor);
            this.y = (in.mouseY() / this.masterParent.Logic.Vrenderer.factor);
        //    System.out.println(new dVector(this.x, this.y).represent());
        }
        if()
    }
    @Override
    public void update(int xd, int yd, objectManager oMb) {
        //System.out.println("physics update");
        this.setDegrees(0);
        this.checkInput(masterParent.Logic.input);
    }
    @Override
    public void overBound(int direction, int xd, int yd){
        if(direction == 2){
            this.y = yd - 1;
        }
        if(direction == 1){
            this.x = xd - 1;
        }
        if(direction == 0){
            this.y = 0;
        }
        if(direction == 3){
            this.x = 0;
        }
    }
}
