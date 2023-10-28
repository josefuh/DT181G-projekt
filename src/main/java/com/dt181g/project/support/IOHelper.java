package com.dt181g.project.support;

import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Support class used to read data from files.
 *
 * @author josef Alirani
 */
public enum IOHelper {
    instance;

    /**
     * Function used to load a JSONObject from "text.JSON".
     *
     * @return JSONObject from "text.JSON".
     * @throws IOException "text.JSON" not found...
     */
    public JSONObject loadJSONFile() throws IOException {
        try (InputStream inputStream = IOHelper.class.getResourceAsStream("/texts/text.JSON")) {
            if (inputStream == null) {
                throw new IOException("File not found: text.JSON");
            }

            byte[] jsonData = inputStream.readAllBytes();
            return new JSONObject(new String(jsonData, StandardCharsets.UTF_8));
        }
    }

    /**
     * Function used to get a specific String from a JSONObject.
     *
     * @param object the JSONObject to get the String from.
     * @param category the category of the String.
     * @param key the corresponding key-value of the String.
     * @return specific String from JSONObject.
     */
    public String parseJSONFile(JSONObject object, String category, String key) {
        JSONObject jsonObject;

        jsonObject = (JSONObject) object.getJSONArray(category).get(0);

        Object resultingValue = jsonObject.get(key);

        return resultingValue.toString();
    }

    /**
     * Function used to get an Image-variable from an image-file.
     *
     * @param fileName the image-file to read.
     * @return Image-variable.
     * @throws IOException in case the image-file can't be found.
     */
    public Image readImageFile(String fileName) throws IOException {
        try (InputStream inputStream = IOHelper.class.getResourceAsStream("/images/"+fileName)) {
            if (inputStream == null) {
                throw new IOException("File not found: "+fileName);
            }
            return ImageIO.read(inputStream);
        }
    }

    /**
     * Function used to get a specific String value from 'resources/text.JSON'
     *
     * @param key the key of the String value
     * @return String value from the corresponding key-value.
     */
    public String readFromJSON(String event, String key) {
        String value;

        try {
            JSONObject jsonObject = IOHelper.instance.loadJSONFile();
            value = IOHelper.instance.parseJSONFile(jsonObject, event, key);

        }catch (IOException err) {
            err.printStackTrace();
            throw new RuntimeException();
        }

        return value;
    }
}
