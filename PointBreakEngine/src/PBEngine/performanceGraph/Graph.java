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

package PBEngine.performanceGraph;

import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 *
 * @author Jonnelafin
 */
public class Graph extends JFrame{
    public int max = 45;
    private int lastMax = max;
    int h = 10;
    
    Random rnd = new Random(420);
    boolean running = true;
    
    public Graph() throws InterruptedException{
        this.setTitle("PointBreakEngine graphing");
        this.setSize(400, 550);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JTextArea area = new JTextArea();
        area.setVisible(true);
        area.setEditable(false);
        
        this.add(area);
        area.setText("wait...");
        
        Integer[] history = new Integer[max];
        zero(history);
        
        String[][] map = new String[h][max];
        empty(map);
        
        int tick = 0;
        float t = 0;
        while(running){
            t = t + 0.1F;
            if(max != lastMax){
                history = new Integer[max];
                zero(history);
                
                map = new String[h][max];
                empty(map);
            }
            
            tick++;
            if(tick > max){
                tick = 0;
            }
            
            
            int num = rnd.nextInt();
            
            int charge = (int) (t * 20);
            
            int place = tick;
            if(place < 0){
                place = 0;
            }
            if(place > max-1){
                place = max-1;
            }
            //Rasterize
            for(int x : new JFUtils.Range(max)){
                for(int y : new JFUtils.Range(h)){
                    if(charge > 0){
                        map[y][place] = "0";
                    }
                    else{
                        map[y][place] = "1";
                    }
                    charge = charge - 1;
                }
            }
            
            //RENDER
            area.setText(toString(map));
            //area.setText(Math.abs(num) + "");
            
            //SLEEP
            Thread.sleep(1);
        }
        
        
    }
    private static void zero(Integer[] arr){
        for(Integer i : arr){
            i = 0;
        }
    }
    private static void empty(String[][] arr){
        for(String[] i : arr){
            for(String x : i){
                x = ".";
            }
        }
    }
    private static String toString(String[][] src){
        String out = "";
        for(String[] i : src){
            for(String x : i){
                if(x == null){
                    out = out + " ";
                }
                else{
                    out = out + x;
                }
            }
            out = out + "\n";
        }
        return out;
    }
    
    public static void main(String[] args) throws InterruptedException {
        new Graph();
    }
}
