# Bus Route Challenge

### Building the code
1. Clone the repo at https://github.com/shashikanthgadgay/bus-route-challenge.git
2. Run `./service.sh dev_build`
3. Run `./service.sh dev_run`

### Configuration
With the assumption that you'd perform all the operation at repository root level
1. To change the location of routes data stop the solution and restart it with `./service.sh dev_run [PATH_TO_ROUTES_DATA_FILE]`
2. In case of running the jar files on your own, execute this ``java -jar target/bus-route-challenge-*.jar [PATH_TO_ROUTES_DATA_FILE]`
3. In a given route, the stations are connected in the forward direction(not bidirectional).

### Control flow
REST Endpoint -> BusRouteController -> BusRouteService -> BusRouteDataStoreManager -> BusRouteDataStore

### Tech Stack
1. Spring boot
2. JUnit
3. Mockito

### Testing
1. Unit tests and Integration tests will be run during the build/package process.
2. If the test fails, the application won't be packaged.