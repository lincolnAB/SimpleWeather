package com.ormanin.simpleweather.simpleweather.Exceptions;

/**
 * Created by patrykormanin on 07/07/2017.
 */

public class NotImplementedException extends Exception {
    @Override
    public String getMessage() {
        return "Method not implemented yet.";
    }
}
