## Service Oriented Architecture - Microservices - Labs Project

### Notifications Service

To start the service run: `docker-compose up -d`

#### To test messaging:
```http
  GET http://localhost:8085/testMessaging
```
Then open: http://localhost:9000/topic/posts/messages \
Click on "View Messages" button and it will show the test messages, there will be as many as you send GET requests to the previous address.
