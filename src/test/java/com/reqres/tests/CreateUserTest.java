package com.reqres.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.reqres.base.BaseTest;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.isEmptyString;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import org.testng.annotations.Listeners;
import com.reqres.reporting.ExtentTestListener;

@Listeners(ExtentTestListener.class)
public class CreateUserTest extends BaseTest {


    @Test
    public void createUserUsingApiChaining() {

        // STEP 1: GET users from page 2
        Response getResponse = userService.getUsers(2);

        Assert.assertEquals(
                getResponse.getStatusCode(),
                200,
                "GET status code should be 200"
        );

        // STEP 2: Extract first name for user ID 10
        String firstName = getResponse.jsonPath()
                .getString("data.find { it.id == 10 }.first_name");

        Assert.assertEquals(
                firstName,
                "Byron",
                "First name for user ID 10 should be Byron"
        );

        // STEP 3: POST using the extracted name
        Response postResponse =
                userService.createUser(firstName, "BA");

        // STEP 4: Verify status and response fields
        postResponse.then()
                .statusCode(201)
                .body("name", equalTo(firstName))
                .body("job", equalTo("BA"))
                .body("id", notNullValue())
                .body("id", not(isEmptyString()));

        // STEP 5: Verify the response JSON schema
        postResponse.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(
                        "create-user-schema.json"));

        // STEP 6: Log useful results
        String generatedId = postResponse.jsonPath()
                .getString("id");

        System.out.println("GET Status: "
                + getResponse.getStatusCode());
        System.out.println("Extracted First Name: "
                + firstName);
        System.out.println("POST Status: "
                + postResponse.getStatusCode());
        System.out.println("Generated User ID: "
                + generatedId);
        System.out.println("JSON Schema Validation: PASSED");
    }
}