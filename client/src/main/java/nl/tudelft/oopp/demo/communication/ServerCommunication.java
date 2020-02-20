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
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8080/quote")).build();
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


    public static String sendLogin(String username, String password){
        String baseurl = "http://localhost:8080/login?";
        String auth = username + ":" + password;
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedAuth = encoder.encodeToString(auth.getBytes());
        baseurl += "user=" + username +"&"+ "auth=" + encodedAuth;
        HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(baseurl)).build();
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
