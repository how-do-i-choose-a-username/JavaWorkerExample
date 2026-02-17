package au.com.logicaldevelopments.javaworkerexample;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

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
            InputStream inputStream = new ByteArrayInputStream(inputData);

            BufferedImage image = Thumbnails.of(inputStream).scale(scale).rotate(rotation).asBufferedImage();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            byte[] raw = outputStream.toByteArray();

            return raw;
        } catch (Exception e) {
            e.printStackTrace();
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
        byte[] binaryInputData = Base64.getDecoder().decode(inputData);
        byte[] binaryOutputData = ProcessBinaryImage(binaryInputData, scale, rotation);
        String outputData = Base64.getEncoder().encodeToString(binaryOutputData);
        return outputData;
    }
}
