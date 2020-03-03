package nl.tudelft.oopp.demo.communication;

import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

import java.time.LocalTime;

public class BuildingServerCommunication extends ServerCommunication {

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
        httpPost.setHeader("Authorization", getPubAuth());
        try {
            httpPost.setEntity(new StringEntity(json.toString()));
            CloseableHttpResponse response = getHttpClient().execute(httpPost);
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
        httpPut.setHeader("Authorization", getPubAuth());
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
            httpGet.setHeader("Authorization", getPubAuth());
            if (name == null) {
                httpGet.setURI(builder.build());
                return getHttpClient().execute(httpGet);
            }
            builder.addParameter("name", name);
            httpGet.setURI(builder.build());
            return getHttpClient().execute(httpGet);
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
            httpDelete.setHeader("Authorization", getPubAuth());
            builder.addParameter("name", name);
            httpDelete.setURI(builder.build());
            return getHttpClient().execute(httpDelete);
        } catch (Exception e) {
            errorAlert();
            return null;
        }
    }


}
