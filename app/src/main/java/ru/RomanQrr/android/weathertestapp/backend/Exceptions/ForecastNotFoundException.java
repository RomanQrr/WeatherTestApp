package ru.RomanQrr.android.weathertestapp.backend.Exceptions;

import androidx.annotation.Nullable;

public class ForecastNotFoundException extends Exception {
    private String message;
    private Throwable cause;
    public ForecastNotFoundException(String message, Throwable cause){
        super();
        this.message = message;
        this.cause = cause;
    }
    public ForecastNotFoundException(String message){
        super();
        this.message = message;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public Throwable getCause(){
        return cause;
    }
}
