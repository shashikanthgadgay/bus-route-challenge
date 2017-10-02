package com.goeuro.challenge.service;

import com.goeuro.challenge.data.BusRouteDataStore;
import com.goeuro.challenge.data.BusRouteDataStoreManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class BusRouteServiceTest {

    @InjectMocks
    private BusRouteService busRouteService;

    @Mock
    private BusRouteDataStore dataStore;

    @Mock
    private BusRouteDataStoreManager dataStoreManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnTrueIfStationsAreConnected() {
        when(dataStoreManager.getDataStore()).thenReturn(dataStore);

        when(dataStore.isValidaDepartureStationId(1)).thenReturn(true);
        when(dataStore.isStationConnected(1, 2)).thenReturn(true);

        assertTrue(busRouteService.isStationConnected(1, 2));
    }


    @Test
    public void shouldReturnFalseIfArrivalStationIdDoesNotExists() {
        when(dataStoreManager.getDataStore()).thenReturn(dataStore);

        when(dataStore.isValidaDepartureStationId(1)).thenReturn(true);
        when(dataStore.isStationConnected(1, 2)).thenReturn(false);

        assertFalse(busRouteService.isStationConnected(1, 2));
    }

    @Test
    public void shouldReturnFalseWhenDepatureStationIdDoesNotExists() {
        when(dataStoreManager.getDataStore()).thenReturn(dataStore);

        when(dataStore.isValidaDepartureStationId(1)).thenReturn(false);

        assertFalse(busRouteService.isStationConnected(1, 2));
    }

}