
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;


//Consuming Reqres in List and Create WebService

class ConsumeData{
    HttpURLConnection connection = null;
    
    void createConnection(String method) throws MalformedURLException, IOException{
        
        URL url = new URL("https://reqres.in/api/users?per_page=10");
        
        connection = (HttpURLConnection)url.openConnection();
        
        //Import Properties to set
        
        connection.setConnectTimeout(5000);
        
        //Compulsary
        connection.setRequestMethod(method); //default is get
        //tell which type of request
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        //containt type
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        //tells only accept jason data
        connection.setRequestProperty("Accept", "application/json");
    }
    
    void getHeaderInfo(){
        //
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
        }
        
    }
    
    void postData(){
        
        //check connection is made proper
        if(connection != null){
            
        }
        
    }
    
}

public class ConsumingWebServices {
    
    
    
    public static void main(String[] args) throws IOException {
        
        ConsumeData cd = new ConsumeData();
        cd.createConnection("GET");
        cd.getData();
        
    }
}
