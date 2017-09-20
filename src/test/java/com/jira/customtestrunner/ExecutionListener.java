package com.jira.customtestrunner;

import com.jiraclient.JiraTestStatus;
import com.jiraclient.JiraTicket;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.util.HashMap;
import java.util.Map;

public class ExecutionListener extends RunListener {

    private Map<String, String> allTests = new HashMap<String, String>();

    /**
     * Called when an atomic test has finished, whether the test succeeds or fails.
     */
    public void testFinished(Description description) throws java.lang.Exception {
        System.out.println("Finished execution of test case : " + description.getMethodName());
        System.out.println(description);
    }

    public void testRunStarted(Description description)	throws java.lang.Exception
    {
        System.out.println("Number of testcases to execute : " + description.testCount());
        description.getChildren().get(0).getChildren().get(0).getAnnotations();
                for (Description testSet : description.getChildren()) {
                    for (Description test : testSet.getChildren()) {
                        JiraTicket annotation = test.getAnnotation(JiraTicket.class);
                        if (annotation != null) {
                            String jiraTicket = annotation.value();
                            allTests.put(jiraTicket, JiraTestStatus.PASS.name());
                        }
                    }
                }
    }

    /**
     * Called when an atomic test fails.
     */
    public void testFailure(Failure failure) throws java.lang.Exception {
        System.out.println("Execution of test case failed : " + failure.getMessage());

        //APNA-5027: test description
        String testMsg = failure.getMessage();
        String [] messageData = testMsg.split(":");
        if (allTests.containsKey(messageData[0])) {
            allTests.put(messageData[0], JiraTestStatus.FAIL.name());
        }
    }

    public void testRunFinished(Result result) throws Exception {
        System.out.println("DONE");
        TestExecutionProcessor.processTestExecution(allTests);
    }
}