package com.jira.customtestrunner;

import com.google.gson.Gson;
import com.jiraclient.JiraTest;
import com.jiraclient.JiraTestStatus;
import com.jiraclient.TestExecution;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;


class TestExecutionProcessor {

    private static String SUCCESS_COMMENT = "Successful execution";

    private static String FAILURE_COMMENT = "Failed execution";

    static void processTestExecution(Map<String, String> allTests) {
        post(allTests);
    }

    /**
     * Get Jira username from config file
     *
     * @return String
     */
    private static String getUserName() {
        ConfigManagement config = ConfigManagement.getInstance();
        return config.getProperty("username");
    }

    /**
     * Get Jira password from config file
     *
     * @return String
     */
    private static String getPassword() {
        ConfigManagement config = ConfigManagement.getInstance();
        return config.getProperty("password");
    }

    /**
     * Get Jira url from config file
     *
     * @return String
     */
    private static String getJiraUrl() {
        ConfigManagement config = ConfigManagement.getInstance();
        return config.getProperty("url");
    }

    /**
     * Post all tests result to create com.jiraclient.TestExecution
     *
     * @param allTests {Map<String, String>} - unit tests results
     */
    private static void post(Map<String, String> allTests) {
        CloseableHttpClient httpclient = HttpClients.createMinimal();
        HttpPost httpPost = new HttpPost(getJiraUrl());

        UsernamePasswordCredentials creds = null;

        try {
            creds = createCredentials();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        httpPost.setEntity(testExecutionToJson(allTests));
        try {
            CloseableHttpResponse response = httpclient.execute(httpPost);
            System.out.println(response);

            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(EntityUtils.toString(response.getEntity()));
            httpclient.close();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                httpclient.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Create usernamePassword credentials to use with httpclient
     *
     * @return UsernamePasswordCredentials instance
     * @throws Exception
     */
    private static UsernamePasswordCredentials createCredentials() throws Exception {
        String user = getUserName();
        String pwd = getPassword();

        if (user.equals("") || pwd.equals("")) {
            throw new Exception("Credentials are not valid");
        }

        return new UsernamePasswordCredentials(user, pwd);
    }

    /**
     * Convert all tests into StringEntity to submit using httpClient
     *
     * @param allTests {Map<String, String>} - unit tests results
     * @return StringEntity
     */
    private static StringEntity testExecutionToJson(Map<String, String> allTests) {
        StringEntity input = null;

        try {
            TestExecution te = createTestExecution(allTests);

            Gson obj = new Gson();
            input = new StringEntity(obj.toJson(te));
            input.setContentType("application/json");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return input;
    }

    /**
     * Create test execution based on unit tests result allTests
     *
     * @param allTests {Map<String, String>} - unit tests results
     * @return instance of TestExecution
     */
    private static TestExecution createTestExecution(Map<String, String> allTests) {

        TestExecution testExecution = new TestExecution(
                "NewTestExecution Jira client",
                "This execution is automatically created when importing execution results from an external source",
                "osyrotenko",
                "1.0.42134",
                "IOS"
        );
        for(String testId : allTests.keySet()) {
            String state = allTests.get(testId);
            String testComment = state.equals(JiraTestStatus.PASS.name()) ? SUCCESS_COMMENT : FAILURE_COMMENT;
            JiraTest jt = new JiraTest(testId, testComment, state);
            testExecution.addTest(jt);
        }
        return testExecution;
    }
}
