/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viridiengine;

import java.util.ArrayList;

/**
 *
 * @author Jonnelafin
 */
public class objectContainer {
    public static String tag;
    public static Object object;
    public static int id;
    public static String type;
    public objectContainer(String tag, Object o, int id, String t){
        this.tag = tag;
        this.object = o;
        this.id = id;
        this.type = t;
    }
}
