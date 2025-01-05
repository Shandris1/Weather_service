# Read Me 
This is a basic implementation of a back end Java weather service that
* Takes a eventId, converts it to lon and lat (currently effectively mocked as the service to do that is already existing and outside the scope of this task)
* Calls met.no to look up weather forecast
* caches the forecast for the location (as met.no gives all available forecasts) for 2 hours
* responds with the temperature and wind speed

Starting the service
With Java and Gradle successfully installed on a machine run 
./gradlew bootRun
in home directory

TODO:
* Replace event lookup with real event storage (seems outside of scope of the task)
* Move Cache from in memory to a separate endpoint to allow of multiple instances to run in parallel
* Create build scripts
* Improve temperature display to display entire range for event, or to show hour by hour weather (outside of scope of tasks)
* Display precipitation as well as temp/wind
* Put the whole project into docker for easy deploy
* Write tests that mock met.no endpoint

