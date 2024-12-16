package api;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Portfolio {
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
	    void uploadFile() {
	        File fileToUpload = new File("C:\\Users\\Admin\\Downloads\\portfolio_updated 1 2.xlsx");

	        given()
	            .contentType("multipart/form-data")
	            .cookie("access_token_cookie", accesstoken)
	            .multiPart("file", fileToUpload) 
	            .when()
	            .post(url + "/api/portfolio/upload-portfolio")
	            .then()
	            .statusCode(200) ;
	    }
	 

	  @Test(dependsOnMethods = {"postUserLogin"})
	    void getAssetPerfomanceGraph() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "asset_performance");
	        queryParams.put("start_date", "2024/06/01");
	        queryParams.put("end_date", "2024/10/01");
	        given()
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/portfolio/graph-dashbord")
	        .then()
	        .statusCode(200) ;
	    }
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void getportfolioGrowthGraph() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "portfolio_growth");
	        queryParams.put("start_date", "2024/06/01");
	        queryParams.put("end_date", "2024/10/01");
	        given()
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/portfolio/graph-dashbord")
	        .then()
	        .statusCode(200) ;
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void getPortfolioDrawdownGraph() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "portfolio_drawdown");
	        queryParams.put("start_date", "2024/06/01");
	        queryParams.put("end_date", "2024/10/01");
	        given()
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/portfolio/graph-dashbord") 
	        .then()
	        .statusCode(200) ;
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void getPortfolioportfolioDistributionGraph() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "portfolio_distribution");
	        queryParams.put("start_date", "2024/06/01");
	        queryParams.put("end_date", "2024/10/01");
	        given()
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/portfolio/graph-dashbord") 
	        .then()
	        .statusCode(200) ;
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void getAllocation_Percentage_PlotGraph() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Allocation_percentage_plot");
	        queryParams.put("start_date", "2024/06/01");
	        queryParams.put("end_date", "2024/10/01");
	        given()
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/portfolio/graph-dataframe") 
	        .then()
	        .statusCode(200) ;
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void getPortfolio_Allocation_Plot_mtmGraph() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Portfolio_allocation_plot_mtm");
	        queryParams.put("start_date", "2024/06/01");
	        queryParams.put("end_date", "2024/10/01");
	        given()
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	        .get(url+"/api/portfolio/graph-dataframe") 
	        .then()
	        .statusCode(200) ;
	    }
	  
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void getPortfolio_Growth_PlotGraph() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Portfolio_growth_plot");
	        queryParams.put("start_date", "2024/06/01");
	        queryParams.put("end_date", "2024/10/01");
	        given()
	        .queryParams(queryParams) 
	        .cookie("access_token_cookie", accesstoken)
	        .when()
	        .get(url+"/api/portfolio/graph-dataframe") 
	        .then()
	        .statusCode(200) ;
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void getMonthly_Pnl_HeatmapGraph() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Monthly_pnl_heatmap");
	        queryParams.put("start_date", "2024/06/01");
	        queryParams.put("end_date", "2024/10/01");
	        given()
	        .queryParams(queryParams) 
	        .cookie("access_token_cookie", accesstoken)
	        .when()
	        .get(url+"/api/portfolio/graph-dataframe") 
	        .then()
	        .statusCode(200) ;
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void getMonthly_HeatmapGraph() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Monthly_heatmap");
	        queryParams.put("start_date", "2024/06/01");
	        queryParams.put("end_date", "2024/10/01");
	        given()
	        .queryParams(queryParams) 
	        .cookie("access_token_cookie", accesstoken)
	        .when()
	        .get(url+"/api/portfolio/graph-dataframe") 
	        .then()
	        .statusCode(200) ;
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void getDrawdown_Graph() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Drawdown_graph");
	        queryParams.put("start_date", "2024/06/01");
	        queryParams.put("end_date", "2024/10/01");
	        given()
	        .queryParams(queryParams) 
	        .cookie("access_token_cookie", accesstoken)
	        .when()
	        .get(url+"/api/portfolio/graph-dataframe") 
	        .then()
	        .statusCode(200) ;
	    }
}
