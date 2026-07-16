package endpoints;

public class Endpoints {

	// To create new employee record
	public static final String POST_EMPLOYEES = "/employees";
	// To fetch all employee records
	public static final String GET_ALL_EMPLOYEES = "/employees";
	// To fetch specific employee record
	public static final String GET_EMPLOYEE = "/employees/{id}";
	// To update a employee record
	public static final String PATCH_EMPLOYEE = "/employees/{id}";
	// To delete a employee record
	public static final String DELETE_EMPLOYEE = "/employees/{id}";

}
