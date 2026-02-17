package au.com.logicaldevelopments.javaworkerexample;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

/**
 * This class handles all the actual image processing this Java module requires
 */
public class ImageProcessing {
    /**
     * Process binary data, and return as binary data. While the Thumbnails library
     * has much more elegant tools for file handling, we want to use binary for
     * conversing with Omnis. This demo is designed to show Omnis - Java
     * communication, without the need for intermediate files.
     * 
     * @param inputData Binary image data to be processed
     * @param scale     Scaling to apply to the image
     * @param rotation  Rotation to apply to the image
     * @return Returns binary image data in the PNG format
     */
    public static byte[] ProcessBinaryImage(byte[] inputData, double scale, double rotation) {
        try {
            // Package the input binary data for processing
            InputStream inputStream = new ByteArrayInputStream(inputData);

            // This is where the magic happens. Uses Thumbnailator to scale and rotate the
            // image, then export
            BufferedImage image = Thumbnails.of(inputStream).scale(scale).rotate(rotation).asBufferedImage();

            // Convert the resulting image to a binary value we can send back to Omnis
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            byte[] raw = outputStream.toByteArray();

            return raw;
        } catch (IOException e) {
            e.printStackTrace();
            // Return empty data on a failure state. Ensures nothing crashes, and Omnis code
            // can deal with it later
            return new byte[0];
        }
    }

    /**
     * Process Base64. I wasn't able to directly pass binary data from Omnis to
     * Java, so a conversion process is needed.
     * 
     * @param inputData Input image data as Base64
     * @param scale     Scaling to apply to the image
     * @param rotation  Rotation to apply to the image
     * @return
     */
    public static String ProcessBase64Image(String inputData, double scale, double rotation) {
        // String to binary data
        byte[] binaryInputData = Base64.getDecoder().decode(inputData);
        // Process the binary image data
        byte[] binaryOutputData = ProcessBinaryImage(binaryInputData, scale, rotation);
        // Convert the binary back to text for Omnis to receive
        String outputData = Base64.getEncoder().encodeToString(binaryOutputData);
        return outputData;
    }
}
