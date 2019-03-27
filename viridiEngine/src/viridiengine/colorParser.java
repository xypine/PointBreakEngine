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
        String tmp = "<font color=" + c.toString() + ">" + content + "</font>";
        return(tmp);
    }
}
