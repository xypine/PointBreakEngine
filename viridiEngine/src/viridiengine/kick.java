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
    public kick(){
        wM = new windowManager(this);
        ea = new Editor(this);
        
        SwingUtilities.invokeLater(wM);
        SwingUtilities.invokeLater(ea);
        ea.setVisible(false);
    }
    public void tog(){
        wM.setVisible(tog);
        wM.running = tog;
        ea.setVisible(!tog);
        ea.running = !tog;
        tog = !tog;
    }
}
