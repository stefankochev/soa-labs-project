from fastapi import FastAPI
import os
PATH_ROUTE = os.environ.get("PATH_ROUTE", "")

app = FastAPI(path_route=PATH_ROUTE)

@app.get("/hello")
async def hello():
    return {"message": "Hello from App 1!"}
