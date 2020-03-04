package nl.tudelft.oopp.demo.communication;

import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.util.List;

public class RoomServerCommunication extends ServerCommunication {

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
        httpPost.setHeader("Authorization", getPubAuth());
        try {
            httpPost.setEntity(new StringEntity(json.toString()));
            httpPost.setHeader("Content-type", "application/json");
            return getHttpClient().execute(httpPost);
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
        httpPut.setHeader("Authorization", getPubAuth());
        try {
            httpPut.setEntity(new StringEntity(json.toString()));
            httpPut.setHeader("Content-type", "application/json");
            return getHttpClient().execute(httpPut);
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
            httpGet.setHeader("Authorization", getPubAuth());
            if (building != null) {
                uri.addParameter("building", building);
                httpGet.setURI(uri.build());
                return getHttpClient().execute(httpGet);
            }
            if (roomId != null) {
                uri.addParameter("roomId", roomId);
                httpGet.setURI(uri.build());
                return getHttpClient().execute(httpGet);
            }
            httpGet.setURI(uri.build());
            return getHttpClient().execute(httpGet);
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
            httpDelete.setHeader("Authorization", getPubAuth());
            httpDelete.setURI(uri.build());
            return getHttpClient().execute(httpDelete);
        } catch (Exception e) {
            errorAlert();
            return null;
        }
    }

}
