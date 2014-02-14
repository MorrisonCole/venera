package com.heisentest.splatter;

public class AnnotationRulesManager {

    public String log(String owner, String name, String desc, boolean isStatic) {
        if (owner.equals("Lcom/heisentest/skeletonandroidapp/MainActivity") && name.equals("getTitle")) {
            return "This is a log";
        } else {
            return null;
        }
    }
}
