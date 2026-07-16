package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportHandler {

	private static ExtentReports extent;
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	static {
		String time = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

		ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExecutionReport_" + time + ".html");

		spark.config().setDocumentTitle("Employee API Automation");
		spark.config().setReportName("Automation Execution Report");

		extent = new ExtentReports();
		extent.attachReporter(spark);

		extent.setSystemInfo("Tester", "Kandula Arun Kumar");
		extent.setSystemInfo("Framework", "Rest Assured");
		extent.setSystemInfo("Environment", "QA");
	}

	public static void createTest(String testName) {
		test.set(extent.createTest(testName));
	}

	public static void info(String message) {
		test.get().info(message);
	}

	public static void pass(String message) {
		test.get().pass(message);
	}

	public static void fail(String message) {
		test.get().fail(message);
	}

	public static void skip(String message) {
		test.get().skip(message);
	}

	public static void flush() {
		extent.flush();
	}
}