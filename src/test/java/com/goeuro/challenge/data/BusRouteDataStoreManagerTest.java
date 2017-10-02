package com.goeuro.challenge.data;

import com.goeuro.challenge.exceptions.InvalidInputException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;

public class BusRouteDataStoreManagerTest {

    final String TEST_DATA_FILE_DIR = "src/test/resources/bus-route-data/";

    @InjectMocks
    private BusRouteDataStoreManager dataStoreManager;

    @Mock
    private BusRouteDataStore dataStore;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);

        // remove final modifier from field
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }


    @Test(expected = FileNotFoundException.class)
    public void testDataFileNotExists() throws IOException {
        dataStoreManager.validateDataFileAndInitializeStore(TEST_DATA_FILE_DIR + "NoFile");
    }
    
    @Test(expected = FileNotFoundException.class)
    public void testDataFileIsDirectory() throws IOException {
        dataStoreManager.validateDataFileAndInitializeStore(TEST_DATA_FILE_DIR);
    }
    
    @Test(expected = InvalidInputException.class)
    public void testDataFileWithRoutesCountMismatch() throws IOException {
        try {
            dataStoreManager.validateDataFileAndInitializeStore(TEST_DATA_FILE_DIR + "routesMismatch");
        } catch (InvalidInputException e) {
            assertEquals(e.getErrorType().getErrorCode(), 100);
            throw e;
        }
    }

   
    @Test(expected = InvalidInputException.class)
    public void testDataFileWithDuplicateRouteIds() throws IOException {
        try {
            dataStoreManager.validateDataFileAndInitializeStore(TEST_DATA_FILE_DIR + "duplicateRouteIds");
        } catch (InvalidInputException e) {
            assertEquals(e.getErrorType().getErrorCode(), 110);
            throw e;
        }
    }

    @Test(expected = InvalidInputException.class)
    public void testDataFileWithInsufficientStationsPerRoute() throws IOException {
        try {
            dataStoreManager.validateDataFileAndInitializeStore(TEST_DATA_FILE_DIR + "insufficientStationsPerRoute");
        } catch (InvalidInputException e) {
            assertEquals(e.getErrorType().getErrorCode(), 160);
            throw e;
        }
    }

    @Test(expected = InvalidInputException.class)
    public void testDataFileWithDuplicateStationsInSingleRoute() throws IOException {
        try {
            dataStoreManager.validateDataFileAndInitializeStore(TEST_DATA_FILE_DIR + "duplicateStationsPerRoute");
        } catch (InvalidInputException e) {
            assertEquals(e.getErrorType().getErrorCode(), 120);
            throw e;
        }
    }

    @Test(expected = InvalidInputException.class)
    public void testDataFileWithMaxStationsPerRouteExceeds() throws IOException {
        try {
            dataStoreManager.validateDataFileAndInitializeStore(TEST_DATA_FILE_DIR + "maximumStationsPerRouteExceeded");
        } catch (InvalidInputException e) {
            assertEquals(e.getErrorType().getErrorCode(), 150);
            throw e;
        }
    }


    @Test
    public void testCorrectReferenceData() throws IOException {
        Mockito.doNothing().when(dataStore).updateStore(anyObject(), anyObject(), anyObject());
        dataStoreManager.validateDataFileAndInitializeStore(TEST_DATA_FILE_DIR + "/busRouteData");
    }

}