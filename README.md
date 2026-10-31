## e-Duka Credit Management System

```
Names: Manzi Moses
Student ID: 27712
Project Name: e-Duka Credit Management System
```

## Steps to run the API

1. **Clone the repository**
   ```
   git clone https://github.com/Mosey-manzi/e--duka-credit-system-backend.git
   cd e--duka-credit-system-backend
   ```

2. **Create the database** in PostgreSQL (pgAdmin or psql):
   ```sql
   CREATE DATABASE eduka_db;
   ```

3. **Check the connection settings** in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5433/eduka_db
   spring.datasource.username=postgres
   spring.datasource.password=${DB_PASSWORD}
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   server.port=8082
   ```
4. **Set the database password** as an environment variable (same terminal that runs the app).

   Windows (Command Prompt):
   ```
   set DB_PASSWORD=your-postgres-password
   ```
   Windows (PowerShell):
   ```
   $env:DB_PASSWORD="your-postgres-password"
   ```

5. **Run the application**
   ```
   mvnw spring-boot:run
   ```
   The tables are created automatically (`ddl-auto=update`). The API is ready when the console shows `Started EDukaCreditSystemApplication`. It runs at `http://localhost:8082`.

6. **Import the Postman collection**
   In Postman, choose Import and select `e--duka-credit-system-midterm.postman_collection.json` from the repository root. Create a collection variable `baseUrl` with the value `http://localhost:8082`.

7. **Enter the seed data (Step 0)** through the save endpoints, in this order:
   goods categories, shops, customers, shopkeepers, then credit requests. Credit requests must be created before the D1 rules are tested, because D1 rejects past dates and always starts a new request as `REQUESTED`.

## Endpoints summary

| Part | Endpoints |
|---|---|
| A (CRUD) | `/api/{goods-categories, shops, shopkeepers, customers, credit-requests}` with `/save`, `/all`, `/{id}`, `/update/{id}`, `/delete/{id}` |
| B (derived) | `GET /api/customers/by-last-name`, `GET /api/credit-requests/by-status`, `GET /api/credit-requests/between` |
| C (JPQL) | `GET /api/shopkeepers/by-goods-category`, `GET /api/shops/free`, `GET /api/goods-categories/unused`, `GET /api/customers/of-shopkeeper/{shopkeeperId}` |
| D (rules) | `POST /api/credit-requests/save`, `PATCH /api/credit-requests/{id}/status`, `PUT /api/shopkeepers/{shopkeeperId}/goods-categories/{goodsCategoryId}`, `PATCH /api/shopkeepers/{shopkeeperId}/shop` |
| E (Repayment) | `POST /api/repayments/save`, `GET /api/repayments/customer/{nationalId}`, `DELETE /api/repayments/delete/{id}` |
| F (reports) | `GET /api/credit-requests/stats/by-status`, `PATCH /api/credit-requests/cancel-day` |
| Bonus | `GET /api/dashboard/summary`, `GET /api/credit-requests/page` |

## Project structure

```
com.auca.eduka
├── Domain       (entities and the CreditStatus enum)
├── repository   (Spring Data JPA repositories)
├── service      (business logic)
└── controller   (REST endpoints)
```

## Testing

All requests are in `e--duka-credit-system-midterm.postman_collection.json`, one request per requirement, named by its ID (A1, A2, B1 ... F2). Failing cases (404, 400, 409) are saved as examples on each request.
```
