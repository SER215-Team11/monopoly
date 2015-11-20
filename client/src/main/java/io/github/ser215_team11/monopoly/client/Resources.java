package io.github.ser215_team11.monopoly.client;

import java.util.Map;
import java.util.HashMap;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.awt.Font;
import java.awt.FontFormatException;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * Contains static methods for dealing with resources.
 */
public class Resources {

    private static Map<String, Image> imageCache;
    private static Map<String, Font> fontCache;

    static {
        imageCache = new HashMap<>();
        fontCache = new HashMap<>();
    }

    /**
     * Returns the corresponding resource path for a given regular path. If you
     * want to access a file called "myFile.txt" inside the resources directory,
     * you can pass "/myFile.txt" into this method and get the actual path to
     * the resource.
     * @param regPath path to the file relative to the resources directory
     * @return path to the actual resource
     */
    public static String path(String regPath) {
        return Resources.class.getResource(regPath).getPath();
    }

    /**
     * Loads and caches an image from the given file path. The path does not
     * need to be passed through Resources.path(). If the image has already been
     * loaded, it returns the cached image.
     * @param regPath The path to the image in the resources folder
     * @return image
     * @throws IOException should probably bubble up to the top
     */
    public static Image getImage(String regPath) throws IOException {
        if(imageCache.containsKey(regPath)) {
            return imageCache.get(regPath);
        }

        imageCache.put(regPath, ImageIO.read(new File(Resources.path(regPath))));
        return imageCache.get(regPath);
    }

    /**
     * Loads and caches a font file from the given file path. The path does not
     * need to be passed through Resources.path(). If the font has already been
     * loaded, it returns the cached font.
     * @param regPath The path to the font in the resources folder
     * @return font
     * @throws IOException, FontFormatException should probably bubble up to the top
     */
    public static Font getFont(String regPath) throws IOException, FontFormatException {
        if(fontCache.containsKey(regPath)) {
            return fontCache.get(regPath);
        }

        fontCache.put(regPath, Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(Resources.path(regPath))));
        return fontCache.get(regPath);
    }

}
