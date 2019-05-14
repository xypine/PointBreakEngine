/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import javax.swing.SwingUtilities;

/**
 *
 * @author Jonnelafin
 */
public class kick {
    windowManager wM;
    Editor ea;
    boolean tog;
    kick ref = this;
    public kick(){
        new Thread(){
                @Override
                public void run(){
        wM = new windowManager(ref);
        ea = new Editor(ref);
        
        SwingUtilities.invokeLater(wM);
        SwingUtilities.invokeLater(ea);
        //Thread a = new Thread(wM, "Thread 1");
        //Thread b = new Thread(ea, "Thread 2");
        //a.start();
        //b.start();
        
        ea.setVisible(false);
                }
        }.start();
    }
    public void tog(){
        if(tog){
            wM.record();
        }
        wM.setVisible(tog);
        wM.running = tog;
        ea.setVisible(!tog);
        ea.running = !tog;
        tog = !tog;
    }
}
