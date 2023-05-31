from fastapi import FastAPI
import os


app = FastAPI(root_path = "/app2")

@app.get("/hello")
async def hello():
    return {"message": "Hello from App 2!"}
