package com.iot.services;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.iot.pojo.AssignedItems;
import com.iot.pojo.DashBoardPOJO;
import com.iot.pojo.LocationUuid;
import com.iot.pojo.Response;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiTest {

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ApiTest test = new ApiTest();
		APIParams params = new APIParams();
        JsonObject jsonObject = new JsonObject();
        
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader("C:\\Users\\Reddy\\Desktop\\test.json"));
            jsonObject = jsonElement.getAsJsonObject();
        } catch (FileNotFoundException e) {
           
        }

        /*params.setUrl("http://52.26.174.162:8081/BeaconServices/webapi/user/login");
        params.setApiName("LOGIN-API");*/
        
        /*String uuid = "ASHJKR986-KJTOl34-NBHF";
        params.setUrl("http://52.26.174.162:8081/BeaconServices/webapi/item/"+uuid);
        params.setApiName("CHANGE-STATUS");*/
        
        /*Integer vehicleId = 1;
        params.setUrl("http://52.26.174.162:8081/BeaconServices/webapi/item/vehicle/assigned/"+vehicleId);
        params.setApiName("ASSIGNED-API");*/
        
        /*params.setUrl("http://52.26.174.162:8081/BeaconServices/webapi/item/vehicle");
        params.setApiName("VEHICLE-ALLOCATION");*/
        
        /*params.setUrl("http://52.26.174.162:8081/BeaconServices/webapi/item/vehicle");
        params.setApiName("VEHICLE-ALLOCATION");*/
        
            /*Integer locationId = 1;
        params.setUrl("http://52.26.174.162:8081/BeaconServices/webapi/location/uuid/"+locationId);
        params.setApiName("LOCATION-UUID");*/
        
        /*params.setUrl("http://52.26.174.162:8081/BeaconServices/webapi/item/location");
        params.setApiName("RACK-ALLOCATION");*/
    
        params.setUrl("http://52.26.174.162:8081/BeaconServices/webapi/item/delocation");
        params.setApiName("RACK-DEALLOCATION");
        
        params.setMethod("POST");
		params.setRequestData(jsonObject.toString());
		//params.setMethod("GET");
		//params.setMethod("PUT");
		
		
		System.out.println("sending");
		test.testTheService(params);
	}

	
	public void testTheService(APIParams apiParams) throws IOException{
		
		if(apiParams.getApiName().equalsIgnoreCase("LOGIN-API")){
			loginApiTest(apiParams);	
		}
		
		if(apiParams.getApiName().equalsIgnoreCase("CHANGE-STATUS")){
			changeStatusToAssigned(apiParams);	
		}
		
		if(apiParams.getApiName().equalsIgnoreCase("ASSIGNED-API")){
			getTheItemsForVehicle(apiParams);	
		}
		
		if(apiParams.getApiName().equalsIgnoreCase("VEHICLE-ALLOCATION")){
			allocateTheVehicle(apiParams);	
		}
		
		if(apiParams.getApiName().equalsIgnoreCase("LOCATION-UUID")){
			getLocationUUID(apiParams);	
		}
		
		if(apiParams.getApiName().equalsIgnoreCase("RACK-ALLOCATION")){
			allocateRack(apiParams);	
		}
		
		if(apiParams.getApiName().equalsIgnoreCase("RACK-DEALLOCATION")){
			deAllocateRack(apiParams);	
		}
	}
	
	public void changeStatusToAssigned(APIParams apiParams) throws IOException {
		HttpURLConnection conn = getTheURL(apiParams);
		System.out.println("response code is: "+conn.getResponseCode());
		if (conn.getResponseCode() != 200) {
			/*throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());*/
			System.out.println("failure response");
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output = br.readLine();
		System.out.println("Output from Server .... \n"+output);
		Gson gson = new Gson();

		Response response = gson.fromJson(output, Response.class);
		System.out.println("result is: "+response.getResult());
		
		conn.disconnect();
		
	}


	public void deAllocateRack(APIParams apiParams) throws IOException{
		HttpURLConnection conn = getTheURL(apiParams);
	
		System.out.println("response code is: "+conn.getResponseCode());
		if (conn.getResponseCode() != 200) {
			/*throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());*/
			System.out.println("failure response");
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

				String output = br.readLine();
		System.out.println("Output from Server .... \n"+output);
		Gson gson = new Gson();

		Response response = gson.fromJson(output, Response.class);
		System.out.println("result is: "+response.getResult());
		
		conn.disconnect();
	}


	public Response allocateRack(APIParams apiParams) throws IOException{
		HttpURLConnection conn = getTheURL(apiParams);
		System.out.println("response code is: "+conn.getResponseCode());
		if (conn.getResponseCode() != 200) {
			/*throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());*/
			System.out.println("failure response");
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output = br.readLine();
		System.out.println("Output from Server .... \n"+output);
		Gson gson = new Gson();

		Response response = gson.fromJson(output, Response.class);
		System.out.println("result is: "+response.getResult());
		
		conn.disconnect();
		return response;
	}


	public LocationUuid getLocationUUID(APIParams apiParams) throws IOException{
		HttpURLConnection conn = getTheURL(apiParams);
			
		System.out.println("response code is: "+conn.getResponseCode());
		if (conn.getResponseCode() != 200) {
			/*throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());*/
			System.out.println("failure response");
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output = br.readLine();
		System.out.println("Output from Server .... \n"+output);
		Gson gson = new Gson();

		LocationUuid response = gson.fromJson(output, LocationUuid.class);
		System.out.println("result is: "+response.getUuid());
		
		conn.disconnect();
		return response;
	}


	public Response allocateTheVehicle(APIParams apiParams) throws IOException{
		HttpURLConnection conn = getTheURL(apiParams);
			
		System.out.println("response code is: "+conn.getResponseCode());
		if (conn.getResponseCode() != 200) {
			/*throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());*/
			System.out.println("failure response");
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		String output = br.readLine();
		System.out.println("Output from Server .... \n"+output);
		Gson gson = new Gson();

		Response response = gson.fromJson(output, Response.class);
		System.out.println("result is: "+response.getResult());
		
		conn.disconnect();
		return response;
		
	}


	public AssignedItems getTheItemsForVehicle(APIParams apiParams) throws IOException {
		URL urlObj = new URL(apiParams.getUrl());
		HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
		InputStream is = urlConnection.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(is)));

		String output = br.readLine();
		System.out.println("Output from Server .... \n"+output);
		Gson gson = new Gson();

		AssignedItems response = gson.fromJson(output, AssignedItems.class);
		System.out.println("result is: "+response.getResult());
		
		return response;

	}



	public void loginApiTest(APIParams apiParams) throws IOException{
		
		HttpURLConnection conn = getTheURL(apiParams);
		System.out.println("request data is: "+apiParams.getRequestData());
		
		System.out.println("response code is: "+conn.getResponseCode());
		if (conn.getResponseCode() != 200) {
			System.out.println("failure case");
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
		String output = br.readLine();
		System.out.println("Output from Server .... \n"+output);
		Gson gson = new Gson();

		DashBoardPOJO loginPOJO = gson.fromJson(output, DashBoardPOJO.class);
		System.out.println("result is: "+loginPOJO.getDashboard().getStatus().getMOVING());
		System.out.println("item placed date: "+loginPOJO.getDashboard().getLocations().get(0).getItemPlacedDate());
		
		conn.disconnect();

	}
	
	public HttpURLConnection getTheURL(APIParams apiParams) throws IOException{
		URL url = new URL(apiParams.getUrl());
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod(apiParams.getMethod());
		conn.setRequestProperty("Content-Type", "application/json");
		
		conn.setDoOutput(true);
		if(apiParams.getMethod().equalsIgnoreCase("POST")){
			OutputStream os = conn.getOutputStream();
			os.write(apiParams.getRequestData().getBytes());
			os.flush();
		}
		return conn;
	}
	
}
