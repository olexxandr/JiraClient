package com.jiraclient;

import java.util.ArrayList;


class TestExecutionInfo {
    private String summary;
    private String description;
    private String user;
    private String revision;
    private ArrayList<String> testEnvironments = new ArrayList<String>();

    TestExecutionInfo(String summary, String description, String user, String revision, String testEnvironment) {
        this.summary = summary;
        this.description = description;
        this.user = user;
        this.revision = revision;
        this.testEnvironments.add(testEnvironment);
    }
}
