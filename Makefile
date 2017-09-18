dist:
	gradle clean 
	rm -rf ./playBinary
	rm -rf RUNNING_PID
	gradle dist 
	unzip build/distributions/playBinary.zip 
	./playBinary/bin/playBinary
