package au.com.logicaldevelopments.javaworkerexample;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import net.omnis.OmnisCalls.OModule;
import net.omnis.OmnisCalls.Response;
import net.omnis.OmnisCalls.SendResponse;

public class WorkerInterface extends OModule {
    public Response TransformImage(Map<String, Object> params) {
        // Init default parameters
        String imageData = "";
        double scale = 1.0;
        double rotation = 0.0;

        // Read parameters from the params map
        // First read the binary data, making sure we get an object of the correct type
        Object imageDataObj = params.get("picture");
        if (imageDataObj != null && imageDataObj instanceof String) {
            imageData = (String) imageDataObj;
        }
        // Read the transformations, defaulting to their current value
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

        // Pack the processed image back into a map to send to Omnis
        Map<String, Object> data = new HashMap<>();
        data.put("imageData", outputImage);
        return new SendResponse(data);
    }
}
