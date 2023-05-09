/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PIClass;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author 21651
 */
public class LabelImage extends Label {
    private ImageView imageView;

    public LabelImage() {
        super();
        imageView = new ImageView();
        setGraphic(imageView);
    }

    public void setImage(Image image) {
        imageView.setImage(image);
    }

    public Image getImage() {
        return imageView.getImage();
    }
      public LabelImage(ImageView imageView) {
        super("", imageView);
    }
}
