/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pointbreak3d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import javax.swing.*;
import jdk.swing.interop.SwingInterOpUtils;



import org.apache.commons.math3.geometry.euclidean.threed.Plane;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

/**
 *
 * @author elias
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SwingUtilities.invokeLater(new pb3D());
    }
    
}
class pb3D extends JFrame implements Runnable{
    public pb3D(){
        
    }

    @Override
    public void run() {
        this.setTitle("PointBreakEngine 3D");
        this.setSize(700, 700);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.add(new renderable());
        this.setVisible(true);
    }
}
class renderable extends JPanel{
    Plane planeX = new Plane(new Vector3D(1, 0, 0));
    Plane planeY = new Plane(new Vector3D(0, 1, 0)); // Must be orthogonal plane of planeX
    
    void drawPoint(Graphics2D g2, Vector3D v) {
        g2.drawLine(0, 0,
        (int) (100 * planeX.getOffset(v)),
        (int) (100 * planeY.getOffset(v)));
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        super.paintComponent(g);

        drawPoint(g2, new Vector3D(2, 1, 0));
        drawPoint(g2, new Vector3D(0, 2, 0));
        drawPoint(g2, new Vector3D(0, 0, 2));
        drawPoint(g2, new Vector3D(1, 1, 1));
    }
}
class Vector3{
    public float x;
    public float y;
    public float z;
    public Vector3(float nx, float ny, float nz){
        this.x = nx;
        this.y = ny;
        this.z = nz;
    }
}