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
package PBEngine;

import JFUtils.dVector;

/**
 *
 * @author Elias Eskelinen <elias.eskelinen@protonmail.com> eskelinen
 */
public class mapParser {
    static String[][] sampleFinish = {
        {"out", "shadingtest","out"},
        {"shadingtest", "out","shadingtest"},
        {"out", "shadingtest","out"}
    };
    static String sampleInput = "out~shadingtest~out~|\n" +
"shadingtest~out~shadingtest~|\n" +
"out~shadingtest~out~|";
    
    
    public static String[][] parseMap(String mapfile){
        String[][] out;
        String currentWord = "";
        int x = 0, y = 0;
        int xd = 0, yd = 0;
        for(char i : mapfile.toCharArray()){
            if(i == '|'){
                y++;
                xd = x;
                x = 0;
            }
            if(i == '~'){
                x++;
            }
        }
        yd = y;
        //xd++;yd++;
        System.out.println(new dVector(xd,yd).represent());
        x = 0; y = 0;
        
        
        out = new String[xd][yd];
        for(char i : mapfile.toCharArray()){
            if(i == '|'){
                y++;
                x = 0;
            }
            else if(i == '~'){
                
                try {
                    out[x][y] = currentWord;
                } catch (Exception e) {
                    System.out.println("error in parsing the levelmap: " + x + " " + y);
                }
                currentWord = "";
                x++;
            }
            else if(i == '\n'){}
            else{
                currentWord = currentWord + i;
            }
        }
        return out;
        
    }
    public static void main(String[] Args){
        String[][] parsed = parseMap(sampleInput);
        int x = 0; int y = 0;
        for(String[] lane : parsed){
            for(String i : lane){
                System.out.print(i +"           ");
                x++;
            }System.out.println("");y++;x = 0;
        }
    }
    public static boolean validateMapSize(int w, int h){
        if(w == h){
            return true;
        }
        return false;
    }
}
