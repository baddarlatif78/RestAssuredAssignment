package com.reqres.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.reqres.base.BaseTest;
import com.reqres.reporting.ExtentTestListener;

import io.restassured.response.Response;

@Listeners(ExtentTestListener.class)
public class ReqResApiChainingTest extends BaseTest {

    @Test
    public void verifyGetUsers() {

        // Send GET request using the reusable service
        Response response = userService.getUsers(2);

        // Verify status code
        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "GET status code should be 200"
        );

        // Extract first name for user ID 10
        String firstName = response.jsonPath()
                .getString("data.find { it.id == 10 }.first_name");

        // Verify first name
        Assert.assertEquals(
                firstName,
                "Byron",
                "First name for user ID 10 should be Byron"
        );

        System.out.println("GET Status Code: "
                + response.getStatusCode());
        System.out.println("First Name for User ID 10: "
                + firstName);
    }
}