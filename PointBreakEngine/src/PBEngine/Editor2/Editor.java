/*
 * The MIT License
 *
 * Copyright 2019 elias eskelinen.
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

import PBEngine.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author elias
 */
public class Editor {
    public boolean saved = false;
    public PBEngine.Supervisor k;
    public int mode = 0;
    JComboBox select;
    @SuppressWarnings("unchecked")
    public Editor(){
        String[] argss = new String[2];
        argss[0] = "nodemo";
        Camera cam = new Camera(0, 0);
        k = new Supervisor(0, false, new dVector(0, 0), 0);
        k.timerType = 1;
        Thread A = new Thread(k);
        A.start();
        while(!k.ready){
            
        }
        k.Logic.setTitle("PointBreakEngine (Editor2)");
        k.Logic.Vrenderer.factor = 20;
        
        JPanel editorPanel = new JPanel();
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout(0, 0));
        
        JButton saveB = new JButton("Save");saveB.addActionListener(new BListener(1, this));
        JButton loadB = new JButton("Load");loadB.addActionListener(new BListener(2, this));
        JButton testB = new JButton("Run");testB.addActionListener(new BListener(3, this));
        String[] options = {"Wall","Light","?"};
        select = new JComboBox(options);select.addActionListener(new BListener(4, this));
        select.setEditable(true);
        
        editorPanel.add(saveB);
        editorPanel.add(loadB);
        editorPanel.add(testB);
        editorPanel.add(select);
        
        container.add(editorPanel, BorderLayout.NORTH);
        k.kit.cont.add(container, BorderLayout.NORTH);
        
        k.Logic.abright = true;
        k.Logic.Vrenderer.camMode = 0;
        k.Logic.Vrenderer.drawGrid = true;
        k.Logic.Vrenderer.gridColor = new Color(0, 90, 20);
        k.Logic.overrideRayBG = Color.GRAY;
        
        Input cursorInput = new Input(k);
        
        cursor = new Cursor(0, 0, 1, "newcursor", "C", 1, Color.white, 0, k, this, cursorInput);
        quickTools.alert(k.toString());
        cursor.onlyColor = true;
        cursor.imageName = "";
        k.objectManager.addObject(cursor);
        System.out.println("Editor initialization finished");
        System.out.println(k.Logic.running);
    }
    Cursor cursor;
}
class BListener implements ActionListener{
    boolean abright = false;
    int type;
    PBEngine.Editor2.Editor editor;
    public BListener(int t, PBEngine.Editor2.Editor d){
        this.type = t;
        this.editor = d;
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(type == 1){
            System.out.println("Save...");
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
                try {
                    PrintWriter writer = new PrintWriter(selectedFile.getAbsolutePath(), "UTF-8");
                    writer.print("");
                    writer.close();
                    new FileLoader("null", editor.k.objectManager, editor.k).write(editor.k.objectManager.getObjects(), selectedFile.getAbsolutePath(), "");
                } catch (Exception ex) {
                    Logger.getLogger(BListener.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }
        if(type == 2){
            System.out.println("Load");
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
                try {
                    LinkedList<gameObject> old = editor.k.Logic.loadLevel(selectedFile.getAbsolutePath(), "", Color.BLUE, Color.GREEN);
                    
                } catch (URISyntaxException ex) {
                    Logger.getLogger(BListener.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }
        if(type == 4){
            int action = editor.select.getSelectedIndex();
            System.out.println(action);
            editor.mode = action;
            /*if(action.equals("Wall")){
            editor.mode = 0;
            }
            if(action.equals("Light")){
            editor.mode = 1;
            }*/
            
        }
    }
    
}
