/*
 * The MIT License
 *
 * Copyright 2019 Elias Eskelinen.
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

package PBEngine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Jonnelafin
 */
public class Question {
    final JLabel output = new JLabel();;
    boolean adding = false;
    int r = 0;
    int g = 0;
    int b = 0;
    int s = 0;
    public Question(String title,String msg,String confirm){
        JFrame frame = new JFrame(title);
        frame.setVisible(false);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel(msg);
        JPanel panel = new JPanel();
        frame.add(panel);
        panel.add(label);

        final JTextField input = new JTextField(1);
        final JTextField input1 = new JTextField(1);
        final JTextField input2 = new JTextField(1);
        final JTextField input3 = new JTextField(1); // The input field with a width of 5 columns
        panel.add(input);
        panel.add(input1);
        panel.add(input2);
        panel.add(input3);

        
        JButton button = new JButton("add light");
        panel.add(button);

//        JLabel output = new JLabel(); // A label for your output
        panel.add(output);
        
        
        
        String out = "";
        
        button.addActionListener(new ActionListener() { // The action listener which notices when the button is pressed
            public void actionPerformed(ActionEvent e) {
                r = Integer.parseInt(input1.getText());
                g = Integer.parseInt(input2.getText());
                b = Integer.parseInt(input3.getText());
                s = Integer.parseInt(input.getText());
                adding = true;
                
            }
        });
        
    }public String exit(){
        return output.getText();
    }
}
