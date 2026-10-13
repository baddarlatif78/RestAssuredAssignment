package com.reqres.reporting;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ExtentTestListener implements ITestListener {

    private static final ExtentReports EXTENT =
            ExtentManager.getInstance();

    private static final ThreadLocal<ExtentTest> EXTENT_TEST =
            new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = EXTENT.createTest(
                result.getMethod().getMethodName());

        test.assignCategory(
                result.getTestClass().getRealClass().getSimpleName());

        EXTENT_TEST.set(test);
        test.log(Status.INFO, "Test execution started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        getTest().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        getTest().log(Status.FAIL, "Test failed");
        getTest().fail(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        getTest().log(Status.SKIP, "Test skipped");

        if (result.getThrowable() != null) {
            getTest().skip(result.getThrowable());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        EXTENT.flush();
        EXTENT_TEST.remove();
    }

    private ExtentTest getTest() {
        return EXTENT_TEST.get();
    }
}