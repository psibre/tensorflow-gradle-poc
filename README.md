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

Run the prediction
```
./gradlew run
```

[Docker]: https://www.docker.com/get-started
