package com.reqres.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class ExtentManager {

    private static ExtentReports extent;

    private ExtentManager() {
        // Prevent object creation
    }

    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(
                            "ExtentReport.html");

            sparkReporter.config()
                    .setDocumentTitle("REST Assured Test Report");

            sparkReporter.config()
                    .setReportName("ReqRes API Automation Results");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            extent.setSystemInfo("Framework", "REST Assured");
            extent.setSystemInfo("Test Runner", "TestNG");
            extent.setSystemInfo("Java Version",
                    System.getProperty("java.version"));
        }

        return extent;
    }
}