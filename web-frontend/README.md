**<h1>React Front-end Service with Keycloak utilization</h1>**

**<h2>About the service</h2>**

This is a Dockerized service utilizing Vite React to make the front-end part of a Web advertisement platform for apartments. Part of the group microservice-based project for the subject Service Oriented Architecture. Proudly made by students of the Faculty of Computer Science and Engineering: 

- Ivan Pavlovski 
- Blagoja Nikoloski 
- Petar Miladinov 
- Ljubomir Kolev 

It is currently designed to show its interaction with a configured Keycloak service, but can be upgraded to have extra functionalities in favor of the Web advertisement platform.

**<h2>Architecture Diagram</h2>**

Below is the structure of our service architecture. It uses Vite React running on Node.js server. For connection to the Keycloak service, it utilizes the react-keycloak package. The react-keycloak package is configured via environment variables and it manages the connection by itself. 

![](https://github.com/stefankochev/soa-labs-project/blob/web-frontend/web-frontend/diagrams/1.jpeg?raw=true)

***Diagram 1:*** *Relation of the front-end service with other entities* 

![](https://github.com/stefankochev/soa-labs-project/blob/web-frontend/web-frontend/diagrams/2.jpeg?raw=true)

***Diagram 2:*** *Details of operation of the Front-end service* 

**<h2>Service Setup</h2>**

**<h3>Prerequisites</h3>**
The steps below instruct you on how to setup the service as a standalone on a machine.

Before starting with the setup, make sure you have the following prerequisites: 

- **Node.js** installed on your system
- **Keycloak** service
- **Internet connection** to receive requests from users 

**<h3>Step 1: Clone the Repository</h3>**

Clone the repository using the following command: 
```console
$ git clone https://github.com/stefankochev/soa-labs-project.git --branch web-frontend
```

**<h3>Step 2: Install Dependencies</h3>**

Install the dependencies by entering the directory of the service and running the installation command: 

```console
$ cd web-frontend && npm install
```

**<h3>Step 3: Configure the Keycloak Configuration</h3>**

By default, there is a .env file which contains our Keycloak service configuration. It consists of the following parameters: 

- **VITE\_KEYCLOAK\_URL**
- **VITE\_KEYCLOAK\_REALM**
- **VITE\_KEYCLOAK\_CLIENTID**

If necessary, edit these parameters to your needs. 

**<h3>Step 4: Build the application</h3>**

To build the application, run the following command: 

```console
$ npm run build
```

**<h3>Step 5: Run the application</h3>**

Use the following command to run the built application:

```console
$ npm start
```

Check if the service is running at http://localhost/. If not, backtrack your steps and check if you did something wrong.

**<h2>Dockerization</h2>**

In the repository, there is a Dockerfile which we used in the production of this service. You can use the same or write your own. Just beware to use the node image as the base and import the necessary files (All the js, ts and json files, as well as the src and public directories) and expose the port for access to the React front-end (our project's default port is 80). 

After that, build the docker image with: 

```console
$ docker build -t <YOUR_IMAGE_NAME> .
```

**NOTE:** *Be sure that Docker Desktop is running in the background when running the command above. The docker daemon is now dependent on running the Docker Desktop.*

Use the following command to run a container with your Docker image: 

```console
$ docker run -p 80:<YOUR_PORT> <YOUR_IMAGE_NAME>
```

Check if the service is running at http://localhost/. If not, backtrack your steps and check if you did something wrong. 
