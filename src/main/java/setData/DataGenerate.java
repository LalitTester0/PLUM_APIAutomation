package setData;

import java.util.UUID;

public class DataGenerate {
	 public  String generateRandomEmail() {
	        String randomString = UUID.randomUUID().toString().replace("-", "").substring(0, 8); // 8-character random string
	        String domain = "@example.com"; // Customizable domain
	        return randomString + domain;
	    }
}
