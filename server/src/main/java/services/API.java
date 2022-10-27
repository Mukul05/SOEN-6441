package services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import bean.UserDetails;
import dao.UserDetailDAOImpl;




public class API {

	private UserDetailDAOImpl userDao = UserDetailDAOImpl.getInstance();
	
	private static class SingletonHelper {
		private static final API INSTANCE = new API();
	}
	
	public static API getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
    public boolean resetData() {
    	boolean success = false;
        try {
        	URL url = new URL("https://jsonplaceholder.typicode.com/users");
        	String appid = "631fc16fd8f86f425bc1fa91";
        	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        	conn.setRequestProperty("app-id", appid);
        	conn.setRequestMethod("GET");
        	conn.connect();

        	int responsecode = conn.getResponseCode();
        	
        	if (responsecode != 200) {
        	    throw new RuntimeException("HttpResponseCode: " + responsecode);
        	} else {
        	    String inline = "";
        	    Scanner scanner = new Scanner(url.openStream());
        	  
        	   //Write all the JSON data into a string using a scanner
        	    while (scanner.hasNext()) {
        	       inline += scanner.nextLine();
        	       
        	    }
        	    System.out.println(inline);
        	    //Close the scanner
        	    scanner.close();
        	    JSONArray jsonArray = new JSONArray(inline);
        	    if (jsonArray.length()==0)
        	    	return false;
        	    userDao.truncateTable();
        	    for (int i = 0; i < jsonArray.length(); i++) {
        	        JSONObject user = jsonArray.getJSONObject(i);
        	        System.out.println(user.getString("name"));
        	        System.out.println(user.getInt("id"));
        	        System.out.println(user.getString("username"));
        	        System.out.println(user.getString("email"));
        	        UserDetails userData = new UserDetails(user.getInt("id"),user.getString("name"),user.getString("username"),user.getString("email"));
        	        boolean currentuser = false;
        			try {
        				if (currentuser = true)
        				 currentuser=userDao.insertUser(userData);
        			} catch (ClassNotFoundException e) {
        				e.printStackTrace();
        			} catch (SQLException e) {
        				e.printStackTrace();
        			} catch (Exception e) {
        				e.printStackTrace();
        			}
        		    String responseMessage = currentuser ? "Succesfully created user" : "Failed to create user";
        		    
        	    }
        	    success=true;
        	    
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}