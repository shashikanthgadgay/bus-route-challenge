package com.goeuro.challenge.constants;

public final class Constants {
    public final static int MAX_ROUTES = 100000;                    /* Maximum number of busRoutesMap allowed in the data file */
    public final static int MAX_STATIONS_PER_ROUTE = 1000;          /* Maximum number of stations allowed per route */
    public final static int MAX_OVERALL_UNIQUE_STATIONS = 1000000;  /* Maximum number of unique stations allowed across all given busRoutesMap */

    public static String ROUTES_COUNT_MISMATCH_REASON = "Number of busRoutesMap provided in the data file is different from pre-defined number of busRoutesMap on the first line of data file.";
    public static String ROUTES_COUNT_MISMATCH_ACTION = "Make sure to provide same amount of busRoutesMap as defined on the first line of data file.";

    public static String DUPLICATE_ROUTE_ID_FOUND_REASON = "Duplicate route ID found.";
    public static String DUPLICATE_ROUTE_ID_FOUND_ACTION = "Make sure to have unique route Ids.";

    public static String DUPLICATE_STATIONS_FOUND_IN_A_ROUTE_REASON = "A route has duplicate stations.";
    public static String DUPLICATE_STATIONS_FOUND_IN_A_ROUTE_ACTION = "Make sure to have only unique stations in every route.";

    public static String NUMBER_OF_ROUTES_EXCEEDED_REASON = "Total number of busRoutesMap in data file exceeded the allowed limit.";
    public static String NUMBER_OF_ROUTES_EXCEEDED_ACTION = String.format("Make sure that total number of busRoutesMap doesn't exceed %s.", Constants.MAX_ROUTES);

    public static String NUMBER_OF_UNIQUE_STATIONS_EXCEEDED_REASON = "Total number of unique stations exceeded the allowed limit.";
    public static String NUMBER_OF_UNIQUE_STATIONS_EXCEEDED_ACTION = String.format("Make sure that total number of unique stations doesn't exceed %s.", Constants.MAX_OVERALL_UNIQUE_STATIONS);

    public static String NUMBER_OF_STATIONS_PER_ROUTE_EXCEEDED_REASON = "Maximum number of stations per route exceeded the allowed limit.";
    public static String NUMBER_OF_STATIONS_PER_ROUTE_EXCEEDED_ACTION = String.format("Make sure that maximum number of stations per route shouldn't exceeds %s.", Constants.MAX_STATIONS_PER_ROUTE);

    public static String INSUFFICIENT_STATIONS_PER_ROUTE_REASON = "Insufficient number of stations provided per route in the data file.";
    public static String INSUFFICIENT_STATIONS_PER_ROUTE_ACTION = "Make sure that at least 2 stations per route is provided.";


}
