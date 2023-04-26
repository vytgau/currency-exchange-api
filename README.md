# Currency Exchange API
This application provides an API allowing to exchange currencies.

### Application startup
You can start the application in either of the following ways:
- Using Docker
- Using Maven

### Application startup using Docker
Prerequisites:
- Docker (tested on version 20.10.22)

First - build the Docker image:
```console
docker build -t currency-exchange-api .
```

Next - start the application:
```console
docker compose up -d
```

Lastly - you can stop the application with:
```console
docker compose down
```

### Application startup using Maven
Prerequisites:
- JDK 17

First - build the project with one of the following commands depending on execution environment:
```console
mvnw clean package
./mvnw clean package
```
Next - start the application with one of the following commands depending on execution environment:
```console
mvnw spring-boot:run
./mvnw spring-boot:run
```

## API
### POST /currency-exchange
Converts the amount from one currency to another.

Request body fields:
- *fromCurrency* - initial currency
- *toCurrency* - final currency
- *amount* - amount in the initial currency

Response body fields:
- *amount* - amount in the final currency

#### Request example
POST http://localhost:8080/currency-exchange
--header 'Content-Type: application/json'
```json
{
  "fromCurrency": "EUR",
  "toCurrency": "GBP",
  "amount": 5
}
```
#### Response example
200 OK
```json
{
  "amount": 4.437758222056545915
}
```