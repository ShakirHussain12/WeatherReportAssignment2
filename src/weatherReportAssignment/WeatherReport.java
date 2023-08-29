package weatherReportAssignment;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class WeatherReport {
	

	public static void main(String[] args) throws Exception {
		WeatherReport wr = new WeatherReport();
		wr.userOutput();
	}
	
	public int userInputChoice() throws Exception{
		Scanner sc= new Scanner(System.in);    //System.in is a standard input stream  
		System.out.print("Enter Your Choices (0/1/2/3) = ");  
		
		int n = sc.nextInt();
		
		return n;
	}
	
	public  String userDateInput() throws Exception{
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter date with time (YYYY/MM/DD HH:MM:SS)"); 
		
		String dateWithTime = sc.nextLine();
		return dateWithTime;
	}
	public  Response weatherAssignment() throws Exception{
		RestAssured.baseURI = "https://samples.openweathermap.org/data/2.5/forecast/hourly"; 
		RequestSpecification httpRequest = RestAssured.given().param("q", "London,us").param("appid", "b6907d289e10d714a6e88b30761fae22"); 
		Response response = httpRequest.request(Method.GET, "");
		return response;
	}
	
	public void userOutput() throws Exception{
		int userInput = userInputChoice();
		Response resp = weatherAssignment();
		long date = dateToSeconds();
		
		String dateString = Long.toString(date);
		JsonPath json = resp.jsonPath();
		int size = json.getInt("list.dt.size()");
		
		switch(userInput) {
		case 1:
			for (int i = 0; i < size; i++) {
		        String detail = json.getString("list[" + i + "].dt");
		        if (detail.equalsIgnoreCase(dateString)) {
		        	System.out.println("yes");
		            String temp = json
		                    .getString("list[" + i + "].main.temp");
		            System.out.println("Temperature is:: "+ temp);
		            }
			}
			break;
		case 2:
			for (int i = 0; i < size; i++) {
		        String detail = json.getString("list[" + i + "].dt");
		        if (detail.equalsIgnoreCase(dateString)) {
		        	System.out.println("yes");
		            String windSpeed = json
		                    .getString("list[" + i + "].wind.speed");
		            System.out.println("Windspeed is:: "+ windSpeed);
		            }
			}
			break;
		case 3:
			for (int i = 0; i < size; i++) {
		        String detail = json.getString("list[" + i + "].dt");
		        if (detail.equalsIgnoreCase(dateString)) {
		        	System.out.println("yes");
		            String pressure = json
		                    .getString("list[" + i + "].main.pressure");
		            System.out.println("Pressure is:: "+ pressure);
		            }
			}
			break;
		
		case 0:
			System.exit(0);
			break;
			
		default:
			System.out.print("Enter valid input::");
				
		}
	
	}	
	
	public  long dateToSeconds() throws Exception{
		String dateWithTime = userDateInput();
		
		System.out.println(dateWithTime);
		LocalDateTime localDateTime = LocalDateTime.parse(dateWithTime,
		    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") );
		
		long seconds = localDateTime.toEpochSecond(ZoneOffset.UTC);
		
		System.out.println("In seconds:: " + seconds);
	
		
		
		return seconds;
	}

}
