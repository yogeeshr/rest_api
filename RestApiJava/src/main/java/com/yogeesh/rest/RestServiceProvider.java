/**
 * This class is responsible for handling all REST calls
 */
package com.yogeesh.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.core.Response.Status;

/**
 * @author yogeesh.srkvs@gmail.com
 *
 */
@Path("/")
public class RestServiceProvider {

    private Logger log = Logger.getLogger(RestServiceProvider.class.getName());

    @POST
    @Path("/restcallpost")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makeRestCallPost(InputStream data) {

        String dataString = doDataToString(data);
        JSONObject payLoad = null;

        //If JSON is empty then throw bad request
        if (dataString.length() <= 0) {
            return Response.status(Status.BAD_REQUEST).
                    entity("Bad Request : JSON in request is empty").build();
        }

        log.info("Request JSON parse and validation started ...");

        //Parsing incoming JSON and getting it into JSONObject
        try {
            payLoad = new JSONObject(dataString);
            log.info("[ Received JSON is : [ " + dataString + " ]");
        } catch (JSONException e) {
            log.severe("Error Parsing JSON " + e.getMessage());
            e.printStackTrace();
        }

        log.info("Request JSON parse and validation complete ...");
        System.out.println("[ Payload "+ payLoad+" ]");

        return Response.status(200).entity(payLoad.toString()).build();
    }

    @GET
    @Path("/restcallget")
    @Produces(MediaType.TEXT_PLAIN)
    public String makeRestCallGet() {
        return "Success";
    }


    //Utility function to return fetched string from incoming stream / REST call
    String doDataToString(InputStream data) {

        StringBuilder lineAppended = new StringBuilder();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(data));
            String line;

            while ((line = in.readLine()) != null) {
                lineAppended.append(line);
            }

        } catch (IOException e) {
            log.severe("Error Reading JSON " + e.getMessage());
            e.printStackTrace();
        }

        return lineAppended.toString();
    }

}

