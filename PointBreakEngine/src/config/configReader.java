/*
 * The MIT License
 *
 * Copyright 2019 Elias.
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

package config;

import PBEngine.Supervisor;
import jfUtils.quickTools;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonnelafin
 */
public class configReader {
    public configReader(){
        
    }

    /**
     * PLEASE RUN ONLY AFTER KICK HAS CREATED THE ENGINE LOGIC
     * @param filename
     * @param k
     */
    public static void load(String filename, Supervisor k) throws FileNotFoundException{
        String source = "";
        try {
            Scanner in = new Scanner(new FileReader(filename));
            while(in.hasNext()){
                source = source + in.next();
            }
        } catch (FileNotFoundException ex) {
            //throw ex;
            System.out.println("Could not read config file: " + ex);
            System.out.println("trying to create a new one...");
            String stsa = "Done!\n";
            try (
                Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filename), "utf-8"))) {
                    writer.write("");
                    writer.close();
                }
            
            catch (IOException ex1) {
                stsa = "";
                System.out.println("FAILED, SKIPPING CONFIG READ BECOUSE:");
                Logger.getGlobal().warning(ex1.toString());
                System.out.println("Console version: " + ex1.toString());
            }
            System.out.print(stsa);
        }
        LinkedList<String[]> raw = fetch(source);
        
        if(k.Logic == null){
            Logger.getGlobal().warning("ENGINE NOT INITIALIZED PROPERLY, ABORTING APPLYING CONFIG");
            return;
        }
        if(k.Logic.Vrenderer == null){
            Logger.getGlobal().warning("ENGINE RENDERER NOT INITIALIZED PROPERLY, ABORTING APPLYING CONFIG");
            return;
        }
        
        for(String[] x: raw){
            String value = x[1];
            if(!k.features_overrideAllStandard)
                switch(x[0]){
                    case "nausea":
                        int numValue = 0;
                        try{
                            numValue = Integer.parseInt(value);
                        }
                        catch(Exception e){
                            quickTools.alert("configReader", "invalid nausea value");
                            break;
                        }
                        if(numValue > 0){
                            k.Logic.Vrenderer.dispEffectsEnabled = true;
                        }
                        //k.Logic.Vrenderer.di
                        break;
                    default:

                        break;
                }
            customFeatures(x[0], x[1], k);
        }
    }

    /**
     * For your own config reading.
     * eg. switch(key) { ... }
     * @param key
     * @param value
     * @param su
     */
    private static void customFeatures(String key, String value, Supervisor su){
        
    }
    private static LinkedList<String[]> fetch(String source){
        LinkedList<String[]> out = new LinkedList<String[]>();
        String word = "";
        String value = "";
        String option = "";
        for(char x : source.toCharArray()){
            switch(x){
                case '\n':
                    value = word;
                    word = "";
                    
                    String[] combo = new String[]{
                        value,
                        option
                    };
                    out.add(combo);
                    break;
                case ':':
                    option = word;
                    word = "";
                    break;
                default:
                    word = word + x;
                    break;
            }
        }
        
        /*//ADD THE LAST WORD
        value = word;
        word = "";
        
        String[] combo = new String[]{
        value,
        option
        };
        out.add(combo);
        //END ADD LAST WORD*/
        
        return out;
    }
}
