package nl.tudelft.oopp.demo.communication;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class ReservationServerCommunication extends ServerCommunication {

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
            httpGet.setHeader("Authorization", getPubAuth());
            return getHttpClient().execute(httpGet);
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
        httpPost.setHeader("Authorization", getPubAuth());
        try {
            httpPost.setEntity(new StringEntity(jsonObject.toString()));
            httpPost.setHeader("Content-type", "application/json");
            return getHttpClient().execute(httpPost);
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
        httpDelete.setHeader("Authorization", getPubAuth());
        try {
            return getHttpClient().execute(httpDelete);
        } catch (Exception e) {
            errorAlert();
            return null;
        }
    }

}
