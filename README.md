# Getting Started

*Make sure you have `Java 17+` installed.

## Run 🏎️

```shell
./gradlew bootRun
```

### To see database changes:
1. visit [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
2. press `connect` button (check that jdbc url = 👉🏻 `jdbc:h2:mem:db` 👈🏻)
3. run sql query to see the data (for example: `select * from balance_transaction`)

> NOTE: Before opening H2 web console, make sure that application is running.
> Also remember to clear the previous query before running new one


## Test 🧑🏻‍💻

```shell
./gradlew test
```
___
To send HTTP request from any `JetBrains IDE` open  [./src/test/resources/test.http](./src/test/resources/test.http) file
and click at needed request.

> NOTE: before executing these requests, make sure that application is running

> If you have another IDE you can also run test curl commands from bash script [./src/test/resources](./src/test/resources)
___

### 📋 TODO (what is not implemented):
- Currency exchange;
- Better exception handling;
- Better DTO converting and not return entity to frontend;
- Better Validation;
- Database migration;
- `created` + `updated` fields on entities;
- More tests.
