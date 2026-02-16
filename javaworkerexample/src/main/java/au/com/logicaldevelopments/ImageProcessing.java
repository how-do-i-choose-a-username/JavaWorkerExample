package au.com.logicaldevelopments;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

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
            // String encoded = Base64.getEncoder().encodeToString(raw);

            return raw;
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
