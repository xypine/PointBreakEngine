/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PBEngine;

import java.awt.Color;
import java.util.LinkedList;

/**
 *
 * @author guest-bfhkvp
 */
public class colorParser {
    

    public String parse(String content, Color c, String style){
        String r = c.getRed() + "";
        String g = c.getGreen() + "";
        String b = c.getBlue() + "";
        Color c2 = getContrastColor(c);
        
        String r2 = c.getRed() + "";
        String g2 = c.getGreen() + "";
        String b2 = c.getBlue() + "";
        if(style == "i"){
            r2 = c2.getRed() + "";
            g2 = c2.getGreen() + "";
            b2 = c2.getBlue() + "";
        }
        else{
            
        }
        
        String tmp = "<span color='rgb(" + r + ","+ g + "," + b + ")' style='background-color:rgb(" + r2 + ","+ g2 + "," + b2 + ");'>" + content + "</span>";
//        R = R + c.toString().toCharArray()[17] + c.toString().toCharArray()[18] + c.toString().toCharArray()[19];

        return(tmp);
    }
    public static Color getContrastColor(Color color) {
        double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
        return y >= 128 ? Color.black : Color.white;
}
}
