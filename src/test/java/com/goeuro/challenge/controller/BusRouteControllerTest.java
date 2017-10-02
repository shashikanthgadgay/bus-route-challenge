package com.goeuro.challenge.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"filePath=src/test/resources/bus-route-data/busRouteData"},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BusRouteControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void shouldTestDirectlyConnectedRoutes() {
        final String apiUrl = String.format("http://localhost:%s/api/direct?dep_sid=6&arr_sid=4", port);
        final ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        assertEquals(response.getBody().get("dep_sid"), 6);
        assertEquals(response.getBody().get("arr_sid"), 4);
        assertTrue((Boolean) response.getBody().get("direct_bus_route"));
    }

    @Test
    public void shouldTestRoutesWhichAreNotDirectlyConnected() {
        final String apiUrl = String.format("http://localhost:%s/api/direct?dep_sid=2&arr_sid=5", port);
        final ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);

        then(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        assertEquals(response.getBody().get("dep_sid"), 2);
        assertEquals(response.getBody().get("arr_sid"), 5);
        assertFalse((Boolean) response.getBody().get("direct_bus_route"));
    }

    @Test
    public void shouldTestDepartureAndArrivalStationIdsNotExists() {
        final String apiUrl = String.format("http://localhost:%s/api/direct?dep_sid=7&arr_sid=8", port);
        final ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        assertEquals(response.getBody().get("dep_sid"), 7);
        assertEquals(response.getBody().get("arr_sid"), 8);
        assertFalse((Boolean) response.getBody().get("direct_bus_route"));
    }


    @Test
    public void shouldTestDepartureStationIdNotExists() {
        final String apiUrl = String.format("http://localhost:%s/api/direct?dep_sid=7&arr_sid=6", port);
        final ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        assertEquals(response.getBody().get("dep_sid"), 7);
        assertEquals(response.getBody().get("arr_sid"), 6);
        assertFalse((Boolean) response.getBody().get("direct_bus_route"));
    }


    @Test
    public void shouldTestArrivalStationIdNotExists() {
        final String apiUrl = String.format("http://localhost:%s/api/direct?dep_sid=2&arr_sid=8", port);
        final ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        assertEquals(response.getBody().get("dep_sid"), 2);
        assertEquals(response.getBody().get("arr_sid"), 8);
        assertFalse((Boolean) response.getBody().get("direct_bus_route"));
    }


    @Test
    public void shouldTestQueryStringInputWithEmptyData() {
        final String apiUrl = String.format("http://localhost:%s/api/direct?dep_sid=&arr_sid=", port);
        assertEquals(restTemplate.getForEntity(apiUrl, Map.class).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldTestEmptyDepartureId() {
        final String apiUrl = String.format("http://localhost:%s/api/direct?dep_sid=&arr_sid=5", port);
        assertEquals(restTemplate.getForEntity(apiUrl, Map.class).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldTestEmptyArrivalId() {
        final String apiUrl = String.format("http://localhost:%s/api/direct?dep_sid=&arr_sid=5", port);
        assertEquals(restTemplate.getForEntity(apiUrl, Map.class).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void shouldTestNoQueryStringInput() {
        final String apiUrl = String.format("http://localhost:%s/api/direct", port);
        assertEquals(restTemplate.getForEntity(apiUrl, Map.class).getStatusCode(), HttpStatus.BAD_REQUEST);
    }

}