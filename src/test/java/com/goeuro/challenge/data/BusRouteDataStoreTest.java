package com.goeuro.challenge.data;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class BusRouteDataStoreTest {

    private BusRouteDataStore dataStore;

    @Before
    public void setUp() {
        dataStore = new BusRouteDataStore();
    }

    @Test
    public void shouldUpdateStoreWithNoData() {
        assertThat(dataStore.isValidaDepartureStationId(1)).isFalse();
        assertThat(dataStore.isStationConnected(1, 2)).isFalse();
    }

    @Test
    public void shouldUpdateStoreWithSingleRouteDataWithTwoStations() {
        dataStore.updateStore(0, 1, Arrays.asList(2));

        assertThat(dataStore.isValidaDepartureStationId(1)).isTrue();
        assertThat(dataStore.isValidaDepartureStationId(2)).isFalse();

        assertThat(dataStore.isStationConnected(1, 2)).isTrue();
    }

    @Test
    public void shouldUpdateStoreWithSingleRouteDataWithManyStations() {
        dataStore.updateStore(0, 1, Arrays.asList(2, 3));
        dataStore.updateStore(0, 2, Arrays.asList(3));

        assertThat(dataStore.isValidaDepartureStationId(1)).isTrue();
        assertThat(dataStore.isValidaDepartureStationId(2)).isTrue();
        assertThat(dataStore.isValidaDepartureStationId(3)).isFalse();

        assertThat(dataStore.isStationConnected(1, 2)).isTrue();
        assertThat(dataStore.isStationConnected(1, 3)).isTrue();
        assertThat(dataStore.isStationConnected(2, 3)).isTrue();
        assertThat(dataStore.isStationConnected(3, 2)).isFalse();
        assertThat(dataStore.isStationConnected(3, 1)).isFalse();
    }


    @Test
    public void shouldUpdateStoreWithTwoRoutesDataWithTwoStations() {

        dataStore.updateStore(0, 1, Arrays.asList(2));
        dataStore.updateStore(1, 3, Arrays.asList(4));

        assertThat(dataStore.isValidaDepartureStationId(1)).isTrue();
        assertThat(dataStore.isValidaDepartureStationId(2)).isFalse();
        assertThat(dataStore.isValidaDepartureStationId(3)).isTrue();
        assertThat(dataStore.isValidaDepartureStationId(4)).isFalse();

        assertThat(dataStore.isStationConnected(1, 2)).isTrue();
        assertThat(dataStore.isStationConnected(3, 4)).isTrue();
        assertThat(dataStore.isStationConnected(1, 3)).isFalse();
        assertThat(dataStore.isStationConnected(2, 4)).isFalse();
    }

    @Test
    public void shouldUpdateStoreWithMultipleRoutesDataWithManyStations() {
        dataStore.updateStore(0, 1, Arrays.asList(2, 3, 4));
        dataStore.updateStore(0, 2, Arrays.asList(3, 4));
        dataStore.updateStore(0, 3, Arrays.asList(4));

        dataStore.updateStore(1, 4, Arrays.asList(5, 6, 7));
        dataStore.updateStore(1, 5, Arrays.asList(6, 7));
        dataStore.updateStore(1, 6, Arrays.asList(7));

        dataStore.updateStore(2, 6, Arrays.asList(5, 1, 7));
        dataStore.updateStore(2, 5, Arrays.asList(1, 7));
        dataStore.updateStore(2, 1, Arrays.asList(7));

        assertThat(dataStore.isValidaDepartureStationId(1)).isTrue();
        assertThat(dataStore.isValidaDepartureStationId(2)).isTrue();
        assertThat(dataStore.isValidaDepartureStationId(3)).isTrue();
        assertThat(dataStore.isValidaDepartureStationId(4)).isTrue();
        assertThat(dataStore.isValidaDepartureStationId(5)).isTrue();
        assertThat(dataStore.isValidaDepartureStationId(6)).isTrue();
        assertThat(dataStore.isValidaDepartureStationId(7)).isFalse();

        assertThat(dataStore.isStationConnected(5, 7)).isTrue();
        assertThat(dataStore.isStationConnected(5, 6)).isTrue();
        assertThat(dataStore.isStationConnected(6, 5)).isTrue();
    }
}