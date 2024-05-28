package com.jiratosheets.jiratosheets;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.http.MediaType;



@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainController {
    
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getList(@RequestParam(value = "issueKey") String key) throws IOException {
        String sURL = "https://jira.beacukai.go.id/rest/api/2/issue/"+ key +"?fields=status"; //just a string

        // Connect to the URL using java's native library
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        request.setDoOutput(true);
        request.setRequestProperty ("Authorization", "Basic emFhZmlyYS5oZW5kcmFydG86WmFhZjIwMjQu");
        request.setRequestProperty( "Content-type", "application/json");
        request.connect();

        // Convert to a JSON object to print data
        JsonParser jp = new JsonParser(); //from gson
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object. 
        JsonObject fields = (JsonObject) rootobj.get("fields");
        JsonObject status = (JsonObject) fields.get("status");
        String name = status.get("name").getAsString(); //just grab the zipcode
        System.out.println(name);
        return ResponseEntity.ok().body(name);
    }


}
