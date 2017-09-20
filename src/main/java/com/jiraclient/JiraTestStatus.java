package com.jiraclient;


public enum  JiraTestStatus {

    PASS("PASS"),

    FAIL("FAIL");

    private final String name;

    JiraTestStatus(String status) {
        name = status;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}
