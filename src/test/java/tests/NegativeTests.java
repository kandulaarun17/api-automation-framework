package tests;

import static io.restassured.RestAssured.given;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import endpoints.Endpoints;
import io.restassured.response.Response;
import payloads.EmployeeRequest;
import specs.RequestSpec;
import utils.LogHandler;

public class NegativeTests {

	private static final Logger log = LogHandler.getLogger(NegativeTests.class);

	@Test(description = "GET Invalid Employee", priority = 1)
	public void getInvalidEmployee() {

		log.info("Sending GET request with Invalid ID");

		Response response = given().spec(RequestSpec.getRequestSpec()).pathParam("id", "Invalid_123").when()
				.get(Endpoints.GET_EMPLOYEE);

		log.info("Response Status Code : {}", response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(), 404);

		log.info("GET Invalid Employee Test Execution Completed");
	}

	@Test(description = "GET Employee Without ID", priority = 2)
	public void getWithoutId() {

		Response response = given().spec(RequestSpec.getRequestSpec()).when().get(Endpoints.GET_EMPLOYEE);

		log.info("Response Status Code : {}", response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(), 404);

		log.info("GET Without ID Test Execution Completed");
	}

	@Test(description = "DELETE Invalid Employee", priority = 3)
	public void deleteInvalidEmployee() {

		log.info("Deleting Employee with Invalid ID");

		Response response = given().spec(RequestSpec.getRequestSpec()).pathParam("id", "ABC123").when()
				.delete(Endpoints.DELETE_EMPLOYEE);

		log.info("Response Status Code : {}", response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(), 404);

		log.info("DELETE Invalid Employee Test Execution Completed");
	}

	@Test(description = "PATCH Invalid Employee", priority = 4)
	public void patchInvalidEmployee() {

		EmployeeRequest request = new EmployeeRequest();
		request.setFirstName("Test");

		log.info("Updating invalid Employee ID");

		Response response = given().spec(RequestSpec.getRequestSpec()).pathParam("id", "ABC123").body(request).when()
				.patch(Endpoints.PATCH_EMPLOYEE);

		log.info("Response Status Code : {}", response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(), 404);

		log.info("PATCH Invalid Employee Test Passed");
	}

	@Test(description = "DELETE Already Deleted Employee", priority = 5)
	public void deleteAlreadyDeletedEmployee() {

		String id = "InvalidId";

		log.info("Deleting already deleted Employee ID : {}", id);

		Response response = given().spec(RequestSpec.getRequestSpec()).pathParam("id", id).when()
				.delete(Endpoints.DELETE_EMPLOYEE);

		log.info("Response Status Code : {}", response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(), 404);

		log.info("DELETE Already Deleted Employee Test Execution Completed");
	}

}