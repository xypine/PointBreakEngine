/*
 * The MIT License
 *
 * Copyright 2019 guest-kxryfn.
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
package astarDemo;

import PBEngine.Range;
import PBEngine.astar;
import PBEngine.dVector;
import PBEngine.gameObject;
import PBEngine.kick;
import java.awt.Color;
import java.util.List;

/**
 *
 * @author guest-kxryfn
 */
public class astardemo{
    
    public static void main(String[] args) {
        kick k;
        k = new kick(0, false, new dVector(0, 0), 1);
        
        Thread A = new Thread(k);
        A.start();
        while(!k.ready){
            
        }
        k.Logic.setTitle("PointBreakEngine (A* Demo)");
        
        System.out.println("Editor initialization complete, running custom code...");
        k.Logic.abright = true;
        k.Logic.Vrenderer.camMode = 0;
        int id = 0;
        for(int x : new Range(20)){
            for(int y : new Range(20)){
                char[][] matrix = k.objectManager.getCollisionmap(new dVector(0, 0), new dVector(k.xd, k.yd), "nocoll");
                List<PBEngine.astarNode> path = PBEngine.astar.getPath(matrix, x, y);
                float val = 100-path.size()*4;
                if(val < 0F){val = 0;}
                Color c = new Color((int)val, (int)val,(int) val);
                gameObject g = new gameObject(x, y, 1, "astardemobg", "D", 1, c, id, k);
                System.out.println(new dVector(x,y).represent());
                g.tag.add("nocoll");
                g.tag.add("static");
                k.objectManager.addObject(g);
                g.setLocation(new dVector(x, y));
                g.onlyColor = true;
                id++;
            }
        }
    }
}
