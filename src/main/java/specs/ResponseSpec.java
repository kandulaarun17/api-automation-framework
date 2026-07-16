package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpec {
	private static ResponseSpecification response;

	public static ResponseSpecification getResponseSpec() {
		response = new ResponseSpecBuilder().expectContentType("application/json").build();
		return response;
	}
}
