/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridilines;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Jonnelafin
 */
public class init implements Runnable, ActionListener{
    render re;
    Timer timer = new Timer(30, this);
    @Override
    public void run() {
        timer.setRepeats(true);
        timer.start();
        re = new render();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        re.updateT();
    }

}
