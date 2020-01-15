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

import JFUtils.Range;
import JFUtils.point.Point2D;
import PBEngine.gameObjects.gameObject;
import PBEngine.Supervisor;
import java.awt.Color;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author guest-kxryfn
 */
public class astardemo{
    
    public static void main(String[] args) {
        Supervisor k;
        k = new Supervisor(0, false, new Point2D(0, 0), 1);
        k.timerType = 0;
        
        Thread A = new Thread(k);
        A.start();
        while(!k.ready){
            
        }
        k.Logic.window.setTitle("PointBreakEngine (A* Demo)");
        
        System.out.println("Editor initialization complete, running custom code...");
        k.Logic.abright = true;
        k.Logic.Vrenderer.camMode = 0;
        int id = 0;
        try {
            k.Logic.loadLevel("house.pblevel");
        } catch (URISyntaxException ex) {
            Logger.getLogger(astardemo.class.getName()).log(Level.SEVERE, null, ex);
        }
        LinkedList<gameObject> lis = new LinkedList<>();
        int res = 50;
        int res2 = 40;
        int p = 0;
        for(int x : new Range(res)){
            for(int y : new Range(res2)){
                char[][] matrix = k.objectManager.getCollisionmap(new Point2D(0, 0), new Point2D(k.xd, k.yd), "nocoll");
                matrix[15][15] = 'X';
                List<JFUtils.pathfinding.astarNode> path = JFUtils.pathfinding.astar.getPath(matrix, x, y);
                float val = 255;
                for(int i : new Range(path.size())){
                    val = val * 0.9F;
                }
                if(val < 0F){val = 0;}
                Color c = new Color((int)val, (int)val,(int) val);
                gameObject g = new gameObject(x, y, 1, "astardemobg", "D", 1, c, id, k);
                //System.out.println(new dVector(x,y).represent());
                g.tag.add("nocoll");
                g.tag.add("static");
                //k.objectManager.addObject(g);
                g.setLocation(new Point2D(x, y));
                g.onlyColor = true;
                lis.add(g);
                id++;
                p++;
                System.out.println("Progress: " + 100F/(res*res2) * p + "%");
            }
        }
        System.out.println("Complete.");
        for(gameObject x : lis){
            k.objectManager.addObject(x);
        }
    }
}
