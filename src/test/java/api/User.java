package api;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import setData.DataGenerate;

public class User {

	Properties prop=new Properties();
	String url;
	String accesstoken;
	String email;
	 @BeforeClass
	    void setup() {
	        try (FileInputStream fsi = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\resources\\GlobalData.properties")) {
	            prop.load(fsi);
	            url = prop.getProperty("URL");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        DataGenerate data=new DataGenerate();
	         email=data.generateRandomEmail();
	    }

	//as a user I should able to login with valid Username and Password
	@Test
	void postUserLogin() {
		HashMap<String, Object> data=new HashMap<>();
		data.put("email", "aman@gmail.com");
		data.put("password", "admin@123");
		accesstoken=given()
		.contentType("application/json")
		.body(data)	
		.when()
		.post(url+"/api/auth/login").then()
        .statusCode(200)
        .extract()
        .jsonPath()
        .getString("access_token");
	}
	
	//as a user I should not able to login with invalid Username and invalid Password
	@Test
	void postUserLogin_UnSuccessful() {
		HashMap<String, Object> data=new HashMap<>();
		data.put("email", "aman12@gmail.com");
		data.put("password", "admin@123");
		given()
		.contentType("application/json")
		.body(data)	
		.when()
		.post(url+"/api/auth/login").then()
        .statusCode(401);
	}
	//as a user I should not able to login with invalid Username and invalid Password
	@Test
	void postUserLogin_UnSuccessful2() {
		HashMap<String, Object> data=new HashMap<>();
		data.put("email", "aman@gmail.com");
		data.put("password", "12admin@123");
		given()
		.contentType("application/json")
		.body(data)	
		.when()
		.post(url+"/api/auth/login").then()
        .statusCode(401);
	}

	@Test(dependsOnMethods = {"postUserLogin"})
	void getAllUserList() {
		given()
		.cookie("access_token_cookie", accesstoken)
		.when()
		.get(url+"/api/auth/list-user")
		.then()
		.statusCode(200);
	}
	
	@Test()
	void postUserCreate()
	{
		HashMap<String, Object> data=new HashMap<>();
		data.put("firstname", "amit");
		data.put("lastname", "sinha");
		data.put("email", email);
		data.put("password", "admin@123");
		given()
		.contentType("application/json")
		.body(data)	
		.when()
		.post(url+"/api/auth/create-user")
		.then()
        .statusCode(200) 
        ;
	}
	
	@Test()
	void postUserCreate2()	{
		HashMap<String, Object> data=new HashMap<>();
		data.put("firstname", "amit123");
		data.put("lastname", "sinha");
		data.put("email", "aman54@gmail.com");
		data.put("password", "admin@123");
		given()
		.contentType("application/json")
		.body(data)	
		.when()
		.post(url+"/api/auth/create-user")
		.then()
		.statusCode(400); 
	}
	
	@Test(dependsOnMethods = {"postUserLogin"})
	void updateUser() {
			HashMap<String, Object> data=new HashMap<>();
			data.put("firstname", "amit");
			data.put("lastname", "sinha");
			given()
			.contentType("application/json")
			.cookie("access_token_cookie", accesstoken)
			.body(data)	
			.when()
			.put(url+"/api/auth/update-user")
			.then()
		    .statusCode(200) 
	        ;
		}
	
	
	@Test(dependsOnMethods = {"postUserLogin"})
	void logoutUser() {
			given()
			.contentType("application/json")
			.cookie("access_token_cookie", accesstoken)
			.when()
			.post(url+"/api/auth/logout")
			.then()
		    .statusCode(200) 
	        ;
		}
}
