# TensorFlow Gradle proof of concept

Adapted from the [TensorFlow Java docs](https://www.tensorflow.org/install/install_java) for Gradle

## Usage

```
$ ./gradlew run
```

### Usage with GPU

To run with GPU support (assuming all drivers are installed), run with the `gpu` property
```
$ ./gradlew run -Pgpu
```

### Run with `nvidia-docker`

Run container interactively and install required packages
```
$ nvidia-docker run -it nvidia/cuda:9.0-cudnn7-devel /bin/bash
# apt update
# apt install -y curl unzip zip git
```
Install [SDKMAN!](http://sdkman.io/) and use it to rapidly provision an OpenJDK
```
# curl -s "https://get.sdkman.io" | bash
# source "$HOME/.sdkman/bin/sdkman-init.sh"
# sdk install java 8u152-zulu
```
Clone [this repo](https://github.com/psibre/tensorflow-gradle-poc) and run the proof of concept app
```
# git clone https://github.com/psibre/tensorflow-gradle-poc.git
# cd tensorflow-gradle-poc
# ./gradlew run -Pgpu
```
