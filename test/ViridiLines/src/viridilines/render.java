/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridilines;

import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author Jonnelafin
 */
public class render extends JFrame {
    int x = 0;
    int y = 0;
    int xp = 0;
    int swipe = 0;
    int step = 5;
    int c = 0;
    public render(){
        
        setTitle("Test");
        setSize(960, 960);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        draw();
    }
    public void paint(Graphics g){
//        update();
        if(swipe == 360 / 5){g.clearRect(0, 0, 960, 960);swipe = 0;}
        swipe = swipe + 1;
        g.setColor(Color.blue);
        g.drawLine(400 + this.x, 480, 560 + this.xp, 480);
        g.setColor(Color.red);
        g.drawLine(400 + this.x, 480, 480, 360 + y);
        g.setColor(Color.green);
        g.drawLine(480, 360 + y, 560 + this.xp, 480);
    }
    public void updateT(){
        if(c < 360 / 5){
            x = x + step;
            xp = xp - step;
            y = y + step;
            c++;
        }
        else{
            step = step * - 1;
            c = 0;
        }
//        Graphics d = getGraphics();
//        d.clearRect(0, 0, 940, 940);swipe = 0;
        revalidate();
        repaint();
        System.out.println(c);
    }
}
