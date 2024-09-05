# Food Ordering App

This project is a comprehensive food ordering platform where customers can browse restaurant menus, place orders, and choose their preferred payment methods. The application also includes features for restaurant owners and delivery partners, facilitating a smooth food ordering and delivery process.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Design Component](#design-components)
- [Future Enhancements](#future-enhancements)
- [Project Demo](#project-demo)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Customer Features:**
  - Browse restaurant menus
  - Place orders and select payment methods (including Cash on Delivery and wallet payments)
  - View order history and status
  - Add money to wallet (Admin Only)

- **Restaurant Features:**
  - Manage restaurant details and menu items
  - Receive notifications for new orders

- **Delivery Partner Features:**
  - View assigned deliveries
  - Update delivery status
  - Receive notifications for new deliveries

## Technologies Used

- **Backend:**
  - Java with Spring Boot
  - JPA for database interactions
  - PostGIS for spatial data handling

- **Database:**
  - PostgreSQL with PostGIS extension

- **Frontend:**
  - React (pending implementation)

- **APIs and Libraries:**
  - Swagger for API documentation
  - JavaMailSender for sending email notifications
  - Razorpay for payment gateway integration (pending implementation)

## Setup Instructions

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/Vinay-shaw-007/Zomato-Backend.git
   cd Zomato-Backend
   ```

2. **Configure Database:**
   - Set up PostgreSQL with PostGIS extension.
   - Update the `application.properties` or `application.yml` with your database credentials.

3. **Build and Run:**
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

4. **Swagger Documentation:**
   - Visit `http://localhost:8080/swagger-ui.html` to explore the API endpoints.

## API Endpoints

### Authentication

- **POST /auth/signup** - Create a new user account
- **POST /auth/login** - Authenticate user and return access and refresh tokens
- **POST /auth/refresh** - Refresh the access token using the refresh token stored in cookies
- **POST /auth/on-board-delivery-partner/user/{userId}** - Onboard a new delivery partner (Admin only)
- **POST /auth/on-board-restaurant-owner/user/{userId}** - Onboard a new restaurant owner (Admin only)
- **POST /auth/on-board-restaurant/restaurant-owner/{userId}** - Onboard a new restaurant under an existing restaurant owner (Admin only)

### Customer

- **POST /customer/restaurant/{restaurantId}** - Create an order from a specific restaurant
- **POST /customer/order/{orderId}** - Cancel an existing order
- **GET /customer/order/{orderId}** - Retrieve details of an existing order
- **PUT /customer/update-payment-method** - Update the payment method for the customer
- **POST /customer/review/restaurant/{restaurantId}** - Review a restaurant about its food items
- **PUT /customer/{customerId}/addMoney** - Add money to customer's wallet (Admin Only)

### Delivery Partner

- **POST /delivery-partner/delivery/{deliveryId}** - Update the status of a delivery
- **POST /delivery-partner/update-delivery-partner-location** - Update the current location of the delivery partner

### Restaurant

- **GET /restaurant/{restaurantId}** - Retrieve detailed information about a restaurant, including its menu items

### Restaurant Owner

- **POST /restaurant-owner/restaurant/{restaurantId}** - Add new menu items to a specific restaurant
- **POST /restaurant-owner/order/{orderId}** - Update the order status (e.g., preparing, ready for pickup)

### User

- **GET /user/{userId}** - Retrieve detailed information about a user (Admin only)

## Database Schema

- **Entities:**
  - CustomerEntity
  - OrderEntity
  - RestaurantEntity
  - DeliveryPartnerEntity
  - PaymentEntity
  - MenuItemEntity
  - PaymentEntity
  - RestaurantOwnerEntity
  - ReviewEntity
  - WalletEntity
  - UserEntity

- **Relationships:**
  - One-to-Many between Restaurant and MenuItems
  - Many-to-One between Order and Customer
  - Many-to-One between Order and Restaurant

## Design Components

- **You can view the design components for this application in the following PDF file. <!-- Add the correct path to the design file -->**
  - [Design Components PDF](https://drive.google.com/file/d/1yi92F_Bt3iQkrItWLIv9Fi26bkunds4A/view?usp=sharing) 
  - The PDF includes detailed designs for customer, restaurant, and delivery partner features.

## Future Enhancements

- **Frontend Implementation:**
  - Build a user-friendly interface with React.

- **Payment Gateway Integration:**
  - Complete Razorpay integration for online payments.

- **Event-Driven Notifications:**
  - Enhance notification service with real-time updates.

## Project Demo

- **Watch the demo of the Food Ordering App on YouTube:**
  - [Project Demo](https://youtu.be/TKZWyAXZeC8?si=od_mtrJAGDp8wA-x)
  - This video provides a walkthrough of the app, including customer, restaurant, and delivery partner features.


## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for review.

## License

This project is licensed under the MIT License. See the LICENSE file for details.