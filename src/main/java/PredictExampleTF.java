import org.tensorflow.*;
import java.nio.FloatBuffer;
import java.nio.LongBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.Iterator;

/**
 *  This an example of how to load and predict using tensorflow based on trained model by python
 *
 */
public class PredictExampleTF {

    public static final String output_file_name = "data/test_out.bin";
    public static final String input_file_name = "data/test_in.bin";
    public static final String model_name = "data/saved_model";
    public static final String tag = "serve";
    public static final String input_layer_name = "input";
    public static final String output_layer_name = "output";
    public static final int dim = 1;
    public static final long[] shape = {1, 784};


    /**
     *  Load the output file which contains only 0 or 1 for label position. So if out[i] == 1.0f it
     * means the label is actually i+1
     *
     *  @return the label
     *  @throws Exception if something is going wrong
     */
    public static int loadOutputFile() throws Exception {

        Path p_output = FileSystems.getDefault().getPath("", output_file_name);
        byte[] data_bytes = Files.readAllBytes(p_output);
        ByteBuffer buffer = ByteBuffer.wrap(data_bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        // Compute size
        int T = data_bytes.length / (dim*4);
        int output_data_size = T*dim;

        // Get which index = 1
	int save_index = -1;
	int i=0;
	while ((i<output_data_size) && (save_index == -1)) {
	    float f = buffer.getFloat();
	    if (f == 1.0f)
		save_index = i + 1;

	    i++;
        }

	return save_index;
    }


    /**
     *  Load the input file into a tensor
     *
     *  @return a tensor float
     *  @throws Exception if something is going wrong
     */
    public static Tensor<Float> loadInputFile() throws Exception {
        Path p_input = FileSystems.getDefault().getPath("", input_file_name);
        byte[] data_bytes = Files.readAllBytes(p_input);
        ByteBuffer buffer = ByteBuffer.wrap(data_bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        // Compute size
        int T = data_bytes.length / (dim*4);
        int input_data_size = T*dim;

        // Generate vector C
        FloatBuffer input_data = FloatBuffer.allocate(input_data_size);
        for (int i=0; i<input_data_size; i++)
        {
	    float f = buffer.getFloat();
	    // System.out.print("f = "); System.out.println(f);

	    if (Float.isNaN(f))
                throw new Exception(input_file_name + " contains nan values! ");

	    input_data.put(f);
        }
	input_data.compact();


	return Tensor.create(shape, input_data);
    }


    /**
     *  Main function which load the model, try to predict the label from the output file with the
     *  info from the input file.
     *
     *  See https://stackoverflow.com/questions/43521439/tensorflow-model-import-to-java#43526228
     *  for the model loading and see python script
     *
     *  @param args useless
     *  @throws Exception if anything is going wrong
     */
    public static void main(String[] args) throws Exception {
        try (SavedModelBundle model = SavedModelBundle.load(model_name, tag)) {
            final String value = "Hello from " + TensorFlow.version();

            // Execute the "MyConst" operation in a Session.
            try (Session s = model.session();
		 Tensor<Float> input = loadInputFile();
		 // Tensor<Float> output = (Tensor<Float>) Tensor.create({1, 10}, FloatBuffer.allocate(10));
		 Tensor<Float> result = (Tensor<Float>) s.runner().feed(input_layer_name, input).fetch(output_layer_name).run().get(0))
		{
		    final long[] rshape = result.shape();
		    if (result.numDimensions() != 1 || rshape[0] != 1) {
		    	throw new RuntimeException(
		    				   String.format(
		    						 "Expected model to produce a [1 %d] shaped tensor where N is the number of labels, instead it produced one with shape %s", 1,
		    						 Arrays.toString(rshape)));
		    }

		    // System.out.println(result.intValue());
		    LongBuffer output = LongBuffer.allocate(1);
		    int loaded = loadOutputFile();
		    result.writeTo(output);
		    for (int k=0; k < output.limit(); ++k) {
		        System.out.print("prediction = " + (output.get(k)+1) + ", expected = " + loaded);
			System.out.println();
		    }
		}
        }
    }
}
