package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import utils.ReportHandler;

public class ExtentListener implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {

		ReportHandler.createTest(result.getMethod().getMethodName());
		ReportHandler.info("Test Execution Started");
	}

	@Override
	public void onTestSuccess(ITestResult result) {

		ReportHandler.pass("Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {

		ReportHandler.fail(result.getThrowable().toString());
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		ReportHandler.skip("Test Skipped");
	}

	@Override
	public void onFinish(ITestContext context) {

		ReportHandler.flush();
	}
}