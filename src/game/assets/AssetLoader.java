package game.assets;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * The AssetLoader class provides a method for loading image assets.
 */
public class AssetLoader {

    private static HashMap<String, BufferedImage> images = new HashMap<>();

    /**
     * Loads an image from the specified file path.
     *
     * @param resourcePath the path to the image file to load.
     * @return the loaded BufferedImage.
     */
    public static BufferedImage loadImage(String resourcePath) {
        if (images.containsKey(resourcePath)) {
            return images.get(resourcePath);
        }

        BufferedImage image = null;
        try (InputStream is = AssetLoader.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                System.out.println("Obrázek nenalezen na cestě: " + resourcePath);
                return null;
            }
            image = ImageIO.read(is);
            images.put(resourcePath, image);
        } catch (IOException e) {
            System.out.println("Chyba při načítání obrázku: " + resourcePath);
            e.printStackTrace();
        }
        return image;
    }
}
