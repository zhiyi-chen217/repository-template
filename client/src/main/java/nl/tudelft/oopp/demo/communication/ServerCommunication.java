package nl.tudelft.oopp.demo.communication;

import javafx.scene.control.Alert;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;

public class ServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();
    private static CloseableHttpClient httpClient = HttpClients.createDefault();
    private static String pubAuth;
    private static String userId;

    /**
     * Shows an Alert with an error to the user.
     */
    public static void errorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText("Something went wrong, please try again.");
        alert.showAndWait();
    }

    public static String getPubAuth() {
        return pubAuth;
    }

    public static void resetPubAuth() {
        pubAuth = null;
    }

    public static String setPubAuth(String auth) {
        return pubAuth = auth;
    }

    public static HttpClient getClient() {
        return client;
    }

    //public static void setClient(HttpClient client) {ServerCommunication.client = client;}

    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    //public static void setHttpClient(CloseableHttpClient httpClient) {ServerCommunication.httpClient = httpClient;}

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        ServerCommunication.userId = userId;
    }
}
