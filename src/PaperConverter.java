import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaperConverter {
    private Set<String> citations;
    private Map<String, IEEEReference> ieeeReferences;

    /**
     * Method to convert citations in paper to IEEE citations
     *
     * @param inputFile  -- String name of input file
     * @param outputFile -- String name of output file
     */
    public void convertPaper(String inputFile, String outputFile) {
        this.citations = extractCitations(inputFile);
        this.ieeeReferences = createIEEEReferences(this.citations);

        replaceCitations(inputFile, outputFile, this.ieeeReferences);
    }

    /**
     * Method to extract all citations in the input file
     * References : https://www.w3schools.com/java/java_regex.asp
     * https://www.geeksforgeeks.org/matcher-group-method-in-java-with-examples/
     *
     * @param inputFile
     * @return
     */
    private Set<String> extractCitations(String inputFile) {
        Set<String> citations = new LinkedHashSet<>();
        Pattern citePattern = Pattern.compile("\\\\cite\\{([^}]+)\\}");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line = reader.readLine();

            // Read line by line and extract the citations
            while (line != null) {
                Matcher citeMatcher = citePattern.matcher(line);

                while (citeMatcher.find()) {
                    String[] keys = citeMatcher.group(1).split(",\\s*");
                    for (String key : keys) {
                        citations.add(key.trim());
                    }
                }
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return citations;
    }

    private Map<String, IEEEReference> createIEEEReferences(Set<String> citations) {
        Map<String, IEEEReference> ieeeReferences = new HashMap<>();
        int index = 1;
        PublicationLibrary publicationLibrary = new PublicationLibrary();

        // Extract the publications from database using the key and convert to IEEE format
        for (String key : citations) {
            try {
                Map<String, String> publicationInfo = publicationLibrary.getPublications(key);

                if (publicationInfo == null) {
                    throw new RuntimeException("Invalid citation: " + key);
                }

                String authors = publicationInfo.get("authors");
                String title = publicationInfo.get("title");
                String venue = publicationInfo.get("venue");
                String location = publicationInfo.get("location");
                String volume = publicationInfo.get("volume");
                String issue = publicationInfo.get("issue");
                String pages = publicationInfo.get("pages");
                String month = publicationInfo.get("month");
                String year = publicationInfo.get("year");

                String ieeeReference = "[" + index + "] " + authors + ", \"" + title + "\", " + venue + ", ";
                if (location != null) {
                    ieeeReference += location + ", ";
                }

                if (volume != null) {
                    ieeeReference += volume + ", ";
                }

                if (issue != null) {
                    ieeeReference += issue + ", ";
                }

                if (volume != null) {
                    ieeeReference += volume + ", ";
                }

                ieeeReference += pages + ", ";

                if (month != null) {
                    ieeeReference += month + ", ";
                }

                ieeeReference += year + ".";


                ieeeReferences.put(key, new IEEEReference(index, ieeeReference));
                index++;
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
        return ieeeReferences;
    }

    /**
     * Method to read the file line by line and replace with citations
     * Also to add references at the bottom
     * <p>
     * References: https://www.javatpoint.com/post/java-matcher-appendreplacement-method
     * https://www.geeksforgeeks.org/matcher-group-method-in-java-with-examples/
     *
     * @param inputFile      -- String name of input file
     * @param outputFile     -- String name of output file
     * @param ieeeReferences -- Map of ieee references
     */
    private void replaceCitations(String inputFile, String outputFile, Map<String, IEEEReference> ieeeReferences) {
        Pattern citePattern = Pattern.compile("\\\\cite\\{([^}]+)\\}");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            String line = reader.readLine();

            while (line != null) {
                Matcher citeMatcher = citePattern.matcher(line);
                StringBuffer updatedLine = new StringBuffer();

                // Read line by line and replace all occurrences of citations with the right reference index
                while (citeMatcher.find()) {
                    String[] keys = citeMatcher.group(1).split(",\\s*");
                    StringBuilder citations = new StringBuilder();

                    for (String key : keys) {
                        int referenceIndex = ieeeReferences.get(key).getReferenceIndex();
                        if (citations.length() > 1) {
                            citations.append(",");
                        }

                        citations.append("[").append(referenceIndex).append("]");
                    }

                    citeMatcher.appendReplacement(updatedLine, citations.toString());
                }

                citeMatcher.appendTail(updatedLine);
                writer.write(updatedLine.toString());
                writer.newLine();

                line = reader.readLine();
            }

            //Appending the IEEE references to end of the file
            writer.newLine();
            writer.write("References");
            writer.newLine();

            Map<Integer, String> referenceMap = new HashMap<>();
            for (IEEEReference reference : ieeeReferences.values()) {
                referenceMap.put(reference.getReferenceIndex(), reference.getPublication());
            }

            for (int i = 1; i <= referenceMap.keySet().size(); i++) {
                writer.write(referenceMap.get(i));
                writer.newLine();
            }

            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
