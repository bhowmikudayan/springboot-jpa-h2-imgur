github url:



Spring Boot REST app is created that exposes endpoints to
• Register a User with basic information, username, and password

below is for registration , so no authentication needed.

http://localhost:8080/api/v1/register

payload : {  
"firstName": "udayan1",
"lastName": "bhowmik1",
"emailId": "bhowmik.udayan@gmail1.com",
"userName" : "bhowmik_udayan",
"password": "password"
}

Id is auto incremented.


• Upload, view and delete images after authorizing the username/password.

image can be uploaded from postman by attaching some image, the api internally will cal imgur upload api to upload into imgur, image url will be updated along with employee

Security: basic authentication to be provided while upload, view, delete
id/pass : udayan/password

while uploading into imgur, oauth2 is configured as per instruction
CLINET ID  7952f282cc3e64e

http://localhost:8080/api/v1/uploadImage/3

when image will be uploaded , url will be updated in the employee table.(here id = 3)

• Associate the updated list of images with the user profile
• View the User Basic Information and the Images
imgur api is integrated with the Spring Boot APP to upload, view and delete the images
https://apidocs.imgur.com/



App Requirements
• H2 (In-memory database) and JPA are used to store the user information with user name and password, retrieve the user name and password to authenticate the user
• Integrated with imgur’s API to upload, view and delete images. 


oAuth2 is implenented and imgur is used as authentication server.

Rate limit:
Optimized API for 100K requests per minute.

kafka: created producer and consumer of kafka to produce and consume massages.
kafka cluster : locally created.


