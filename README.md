# AviasalesSmokeTests


Steps to run tests

#1 Create project folder and clone the repository
git clone https://github.com/mariabeqa/AviasalesSmokeTests.git

#2 In the project folder

Basic search flow. Check that airports in search results match airports entered in the search parameters. Apply filters
with/without baggage and check that search results match the criteria. Apply filter with/without transfer and check that search
results match the criteria.
linux
./gradlew clean basicSearchTests
windows
gradlew clean basicSearchTests

Complex search flow. Same flow as for the basic search flow, but the flight consists of a complex route
linux
./gradlew clean complexSearchTests
windows
gradlew clean complexSearchTests

Request prices on the specific date/month/season using calendar. Check that selected date/month/season matches the search results.
Check that suggested cheapest price matches the cheapest price in the table
linux
./gradlew clean calendarTests
windows
gradlew clean calendarTests