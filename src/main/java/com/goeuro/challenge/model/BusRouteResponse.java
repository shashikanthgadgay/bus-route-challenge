package com.goeuro.challenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link BusRouteResponse} qualifies the required response object as required by bus
 * route challenge
 */
public class BusRouteResponse {

    int depStationId;

    int arrStationId;

    boolean isDirectBusRoute;

    public BusRouteResponse(int depId, int arrId, boolean isDirectBusRoute) {
        this.depStationId = depId;
        this.arrStationId = arrId;
        this.isDirectBusRoute = isDirectBusRoute;
    }

    @JsonProperty("dep_sid")
    public int getDepStationId() {
        return depStationId;
    }

    @JsonProperty("arr_sid")
    public int getArrStationId() {
        return arrStationId;
    }

    @JsonProperty("direct_bus_route")
    public boolean isDirectBusRoute() {
        return isDirectBusRoute;
    }

}
