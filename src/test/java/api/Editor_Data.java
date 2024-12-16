package api;

import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Editor_Data {
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
	    void Get_CodeToEditor_portfolio_distribution() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "portfolio_distribution");
	        given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/editor/get-code") 
	        .then()
	        .statusCode(200) ;
	    }
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void Get_CodeToEditor_asset_performance() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "asset_performance");
	        given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/editor/get-code") 
	        .then()
	        .statusCode(200) ;
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void Get_CodeToEditor_portfolio_growth() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "portfolio_growth");
	        given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/editor/get-code") 
	        .then()
	        .statusCode(200) ;
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void Get_CodeToEditor_portfolio_drawdown() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "portfolio_drawdown");
	        given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/editor/get-code") 
	        .then()
	        .statusCode(200) ;
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void put_save_code_db_Allocation_percentage_plot() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Allocation_percentage_plot");
	        String data="{\r\n"
	        		+ "    \"code\": \"\\ndef generate_portfolio_growth_plot(df_end, tickers,df_start , df_trades):\\n    # Prepare the data\\n    df_endx = df_end.reset_index()\\n    start_value = df_endx.loc[df_endx.index[0], \\\"portfolio_mtm\\\"]\\n\\n    # Create the trace for portfolio growth\\n    growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),  # Dates\\n        \\\"y\\\": df_endx['portfolio_mtm'].tolist(),  # Portfolio balances\\n        \\\"type\\\": \\\"line\\\",\\n        \\\"name\\\": \\\"Portfolio Growth\\\",\\n        \\\"line\\\": {\\\"color\\\": \\\"blue\\\"}\\n    }\\n\\n    # Create the filled areas for positive and negative growth\\n    positive_growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),\\n        \\\"y\\\": [\\n            value if value > start_value else start_value for value in df_endx['portfolio_mtm']\\n        ],\\n        \\\"type\\\": \\\"scatter\\\",\\n        \\\"mode\\\": \\\"lines\\\",\\n        \\\"fill\\\": \\\"tonexty\\\",\\n        \\\"name\\\": \\\"Positive Growth\\\",\\n        \\\"fillcolor\\\": \\\"rgba(0, 128, 0, 0.3)\\\",  # Transparent green\\n        \\\"line\\\": {\\\"color\\\": \\\"rgba(0, 128, 0, 0)\\\"},  # No line color\\n    }\\n\\n    negative_growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),\\n        \\\"y\\\": [\\n            value if value < start_value else start_value for value in df_endx['portfolio_mtm']\\n        ],\\n        \\\"type\\\": \\\"scatter\\\",\\n        \\\"mode\\\": \\\"lines\\\",\\n        \\\"fill\\\": \\\"tonexty\\\",\\n        \\\"name\\\": \\\"Negative Growth\\\",\\n        \\\"fillcolor\\\": \\\"rgba(255, 0, 0, 0.3)\\\",  # Transparent red\\n        \\\"line\\\": {\\\"color\\\": \\\"rgba(255, 0, 0, 0)\\\"},  # No line color\\n    }\\n\\n    # Define the layout for the plot\\n    layout = {\\n        \\\"xaxis\\\": {\\\"title\\\": \\\"Date\\\"},\\n        \\\"yaxis\\\": {\\\"title\\\": \\\"Portfolio Balance ($)\\\"},\\n        \\\"title\\\": \\\"Portfolio Growth Over Time\\\",\\n        \\\"showlegend\\\": True\\n    }\\n\\n    return {\\n        \\\"data\\\": [growth_trace, positive_growth_trace, negative_growth_trace],\\n        \\\"layout\\\": layout\\n    }\\ndata = generate_portfolio_growth_plot(df_end, tickers,df_start , df_trades)\\n\"\r\n"
	        		+ "}";
	        given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .body(data)
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .put(url+"/api/editor/save-code") 
	        .then()
	        .statusCode(200);
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void put_save_code_db_Portfolio_allocation_plot_mtm() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Portfolio_allocation_plot_mtm");
	        String data="{\r\n"
	        		+ "    \"code\": \"\\ndef generate_portfolio_growth_plot(df_end, tickers,df_start , df_trades):\\n    # Prepare the data\\n    df_endx = df_end.reset_index()\\n    start_value = df_endx.loc[df_endx.index[0], \\\"portfolio_mtm\\\"]\\n\\n    # Create the trace for portfolio growth\\n    growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),  # Dates\\n        \\\"y\\\": df_endx['portfolio_mtm'].tolist(),  # Portfolio balances\\n        \\\"type\\\": \\\"line\\\",\\n        \\\"name\\\": \\\"Portfolio Growth\\\",\\n        \\\"line\\\": {\\\"color\\\": \\\"blue\\\"}\\n    }\\n\\n    # Create the filled areas for positive and negative growth\\n    positive_growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),\\n        \\\"y\\\": [\\n            value if value > start_value else start_value for value in df_endx['portfolio_mtm']\\n        ],\\n        \\\"type\\\": \\\"scatter\\\",\\n        \\\"mode\\\": \\\"lines\\\",\\n        \\\"fill\\\": \\\"tonexty\\\",\\n        \\\"name\\\": \\\"Positive Growth\\\",\\n        \\\"fillcolor\\\": \\\"rgba(0, 128, 0, 0.3)\\\",  # Transparent green\\n        \\\"line\\\": {\\\"color\\\": \\\"rgba(0, 128, 0, 0)\\\"},  # No line color\\n    }\\n\\n    negative_growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),\\n        \\\"y\\\": [\\n            value if value < start_value else start_value for value in df_endx['portfolio_mtm']\\n        ],\\n        \\\"type\\\": \\\"scatter\\\",\\n        \\\"mode\\\": \\\"lines\\\",\\n        \\\"fill\\\": \\\"tonexty\\\",\\n        \\\"name\\\": \\\"Negative Growth\\\",\\n        \\\"fillcolor\\\": \\\"rgba(255, 0, 0, 0.3)\\\",  # Transparent red\\n        \\\"line\\\": {\\\"color\\\": \\\"rgba(255, 0, 0, 0)\\\"},  # No line color\\n    }\\n\\n    # Define the layout for the plot\\n    layout = {\\n        \\\"xaxis\\\": {\\\"title\\\": \\\"Date\\\"},\\n        \\\"yaxis\\\": {\\\"title\\\": \\\"Portfolio Balance ($)\\\"},\\n        \\\"title\\\": \\\"Portfolio Growth Over Time\\\",\\n        \\\"showlegend\\\": True\\n    }\\n\\n    return {\\n        \\\"data\\\": [growth_trace, positive_growth_trace, negative_growth_trace],\\n        \\\"layout\\\": layout\\n    }\\ndata = generate_portfolio_growth_plot(df_end, tickers,df_start , df_trades)\\n\"\r\n"
	        		+ "}";
	        given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .body(data)
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .put(url+"/api/editor/save-code") 
	        .then()
	        .statusCode(200);
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void put_save_code_db_Portfolio_growth_plot() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Portfolio_growth_plot");
	        String data="{\r\n"
	        		+ "    \"code\": \"\\ndef generate_portfolio_growth_plot(df_end, tickers,df_start , df_trades):\\n    # Prepare the data\\n    df_endx = df_end.reset_index()\\n    start_value = df_endx.loc[df_endx.index[0], \\\"portfolio_mtm\\\"]\\n\\n    # Create the trace for portfolio growth\\n    growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),  # Dates\\n        \\\"y\\\": df_endx['portfolio_mtm'].tolist(),  # Portfolio balances\\n        \\\"type\\\": \\\"line\\\",\\n        \\\"name\\\": \\\"Portfolio Growth\\\",\\n        \\\"line\\\": {\\\"color\\\": \\\"blue\\\"}\\n    }\\n\\n    # Create the filled areas for positive and negative growth\\n    positive_growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),\\n        \\\"y\\\": [\\n            value if value > start_value else start_value for value in df_endx['portfolio_mtm']\\n        ],\\n        \\\"type\\\": \\\"scatter\\\",\\n        \\\"mode\\\": \\\"lines\\\",\\n        \\\"fill\\\": \\\"tonexty\\\",\\n        \\\"name\\\": \\\"Positive Growth\\\",\\n        \\\"fillcolor\\\": \\\"rgba(0, 128, 0, 0.3)\\\",  # Transparent green\\n        \\\"line\\\": {\\\"color\\\": \\\"rgba(0, 128, 0, 0)\\\"},  # No line color\\n    }\\n\\n    negative_growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),\\n        \\\"y\\\": [\\n            value if value < start_value else start_value for value in df_endx['portfolio_mtm']\\n        ],\\n        \\\"type\\\": \\\"scatter\\\",\\n        \\\"mode\\\": \\\"lines\\\",\\n        \\\"fill\\\": \\\"tonexty\\\",\\n        \\\"name\\\": \\\"Negative Growth\\\",\\n        \\\"fillcolor\\\": \\\"rgba(255, 0, 0, 0.3)\\\",  # Transparent red\\n        \\\"line\\\": {\\\"color\\\": \\\"rgba(255, 0, 0, 0)\\\"},  # No line color\\n    }\\n\\n    # Define the layout for the plot\\n    layout = {\\n        \\\"xaxis\\\": {\\\"title\\\": \\\"Date\\\"},\\n        \\\"yaxis\\\": {\\\"title\\\": \\\"Portfolio Balance ($)\\\"},\\n        \\\"title\\\": \\\"Portfolio Growth Over Time\\\",\\n        \\\"showlegend\\\": True\\n    }\\n\\n    return {\\n        \\\"data\\\": [growth_trace, positive_growth_trace, negative_growth_trace],\\n        \\\"layout\\\": layout\\n    }\\ndata = generate_portfolio_growth_plot(df_end, tickers,df_start , df_trades)\\n\"\r\n"
	        		+ "}";
	        given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .body(data)
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .put(url+"/api/editor/save-code") 
	        .then()
	        .statusCode(200);
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void put_save_code_db_Drawdown_graph() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Drawdown_graph");
	        String data="{\r\n"
	        		+ "    \"code\": \"\\ndef generate_portfolio_growth_plot(df_end, tickers,df_start , df_trades):\\n    # Prepare the data\\n    df_endx = df_end.reset_index()\\n    start_value = df_endx.loc[df_endx.index[0], \\\"portfolio_mtm\\\"]\\n\\n    # Create the trace for portfolio growth\\n    growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),  # Dates\\n        \\\"y\\\": df_endx['portfolio_mtm'].tolist(),  # Portfolio balances\\n        \\\"type\\\": \\\"line\\\",\\n        \\\"name\\\": \\\"Portfolio Growth\\\",\\n        \\\"line\\\": {\\\"color\\\": \\\"blue\\\"}\\n    }\\n\\n    # Create the filled areas for positive and negative growth\\n    positive_growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),\\n        \\\"y\\\": [\\n            value if value > start_value else start_value for value in df_endx['portfolio_mtm']\\n        ],\\n        \\\"type\\\": \\\"scatter\\\",\\n        \\\"mode\\\": \\\"lines\\\",\\n        \\\"fill\\\": \\\"tonexty\\\",\\n        \\\"name\\\": \\\"Positive Growth\\\",\\n        \\\"fillcolor\\\": \\\"rgba(0, 128, 0, 0.3)\\\",  # Transparent green\\n        \\\"line\\\": {\\\"color\\\": \\\"rgba(0, 128, 0, 0)\\\"},  # No line color\\n    }\\n\\n    negative_growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),\\n        \\\"y\\\": [\\n            value if value < start_value else start_value for value in df_endx['portfolio_mtm']\\n        ],\\n        \\\"type\\\": \\\"scatter\\\",\\n        \\\"mode\\\": \\\"lines\\\",\\n        \\\"fill\\\": \\\"tonexty\\\",\\n        \\\"name\\\": \\\"Negative Growth\\\",\\n        \\\"fillcolor\\\": \\\"rgba(255, 0, 0, 0.3)\\\",  # Transparent red\\n        \\\"line\\\": {\\\"color\\\": \\\"rgba(255, 0, 0, 0)\\\"},  # No line color\\n    }\\n\\n    # Define the layout for the plot\\n    layout = {\\n        \\\"xaxis\\\": {\\\"title\\\": \\\"Date\\\"},\\n        \\\"yaxis\\\": {\\\"title\\\": \\\"Portfolio Balance ($)\\\"},\\n        \\\"title\\\": \\\"Portfolio Growth Over Time\\\",\\n        \\\"showlegend\\\": True\\n    }\\n\\n    return {\\n        \\\"data\\\": [growth_trace, positive_growth_trace, negative_growth_trace],\\n        \\\"layout\\\": layout\\n    }\\ndata = generate_portfolio_growth_plot(df_end, tickers,df_start , df_trades)\\n\"\r\n"
	        		+ "}";
	        given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .body(data)
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .put(url+"/api/editor/save-code") 
	        .then()
	        .statusCode(200);
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void put_save_code_db_Monthly_pnl_heatmap() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Monthly_pnl_heatmap");
	        String data="{\r\n"
	        		+ "    \"code\": \"\\ndef generate_portfolio_growth_plot(df_end, tickers,df_start , df_trades):\\n    # Prepare the data\\n    df_endx = df_end.reset_index()\\n    start_value = df_endx.loc[df_endx.index[0], \\\"portfolio_mtm\\\"]\\n\\n    # Create the trace for portfolio growth\\n    growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),  # Dates\\n        \\\"y\\\": df_endx['portfolio_mtm'].tolist(),  # Portfolio balances\\n        \\\"type\\\": \\\"line\\\",\\n        \\\"name\\\": \\\"Portfolio Growth\\\",\\n        \\\"line\\\": {\\\"color\\\": \\\"blue\\\"}\\n    }\\n\\n    # Create the filled areas for positive and negative growth\\n    positive_growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),\\n        \\\"y\\\": [\\n            value if value > start_value else start_value for value in df_endx['portfolio_mtm']\\n        ],\\n        \\\"type\\\": \\\"scatter\\\",\\n        \\\"mode\\\": \\\"lines\\\",\\n        \\\"fill\\\": \\\"tonexty\\\",\\n        \\\"name\\\": \\\"Positive Growth\\\",\\n        \\\"fillcolor\\\": \\\"rgba(0, 128, 0, 0.3)\\\",  # Transparent green\\n        \\\"line\\\": {\\\"color\\\": \\\"rgba(0, 128, 0, 0)\\\"},  # No line color\\n    }\\n\\n    negative_growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),\\n        \\\"y\\\": [\\n            value if value < start_value else start_value for value in df_endx['portfolio_mtm']\\n        ],\\n        \\\"type\\\": \\\"scatter\\\",\\n        \\\"mode\\\": \\\"lines\\\",\\n        \\\"fill\\\": \\\"tonexty\\\",\\n        \\\"name\\\": \\\"Negative Growth\\\",\\n        \\\"fillcolor\\\": \\\"rgba(255, 0, 0, 0.3)\\\",  # Transparent red\\n        \\\"line\\\": {\\\"color\\\": \\\"rgba(255, 0, 0, 0)\\\"},  # No line color\\n    }\\n\\n    # Define the layout for the plot\\n    layout = {\\n        \\\"xaxis\\\": {\\\"title\\\": \\\"Date\\\"},\\n        \\\"yaxis\\\": {\\\"title\\\": \\\"Portfolio Balance ($)\\\"},\\n        \\\"title\\\": \\\"Portfolio Growth Over Time\\\",\\n        \\\"showlegend\\\": True\\n    }\\n\\n    return {\\n        \\\"data\\\": [growth_trace, positive_growth_trace, negative_growth_trace],\\n        \\\"layout\\\": layout\\n    }\\ndata = generate_portfolio_growth_plot(df_end, tickers,df_start , df_trades)\\n\"\r\n"
	        		+ "}";
	        given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .body(data)
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .put(url+"/api/editor/save-code") 
	        .then()
	        .statusCode(200);
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void put_save_code_db_Monthly_heatmap() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Monthly_heatmap");
	        String data="{\r\n"
	        		+ "    \"code\": \"\\ndef generate_portfolio_growth_plot(df_end, tickers,df_start , df_trades):\\n    # Prepare the data\\n    df_endx = df_end.reset_index()\\n    start_value = df_endx.loc[df_endx.index[0], \\\"portfolio_mtm\\\"]\\n\\n    # Create the trace for portfolio growth\\n    growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),  # Dates\\n        \\\"y\\\": df_endx['portfolio_mtm'].tolist(),  # Portfolio balances\\n        \\\"type\\\": \\\"line\\\",\\n        \\\"name\\\": \\\"Portfolio Growth\\\",\\n        \\\"line\\\": {\\\"color\\\": \\\"blue\\\"}\\n    }\\n\\n    # Create the filled areas for positive and negative growth\\n    positive_growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),\\n        \\\"y\\\": [\\n            value if value > start_value else start_value for value in df_endx['portfolio_mtm']\\n        ],\\n        \\\"type\\\": \\\"scatter\\\",\\n        \\\"mode\\\": \\\"lines\\\",\\n        \\\"fill\\\": \\\"tonexty\\\",\\n        \\\"name\\\": \\\"Positive Growth\\\",\\n        \\\"fillcolor\\\": \\\"rgba(0, 128, 0, 0.3)\\\",  # Transparent green\\n        \\\"line\\\": {\\\"color\\\": \\\"rgba(0, 128, 0, 0)\\\"},  # No line color\\n    }\\n\\n    negative_growth_trace = {\\n        \\\"x\\\": df_endx['index'].dt.strftime('%Y-%m-%d').tolist(),\\n        \\\"y\\\": [\\n            value if value < start_value else start_value for value in df_endx['portfolio_mtm']\\n        ],\\n        \\\"type\\\": \\\"scatter\\\",\\n        \\\"mode\\\": \\\"lines\\\",\\n        \\\"fill\\\": \\\"tonexty\\\",\\n        \\\"name\\\": \\\"Negative Growth\\\",\\n        \\\"fillcolor\\\": \\\"rgba(255, 0, 0, 0.3)\\\",  # Transparent red\\n        \\\"line\\\": {\\\"color\\\": \\\"rgba(255, 0, 0, 0)\\\"},  # No line color\\n    }\\n\\n    # Define the layout for the plot\\n    layout = {\\n        \\\"xaxis\\\": {\\\"title\\\": \\\"Date\\\"},\\n        \\\"yaxis\\\": {\\\"title\\\": \\\"Portfolio Balance ($)\\\"},\\n        \\\"title\\\": \\\"Portfolio Growth Over Time\\\",\\n        \\\"showlegend\\\": True\\n    }\\n\\n    return {\\n        \\\"data\\\": [growth_trace, positive_growth_trace, negative_growth_trace],\\n        \\\"layout\\\": layout\\n    }\\ndata = generate_portfolio_growth_plot(df_end, tickers,df_start , df_trades)\\n\"\r\n"
	        		+ "}";
	        given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .body(data)
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .put(url+"/api/editor/save-code") 
	        .then()
	        .statusCode(200);
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void get_shareReport() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Drawdown_graph");
	       given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/editor/share-report") 
	        .then()
	        .statusCode(200);
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void get_shareReportfile() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "portfolio_distribution");
	       given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/editor/share-report-file") 
	        .then()
	        .statusCode(200);
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void get_code_to_editor_backtest_Allocation_percentage_plot() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Allocation_percentage_plot");
	       given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/editor/get-code-backtest") 
	        .then()
	        .statusCode(200);
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void get_code_to_editor_backtest_Portfolio_allocation_plot_mtm() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Portfolio_allocation_plot_mtm");
	       given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/editor/get-code-backtest") 
	        .then()
	        .statusCode(200);
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void get_code_to_editor_backtest_Portfolio_growth_plot() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Portfolio_growth_plot");
	       given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/editor/get-code-backtest") 
	        .then()
	        .statusCode(200);
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void get_code_to_editor_backtest_Drawdown_graph() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Drawdown_graph");
	       given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/editor/get-code-backtest") 
	        .then()
	        .statusCode(200);
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void get_code_to_editor_backtest_Monthly_pnl_heatmap() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Monthly_pnl_heatmap");
	       given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/editor/get-code-backtest") 
	        .then()
	        .statusCode(200);
	    }
	  
	  @Test(dependsOnMethods = {"postUserLogin"})
	    void get_code_to_editor_backtest_Monthly_heatmap() {
	        Map<String, Object> queryParams = new HashMap<>();
	        queryParams.put("graph_name", "Monthly_heatmap");
	       given()
	        .header("Content-Type", "application/json")
	            .queryParams(queryParams) 
	            .cookie("access_token_cookie", accesstoken)
	        .when()
	            .get(url+"/api/editor/get-code-backtest") 
	        .then()
	        .statusCode(200);
	    }
}
