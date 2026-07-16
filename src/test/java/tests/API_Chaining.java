package tests;

import static io.restassured.RestAssured.given;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import dataProviders.EmployeeDataProvider;
import endpoints.Endpoints;
import io.restassured.response.Response;
import payloads.EmployeeRequest;
import payloads.EmployeeResponse;
import specs.RequestSpec;
import specs.ResponseSpec;
import utils.LogHandler;
import utils.ReportHandler;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class API_Chaining {
	private static final Logger log = LogHandler.getLogger(API_Chaining.class);
	private static String id;

	@Test(description = "POST record", priority = 1, dataProvider = "employee_data", dataProviderClass = EmployeeDataProvider.class)
	public void createEmployeeRecord(String emp_id, String first_name, String last_name, String email, String phone,
			String department, String designation, String emp_salary) {
		ReportHandler.createTest("POST Employee");

		Integer employeeId = Integer.parseInt(emp_id);
		Double salary = Double.parseDouble(emp_salary);

		log.info("Creating employee with ID : {}", employeeId);
		ReportHandler.info("Creating Employee Request Payload");

		EmployeeRequest requestBody = new EmployeeRequest();
		requestBody.setEmployeeId(employeeId);
		requestBody.setFirstName(first_name);
		requestBody.setLastName(last_name);
		requestBody.setEmail(email);
		requestBody.setPhone(phone);
		requestBody.setDepartment(department);
		requestBody.setDesignation(designation);
		requestBody.setSalary(salary);

		log.info("POST Request Execution Started");
		ReportHandler.info("Sending POST Request");

		Response response = given().spec(RequestSpec.getRequestSpec()).body(requestBody).when()
				.post(Endpoints.POST_EMPLOYEES);

		log.info("Response Status Code : {}", response.getStatusCode());
		ReportHandler.info("Status Code : " + response.getStatusCode());

		response.then().spec(ResponseSpec.getResponseSpec())
				.body(matchesJsonSchemaInClasspath("schemas/EmployeeSchema.json"));
		ReportHandler.pass("JSON Schema Validation Executed");

		id = response.jsonPath().getString("id");

		log.info("Generated Employee Record ID : {}", id);
		ReportHandler.info("Generated Employee ID : " + id);

		EmployeeResponse responseBody = response.as(EmployeeResponse.class);

		Assert.assertEquals(response.getStatusCode(), 201);
		Assert.assertNotNull(id);
		Assert.assertEquals(responseBody.getEmployeeId(), requestBody.getEmployeeId());
		Assert.assertEquals(responseBody.getFirstName(), requestBody.getFirstName());
		Assert.assertEquals(responseBody.getLastName(), requestBody.getLastName());
		Assert.assertEquals(responseBody.getEmail(), requestBody.getEmail());
		Assert.assertEquals(responseBody.getPhone(), requestBody.getPhone());
		Assert.assertEquals(responseBody.getDepartment(), requestBody.getDepartment());
		Assert.assertEquals(responseBody.getDesignation(), requestBody.getDesignation());
		Assert.assertEquals(responseBody.getSalary(), requestBody.getSalary());

		log.info("POST Employee Test Execution Completed");
		ReportHandler.pass("POST Employee Test Execution Completed");
	}

	@Test(description = "GET record", priority = 2)
	public void getEmployeeRecord() {
		ReportHandler.createTest("GET Employee");

		log.info("Fetching Employee ID : {}", id);
		ReportHandler.info("Sending GET Request");

		Response response = given().spec(RequestSpec.getRequestSpec()).pathParam("id", id).when()
				.get(Endpoints.GET_EMPLOYEE);

		log.info("Response Status Code : {}", response.getStatusCode());
		ReportHandler.info("Status Code : " + response.getStatusCode());

		response.then().spec(ResponseSpec.getResponseSpec())
				.body(matchesJsonSchemaInClasspath("schemas/EmployeeSchema.json"));

		ReportHandler.pass("Response Specification Executed");

		EmployeeResponse responseBody = response.as(EmployeeResponse.class);

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(responseBody.getEmployeeId(), response.jsonPath().getInt("employee_id"));
		Assert.assertEquals(responseBody.getFirstName(), response.jsonPath().getString("first_name"));
		Assert.assertEquals(responseBody.getLastName(), response.jsonPath().getString("last_name"));

		log.info("GET Employee Test Execution Completed");
		ReportHandler.pass("Employee Retrieved Successfully");
	}

	@Test(description = "PATCH record", priority = 3)
	public void updateEmployeeRecord() {
		ReportHandler.createTest("UPDATE Employee");
		log.info("Updating Employee ID : {}", id);
		ReportHandler.info("Sending PATCH Request");

		EmployeeRequest requestBody = new EmployeeRequest();
		ReportHandler.info("Updating Employee Name");
		requestBody.setFirstName("Kandula Arun");
		requestBody.setLastName("Kumar");

		Response response = given().spec(RequestSpec.getRequestSpec()).pathParam("id", id).body(requestBody).when()
				.patch(Endpoints.PATCH_EMPLOYEE);

		response.then().spec(ResponseSpec.getResponseSpec());

		log.info("Response Status Code : {}", response.getStatusCode());
		ReportHandler.info("Status Code : " + response.getStatusCode());

		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("first_name"), "Kandula Arun");
		Assert.assertEquals(response.jsonPath().getString("last_name"), "Kumar");

		log.info("PATCH Employee Test Execution Completed");
		ReportHandler.pass("Employee Updated Successfully");

	}

	@Test(description = "DELETE record", priority = 4)
	public void deleteEmployeeRecord() {

		ReportHandler.createTest("DELETE Employee");

		log.info("Deleting Employee ID : {}", id);
		ReportHandler.info("Sending DELETE Request");

		Response response = given().spec(RequestSpec.getRequestSpec()).pathParam("id", id).when()
				.delete(Endpoints.DELETE_EMPLOYEE);

		log.info("Response Status Code : {}", response.getStatusCode());
		ReportHandler.info("Status Code : " + response.getStatusCode());

		response.then().spec(ResponseSpec.getResponseSpec());

		Assert.assertEquals(response.getStatusCode(), 200);

		log.info("DELETE Employee Test ");
		ReportHandler.pass("Employee Deleted Successfully");
	}

}