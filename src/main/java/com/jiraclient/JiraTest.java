package com.jiraclient;


public class JiraTest {

    private String testKey;

    private String comment;

    private String status;

    public JiraTest(String testKey, String comment, String status) {
        this.testKey = testKey;
        this.comment = comment;
        this.status = status;
    }
}
