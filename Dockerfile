FROM openjdk:17-alpine
EXPOSE 8080
ADD build/libs/blog-application.jar blog-application.jar
ENTRYPOINT ["java", "-jar", "blog-application.jar"]