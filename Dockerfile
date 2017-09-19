FROM java 
ADD distributions/playBinary.tar /
RUN mv /playBinary /play
WORKDIR /play
RUN ls bin
CMD ["./bin/playBinary"]
