/*
 * The MIT License
 *
 * Copyright 2019 elias.
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
package JFUtils;

/**
 *
 * @author elias
 */
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JSliderOnJOptionPane extends JOptionPane{
  public static void main(final String[] args) {
        new JSliderOnJOptionPane();
  }
  public static Object Ask(String title, String msg, int spacing, int min, int max){
        JFrame parent = new JFrame();
        JOptionPane optionPane = new JOptionPane();
        JSlider slider = getSlider(optionPane, spacing, min, max);
        optionPane.setMessage(new Object[] { msg, slider });
        optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
        optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(parent, title);
        dialog.setVisible(true);
        //System.out.println("Input: " + optionPane.getInputValue());
        return optionPane.getInputValue();
  }
  public JSliderOnJOptionPane(){
      Ask("Slide Chooser", "Select a value: " , 1 , 0, 10);
  }
  public JSliderOnJOptionPane(String title, String msg, int spacing, int min, int max){
      Ask(title, msg, spacing, min, max);
  }

  static JSlider getSlider(final JOptionPane optionPane, int spacing, int min, int max) {
        JSlider slider = new JSlider();
        slider.setMajorTickSpacing(spacing);
        slider.setMaximum(max);
        slider.setMinimum(min);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        ChangeListener changeListener = new ChangeListener() {
          public void stateChanged(ChangeEvent changeEvent) {
            JSlider theSlider = (JSlider) changeEvent.getSource();
            if (!theSlider.getValueIsAdjusting()) {
              optionPane.setInputValue(new Integer(theSlider.getValue()));
            }
          }
        };
        slider.addChangeListener(changeListener);
        return slider;
  }

}
