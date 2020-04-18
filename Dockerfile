FROM openjdk:8
ADD target/bgrima-web-app.war bgrima-web-app.war
ADD target/classes/words words/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "bgrima-web-app.war"]