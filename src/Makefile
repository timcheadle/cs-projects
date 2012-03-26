SRC_FILES = src/Process.java \
			src/Scheduler.java \
			src/FCFSScheduler.java \
			src/SJFScheduler.java \
			src/SRTFScheduler.java \
			src/Simulation.java
			
build: ${SRC_FILES}
	javac -d build/ ${SRC_FILES}
	
run_sim: build
	java -cp build Simulation ${FILE}