# TensorFlow Gradle proof of concept

Adapted from the [TensorFlow Java docs](https://www.tensorflow.org/install/install_java) for Gradle

## Usage

```
$ ./gradlew run
```

### Usage with GPU

To run with GPU support (assuming all drivers are installed), run with the `gpu` property:
```
$ ./gradlew run -Pgpu
```
