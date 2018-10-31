package image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {

    Image img;

    public ImagePanel() {
        try {
            this.img = ImageIO.read(new File("" + FileSystems.getDefault().getPath("src/img/defaultImage.png").toAbsolutePath()));
        } catch (IOException ex) {
            Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ImagePanel(Image ximg) {
        img = ximg;
    }

    public void setImage(Image img) {
        this.img = img;
        repaint();
    }

    public Image getImage() {
        return img;
    }

    public void paint(Graphics g) {
        if (img != null) {
            g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0, img.getWidth(this), img.getHeight(this), this);
        } else {

            g.fillRect(0, 0, getWidth(), getHeight());
        }

    }
}
