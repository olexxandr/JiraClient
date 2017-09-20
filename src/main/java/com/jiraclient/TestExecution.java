package com.jiraclient;

import com.jiraclient.JiraTest;
import com.jiraclient.TestExecutionInfo;
import java.util.ArrayList;
import java.util.List;


public class TestExecution {

    private TestExecutionInfo info;

    private List<JiraTest> tests = new ArrayList<JiraTest>();

    public TestExecution(String summary, String description, String user, String revision, String testEnvironment) {
        this.info = new TestExecutionInfo(summary, description, user, revision, testEnvironment);
    }

    public void addTest(JiraTest test) {
        this.tests.add(test);
    }
}
