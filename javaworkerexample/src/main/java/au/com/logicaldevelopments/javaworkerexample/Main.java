package au.com.logicaldevelopments.javaworkerexample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Main class is used to run the program from the command line.
 */
public class Main {
    /**
     * Program entry point. Very basic argument parsing to allow simple testing of
     * the Java code without having to rebuild and deploy to Omnis every time a
     * change is made.
     * 
     * @param args Command line arguments
     */
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
            // Read file paths
            String inputPath = args[0];
            String outputPath = args[3];

            try {
                // Read and parse numeric values
                double scale = Double.parseDouble(args[1]);
                double rotation = Double.parseDouble(args[2]);

                // Run the image processing function
                TestImageProcessing(inputPath, scale, rotation, outputPath);
            } catch (NumberFormatException e) {
                String invalidFormatMessage = "Please ensure scale and rotation are numeric values.";
                System.out.println(invalidFormatMessage);
            }
        }
    }

    /**
     * Test method for the image processing function. Reads a file to binary,
     * processes it, and writes the resulting binary back to disk.
     * 
     * @param inputPath  Path to read the image from
     * @param scale      Scale to apply to the image (0 - 1)
     * @param rotation   Rotation to apply in degrees
     * @param outputPath Output path of the resulting image
     */
    public static void TestImageProcessing(String inputPath, double scale, double rotation, String outputPath) {
        try {
            // Read the image from file
            FileInputStream inputStream = new FileInputStream(new File(inputPath));
            byte[] inputImage = inputStream.readAllBytes();
            inputStream.close();

            // Perform the actual processing
            byte[] outputImage = ImageProcessing.ProcessBinaryImage(inputImage, scale, rotation);

            // Write the image to the output
            FileOutputStream outputStream = new FileOutputStream(new File(outputPath));
            outputStream.write(outputImage);
            outputStream.close();
        } catch (Exception e) {
            // As this is a testing function, catch all exceptions and print them. This is
            // usually bad practice
            e.printStackTrace();
        }
    }
}