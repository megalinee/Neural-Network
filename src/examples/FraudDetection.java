package examples;

import dev.megaline.neuralnetwork.*;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FraudDetection {
    public static void main(String[] args) throws Exception {

        // Logs data parsing start time
        Instant dataStart = Instant.now();

        List<List<String>> data = new ArrayList<>();

        File transactionData = new File("./src/examples/data/creditcard.csv");
        try (Scanner scanner = new Scanner(transactionData);) {
            while (scanner.hasNextLine()) {
                data.add(getCSVLine(scanner.nextLine()));
            }
        }

        double[][] inputArray = new double[data.size()][data.get(0).size() - 2];
        double[][] outputArray = new double[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            for (int j = 1; j < data.get(i).size() - 1; j++) {
                inputArray[i][j - 1] = Double.parseDouble(data.get(i).get(j));
            }
            outputArray[i][0] = Double.parseDouble(data.get(i).get(data.get(i).size() - 1).substring(1, 2));
        }

        // Logs neural network start time
        Instant neuralStart = Instant.now();

        int[] layers = { 29, 30, 1 };
        NeuralNetwork nn = new NeuralNetwork(layers, .1);
        nn.fit(inputArray, outputArray, 200000);

        // Logs neural network end time
        Instant neuralEnd = Instant.now();
        System.out
                .println("TOTAL TIME ELAPSED: " + Duration.between(dataStart, neuralEnd).toString().substring(2) + ".");
        System.out.println(
                "TIME FOR DATA PARSING: " + Duration.between(dataStart, neuralStart).toString().substring(2) + ".");
        System.out.println(
                "TIME FOR NEURAL NETWORK TRAINING: "
                        + Duration.between(neuralStart, neuralEnd).toString().substring(2) + ".");

        double[][] input = {
                {
                        -1.64500860387508, -2.09079913256286, 1.59256683629435, -1.38885259038471, 1.7328991922002,
                        -1.63105878064131, -0.922859376135218, 0.0138144495343825, 1.76077705624325, -1.2701642777003,
                        -1.40105323853284, 0.356018139922227, 0.191579696372312, -0.473274885943421, 0.707895259439591,
                        -0.179083043036391, -0.816905117057215, 0.63124654276874, 0.309446502210526, 0.5642458027703,
                        0.248155008474276, 0.415496484030839, 0.293293936511936, -0.46342503754589, -0.405175359797423,
                        -0.924103280076778, 0.00658569043568427, 0.0186830113319059, 46.68
                },
                {
                        -2.3122265423263, 1.95199201064158, -1.60985073229769, 3.9979055875468, -0.522187864667764,
                        -1.42654531920595, -2.53738730624579, 1.39165724829804, -2.77008927719433, -2.77227214465915,
                        3.20203320709635, -2.89990738849473, -0.595221881324605, -4.28925378244217, 0.389724120274487,
                        -1.14074717980657, -2.83005567450437, -0.0168224681808257, 0.416955705037907, 0.126910559061474,
                        0.517232370861764, -0.0350493686052974, -0.465211076182388, 0.320198198514526,
                        0.0445191674731724, 0.177839798284401, 0.261145002567677, -0.143275874698919, 0
                },
                {
                        -1.63019333079727, 2.0710920968395, -1.20702215352626, -0.65265630730975, 1.47103269516532,
                        -0.93392576772999, 0.968998814695107, -1.18217455087738, -0.023912255014083, -0.718259734764856,
                        1.70355624289611, 0.661141245335576, 1.0220309822865, -2.99782598145604, -0.0846116473181537,
                        0.820213566953318, 1.25898648783367, 1.73799388754814, -0.522643742165211, -0.14363445744123,
                        0.980539732985332, 0.621112602580616, -0.338921182203709, -0.592278150191372,
                        -0.0173906774034537, -0.284232234033048, -0.817523168402214, -0.123276036878207, 1
                }
        };
        for (double[] d : input) {
            System.out.println(nn.predict(d).toString());
        }
    }

    public static List<String> getCSVLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }
}
