/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
