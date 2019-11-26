package com.max.spring;

public class Validation
{
    public Boolean length(final String data, final int min, final int max) {
        final int len = data.length();
        if (len >= min && len <= max) {
            return true;
        }
        return false;
    }
    
    public Boolean isAlphaNumericWithSpace(final String data) {
        final String pattern = "^[a-zA-Z0-9 ]*$";
        return data.matches(pattern);
    }
    
    public Boolean isAlphaNumericOnly(final String data) {
        final String pattern = "^[a-zA-Z0-9]*$";
        return data.matches(pattern);
    }
    
    public Boolean isAlphabeticOnly(final String data) {
        final String pattern = "^[a-zA-Z]*$";
        return data.matches(pattern);
    }
    
    public Boolean isAlphabeticWithSpace(final String data) {
        final String pattern = "^[a-zA-Z ]*$";
        return data.matches(pattern);
    }
    
    public Boolean isNumericOnly(final String data) {
        final String pattern = "^[0-9]*$";
        return data.matches(pattern);
    }
}