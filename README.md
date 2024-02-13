# Enterprise Assistant

## About

Enterprise Assistant is a backend system designed to support the operations of a production and service company in the B2B sector. It optimizes the management of clients, services, products, and orders, thereby streamlining everyday business processes. A key feature is the automation of generating PDF invoices and the ability to send them directly via email.

## Built With

Enterprise Assistant is developed using the following technologies:

<div >
	<img width="60" src="https://user-images.githubusercontent.com/25181517/117201156-9a724800-adec-11eb-9a9d-3cd0f67da4bc.png" alt="Java" title="Java"/>
	<img width="60" src="https://user-images.githubusercontent.com/25181517/192107858-fe19f043-c502-4009-8c47-476fc89718ad.png" alt="REST" title="REST"/>
	<img width="60" src="https://user-images.githubusercontent.com/25181517/117207242-07d5a700-adf4-11eb-975e-be04e62b984b.png" alt="Maven" title="Maven"/>
	<img width="60" src="https://user-images.githubusercontent.com/25181517/183891303-41f257f8-6b3d-487c-aa56-c497b880d0fb.png" alt="Spring Boot" title="Spring Boot"/>
	<img width="60" src="https://user-images.githubusercontent.com/25181517/117208740-bfb78400-adf5-11eb-97bb-09072b6bedfc.png" alt="PostgreSQL" title="PostgreSQL"/>
	<img width="60" src="https://user-images.githubusercontent.com/25181517/117207330-263ba280-adf4-11eb-9b97-0ac5b40bc3be.png" alt="Docker" title="Docker"/>
	<img width="60" src="https://user-images.githubusercontent.com/25181517/117533873-484d4480-afef-11eb-9fad-67c8605e3592.png" alt="JUnit" title="JUnit"/>
  <img width="60" src="https://user-images.githubusercontent.com/25181517/184097317-690eea12-3a26-4f7c-8521-729ebbbb3f98.png" alt="Testcontainers" title="Testcontainers"/>
</div>

## Architecture

The Enterprise Assistant backend is structured as a modular application, each module encapsulating its business logic within a well-defined domain context. This modularity enables separation of concerns and high cohesion while maintaining a clean separation between the system's business rules and its technical implementation.

### Modular Design

Each module in the system corresponds to a core business capability and is structured as follows:

- `domain` - Contains the core business logic and entities.
- `facade` - Implements the Facade design pattern to provide a simple interface to complex subsystems in the domain layer.
- `dto` - Data Transfer Objects (DTOs) serve as a model for the data being transferred between the client and server. 
          They are designed to carry data between processes in order to separate the internal representation of information from the data that's transmitted.
- `exception` - This package contains custom exceptions specific to the domain logic of the application. 
- `controller` - Handles HTTP requests and responses, utilizing facades to interact with the domain logic.

### Business Logic Encapsulation

The domain layer is the heart of the business logic, with each domain entity accompanied by a dedicated repository and optional validators to enforce business rules. The use of facades abstracts the complexity of direct interactions with the domain layer, allowing for more maintainable and testable code.

### Facade Pattern

The facade pattern is instrumental in hiding the complexity of interactions within the domain layer and providing a unified interface to the controllers. It reduces the dependencies between the system's components and promotes a more decoupled architecture.

By adhering to these architectural principles, Enterprise Assistant ensures that the application remains scalable, maintainable, and adaptable to changing business requirements.

## Usage

The system provides a variety of endpoints for managing the business logic:

### Product Controller

- `GET /products` - Retrieves all products from the database.
- `POST /products` - Creates a new product with the provided data.
- `PUT /products/{id}` - Updates the details of a specific product identified by its ID.
- `DELETE /products/{id}` - Deletes a specific product from the database using its ID.
- `GET /products/gtin/{productGtin}` - Fetches a product by its GTIN (Global Trade Item Number).
- `GET /products/name/{productName}` - Obtains a product's details by its name.

### Service Controller

- `GET /services` - Retrieves a list of all services.
- `POST /services` - Adds a new service to the system.
- `PUT /services/{id}` - Updates the information for an existing service by its ID.
- `DELETE /services/{id}` - Removes a service from the database by its ID.
- `GET /services/name/{serviceName}` - Finds a service by its name.

### Login and Register Controller

- `POST /auth/login` - Allows user authentication and login.
- `POST /auth/register` - Registers a new user account.
- `POST /auth/register-admin` - Registers a new admin account (for testing purposes only).

### User Controller

- `GET /users` - Reads and lists all user accounts.
- `DELETE /users/{id}` - Deletes a specific user account by ID.
- `PATCH /users/enable/{id}` - Updates the active status of a user's account.
- `GET /users/user` - Retrieves data of the currently logged-in user.

### Invoice Controller

- `GET /invoices` - Retrieves all invoices.
- `GET /invoices/{id}` - Fetches a specific invoice by its identifier.
- `POST /invoices/create/{id}` - Creates a new invoice for a given ID.
- `GET /invoices/download/{id}` - Downloads an invoice as a PDF file.
- `POST /invoices/send/{id}` - Sends an invoice to a client's email address.

### Order Controller

- `GET /orders` - Retrieves all orders.
- `POST /orders` - Creates a new order.
- `GET /orders/{id}` - Fetches details of a specific order by its ID.
- `DELETE /orders/{id}` - Deletes an order by its ID.
- `PATCH /orders/status/{id}` - Updates the status of an order.
- `GET /orders/year` - Gets orders made in a specific year.

### Client Controller

- `GET /clients` - Retrieves all client records.
- `POST /clients` - Creates a new client entry.
- `DELETE /clients/{id}` - Deletes a client's record using their ID.
- `PUT /clients/address/{id}` - Updates the address information for a client.
- `GET /clients/byName/{companyName}` - Retrieves a client's details by their company name.
- `PUT /clients/client/{id}` - Updates the client information.
- `PUT /clients/representative/{id}` - Updates the representative information for a client's company.


