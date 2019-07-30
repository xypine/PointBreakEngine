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
    public Question(String title,String msg,String confirm){
        JFrame frame = new JFrame(title);
        frame.setVisible(true);
        frame.setSize(300, 300);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel(msg);
        JPanel panel = new JPanel();
        frame.add(panel);
        panel.add(label);

        final JTextField input = new JTextField(1); // The input field with a width of 5 columns
        panel.add(input);

        JButton button = new JButton(confirm);
        panel.add(button);

//        JLabel output = new JLabel(); // A label for your output
        panel.add(output);
        
        
        
        String out = "";
        
        button.addActionListener(new ActionListener() { // The action listener which notices when the button is pressed
            public void actionPerformed(ActionEvent e) {
                output.setText(input.getText());
            }
        });
    }public String exit(){
        return output.getText();
        }
    public boolean ready(){
        if(output.getText() != ""){return true;}return false;
    }
}
