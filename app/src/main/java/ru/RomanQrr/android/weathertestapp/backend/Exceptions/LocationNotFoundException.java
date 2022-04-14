package ru.RomanQrr.android.weathertestapp.backend.Exceptions;

import androidx.annotation.Nullable;

public class LocationNotFoundException extends Exception {
    private String message;
    private Throwable cause;
    public LocationNotFoundException(String message, Throwable cause){
        super();
        this.message = message;
        this.cause = cause;
    }
    public LocationNotFoundException(String message){
        super();
        this.message = message;
    }

    @Nullable
    @Override
    public String getMessage() {
        return message;
    }

    public Throwable getCause(){
        return cause;
    }
}
