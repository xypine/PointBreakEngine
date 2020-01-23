/*
 * The MIT License
 *
 * Copyright 2020 guest-fjrlfh.
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
package PBEngine.gameObjects;

import JFUtils.point.Point2D;
import JFUtils.quickTools;
import PBEngine.Rendering.core.renderType;
import PBEngine.Supervisor;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 *
 * @author guest-fjrlfh
 */
public class GameObject_img extends gameObject {
    //Basic tag: "level_preview"
    private BufferedImage image;
    public boolean collisions = false;
    public GameObject_img(Point2D location, int size, Supervisor master, BufferedImage image) {
        super(location, size, 1, renderType.box, master, master.Logic.oM.getUsableID());
        setImage(image);
        this.collisions = false;
    }
    public void setImage(BufferedImage bi){
        //this.brightColor = true;
        //this.onlyColor = true;
        this.setShading(false);
        if(Objects.isNull(bi)){
            quickTools.alert("could not set image: it was null!");
            return;
        }
        masterParent.Logic.Vrenderer.addImageToCache(bi);
        this.imageName =bi.hashCode() + "";
    }
    @Override
    public void overBound(int direction, int xd, int yd){
        //Do nothing...
    }
}
