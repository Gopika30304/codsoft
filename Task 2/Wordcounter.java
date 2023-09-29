import java.io.*;
import java.util.*;

public class WordCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Word Counter!");
        System.out.print("Enter '1' to input text or '2' to provide a file: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        String text = "";

        if (choice == 1) {
            System.out.print("Enter the text: ");
            text = scanner.nextLine();
        } else if (choice == 2) {
            System.out.print("Enter the path to the file: ");
            String filePath = scanner.nextLine();

            try {
                text = readTextFromFile(filePath);
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
                System.exit(1);
            }
        } else {
            System.err.println("Invalid choice. Please enter '1' or '2'.");
            System.exit(1);
        }

        // Split the text into words using space or punctuation as delimiters
        String[] words = text.split("[\\s\\p{Punct}]+");

        // Initialize a counter for word count and a set for unique words
        int wordCount = 0;
        Set<String> uniqueWords = new HashSet<>();

        // Initialize a map for word frequencies
        Map<String, Integer> wordFrequencies = new HashMap<>();

        // Define common stop words to ignore (you can extend this list)
        Set<String> stopWords = new HashSet<>(Arrays.asList("a", "an", "the", "in", "on", "at", "to"));

        for (String word : words) {
            word = word.toLowerCase(); // Convert to lowercase for case-insensitive comparison
            if (!stopWords.contains(word)) {
                wordCount++;
                uniqueWords.add(word);
                wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0) + 1);
            }
        }

        System.out.println("Total words: " + wordCount);
        System.out.println("Unique words: " + uniqueWords.size());

        // Display word frequencies
        System.out.println("Word frequencies:");
        for (Map.Entry<String, Integer> entry : wordFrequencies.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        scanner.close();
    }

    private static String readTextFromFile(String filePath) throws IOException {
        StringBuilder text = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = reader.readLine()) != null) {
            text.append(line).append(" ");
        }

        reader.close();
        return text.toString();
    }
}
