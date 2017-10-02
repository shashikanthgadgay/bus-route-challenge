package com.goeuro.challenge.data;

import com.goeuro.challenge.constants.Constants;
import com.goeuro.challenge.exceptions.InputErrorType;
import com.goeuro.challenge.exceptions.InvalidInputException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class BusRouteDataStoreManager {

    private static final Logger logger = Logger.getLogger(BusRouteDataStoreManager.class);

    private static final Pattern WHITE_SPACE_REGEX_PATTERN = Pattern.compile("\\s+");

    @Autowired
    private BusRouteDataStore dataStore;

    /**
     * Verify, validate and initialize the data in in-memory data store
     * @param dataFilePath
     * @throws IOException
     */
    public void validateDataFileAndInitializeStore(final String dataFilePath) throws IOException {

        verifyDataFilePath(dataFilePath);

        final List<String> lines =
                Files.lines(Paths.get(dataFilePath))
                        .map(line -> line.trim())
                        .collect(Collectors.toList());

        validateDataFileContent(lines);

        initializeDataStore(lines);
    }

    /**
     * Validate the data containing in the file
     * @param lines List of lines present in the input data file
     */
    private void validateDataFileContent(final List<String> lines) {

        final long totalRoutes = lines.stream().findFirst().map(Long::valueOf).get();

        validateRoutesCountMismatch(lines, totalRoutes);
        validateDuplicateRouteIds(lines, totalRoutes);
        validateMaxRoutesExceeded(totalRoutes);

        validateMaxUniqueStationsOverallExceeded(lines);

        validateInsufficientStationsPerRoute(lines);
        validateRoutesWithDuplicateStations(lines);
        validateMaxStationsPerRouteExceeded(lines);
    }

    /**
     * Check if there is any mismatch in the given total routes in the header and the actual routes present.
     *
     * @param lines       List of lines present in the input data file
     * @param totalRoutes Total routes specified in the header line
     * @return
     */
    private void validateRoutesCountMismatch(final List<String> lines, final long totalRoutes) {
        final long totalRoutesActual = lines.stream()
                .filter(line -> !StringUtils.isEmpty(line))
                .skip(1)
                .count();

        if (totalRoutesActual != totalRoutes) {
            throw new InvalidInputException(InputErrorType.ROUTES_COUNT_MISMATCH);
        }
    }

    /**
     * Check if duplicate route IDs are present.
     * 
     * @param lines             List of lines present in the input data file
     * @param totalRoutes  Total routes specified in the header line
     */
    private void validateDuplicateRouteIds(final List<String> lines, final long totalRoutes) {
        final Set<Integer> uniqueRouteIds = lines.stream()
                .filter(line -> !StringUtils.isEmpty(line))
                .skip(1)
                .map(line -> WHITE_SPACE_REGEX_PATTERN.split(line)[0])
                .map(Integer::valueOf)
                .collect(Collectors.toSet());

        if (uniqueRouteIds.size() != totalRoutes) {
            throw new InvalidInputException(InputErrorType.DUPLICATE_ROUTE_ID_FOUND);
        }
    }

    /**
     * Check if the total number of given routes exceeds the allowed limit.
     * 
     * @param totalRoutes  Total routes specified in the header line
     */
    private void validateMaxRoutesExceeded(final long totalRoutes) {
        if (totalRoutes > Constants.MAX_ROUTES) {
            throw new InvalidInputException(InputErrorType.NUMBER_OF_ROUTES_EXCEEDED);
        }
    }

    /**
     * Check if total number of given unique stations across all the routes exceed the allowed limit.
     * 
     * @param lines List of lines present in the input data file
     */
    private void validateMaxUniqueStationsOverallExceeded(final List<String> lines) {
        final Set<Integer> uniqueStationIds = lines.stream()
                .filter(line -> !StringUtils.isEmpty(line))
                .skip(1)
                .map(line -> WHITE_SPACE_REGEX_PATTERN.splitAsStream(line).skip(1).map(Integer::valueOf).collect(Collectors.toList()))
                .flatMap(x -> x.stream())
                .collect(Collectors.toSet());

        if (uniqueStationIds.size() > Constants.MAX_OVERALL_UNIQUE_STATIONS) {
            throw new InvalidInputException(InputErrorType.NUMBER_OF_UNIQUE_STATIONS_EXCEEDED);
        }
    }

    /**
     * Check if any route information doesn't contain the enough stations
     *
     * @param lines List of lines present in the input data file
     */
    private void validateInsufficientStationsPerRoute(final List<String> lines) {
        boolean routeInfoFormatIncorrect = lines.stream()
                .filter(line -> !StringUtils.isEmpty(line))
                .skip(1)
                .map(line -> WHITE_SPACE_REGEX_PATTERN.splitAsStream(line).map(Integer::valueOf).collect(Collectors.toList()))
                .anyMatch(routeInfo -> (routeInfo.size() < 3));

        if (routeInfoFormatIncorrect) {
            throw new InvalidInputException(InputErrorType.INSUFFICIENT_STATIONS_PER_ROUTE);
        }
    }

    /**
     *  Check if any duplicate stations are provided in the same route.
     *
     * @param lines List of lines present in the input data file
     */
    private void validateRoutesWithDuplicateStations(final List<String> lines) {
        boolean duplicateStationIdsPresent = lines.stream()
                .filter(line -> !StringUtils.isEmpty(line))
                .skip(1)
                .map(line -> WHITE_SPACE_REGEX_PATTERN.splitAsStream(line).skip(1).map(Integer::valueOf).collect(Collectors.toList()))
                .anyMatch(stationIds -> (stationIds.size() != (new HashSet<>(stationIds)).size()));

        if (duplicateStationIdsPresent) {
            throw new InvalidInputException(InputErrorType.DUPLICATE_STATIONS_FOUND_IN_A_ROUTE);
        }
    }

    /**
     * Check if total number of given stations in one or more routes exceeds the allowed limit.
     *
     * @param lines List of lines present in the input data file
     */
    private void validateMaxStationsPerRouteExceeded(final List<String> lines) {
        boolean maxStationsPerRouteExceeds = lines.stream()
                .filter(line -> !StringUtils.isEmpty(line))
                .skip(1)
                .map(line -> WHITE_SPACE_REGEX_PATTERN.splitAsStream(line).skip(1).map(Integer::valueOf).collect(Collectors.toList()))
                .anyMatch(routeInfo -> (routeInfo.size() > Constants.MAX_STATIONS_PER_ROUTE));

        if (maxStationsPerRouteExceeds) {
            throw new InvalidInputException(InputErrorType.MAX_STATIONS_PER_ROUTE_EXCEEDED);
        }
    }

    /**
     * @param lines List of lines present in the input data file
     */
    private void initializeDataStore(final List<String> lines) {
        lines.stream()
                .map(line -> WHITE_SPACE_REGEX_PATTERN.split(line))
                .map(BusRouteDataStoreManager::convertAndCollectIntRouteInfo)
                .forEach(routeInfo -> populateDataStore(routeInfo, dataStore));
    }

    /**
     * Convert each element given in the route information line into Integer and collect it into a list
     *
     * @param routeElements Array of elements present in the route information line
     * @return List of Integers parsed from route information line
     */
    private static List<Integer> convertAndCollectIntRouteInfo(final String[] routeElements) {
        return Arrays.stream(routeElements)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    /**
     * Populate the Data Store with the stations and its connections.
     *
     * @param routeInfo Route ID + Stations List
     * @param dataStore
     */
    private static void populateDataStore(final List<Integer> routeInfo,
                                          final BusRouteDataStore dataStore) {

        final Integer routeId = Integer.valueOf(routeInfo.get(0));

        final List<Integer> routeStationIds = routeInfo.subList(1, routeInfo.size());

        IntStream.range(0, routeStationIds.size()).forEach(
                index -> updateConnectedStationsForEachStationInStore(index, routeStationIds, routeId, dataStore)
                );
    }

    /**
     * For each station, the connected stations will be determined and updated in the data store along with the route ID.
     *
     * @param index
     * @param routeStationIds
     * @param routeId
     * @param dataStore
     */
    private static void updateConnectedStationsForEachStationInStore(final int index, final List<Integer> routeStationIds,
                                                                     final Integer routeId, final BusRouteDataStore dataStore) {
        final Integer currentStationId = routeStationIds.get(index);
        final List<Integer> connectedStationIds = routeStationIds.subList(index + 1, routeStationIds.size());
        dataStore.updateStore(routeId, currentStationId, connectedStationIds);
    }

    /**
     * Checks to verify if the file exists and is readable.
     *
     * @param filePath
     * @throws IOException
     */
    private void verifyDataFilePath(final String filePath) throws IOException {

        final File busRouteDataFile = new File(filePath);

        if (!busRouteDataFile.exists() || busRouteDataFile.isDirectory()) {
            final String errorMessage = String.format("Provided bus route data file '%s' doesn't exists.",
                    busRouteDataFile.getCanonicalPath());
            logger.error(errorMessage);
            throw new FileNotFoundException(errorMessage);
        }

        if (!busRouteDataFile.canRead()) {
            final String errorMessage = String.format("Provided bus route data file '%s' is not readable.",
                    busRouteDataFile.getCanonicalPath());
            logger.error(errorMessage);
            throw new SecurityException(errorMessage);
        }

    }

    public BusRouteDataStore getDataStore() {
        return dataStore;
    }

}

