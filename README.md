This is a Java Spring Boot application using Maven as the build tool.
Base requirements to run this is Java 9 and Maven (any newer version), and npm and/or yarn if you want to run it that way. 
You can use the command line to build, run tests, and start the server using yarn, npm and/or maven
<br/><br/>
To build: <br/>
npm run build  <br/>
yarn build <br/>
mvn clean install
<br/><br/>To execute the tests:<br/>
npm run test <br/>
yarn test <br/>
mvn test
<br/><br/>To start the application on a Tomcat server at localhost:8080 :<br/>
npm run start <br/>
yarn start <br/>
mvn spring-boot:run
<br/><br/>
The architecture my app is MVC using Spring with the entry points for a API service calls in
ZuoraController, which in turn invokes services that perform the Zuora Api calls.
I created fake data in the controller that normally should be passed in by the calling app
but due to time constraints I thought this was an acceptable alternative.
All the services use ZuoraService which handles the GET, PUT and POST requests 
with Oath2 Authorization if needed. The Oath token is reused until it expires.
I also have some basic error handling but nothing crazy. 

