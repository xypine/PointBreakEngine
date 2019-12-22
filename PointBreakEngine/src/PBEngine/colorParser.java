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
