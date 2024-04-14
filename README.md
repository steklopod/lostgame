# Getting Started

*Make sure you have Java 17+ installed.

## Run 🏎️

```shell
./gradlew bootRun
```

### To see database changes:
1. visit [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
2. press `connect` button
3. Run SQL query to see the data. For example: `SELECT * FROM BALANCE_TRANSACTION`

> NOTE: Before opening H2 web console, make sure that application is running. 
> Also remember to clear the previous query before running new one


## Test 🧑🏻‍💻

```shell
./gradlew test
```
___
Also you can send HTTP request from Intellij IDE. Open file [./src/test/resources/test.http](./src/test/resources/test.http)
and just click for needed request.

> NOTE: before executing these requests, make sure that application is running 

___

### 📋 TODO (what is not implemented):
- Currency exchange;
- Better exception handling;
- Better DTO converting;
- Better Validation;
- More tests.
