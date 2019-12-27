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
    public String fonts = appendix+"assets/fonts/";
    public String root = appendix;
    public directory(){
        if(System.getProperty("os.name").equals("Linux")){
            appendix = System.getProperty("user.dir")+"/";
            levels = appendix+"assets/levels/";
            textures = appendix+"assets/textures/";
            replays = appendix+"assets/replays/";
            music = appendix+"assets/music/";
            config = appendix+"assets/config/";
            config = appendix+"assets/fonts/";
        }
    }
}
