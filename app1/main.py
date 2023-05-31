from fastapi import FastAPI
import os


app = FastAPI(root_path = "/app1")

@app.get("/hello")
async def hello():
    return {"message": "Hello from App 1!"}
