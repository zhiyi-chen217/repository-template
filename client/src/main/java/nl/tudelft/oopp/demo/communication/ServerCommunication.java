package nl.tudelft.oopp.demo.communication;

import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

public class ServerCommunication {

    private static HttpClient client = HttpClient.newBuilder().build();
    private static CloseableHttpClient httpClient = HttpClients.createDefault();
    private static String pubAuth;

    /**
     * Send the username and password under the authentication header to the server.
     * @param username the username that is inputted
     * @param password the password that the user inputs
     * @param baseurl the url to where the request is sent
     * @return the string to present in the alert
     */

    public static HttpResponse<String> sendLogin(String username, String password, String baseurl) {
        String auth = username + ":" + password;
        String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());

        pubAuth = encodedAuth;
        URI urihttp = URI.create(baseurl);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(urihttp)
                .header("Authorization", encodedAuth).build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (response.statusCode() != 200) {
            System.out.println("Status: " + response.statusCode());
        }
        return response;
    }

    /**
     * Sending the signup info to the server, so it can be stored in the database.
     * @param netid the username of the user signing up
     * @param email the email address of the user signing up
     * @param password the password of the user signing up
     * @return a ClosableHttpResponse containing text about the success of signing up.
     * @throws IOException if something goes wrong with sending the info
     */

    public static CloseableHttpResponse sendSignUp(String netid,
                             String email, String password) throws IOException {
        HttpPost httpPost = new HttpPost("http://localhost:8080/signup");
        String json = "{\"userId\" : \"" + netid + "\","
                + "\"email\" : \"" + email + "\","
                + "\"password\" : \"" + password + "\"}";
        httpPost.setEntity(new StringEntity(json));
        //httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return response;
    }

    /**
     * Sends the info about a building to the server so it can be stored in the database.
     * @param name the name of the building
     * @param location the location of the building
     * @param openingHour the opening hour of the building
     * @param closingHour the closing hour of the building
     * @param picturesPath the path of the picture of the building
     * @param bikes the amount of bikes at the building
     * @return a ClosableHttpResponse which contains info about if the building is saved
     * @throws IOException if anything goes wrong with communicating with the server
     */

    public static CloseableHttpResponse createBuilding(String name, String location,
                                                       LocalTime openingHour, LocalTime closingHour,
                                                       String picturesPath, int bikes)
            throws IOException {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("location", location);
        json.put("openingHour", openingHour);
        json.put("closingHour", closingHour);
        json.put("picturesPath", picturesPath);
        json.put("bikes", bikes);
        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/buildings");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Authorization", pubAuth);
        httpPost.setEntity(new StringEntity(json.toString()));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        return response;
    }

    /**
     * Sending the details to update a building in the database.
     * @param name the name of the building to be updated
     * @param location the updated location of the building
     * @param openingHour the updated opening hour of the building
     * @param closingHour the updated closing hour of the building
     * @param picturesPath the updated pictures path of the building
     * @param bikes the updated amount of bikes at the building
     * @return a ClosableHttpResponse containing info about the success of updating a building
     * @throws IOException if anything goes wrong with communicating with the server
     */

    public static CloseableHttpResponse updateBuilding(String name, String location,
                                                       LocalTime openingHour, LocalTime closingHour,
                                                       String picturesPath, int bikes)
            throws IOException {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("location", location);
        json.put("openingHour", openingHour);
        json.put("closingHour", closingHour);
        json.put("picturesPath", picturesPath);
        json.put("bikes", bikes);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut("http://localhost:8080/admin/buildings");
        httpPut.setHeader("Content-type", "application/json");
        httpPut.setHeader("Authorization", pubAuth);
        httpPut.setEntity(new StringEntity(json.toString()));
        CloseableHttpResponse response = httpClient.execute(httpPut);
        return response;
    }

    /**
     * read the info of a building.
     * @param name the name of a building
     * @return a ClosableHttpResponse containing info about the building
     * @throws IOException if anything goes wrong with communicating with the server
     * @throws URISyntaxException if the uri has any syntax errors
     */

    public static CloseableHttpResponse readBuilding(String name)
            throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder("http://localhost:8080/buildings");
        HttpGet httpGet = new HttpGet();
        httpGet.setHeader("Authorization", pubAuth);
        if (name == null) {
            httpGet.setURI(builder.build());
            return httpClient.execute(httpGet);
        }
        builder.addParameter("name", name);
        httpGet.setURI(builder.build());
        return httpClient.execute(httpGet);
    }

    /**
     * Send a name to the server so it can delete a building out the database.
     * @param name the name of the building to be deleted
     * @return a ClosableHttpResponse containing about the success of deleting the building
     * @throws IOException if anything goes wrong with the communication with the server
     * @throws URISyntaxException if there is any syntax error in the uri
     */

    public static CloseableHttpResponse deleteBuilding(List<String> name)
            throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder("http://localhost:8080/admin/buildings");
        HttpDelete httpDelete = new HttpDelete();
        httpDelete.setHeader("Authorization", "Basic YWRtaW46MTIzNDU=");
        for (String s: name) {
            builder.addParameter("names", s);
        }
        httpDelete.setURI(builder.build());
        return httpClient.execute(httpDelete);
    }



    /**
     * Helper function for authentication of a normal user.
     * @param username the username that is inputted
     * @param password the password that the user inputs
     * @return the string to present in the alert
     */

    public static HttpResponse<String> sendLoginUser(String username, String password) {
        return sendLogin(username, password, "http://localhost:8080/login");
    }

    /**
     * Helper function for authentication of an admin.
     * Send the username and password under the authentication header to the server
     * @param username the username that is inputted
     * @param password the password that the user inputs
     * @return the string to present in the alert
     */

    public static HttpResponse<String> sendLoginAdmin(String username, String password) {
        return sendLogin(username, password, "http://localhost:8080/login/admin");
    }
    
    public static String getPubAuth() {
        return pubAuth;
    }

    public static void resetPubAuth() {
        pubAuth = null;
    }
}
