package io.github.ser215_team11.monopoly.client;

/**
 * Contains static methods for dealing with resources.
 */
public class Resources {

    /**
     * Returns the corresponding resource path for a given regular path. If you
     * want to access a file called "myFile.txt" inside the resources directory,
     * you can pass "/myFile.txt" into this method and get the actual path to
     * the resource.
     *
     * @param regPath path to the file relative to the resources directory
     * @return path to the actual resource
     */
    public static String path(String regPath) {
        return Resources.class.getResource(regPath).getPath();
    }

}
