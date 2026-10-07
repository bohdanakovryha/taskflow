package com.taskflow.core;

public class TaskFlowApp {

    public static final String NAME = "TaskFlow";

    public static String greeting() {
        return NAME + " is up and running";
    }

    public static void main(String[] args) {
        System.out.println(greeting());
    }
}
