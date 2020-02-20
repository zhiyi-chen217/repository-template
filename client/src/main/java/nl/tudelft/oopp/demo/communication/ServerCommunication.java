package nl.tudelft.oopp.demo.communication;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();

    //SendLogin method without encoding
//    public static String sendLogin(String username, String password) {
//        if (password == null || username == null) return null;
//        //Parameters user and password are added to the URI
//        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/login?user=" + username + "?pw=" + password)).build();
//        HttpResponse<String> response = null;
//        try {
//            response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Communication with server failed";
//        }
//        if (response.statusCode() != 200) {
//            System.out.println("Status: " + response.statusCode());
//        }
//        return response.body();
//    }


    //Old getQuote() method
    /**
     * Retrieves a quote from the server.
     * @return the body of a get request to the server.
     * @throws Exception if communication with the server fails.
     */
    public static String getQuote() {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/admin")).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        return response.body();
    }

    /**
     * Send the username and password under the authentication header to the server
     * @param username the username that is inputted
     * @param password the password that the user inputs
     * @return the string to present in the alert
     */
    public static String sendLogin(String username, String password){
        String baseurl = "http://localhost:8080/login";
        String auth = username + ":" + password;
        String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());

        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(baseurl)).header("Authorization", encodedAuth).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return "Communication with server failed";
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        return response.body();
    }

}
