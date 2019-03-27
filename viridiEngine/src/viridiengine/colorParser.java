/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viridiengine;

import java.awt.Color;

/**
 *
 * @author guest-bfhkvp
 */
public class colorParser {

    public String parse(String content, Color c){
        String r = c.getRed() + "";
        String g = c.getGreen() + "";
        String b = c.getBlue() + "";

        String tmp = "<font color=" + "rgb(" + r + ","+ g + "," + b + ")>" + content + "</font>";
//        R = R + c.toString().toCharArray()[17] + c.toString().toCharArray()[18] + c.toString().toCharArray()[19];


        return(tmp);
    }
}
