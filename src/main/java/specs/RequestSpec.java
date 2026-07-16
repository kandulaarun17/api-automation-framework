package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;

public class RequestSpec {

	private static RequestSpecification request;

	public static RequestSpecification getRequestSpec() {
		request = new RequestSpecBuilder().setBaseUri(ConfigReader.getConfigProperty("baseUrl"))
				.setContentType("application/json").setAccept("application/json").build();
		return request;
	}

}
