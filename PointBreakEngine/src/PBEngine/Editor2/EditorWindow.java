/*
 * The MIT License
 *
 * Copyright 2019 Elias Eskelinen <elias.eskelinen@protonmail.com>.
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
package PBEngine.Editor2;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Elias Eskelinen aka Jonnelafin
 */
public class EditorWindow extends JFrame{
    public static void main(String[] args) {
        EditorWindow editorWindow = new EditorWindow();
        editorWindow.container.add(new JButton("North"), BorderLayout.PAGE_START);
        editorWindow.container.add(new JButton("CENTER"), BorderLayout.CENTER);
        editorWindow.container.add(new JButton("END"), BorderLayout.PAGE_END);
    }
    BorderLayout layout = new BorderLayout();
    public JPanel container = new JPanel(layout);
    private Component north;
    private Component east;
    private Component south;
    private Component west;
    private Component center;
    
    public EditorWindow(){
        setTitle("PBEngine editor2");
        setSize(200, 200);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        add(container);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    public void setNorth(Component n){
        try {
            container.remove(north);
        } catch (Exception e) {
        }
        north = n;
        container.add(north, BorderLayout.PAGE_START);
    }
    public void setEast(Component n){
        try {
            container.remove(east);
        } catch (Exception e) {
        }
        east = n;
        container.add(north, BorderLayout.LINE_END);
    }
    public void setSouth(Component n){
        try {
            container.remove(south);
        } catch (Exception e) {
        }
        south = n;
        container.add(north, BorderLayout.PAGE_END);
    }
    public void setWest(Component n){
        try {
            container.remove(west);
        } catch (Exception e) {
        }
        west = n;
        container.add(north, BorderLayout.LINE_START);
    }
    public void setCenter(Component n){
        try {
            container.remove(center);
        } catch (Exception e) {
        }
        center = n;
        container.add(north, BorderLayout.CENTER);
    }
}
