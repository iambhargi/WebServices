
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mca241
 */

//Consuming Reqres in List and Create WebService

class ConsumeData{
    HttpURLConnection connection = null;
    
    void createConnection(String method) throws MalformedURLException, IOException{
        
        URL url = new URL("https://reqres.in/api/users?per_page=10");
        
        connection = (HttpURLConnection)url.openConnection();
        
        //Import Properties to set
        
        connection.setConnectTimeout(5000);
        
        //Compulsary steps :-
        //to tell which mwethod to use GET/Post
        connection.setRequestMethod(method); //default is get
        //tell which type of request
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        //containt type
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        //tells only accept json data
        connection.setRequestProperty("Accept", "application/json");
    }
    
    void getHeaderInfo(){
        //to get the header information i.e. metadata 
        //iteration of hashmap
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        for (Map.Entry<String, List<String>> entrySet : headerFields.entrySet()) {
            String key = entrySet.getKey();
            List<String> value = entrySet.getValue();
            System.out.println(key+ " : "+value);
        }
        //accesing individual header info
        System.out.println("Content Type : " + connection.getRequestProperty("Content-Type"));
    }
    
    void getData() throws IOException{
        
        //check connection is made proper
        if(connection != null){
            connection.connect();
            getHeaderInfo();
            //check the response code
            if(connection.getResponseCode()==200){
                // for reading the data
                Scanner sc =new Scanner(connection.getInputStream(),"UTF-8");
                String response="";
            
                while(sc.hasNext()){
                    response+=sc.next();
                }
                System.out.println(response);
                
                //Parse Json
                 JSONObject jsonObj =new JSONObject(response);
                 JSONArray jsonArray=jsonObj.getJSONArray("data"); // KEY string is case sensitive
                 
                 //itterating through jsonArray
                 //System.out.println("First Name "+" Last Name");
                 for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject userObj = jsonArray.getJSONObject(i);
                    String name=userObj.getString("first_name")+" "+userObj.getString("last_name"); 
                    System.out.println(name);
                }
                 
            }
            else{
                System.out.println("Something is wrong");
            }
        }
    }
    
    void postData() throws IOException{
        
        //check connection is made proper
        if(connection != null){
            //compulsory property for reading out info with request
            connection.setDoOutput(true);
            connection.connect();
            
            //send the data
            JSONObject userObj = new JSONObject();
            userObj.put("Name", "Xavier");
            userObj.put("job", "Actor");
            
            //writing data using OutputStream 
            OutputStream outStream = connection.getOutputStream();
            outStream.write(userObj.toString().getBytes());
            outStream.close();
            
            getHeaderInfo();
            
            //read response
            if(connection.getResponseCode()==201){
                // for reading the data
                Scanner sc =new Scanner(connection.getInputStream(),"UTF-8");
                String response="";
            
                while(sc.hasNext()){
                    response+=sc.next();
                }
                System.out.println(response);
                //parse Json
                JSONObject jsonObj = new JSONObject(response);
                System.out.println("ID: "+jsonObj.getString("id")+"\nDATE: "+jsonObj.getString("createdAt"));
            }
            else{
                System.out.println("Something is wrong");
            }
        }
        
    }
    
}

public class ConsumingWebServices {
    public static void main(String[] args) throws IOException {
        
        ConsumeData cd = new ConsumeData();
        cd.createConnection("GET");
        cd.getData();
        
        cd.createConnection("POST");
        cd.postData();
    }
}

