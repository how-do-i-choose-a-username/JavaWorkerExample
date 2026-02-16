package au.com.logicaldevelopments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Main {
    public static void main(String[] args) {
        System.out.println();
        // Show help if there are an incorrect number of parameters, or help has been
        // requested
        if (args.length != 4 || args[0] == "--help") {
            String helpMessage = """
                    Command line interface parameter help
                    This application expects the following parameters (must specify exactly 4):
                    <input-image-path> <scale> <rotation> <output-image-path>

                    Parameter description is as follows
                    input-image-path: Image file of the following formats to read for processing
                    scale: value to scale the image by from 0 (exclusive) to 1 (inclusive) where 1 is full size
                    rotation: rotation in degrees to apply to the image
                    output-image-path: png path to write the image to. Will overwrite files
                    """;
            System.out.println(helpMessage);
        } else {
            String inputPath = args[0];
            String outputPath = args[3];

            try {
                double scale = Double.parseDouble(args[1]);
                double rotation = Double.parseDouble(args[2]);

                // Call our code
                TestImageProcessing(inputPath, scale, rotation, outputPath);
            } catch (NumberFormatException e) {
                String invalidFormatMessage = "Please ensure scale and rotation are numeric values.";
                System.out.println(invalidFormatMessage);
            }
        }
    }

    public static void TestImageProcessing(String inputPath, double scale, double rotation, String outputPath) {
        try {
            // Read the image from file
            FileInputStream inputStream = new FileInputStream(new File(inputPath));
            byte[] inputImage = inputStream.readAllBytes();
            inputStream.close();

            byte[] outputImage = ImageProcessing.ProcessBinaryImage(inputImage, scale, rotation);

            // Write the image to the output
            FileOutputStream outputStream = new FileOutputStream(new File(outputPath));
            outputStream.write(outputImage);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}