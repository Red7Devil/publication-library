import DatabaseComm.DatabaseService;
import Publishers.Publisher;

import java.util.*;

public class PaperConversion {
    /**
     * Main function accepts input and output file and passes it to convertPaper() method
     *
     * @param args
     */
    public static void main(String[] args) {
        PaperConverter paperConverter = new PaperConverter();
        String inputFile = getInput("Enter input file: ");
        String outputFile = getInput("Enter output file: pa");

        paperConverter.convertPaper(inputFile, outputFile);
    }

    /**
     * Static method to accept input from user
     *
     * @param message -- String message to be shown
     * @return -- String input of the user
     */
    public static String getInput(String message) {
        Scanner scanner = new java.util.Scanner(System.in);
        System.out.print(message);
        return scanner.nextLine();
    }
}
