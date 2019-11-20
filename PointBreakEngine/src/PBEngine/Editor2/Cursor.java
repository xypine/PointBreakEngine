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
package PBEngine.Editor2;

import JFUtils.Input;
import JFUtils.dVector;
import PBEngine.*;
import java.awt.Color;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.logging.Logger;

/**
 *
 * @author elias
 */
public class Cursor extends Player{
    public PBEngine.Editor2.Editor editor;
    Input mouse;
    
    public boolean positionValid = true;
    public Cursor(int ypos, int xpos, int size, String tag, String ap, float mas, Color cot, int ID, Supervisor master, PBEngine.Editor2.Editor editor, Input mouse) {
        super(ypos, xpos, size, tag, ap, mas, cot, ID, master);
        this.imageName = "";
        this.editor = editor;
        this.tag.add("nocoll");
        this.mouse = mouse;
    }
    
    private int usedUse = 200;
    @Override
    public void doOnPreciseMovement(){
        
    }
    @Override
    public void checkInput(Input in2){
        //System.out.println("input check");
        //in.mouseX() != in.lX && in.mouseY() != in.lY
        //System.out.println("O=POPOP");
        boolean r = false;
        try {
            if(mouse.chars.containsKey("r")){
                if(mouse.chars.get("r") != null){
                    r = mouse.chars.get("r");
                    System.out.println(r);
                }
            }
        } catch (Exception e) {
           //e.printStackTrace();
        }
        if(r && !masterParent.objectManager.colliding((int)x,(int)y,"null") && positionValid){
            gameObject o = new gameObject((int)x,(int)y, 1, "static", "█", 1F, Color.blue, masterParent.objectManager.getUsableID(), masterParent);
            if(editor.mode == 1){
                o = new gameObject((int)x,(int)y, 1, "light", "█", 1F, Color.green, masterParent.objectManager.getUsableID(), masterParent);
                o.tag.add("static");
            }
            o.onlyColor = true;
            masterParent.objectManager.addObject(o);
            //System.out.println("new wall!");
            editor.saved = false;
        }
        /*if(in.ke == 'l' && !editor.saved){
        try {
        LevelLoader aol = new LevelLoader("null", masterParent.objectManager, masterParent);
        aol.write(masterParent.objectManager.getObjects(), "newout.pblevel");
        } catch (UnsupportedEncodingException ex) {
        Logger.getLogger(PBEngine.LegacyEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IOException ex) {
        Logger.getLogger(PBEngine.LegacyEditor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
        Logger.getLogger(Cursor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        editor.saved = true;
        }*/
        //System.out.println("GOT HERE");
        if(true){
            //System.out.println(new dVector(this.x, this.y).represent());
            this.x = (mouse.mouseX() / this.masterParent.Logic.Vrenderer.factor);
            this.y = (mouse.mouseY() / this.masterParent.Logic.Vrenderer.factor);
            this.x = (int)x;
            this.y = (int)y - 1;
        //    System.out.println(new dVector(this.x, this.y).represent());
        }
        usedUse = usedUse + 10;
    }
    @Override
    public void update(int xd, int yd, objectManager oMb) {
        //System.out.println("physics update");
        this.setDegrees(0);
        
        checkValidity(xd, yd);
        
        if(positionValid){
            this.setColor(Color.white);
        }else{
            this.setColor(Color.red);
        }
        
        this.checkInput(masterParent.Logic.input);
    }
    public void checkValidity(int xd, int yd){
        if(this.y > yd - 1){
            overBound(2, xd, yd);
        }
        else if(this.x > xd - 1){
            overBound(1, xd, yd);
        }
        else if(this.y < 0){
            overBound(0,xd,yd);
        }
        else if(this.x < 0){
            overBound(3, xd, yd);
        }
        else{
            doIfInside();
        }
        
    }
    @Override
    public void doIfInside(){
        positionValid = true;
    }
    @Override
    public void overBound(int direction, int xd, int yd){
        positionValid = false;
        if(direction == 2){
            //this.y = yd - 1;
        }
        if(direction == 1){
            //this.x = xd - 1;
        }
        if(direction == 0){
            //this.y = 0;
        }
        if(direction == 3){
            //this.x = 0;
        }
    }
}
