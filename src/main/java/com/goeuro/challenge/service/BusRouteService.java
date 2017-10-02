package com.goeuro.challenge.service;

import com.goeuro.challenge.data.BusRouteDataStore;
import com.goeuro.challenge.data.BusRouteDataStoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation to check if the two given stations are directly connected or not.
 * It uses the data loaded during the application/server start.
 */
@Service
public class BusRouteService {

    @Autowired
    private BusRouteDataStoreManager dataStoreManager;

    /**
     * Checks if the given Departure Station and Arrival Station are directly connected.
     *
     * @param depStationId
     * @param arrStationId
     * @return true if the stations are directly connected.
     */
    public boolean isStationConnected(final Integer depStationId,
                                      final Integer arrStationId) {

        final BusRouteDataStore dataStore = dataStoreManager.getDataStore();
        return dataStore.isStationConnected(depStationId, arrStationId);

    }

}
