package com.goeuro.challenge.exceptions;

import com.goeuro.challenge.constants.Constants;

public enum InputErrorType {

    ROUTES_COUNT_MISMATCH(100, Constants.ROUTES_COUNT_MISMATCH_REASON,
            Constants.ROUTES_COUNT_MISMATCH_ACTION),

    DUPLICATE_ROUTE_ID_FOUND(110, Constants.DUPLICATE_ROUTE_ID_FOUND_REASON,
            Constants.DUPLICATE_ROUTE_ID_FOUND_ACTION),

    DUPLICATE_STATIONS_FOUND_IN_A_ROUTE(120,
            Constants.DUPLICATE_STATIONS_FOUND_IN_A_ROUTE_REASON,
            Constants.DUPLICATE_STATIONS_FOUND_IN_A_ROUTE_ACTION),

    NUMBER_OF_ROUTES_EXCEEDED(130, Constants.NUMBER_OF_ROUTES_EXCEEDED_REASON,
            Constants.NUMBER_OF_ROUTES_EXCEEDED_ACTION),

    NUMBER_OF_UNIQUE_STATIONS_EXCEEDED(140,
            Constants.NUMBER_OF_UNIQUE_STATIONS_EXCEEDED_REASON,
            Constants.NUMBER_OF_UNIQUE_STATIONS_EXCEEDED_ACTION),

    MAX_STATIONS_PER_ROUTE_EXCEEDED(150, Constants.NUMBER_OF_STATIONS_PER_ROUTE_EXCEEDED_REASON,
            Constants.NUMBER_OF_STATIONS_PER_ROUTE_EXCEEDED_ACTION),

    INSUFFICIENT_STATIONS_PER_ROUTE(160, Constants.INSUFFICIENT_STATIONS_PER_ROUTE_REASON,
            Constants.INSUFFICIENT_STATIONS_PER_ROUTE_ACTION);


    private final int errorCode;
    private final String reason;
    private final String action;

    private InputErrorType(int errorCode, String reason, String action) {
        this.errorCode = errorCode;
        this.reason = reason;
        this.action = action;
    }

    /**
     * @return Formatted message corresponding to an error defined above
     */
    public String getMessage() {
        return String.format("Error Code: %s, Reason: %s, Suggestion: %s.",
                this.errorCode, this.reason, this.action);
    }

    public int getErrorCode(){
        return this.errorCode;
    }

}