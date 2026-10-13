package com.reqres.base;

import org.testng.annotations.BeforeClass;

import com.reqres.services.UserService;

public class BaseTest {

    protected UserService userService;

    @BeforeClass
    public void setUp() {
        userService = new UserService();
    }
}