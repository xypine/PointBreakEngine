/*
 * The MIT License
 *
 * Copyright 2019 Elias Arno Eskelinen.
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

package PBEngine.vfx;

import JFUtils.Input;
import JFUtils.Range;
import JFUtils.point.Point2D;
import PBEngine.Rendering.Renderer;
import PBEngine.Supervisor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Jonnelafin
 */
public class engineWindow extends JFrame{
    public boolean useVFX = false;
    
    Input input = null;
    public Renderer Vrenderer;
    public final double h = 540D;
    public final double w = 1080D;
    public final double size = 1D;
    public Supervisor k;
    public BufferedImage actualImage;
    ImagePanel2 out;
    
    public engineWindow(Input in, Supervisor k, Renderer ren){
        this.k = k;
        this.input = in;
        
        this.setTitle("PointBreakEngine");
        this.setSize((int) Math.ceil(w), (int) Math.ceil(h*1.05F));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.requestFocusInWindow();
        this.addKeyListener(input);
        this.addMouseMotionListener(input);
        this.setVisible(true);
        getContentPane().setBackground( Color.black );
        
        Vrenderer = ren;
        Vrenderer.sSi = true;
        clean();
        out = new ImagePanel2(actualImage);
        //this.add(Vrenderer);
        this.add(out);
        Vrenderer.setVisible(false);
        clean();
    }
    
    
    public void clean(){
        if (useVFX) {
            try {
                Vrenderer.setVisible(false);
                this.remove(Vrenderer);
                this.add(out);} catch (Exception e) {}
            try {
                actualImage = createImage(Vrenderer);
                out.imagec = actualImage;
            } catch (Exception e) {
                //e.printStackTrace();
            }
        } else {
            try {this.remove(out);
                this.add(Vrenderer);
                Vrenderer.setVisible(true);
                } catch (Exception e) {}
            
        }
        this.revalidate();
        this.repaint();
    }
    
    
    
    public BufferedImage createImage(Renderer panel) {
        int w2 = panel.getWidth();  panel.setW(w2);
        int h2 = panel.getHeight(); panel.setH(h2);
        BufferedImage bi = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        
        
        //panel.paint(g);
        panel.printAll(g);
        g.dispose();
        for(int x : new Range(bi.getWidth())){
            for(int y : new Range(bi.getHeight())){
                int rgb = bi.getRGB(x, y);
                Color i = new Color(rgb);
                i = new Color(0, i.getGreen(), i.getBlue());
                
                //System.out.println(rgb);
                bi.setRGB(x, y, i.getRGB());
            }
        }
        
        return bi;
    }
    
    public static void main(String[] args) {
        Supervisor k = new Supervisor(0, true, new Point2D(0, 0), 0);
        k.run();
        new engineWindow(k.Logic.input, k, k.Logic.Vrenderer);
    }
}
class ImagePanel2 extends JPanel{

    public BufferedImage imagec;

    public ImagePanel2(BufferedImage img) {
        super();
        this.setImage(img);
    }
    
    public void setImage(BufferedImage img){this.imagec = img;}
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagec, 0, 0, null); // see javadoc for more info on the parameters            
    }

}