FROM tensorflow/tensorflow:latest-gpu

# Default bash
ENTRYPOINT [ "/bin/bash", "-c" ]

# Install baseline
RUN apt-get update
RUN apt-get install -y curl unzip zip git

# Clone repository
ADD . /tensorflow-gradle-poc
WORKDIR /tensorflow-gradle-poc

# Install java
RUN curl -s "https://get.sdkman.io" | bash
RUN ["/bin/bash", "-c", "source $HOME/.sdkman/bin/sdkman-init.sh && sdk install java 8u152-zulu"]
