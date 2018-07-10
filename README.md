# TensorFlow Gradle proof of concept with loading

Adapted from the [TensorFlow Java docs](https://www.tensorflow.org/install/install_java) for Gradle

## Usage

First the model is trained using Python.
Then you can load the model with Java.

```
./gradlew run
```

### Usage with GPU

To run with GPU support (assuming all drivers are installed), run with the `gpu` property
```
./gradlew run -Pgpu
```

#### Run with `nvidia-docker`
##### Prepare the environment
Generate the image
```
nvidia-docker build . -t test_tf_java
```

Start docker container
```
nvidia-docker run -it test_tf_java /bin/bash
```

#### In the container
Run the training
```
(cd python_part/ && python train.py)
```

Run the prediction
```
./gradlew -Pgpu run
```
