FROM tiangolo/uvicorn-gunicorn-fastapi:python3.9

WORKDIR /app

COPY main.py .

RUN pip install fastapi uvicorn

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "80"]