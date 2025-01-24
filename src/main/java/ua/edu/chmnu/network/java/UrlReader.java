package ua.edu.chmnu.network.java;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class UrlReader {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String urlString;

        System.out.println("URL Reader Application");
        System.out.println("Type a URL to fetch information or type 'exit' to quit.");

        while (true) {
            System.out.println("\nEnter the URL address (e.g., https://www.example.com):");
            urlString = scanner.nextLine();

            if (urlString.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the application. Goodbye!");
                break;
            }

            if (!isValidUrl(urlString)) {
                System.out.println("Invalid URL. Please make sure the URL starts with http:// or https://");
                continue;
            }

            try {
                fetchAndDisplayUrlInfo(urlString);
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void fetchAndDisplayUrlInfo(String urlString) throws Exception {

        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

            reader.close();

            String contentString = content.toString();

            System.out.println("Content Length: " + contentString.length());

            int previewLength = Math.min(500, contentString.length());
            System.out.println("First 500 Characters:\n" + contentString.substring(0, previewLength));
        } else {
            System.out.println("Failed to fetch content. HTTP Response Code: " + responseCode);
        }
    }

    private static boolean isValidUrl(String urlString) {
        return urlString.startsWith("http://") || urlString.startsWith("https://");
    }
}