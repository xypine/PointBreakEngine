/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PBEngine;

/**
 *
 * @author Jonnelafin
 */
public class directory {
    private String appendix = "";
    public String levels = appendix+"assets/levels/";
    public String textures = appendix+"assets/textures/";
    public String replays = appendix+"assets/replays/";
    public String music = appendix+"assets/music/";
    public String config = appendix+"assets/config/";
    public String root = appendix;
    public directory(){
        if(System.getProperty("os.name").equals("Linux")){
            appendix = System.getProperty("user.dir")+"/";
            levels = appendix+"assets/levels/";
            textures = appendix+"assets/textures/";
            replays = appendix+"assets/replays/";
            music = appendix+"assets/music/";
            config = appendix+"assets/config/";
        }
    }
}
