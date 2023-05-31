from typing import List, Optional

import uvicorn
from fastapi import FastAPI, Depends, Query, Body, Request, HTTPException
from fastapi.responses import JSONResponse
from pydantic import SecretStr
from starlette.responses import RedirectResponse

from fastapi_keycloak import (
    FastAPIKeycloak,
    OIDCUser,
    UsernamePassword,
    HTTPMethod,
    KeycloakUser,
    KeycloakGroup,
    KeycloakError
)
from fastapi_keycloak.model import KeycloakCreateUser, KeycloakToken

idp = FastAPIKeycloak(
    server_url="http://192.168.0.108:8085/auth",
    client_id="soa-client",
    client_secret="GzgACcJzhzQ4j8kWhmhazt7WSdxDVUyE",
    admin_client_secret="BIcczGsZ6I8W5zf0rZg5qSexlloQLPKB",
    realm="SOA",
    callback_uri="http://localhost:8081/callback"
)
app = FastAPI()
idp.add_swagger_config(app)


@app.get("/")  # Unprotected
def root():
    return RedirectResponse(idp.login_uri)


# Custom error handler for showing Keycloak errors on FastAPI
@app.exception_handler(KeycloakError)
async def keycloak_exception_handler(request: Request, exc: KeycloakError):
    return JSONResponse(
        status_code=exc.status_code,
        content={"message": exc.reason},
    )


# Admin
@app.get("/identity-providers", tags=["admin-cli"])
def get_identity_providers():
    return idp.get_identity_providers()


@app.get("/idp-configuration", tags=["admin-cli"])
def get_idp_config():
    return idp.open_id_configuration


# User Management

@app.get("/users", tags=["user-management"])
def get_users(user: OIDCUser = Depends(idp.get_current_user())):
    return idp.get_all_users()


@app.get("/user", tags=["user-management"])
def get_user_by_query(query: str = None, user: OIDCUser = Depends(idp.get_current_user())):
    return idp.get_user(query=query)


@app.post("/users", tags=["user-management"])
def create_user(
        data: KeycloakCreateUser
):
    # TODO
    return idp.create_user(
        first_name=data.first_name,
        last_name=data.last_name,
        username=data.email,
        email=data.email,
        password=data.password.get_secret_value(),
        enabled=True,
        email_verified=True,
    )


@app.get("/user/{user_id}", tags=["user-management"])
def get_user(user_id: str = None, user: OIDCUser = Depends(idp.get_current_user())):
    return idp.get_user(user_id=user_id)


@app.put("/user", tags=["user-management"])
def update_user(user: KeycloakUser):
    return idp.update_user(user=user)


@app.delete("/user/{user_id}", tags=["user-management"])
def delete_user(user_id: str):
    return idp.delete_user(user_id=user_id)


@app.put("/user/{user_id}/change-password", tags=["user-management"])
def change_password(user_id: str, new_password: SecretStr):
    return idp.change_password(user_id=user_id, new_password=new_password)


@app.put("/user/{user_id}/send-email-verification", tags=["user-management"])
def send_email_verification(user_id: str):
    return idp.send_email_verification(user_id=user_id)


# Role Management

@app.get("/roles", tags=["role-management"])
def get_all_roles(user: OIDCUser = Depends(idp.get_current_user())):
    return idp.get_all_roles()


@app.get("/role/{role_name}", tags=["role-management"])
def get_role(role_name: str, user: OIDCUser = Depends(idp.get_current_user())):
    return idp.get_roles([role_name])


@app.post("/roles", tags=["role-management"])
def add_role(role_name: str, user: OIDCUser = Depends(idp.get_current_user())):
    return idp.create_role(role_name=role_name)


@app.delete("/roles", tags=["role-management"])
def delete_roles(role_name: str, user: OIDCUser = Depends(idp.get_current_user())):
    return idp.delete_role(role_name=role_name)


# User Roles

@app.post("/users/{user_id}/roles", tags=["user-roles"])
def add_roles_to_user(user_id: str, roles: Optional[List[str]] = Query(None),
                      user: OIDCUser = Depends(idp.get_current_user())):
    return idp.add_user_roles(user_id=user_id, roles=roles)


@app.get("/users/{user_id}/roles", tags=["user-roles"])
def get_user_roles(user_id: str, user: OIDCUser = Depends(idp.get_current_user())):
    return idp.get_user_roles(user_id=user_id)


@app.delete("/users/{user_id}/roles", tags=["user-roles"])
def delete_roles_from_user(user_id: str, roles: Optional[List[str]] = Query(None),
                           user: OIDCUser = Depends(idp.get_current_user())):
    return idp.remove_user_roles(user_id=user_id, roles=roles)


# Auth Flow

@app.get("/login", tags=["auth-flow"])
def login_redirect():
    return RedirectResponse(idp.login_uri)


@app.get("/callback", tags=["auth-flow"])
def callback(session_state: str, code: str):
    return idp.exchange_authorization_code(session_state=session_state, code=code)


@app.get("/admin", tags=["example-user-request"])
def company_admin(user: OIDCUser = Depends(idp.get_current_user(required_roles=["admin"]))):
    return f"Hi admin {user}"


@app.post("/login", tags=["example-user-request"])
def login(user: UsernamePassword = Body(...)):
    return idp.user_login(
        username=user.username, password=user.password.get_secret_value()
    )


@app.get("/logout", tags=["auth-flow"])
def logout():
    return RedirectResponse(idp.logout_uri)


if __name__ == '__main__':
    uvicorn.run('app:app', host="0.0.0.0", port=8081)
