<<<<<<< HEAD
## Service Oriented Architecture - Microservices - Labs Project

TBD
=======
# SOA API Gateway


## Prerequiste

Make sure [`Docker`]([http](https://www.docker.com/)) is installed and running

## Project structure
```
Kong/
├── app1/
│   ├── main.py
│   └── Dockerfile
├── app2/
│   ├── main.py
│   └── Dockerfile
├── docker-compose.yml
├── kong.yml
└── README.md
```
* `app1/` and `app2/` directories contain the source code and Dockerfiles for testing purposes.

* `docker-compose.yml` defines the services and their configurations, including the Kong API Gateway and your backend applications.

* `kong.yml` contains the Kong API Gateway configuration, including services and routes.

* `README.md` is a Markdown file that provides instructions and information about the project.

## Running

`docker compose up`

Launches 3 docker containers
* `kong` starts [`Kong API Gateway`](https://konghq.com/) with ports `8000` for the public API and `8001` for the Admin API
* `app1` starts [`FastAPI`](https://fastapi.tiangolo.com/) application with port `81`
* `app2` starts [`FastAPI`](https://fastapi.tiangolo.com/) application with port `80`

## Accessing The Endpoints

To access the endpoints of your backend applications through Kong API Gateway, use the following URLs:

* `http://localhost:8000/app1/hello`: Accesses the `/hello` endpoint of app1.
* `http://localhost:8000/app2/hello`: Accesses the `/hello` endpoint of app2.

## Troubleshooting

* If you encounter any issues, check the Kong API Gateway logs by running the following command:
* `docker compose logs`
* Ensure that your backend applications are running and accessible directly without going through Kong. You can test accessing the endpoints of your applications directly using their respective URLs.

>>>>>>> master
