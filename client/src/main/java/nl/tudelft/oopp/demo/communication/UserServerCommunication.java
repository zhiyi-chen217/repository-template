package nl.tudelft.oopp.demo.communication;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class UserServerCommunication extends ServerCommunication {

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

        setPubAuth(encodedAuth);
        setUserId(username);
        URI urihttp = URI.create(baseurl);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(urihttp)
                .header("Authorization", encodedAuth).build();
        HttpResponse<String> response;
        try {
            response = getClient().send(request, HttpResponse.BodyHandlers.ofString());
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

    /**
     * Sending the signup info to the server, so it can be stored in the database.
     * @param netid the username of the user signing up
     * @param email the email address of the user signing up
     * @param password the password of the user signing up
     * @return a ClosableHttpResponse containing text about the success of signing up.
     */

    public static CloseableHttpResponse sendSignUp(String netid, String email, String password) {
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/signup");
            String json = "{\"userId\" : \"" + netid + "\","
                    + "\"email\" : \"" + email + "\","
                    + "\"password\" : \"" + password + "\"}";
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = getHttpClient().execute(httpPost);
            return response;
        } catch (Exception e) {
            errorAlert();
            return null;
        }
    }


}
