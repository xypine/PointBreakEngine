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
    String R = "";
    public String parse(String content, Color c){
        String tmp = "<font color=" + c.toString() + ">" + content + "</font>";
        R = "";
        R = R + c.toString().toCharArray()[17] + c.toString().toCharArray()[18] + c.toString().toCharArray()[19];
        String r = c.getRed() + "";
        System.out.println(R);
        return(tmp);
    }
}
