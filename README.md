i# Friend System API with Autocomplete and JWT Authentication

This Spring Boot project implements a comprehensive friend system API that includes features like friend requests, managing friendships, and user authentication with JWT. Additionally, it offers an autocomplete feature using the Trie data structure.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [API Endpoints](#api-endpoints)
- [JWT Authentication](#jwt-authentication)
- [Autocomplete Feature](#autocomplete-feature)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Friend System API**: Send, accept, and manage friend requests.
- **User Management**: Create and log in users with secure authentication.
- **JWT Implementation**: Secure API endpoints using JSON Web Tokens.
- **Autocomplete**: Implement a Trie-based autocomplete feature for fast search suggestions.

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven or Gradle
- MySQL or any other relational database

### Installation

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/friend-system-api.git
   cd friend-system-api
   ```
2. **Configure the databases:**
    spring.datasource.url=jdbc:mysql://localhost:3306/friend_system_db
    spring.datasource.username=yourusername
    spring.datasource.password=yourpassword

3. **Build the project:**
    mvn clean install

## API Endpoints

#### POST Login user
http://localhost:8080/auth/login

Body 
```json
    {
        "email": "sah123@gmail.com",
        "password": "qwerty"
    }
```
This is a POST request, submitting data to an API via the request body. This request submits JSON data, and the data is reflected in the response.
A successful POST request typically returns a 200 OK or 201 Created response code. Returns JWT Token


#### POST Signup user

http://localhost:8080/auth/signup

```json
    {
      "name": "Rajpal raina",
      "password": "qwerty",
      "confirmPass": "qwerty",
      "email": "test9@gmail.com",
      "agreeterms":true
   }
```
Creating new user , Returns JWT token if user successfully created.

#### GET UserList
http://localhost:8080/api/search?q=Jo&page=0&size=10

This API returns list of users whose name starts with Jo , pagination is implemented (Backend calls DB each time for this request).

**JWT Token needs to be added.**

#### GET Autocomplete Request
http://localhost:8080/api/search/ta?q=raj

Returns list of users whose name starts with raj (It use Trie,backend do not call db each time for this request , Inmemory Data Structure is used to fulfill this request).
Scheduler is running which recreate the trie object to cater new created users.

**JWT Token needs to be added.**

Response JSON
```json
        [
            {
                "id": 0,
                "name": "Rajesh",
                "searchUrl": "http:/localhost:8080/api/search?q=raj&page=0&size=10"
            },
            {
                "id": 1,
                "name": "Rajpal Raina",
                "searchUrl": "http:/localhost:8080/api/search?q=raj&page=0&size=10"
            },
            {
                "id": 2,
                "name": "Raju",
                "searchUrl": "http:/localhost:8080/api/search?q=raj&page=0&size=10"
            }
        ]
```

#### POST Friend Request
http://localhost:8080/api/freq

Sending friend request to user using receiver id.

Sent JSON
```json
    {
         "receiverId": "34"
    }
```

#### PATCH Response To FriendRequest
http://localhost:8080/api/freq?userId=34&action=ACCEPT

Respond to friend request , return BAD REQUEST if action not allowed

action param accept (ACCEPT OR DENY)
userId param represent id of the user who sent the request.

#### GET Get friend req sent
http://localhost:8080/api/freq?sent=true&page=0&size=10

Return list of friend request sent by the logged user

#### GET Get friend list
http://localhost:8080/api/flist?page=0&size=10

Return list of friend of logged user
