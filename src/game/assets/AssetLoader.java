package game.assets;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class AssetLoader {

    private static HashMap<String, BufferedImage> images = new HashMap<>();

    public static BufferedImage loadImage(String filePath) {
        if (images.containsKey(filePath)) {
            return images.get(filePath);
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(filePath));
            images.put(filePath, image);

        }catch (IOException e) {
            System.out.println("Chyba pri nacitani obrazku: " + filePath);
            e.printStackTrace();
        }
        return image;
    }
}
