package com.goeuro.challenge.data;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BusRouteDataStore {

    private Map<Integer, Map<Integer, List<Integer>>> busRoutesMap = new HashMap<>();

    /**
     * Update the store with the given station ID along with its connected stations and route Id.
     * 
     * @param routeId
     * @param stationId
     * @param connectedStationIds
     */
    public void updateStore(final Integer routeId,
                            final Integer stationId,
                            final List<Integer> connectedStationIds) {

        final Map<Integer, List<Integer>> connectedStationsInfo = getConnectedStationsInfo(stationId);

        connectedStationIds.stream().forEach(
                connectedStationId -> updateConnectedStationInfo(connectedStationId, routeId, connectedStationsInfo)
        );
    }

    /**
     * Fetch the existing connected station information for the given station ID.
     * 
     * @param stationId
     * @return
     */
    private Map<Integer, List<Integer>> getConnectedStationsInfo(final Integer stationId) {

        if (!busRoutesMap.containsKey(stationId)) {
            busRoutesMap.put(stationId, new HashMap<>());
        }

        return busRoutesMap.get(stationId);
    }

    /**
     * Update the store with the given station ID along with its connected stations and route Id.
     *
     * @param connectedStationId
     * @param routeId
     * @param connectedStationsInfo
     */
    private void updateConnectedStationInfo(final Integer connectedStationId,
                                            final Integer routeId,
                                            final Map<Integer, List<Integer>> connectedStationsInfo) {

        if (!connectedStationsInfo.containsKey(connectedStationId)) {
            connectedStationsInfo.put(connectedStationId, new ArrayList<>());
        }

        connectedStationsInfo.get(connectedStationId).add(routeId);
    }

    /**
     * Checks if the departure station is present in the store
     * 
     * @param departureStationId
     * @return true if it is present.
     */
    public boolean isValidaDepartureStationId(final Integer departureStationId) {
        return busRoutesMap.containsKey(departureStationId);
    }

    /**
     * Checks if the arrival station is added to the connection details of the departure station in the store
     *
     * @param departureStationId
     * @param arrivalStationId
     * @return true if arrival station is present in the value of departure station key
     */
    public boolean isStationConnected(final Integer departureStationId,
                                      final Integer arrivalStationId) {

        return isValidaDepartureStationId(departureStationId) &&
                busRoutesMap.get(departureStationId).containsKey(arrivalStationId);
    }

}
