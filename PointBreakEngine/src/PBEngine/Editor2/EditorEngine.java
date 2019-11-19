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
package PBEngine.Editor2;

import JFUtils.dVector;

/**
 *
 * @author guest-kxryfn
 */
public class EditorEngine extends PBEngine.Supervisor{
    Editor editor;
    public EditorEngine(int mode, boolean bakedLights, dVector gravity, Editor e) {
        super(mode, bakedLights, gravity);
        this.editor = e;
    }
    public EditorEngine(int mode, boolean bakedLights, dVector gravity, int i, Editor e) {
        super(mode, bakedLights, gravity, i);
        this.editor = e;
    }
    @Override
    public void tick_pre(){
        if(editor.cursor != null){
            editor.cursor.update(xd, yd, objectManager);
        }
    }
    @Override
    public void tick_late(){
        
    }
}
