# TensorFlow Gradle proof of concept with loading

Adapted from the [TensorFlow Java docs](https://www.tensorflow.org/install/install_java) for Gradle

## Usage

First the model is trained using Python in Docker.
Then you can load the model with Java.

[Docker] must be installed.

```
./gradlew run
```

### Usage with GPU

To run with GPU support (assuming all drivers are installed), run with the `gpu` property
```
./gradlew run -Pgpu=1
```
This will use the first GPU for training.
On multi-GPU compute nodes, you can give a higher value to specify a different GPU, e.g.,
```
./gradlew run -Pgpu=3
```
which selects the third GPU.

[Docker]: https://www.docker.com/get-started
