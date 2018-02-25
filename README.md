# Twitter project
 Basic implementation of some twitter functions, such as pushing messages, following users, getting live messages

## Getting Started

This application is based on spring boot 2.0.
Default 8080 port is user for application server
You can access application under url
```
http://localhost:8080/
```

Swagger is under:
```
http://localhost:8080/swagger-ui.html
```
You should always specify "current" user in URL, cause spring security is not enabled.
Basically that is why user/currentUser is present in URL

### Prerequisites
Maven to build a project


## Test Scenarios
### Get live messages
1. User A push some tweet

```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d 'some text 2' 'http://localhost:8080/api/v1/users/A/tweets'
```
2. User B push some tweet

```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d 'some text 2' 'http://localhost:8080/api/v1/users/B/tweets'
```
3. User A follow B

```
curl -X POST --header 'Content-Type: application/json' --header 'Accept: */*' -d '["B"]' 'http://localhost:8080/api/v1/users/A/followers'
```
4. Now we can get live messages in browser.
 Open in browser:
```
http://localhost:8080/api/v1/users/A/feed?live=true
```
*Note: due to swagger limitation, only one operation will be present based on url path and http method*

5. User B push new tweet (step 2), user A should get it in his feed (check tab in step 4)


