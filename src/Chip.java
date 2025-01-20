import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.File;

public class Chip {
    private int value;
    private Image chipImage;
    private ImageView imageView;
    private StackPane stackPane;

    public Chip(int value, String fileName) {
        this.value = value;
        File imageFile = new File(fileName);
        chipImage = new Image("file:" + imageFile.getAbsolutePath());
        stackPane = new StackPane();

        imageView = new ImageView(chipImage);

        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        imageView.setSmooth(true);
        imageView.setCache(true);

        stackPane.getChildren().add(imageView);
    }

    public StackPane getStackPane() {
        return stackPane;
    }

    public int getValue() {
        return value;
    }
}
