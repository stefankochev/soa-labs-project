# Service-oriented architecture (SOA) / Сервисно ориентирани архитектури

## OAuth2 with Keycloak and FastAPI 

The service allows clients to authenticate and authorize access to protected
resources using OAuth2 with Keycloak as the authorization server. It is built
using FastAPI, a modern, fast (high-performance) web framework for
building APIs.

### API Endpoints

### __default__
<code> GET / -> Root</code>

### __admin-cli__
<code> GET /identity-providers -> Get Identity Providers </code> <br />
<code> GET /idp-configuration -> Get Idp Config </code>

### __user-management__
<code> GET /users -> Get Users </code> <br />
<code> POST /users -> Create Users </code> <br />
<code> GET /user -> Get User By Query </code> <br />
<code> PUT /user -> Update User </code> <br />
<code> GET /user/{used_id} -> Get User </code> <br />
<code> DELETE /user/{user_id} -> Delete User </code> <br />
<code> PUT /user/{user_id}/change-password -> Change Password </code> <br />
<code> PUT /user/{user_id}/send-email-verification -> Send Email Verification </code>

### __role-management__
<code> GET /roles -> Get All Role </code> <br />
<code> POST /roles -> Add Role </code> <br />
<code> DELETE /roles -> Delete Roles </code> <br />
<code> GET /role/{role_name} -> Get Role </code>

### __user-roles__
<code> GET /users/{user_id}/roles -> Get User Roles </code> <br />
<code> POST /users/{user_id}/roles -> Add Roles To User </code> <br />
<code> DELETE /users/{user_id}/roles -> Delete Roles From User </code>

### __example-user-request__
<code> GET /admin -> Company Admin </code> <br />
<code> POST /login -> Login </code>

### __auth-flow__
<code> GET /login -> Login Redirect </code> <br />
<code> GET /callback -> Callback </code> <br />
<code> GET /logout -> Logout </code>

### Request/Response Formats:
- JSON

### Parameters and headers:
#### 4.1 Parameters
- Path Parameter: user_id (string)
#### 4.2 Headers
- Header: Authorization (Bearer token)

### Authentication:
- Use OAuth 2.0 with bearer tokens. Include the access token in the
Authorization header for every request

### Authorization:
- Different access levels are available for customers, administrators, and
support staff. The required access level is specified in the endpoint
documentation

### Demo version
![Image](https://cdn.discordapp.com/attachments/1089118686402580500/1112316668807495761/keycloak49.png)
