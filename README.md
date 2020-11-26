# Assessment
### Lucky Shop API

This project includes API for calculating bill with total and discount amounts by applying pre-defined discount rules to the request.
There is 1 endpoint in the API;

-   POST /bills

To create an executable jar and run the application you can follow below steps instead of using IDE;

- You need to create executable jar first with; 

    `./gradlew clean build`
    
- You can run the application with;

    `java -jar -DDB_URL={host_url} -DDB_PORT={postgre_db_port} -DDB_NAME={name_of_the_db_to_be_used} -DDB_USERNAME={db_user_name} -DDB_PASSWORD={db_user_password} build/libs/lucky-shop.jar`

Note: You must provide below parameters for connect to database.

    - DB_URL (default value is 'localhost')
    - DB_PORT (default value is '5432')
    - DB_NAME (default value is 'ls')
    - DB_USERNAME (default value is 'ls')
    - DB_PASSWORD (default value is 'ls')
You can execute docker-compose.yaml to initialize PostgreSQL db and pg-admin on your local.
-   To execute docker-compose.yaml;
    
    `docker-compose up -d`
    
-   To stop resources initialised with docker-compose.yaml file
    
    `docker-compose stop`

-   To delete containers and network initialised with docker-compose.yaml file
    
    `docker-compose down`    

-   To remove resources initialised with docker-compose.yaml file
    
    `docker-compose down -v --rmi all --remove-orphan`    

---- 
- Here is a health check url to be sure that app is up and running
 
    `http://localhost:8080/actuator/health`

- You can check API documentation, make requests and play with APIs on swagger with url below;
    
    `http://localhost:8080/swagger-ui.html` 
     
- If you want to check code coverage report, you need to run below command
    
    `./gradlew clean build jacocoTestReport`
    
    you will find reports under `build/jacocoHtml/index.html`
    
- I added an auto-generated UML diagram under the root directory with the name `Class_Diagram.png`.