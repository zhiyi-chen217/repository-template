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
    public static String pubAuth;
    public static String userId;

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
        userId = username;
        URI urihttp = URI.create(baseurl);
        HttpRequest request = HttpRequest.newBuilder().GET().uri(urihttp)
                .header("Authorization", encodedAuth).build();
        HttpResponse<String> response;
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
     */

    public static CloseableHttpResponse sendSignUp(String netid, String email, String password) {
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/signup");
            String json = "{\"userId\" : \"" + netid + "\","
                    + "\"email\" : \"" + email + "\","
                    + "\"password\" : \"" + password + "\"}";
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return response;
        } catch (Exception e) {
            errorAlert();
            return null;
        }
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
     */

    public static CloseableHttpResponse createBuilding(String name, String location,
                                                       LocalTime openingHour, LocalTime closingHour,
                                                       String picturesPath, int bikes) {
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
        try {
            httpPost.setEntity(new StringEntity(json.toString()));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            return response;
        } catch (Exception e) {
            errorAlert();
            return null;
        }
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
     */

    public static CloseableHttpResponse updateBuilding(String name, String location,
                                                       LocalTime openingHour, LocalTime closingHour,
                                                       String picturesPath, int bikes) {
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
        try {
            httpPut.setEntity(new StringEntity(json.toString()));
            CloseableHttpResponse response = httpClient.execute(httpPut);
            return response;
        } catch (Exception e) {
            errorAlert();
            return null;
        }
    }

    /**
     * read the info of a building.
     * @param name the name of a building
     * @return a ClosableHttpResponse containing info about the building
     */

    public static CloseableHttpResponse readBuilding(String name) {
        try {
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
        } catch (Exception e) {
            errorAlert();
            return null;
        }
    }

    /**
     * Send a name to the server so it can delete a building out the database.
     * @param name the name of the building to be deleted
     * @return a ClosableHttpResponse containing about the success of deleting the building
     */

    public static CloseableHttpResponse deleteBuilding(String name) {
        try {
            URIBuilder builder = new URIBuilder("http://localhost:8080/admin/buildings");
            HttpDelete httpDelete = new HttpDelete();
            httpDelete.setHeader("Authorization", pubAuth);
            builder.addParameter("name", name);
            httpDelete.setURI(builder.build());
            return httpClient.execute(httpDelete);
        } catch (Exception e) {
            errorAlert();
            return null;
        }
    }

    /**Method that creates a room through the server.
     * @param roomId is the id of the room
     * @param name is the name of the room
     * @param capacity is the capacity of the room
     * @param building is the building the room is in
     * @param description is the description of the room
     * @param type is the type of romo
     * @param picturesPath is the path where the pictures of the room are at
     * @param whiteboard is whether the room contains a whiteboard
     * @param tv is whether the room contains a tv
     * @return httpPost which will be posted to the server
     */
    public static CloseableHttpResponse createRoom(String roomId, String name, int capacity,
                                                   String building, String description, String type,
                                                   String picturesPath, boolean whiteboard,
                                                   boolean tv) {
        JSONObject json = new JSONObject();
        JSONObject innerJson = new JSONObject();
        innerJson.put("name", building);
        json.put("roomId", roomId)
                .put("name", name)
                .put("capacity", capacity)
                .put("building", innerJson)
                .put("description", description)
                .put("picturesPath", picturesPath)
                .put("type", type)
                .put("whiteboard", whiteboard)
                .put("tv", tv);
        HttpPost httpPost = new HttpPost("http://localhost:8080/admin/room");
        httpPost.setHeader("Authorization", pubAuth);
        try {
            httpPost.setEntity(new StringEntity(json.toString()));
            httpPost.setHeader("Content-type", "application/json");
            return httpClient.execute(httpPost);
        } catch (Exception e) {
            errorAlert();
            return null;
        }
    }

    /**Method that updates a room through the server.
     * @param roomId is the id of the room
     * @param name is the name of the room
     * @param capacity is the capacity of the room
     * @param building is the building the room is in
     * @param description is the description of the room
     * @param type is the type of romo
     * @param picturesPath is the path where the pictures of the room are at
     * @param whiteboard is whether the room contains a whiteboard
     * @param tv is whether the room contains a tv
     * @return httpPost which will be posted to the server
     */
    public static CloseableHttpResponse updateRoom(String roomId, String name, int capacity,
                                                   String building, String description, String type,
                                                   String picturesPath, boolean whiteboard,
                                                   boolean tv) {
        JSONObject json = new JSONObject();
        JSONObject innerJson = new JSONObject();
        innerJson.put("name", building);
        json.put("roomId", roomId)
                .put("name", name)
                .put("capacity", capacity)
                .put("building", innerJson)
                .put("description", description)
                .put("picturesPath", picturesPath)
                .put("type", type)
                .put("whiteboard", whiteboard)
                .put("tv", tv);
        HttpPut httpPut = new HttpPut("http://localhost:8080/admin/room");
        httpPut.setHeader("Authorization", pubAuth);
        try {
            httpPut.setEntity(new StringEntity(json.toString()));
            httpPut.setHeader("Content-type", "application/json");
            return httpClient.execute(httpPut);
        } catch (Exception e) {
            errorAlert();
            return null;
        }
    }

    /**Method that reads a room through the server.
     * @param roomId is the id of the room
     * @param building is the building the room is located in
     * @return httpGet which gets the information
     */
    public static CloseableHttpResponse readRoom(String roomId, String building) {
        try {
            URIBuilder uri = new URIBuilder("http://localhost:8080/rooms");
            HttpGet httpGet = new HttpGet();
            httpGet.setHeader("Authorization", pubAuth);
            if (building != null) {
                uri.addParameter("building", building);
                httpGet.setURI(uri.build());
                return httpClient.execute(httpGet);
            }
            if (roomId != null) {
                uri.addParameter("roomId", roomId);
                httpGet.setURI(uri.build());
                return httpClient.execute(httpGet);
            }
            httpGet.setURI(uri.build());
            return httpClient.execute(httpGet);
        } catch (Exception e) {
            errorAlert();
            return null;
        }
    }

    /**Method that deletes a room in the server.
     * @param roomIds is the list of rooms which are to be deleted
     * @return httpDelete which deletes information on the server
     */
    public static CloseableHttpResponse deleteRoom(List<String> roomIds) {
        try {
            URIBuilder uri = new URIBuilder("http://localhost:8080/admin/room");
            for (String s : roomIds) {
                uri.addParameter("roomIds", s);
            }
            HttpDelete httpDelete = new HttpDelete();
            httpDelete.setHeader("Authorization", pubAuth);
            httpDelete.setURI(uri.build());
            return httpClient.execute(httpDelete);
        } catch (Exception e) {
            errorAlert();
            return null;
        }
    }

    /**
     * This method sends an HttpGet request to the server asking for all the room reservations
     * fulfilling the given constraints.
     * @param user - the userId
     * @param room - the roomId
     * @param date - the date
     * @return - An HttpResponse containing all the qualified room reservations
     */
    public static CloseableHttpResponse readRoomReservation(String user, String room, String date) {
        try {
            URIBuilder uri = new URIBuilder("http://localhost:8080/roomReservations");
            if (user != null) {
                uri.addParameter("user", user);
            }
            if (room != null) {
                uri.addParameter("room", room);
            }
            if (date != null) {
                uri.addParameter("date", date);
            }
            HttpGet httpGet = new HttpGet();
            httpGet.setURI(uri.build());
            httpGet.setHeader("Authorization", pubAuth);
            return httpClient.execute(httpGet);
        } catch (Exception e) {
            errorAlert();
            return null;
        }
    }

    /**
     * This method sends a HttpPost request to the server,
     * in order to post a new room reservation for the user.
     * @param user - the userId
     * @param beginTime - the begin time of the reservation
     * @param endTime - the end time of the reservation
     * @param room - the roomId
     * @return - An HttpResponse specifying the state of the request
     */
    public static CloseableHttpResponse createRoomReservation(String user, LocalDateTime beginTime,
                                                              LocalDateTime endTime, String room) {
        JSONObject jsonObject = new JSONObject();
        JSONObject temp = new JSONObject();
        temp.put("userId", user);
        jsonObject.put("user", temp);
        jsonObject.put("beginTime", beginTime);
        jsonObject.put("endTime", endTime);
        temp = new JSONObject();
        temp.put("roomId", room);
        jsonObject.put("room", temp);
        HttpPost httpPost = new HttpPost("http://localhost:8080/roomReservation");
        httpPost.setHeader("Authorization", pubAuth);
        try {
            httpPost.setEntity(new StringEntity(jsonObject.toString()));
            httpPost.setHeader("Content-type", "application/json");
            return httpClient.execute(httpPost);
        } catch (Exception e) {
            errorAlert();
            return null;
        }
    }

    /**
     * This method sends a HttpDelete request to the server,
     * in order to delete the given room reservation.
     * @param roomReservationId - the id of the room reservation
     * @return - An HttpResponse specifying the state of the request
     */
    public static CloseableHttpResponse deleteRoomReservation(Long roomReservationId) {
        HttpDelete httpDelete = new HttpDelete("http://localhost:8080/roomReservation");
        httpDelete.setHeader("Authorization", pubAuth);
        try {
            return httpClient.execute(httpDelete);
        } catch (Exception e) {
            errorAlert();
            return null;
        }
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

}
