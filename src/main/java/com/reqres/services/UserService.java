package com.reqres.services;

import java.util.HashMap;
import java.util.Map;

import com.reqres.specifications.RequestSpec;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserService {

    private static final String USERS_ENDPOINT = "/api/users";

    public Response getUsers(int page) {
        return given()
                .spec(RequestSpec.getRequestSpec())
                .queryParam("page", page)
            .when()
                .get(USERS_ENDPOINT);
    }

    public Response createUser(String name, String job) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", name);
        requestBody.put("job", job);

        return given()
                .spec(RequestSpec.getRequestSpec())
                .body(requestBody)
            .when()
                .post(USERS_ENDPOINT);
    }
}