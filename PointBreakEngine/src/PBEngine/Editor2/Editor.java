/*
 * The MIT License
 *
 * Copyright 2019 elias eskelinen.
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
public class Editor {
    public boolean saved = false;
    public Editor(){
        String[] argss = new String[2];
        argss[0] = "nodemo";
        Camera cam = new Camera(0, 0);
        PBEngine.kick k = new kick(0, false, new dVector(0, 0));
        
        Thread A = new Thread(k);
        A.start();
        while(!k.ready){
            
        }
        k.Logic.abright = true;
        k.Logic.Vrenderer.camMode = 0;
        k.Logic.Vrenderer.drawGrid = true;
        k.Logic.Vrenderer.gridColor = new Color(0, 90, 20);
        k.Logic.overrideRayBG = Color.GRAY;
        
        Cursor cursor = new Cursor(0, 0, 1, "newcursor", "C", 1, Color.white, 0, k, this);
        cursor.onlyColor = true;
        cursor.imageName = "";
        k.objectManager.addObject(cursor);
        System.out.println("Editor initialization finished");
        System.out.println(k.Logic.running);
    }
}
