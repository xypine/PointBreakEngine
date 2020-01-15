import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PaintComponent {

    public static void main(String[] args) {
        new PaintComponent();
    }

    public PaintComponent() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        private JPanel paintPane;

        public TestPane() {

            paintPane = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;

            paintPane.add(new JLabel("I'm a label"), gbc);
            paintPane.add(new JTextField("I'm a text field", 20), gbc);
            paintPane.add(new JLabel(new ImageIcon("")), gbc);

            setLayout(new BorderLayout());
            add(paintPane);

            JButton paint = new JButton("Capture");
            paint.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BufferedImage image = new BufferedImage(paintPane.getWidth(), paintPane.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D g = image.createGraphics();
                    paintPane.printAll(g);
                    g.dispose();
                    try {
                        ImageIO.write(image, "jpg", new File("Paint.jpg"));
                        ImageIO.write(image, "png", new File("Paint.png"));
                    } catch (IOException exp) {
                        exp.printStackTrace();
                    }
                }
            });
            add(paint, BorderLayout.SOUTH);

        }
    }
}