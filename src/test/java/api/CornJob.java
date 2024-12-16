package api;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CornJob {

	Properties prop=new Properties();
	String url;
	String accesstoken;
	
	 @BeforeClass
	    void setup() {
	        try (FileInputStream fsi = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\resources\\GlobalData.properties")) {
	            prop.load(fsi);
	            url = prop.getProperty("URL");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

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
        .statusCode(200) // Add status code check
        .extract()
        .jsonPath()
        .getString("access_token");
	}
	
	@Test(dependsOnMethods = {"postUserLogin"})
    void poststore_prices_coinpaprika() {
        Map<String, Object> queryParams = new HashMap<>();
        HashMap<String, Object> data=new HashMap<>();
        data.put("coins", new String[] {"ripple", "bitcoin", "ethereum", "litecoin", "binancecoin", "cardano", "usd-coin"});
        data.put("days", 10);
        queryParams.put("api_name", "coinpaprika");
        given()
        .header("Content-Type", "application/json")
            .queryParams(queryParams) 
    		.body(data)	
            .cookie("access_token_cookie", accesstoken)
        .when()
            .post(url+"/api/cron/store-prices") 
        .then()
        .statusCode(200) ;
    }
  
  @Test(dependsOnMethods = {"postUserLogin"})
    void poststore_prices_coinapi() {
        Map<String, Object> queryParams = new HashMap<>();
        HashMap<String, Object> data=new HashMap<>();
        data.put("coins", new String[] {
                "ripple", "bitcoin", "ethereum", "litecoin", "binancecoin", "cardano", "usd-coin"
            });data.put("days", 10);
        queryParams.put("api_name", "coinapi");
        
        given()
        .header("Content-Type", "application/json")
            .queryParams(queryParams) 
    		.body(data)	
            .cookie("access_token_cookie", accesstoken)
        .when()
            .post(url+"/api/cron/store-prices") 
        .then()
        .statusCode(200) ;
    }
  
  
  @Test(dependsOnMethods = {"postUserLogin"})
    void poststore_prices_coingecko() {
        Map<String, Object> queryParams = new HashMap<>();
        HashMap<String, Object> data=new HashMap<>();
        data.put("coins", new String[] {
                "ripple", "bitcoin", "ethereum", "litecoin", "binancecoin", "cardano", "usd-coin"
            });data.put("days", 10);
        queryParams.put("api_name", "coingecko");
        
        given()
        .header("Content-Type", "application/json")
            .queryParams(queryParams) 
    		.body(data)	
            .cookie("access_token_cookie", accesstoken)
        .when()
            .post(url+"/api/cron/store-prices") 
        .then()
        .statusCode(200) ;
    }
  
  @Test(dependsOnMethods = {"postUserLogin"})
    void poststore_prices_binance() {
        Map<String, Object> queryParams = new HashMap<>();
        HashMap<String, Object> data=new HashMap<>();
        data.put("coins", new String[] {
                "ripple", "bitcoin", "ethereum", "litecoin", "binancecoin", "cardano", "usd-coin"
            });data.put("days", 10);
        queryParams.put("api_name", "binance");
        
        given()
        .header("Content-Type", "application/json")
            .queryParams(queryParams) 
    		.body(data)	
            .cookie("access_token_cookie", accesstoken)
        .when()
            .post(url+"/api/cron/store-prices") 
        .then()
        .statusCode(200) ;
    }
}
