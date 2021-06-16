package dtu.gruppe04.littlebrain;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.tensorflow.lite.annotations.UsedByReflection;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.task.core.TaskJniUtils;
import org.tensorflow.lite.task.core.TaskJniUtils.EmptyHandleProvider;
import org.tensorflow.lite.task.core.TaskJniUtils.FdAndOptionsHandleProvider;
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions;
import org.tensorflow.lite.task.vision.core.BaseVisionTaskApi;
import org.tensorflow.lite.task.vision.core.BaseVisionTaskApi.InferenceProvider;
import org.tensorflow.lite.task.vision.detector.Detection;
import org.tensorflow.lite.task.vision.detector.ObjectDetector;
import org.tensorflow.lite.task.vision.detector.ObjectDetector.ObjectDetectorOptions;

public class ObjectDetection {

    ObjectDetector objectDetector;

    public void Init (Context context, String modelPath) throws IOException {
        ObjectDetectorOptions options = ObjectDetectorOptions.builder().setMaxResults(1).build();

        objectDetector = ObjectDetector.createFromFileAndOptions(context, modelPath, options);
    }

    public void Detection(TensorImage image){
        List<Detection> results = objectDetector.detect(image);
    }
}
