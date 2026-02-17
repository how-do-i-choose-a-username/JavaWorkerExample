package au.com.logicaldevelopments.javaworkerexample;

import java.util.HashMap;
import java.util.Map;

import net.omnis.OmnisCalls.OModule;
import net.omnis.OmnisCalls.Response;
import net.omnis.OmnisCalls.SendError;
import net.omnis.OmnisCalls.SendResponse;

/**
 * Class which interfaces with Omnis. It extends from OModule which is the key
 * requirement.
 */
public class WorkerInterface extends OModule {
    /**
     * Transform the image supplied from Omnis. I find it best to keep this method
     * lean. The following is my procedure:
     * 1) Unpack parameters
     * 2) Call function to do the processing
     * 3) Pack results to return to Omnis
     * This is because deploying a jar to run with Omnis is relatively slow, and
     * it's easier to test code by running the Java program directly. To make this
     * effective, the bulk of the code should be located elsewhere.
     * 
     * @param params Parameters from Omnis
     * @return Response to Omnis containing the function results
     */
    public Response TransformImage(Map<String, Object> params) {
        // Init default parameters
        String imageData = "";
        double scale = 1.0;
        double rotation = 0.0;

        // Read parameters from the params map
        // Making sure to check the type, since the params come in as type Object
        // First read the picture data, which is base64 encoded binary
        Object imageDataObj = params.get("picture");
        if (imageDataObj != null && imageDataObj instanceof String) {
            imageData = (String) imageDataObj;
        }
        // Read the transformations, defaulting to their current value (while not
        // necessary here, default values can make parameter processing simpler)
        Object scaleObj = params.getOrDefault("scale", scale);
        if (scaleObj instanceof Double) {
            scale = (Double) scaleObj;
        }
        Object rotationObj = params.getOrDefault("rotation", rotation);
        if (rotationObj instanceof Double) {
            rotation = (Double) rotationObj;
        }

        // Do the actual processing
        String outputImage = ImageProcessing.ProcessBase64Image(imageData, scale, rotation);

        // Response object to return to Omnis
        Response response;
        if (!outputImage.isEmpty()) {
            // Pack the processed image back into a map to send to Omnis
            Map<String, Object> data = new HashMap<>();
            data.put("imageData", outputImage);
            response = new SendResponse(data);
        } else {
            // No image data returned, so return an error to Omnis
            Map<String, Object> data = new HashMap<>();
            data.put("errorMessage", "No image data was returned, ensure an image was provided.");
            response = new SendError(data);
        }

        return response;
    }
}
