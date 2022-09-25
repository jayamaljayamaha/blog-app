# blog-app

This is a REST api for manage the buisness related to blog posts.

Technologies used
* Java 17
* Springboot
* H2 database
* Gradle
* Docker

### How to run the API

I have added a docker file to the project, so only you need to eun the docker demon in your machine in order to run this, nothing more. 
There are two ways to run the docker image

1. You can run below command in order to create the image (Should run command within the project folder)
```
docker build -t blog-app-image:0.0.1 .
```
```
docker run -p 8080:8080 blog-app-image:0.0.1
```
Now the service is running on your machine on port 8080

2. I have directly uploaded the build image to docker hub, so you can download that by docker hub and run like follows
```
docker pull jayamaljayamaha/blog-app-image:0.0.1
```
```
docker run -p 8080:8080 jayamaljayamaha/blog-app-image:0.0.1
```
3. You can run it as a normal java project via your IDE, hust run the main class via IDE
4. You can run via gradle by executing folowwing gradle wrapper command
```
gradlew bootRun
```

Now the service is running on your machine on port 8080

### API Doc
I have used open API 3 swagger dpcumentation to document this API. So you can easily interact with the API using that document. below are some important points
* Requests are athenticated using bearer token
* So in order to access resources first you need to obtain a jwt token from auth end point in auth-controller
* Just send a request with default payload to that end point and then it will return back the jwt token
* After obtaining the token you need to authorize the api. In order to do that click the authroize button and paste the token then click authorize
* Now you are authorize to intaract with resources.

swagger API doc can be accessd via following after the service is up and running
```
http://localhost:8080/swagger-ui/index.html
```
