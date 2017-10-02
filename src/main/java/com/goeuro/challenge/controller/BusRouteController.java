package com.goeuro.challenge.controller;

import com.goeuro.challenge.model.BusRouteResponse;
import com.goeuro.challenge.service.BusRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BusRouteController {

    @Autowired
    private BusRouteService busRouteService;

    /**
     * API to check if the given Departure Station ID and Arrival Station ID are directly connected.
     * It will accepts only GET/HEAD HTTP methods and 
     * It will return 200 OK if the input validation succeeds else 400 Bad Request will be sent.
     *
     * @param departureStationId
     * @param arrivalStationId
     * @return JSON response with the given Departure Station ID, Arrival Station ID and a flag to indicate if they are connected.
     */
    @RequestMapping(value = "/direct", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    BusRouteResponse isStationConnected(@RequestParam(value = "dep_sid") final int departureStationId,
                                            @RequestParam(value = "arr_sid") final int arrivalStationId) {

        final boolean directBusRouteExists = busRouteService.isStationConnected(departureStationId, arrivalStationId);
        return new BusRouteResponse(departureStationId, arrivalStationId, directBusRouteExists);
    }
}
