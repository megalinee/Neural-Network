package dev.megaline.time;

import java.time.Duration;
import java.time.Instant;

public class NeuralNetworkTimer {
    private Instant dataStart;
    private Instant neuralStart;
    private Instant neuralEnd;

    public NeuralNetworkTimer() {
    }

    public NeuralNetworkTimer(TimeID id) {
        setTime(id);
    }

    public enum TimeID {
        DATA_PARSE_START,
        NEURAL_NETWORK_START,
        NEURAL_NETWORK_END
    }

    public void setTime(TimeID id) {
        switch (id) {
            case DATA_PARSE_START:
                dataStart = Instant.now();
                break;
            case NEURAL_NETWORK_START:
                neuralStart = Instant.now();
                break;
            case NEURAL_NETWORK_END:
                neuralEnd = Instant.now();
                break;
        }
    }

    public void printTimeInfo(TimeID id) {
        setTime(id);
        printTimeInfo();
    }

    public void printTimeInfo() {
        if (dataStart == null || neuralStart == null || neuralEnd == null)
            return;
        String totalTimeElapsed = Duration.between(dataStart, neuralEnd).toString().substring(2) + ".";
        System.out.println("TOTAL TIME ELAPSED: " + totalTimeElapsed);
        String parsingTimeElapsed = Duration.between(dataStart, neuralStart).toString().substring(2) + ".";
        System.out.println("TIME FOR DATA PARSING: " + parsingTimeElapsed);
        String networkTimeElapsed = Duration.between(neuralStart, neuralEnd).toString().substring(2) + ".";
        System.out.println("TIME FOR NEURAL NETWORK TRAINING: " + networkTimeElapsed);
    }
}