
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

image can be uploaded from postman by attaching some image, the api internally will cal imgur upload api to upload into imgur, image url will be updated along with employee id.

Security: Spring security for basic auth implented. basic authentication to be provided while upload, view, delete
id/pass : udayan/password

while uploading into imgur, oauth2 is configured as per instruction
CLINET ID  7952f282cc3e64e

http://localhost:8080/api/v1/uploadImage/3

when image will be uploaded , url will be updated in the employee table.(here id = 3)



imgur api is integrated with the Spring Boot APP to upload, view and delete the images.
basic user information can be viewed using rest api, image will be grabbed from imgur using image id.


• H2 (In-memory database) and JPA are used to store the user information with user name and password, retrieve the user name and password to authenticate the user
• Integrated with imgur’s API to upload, view and delete images. 


oAuth2 is implenented and imgur is used as authentication server.

Rate limit:
Bucket4j integrated for rate limit.


kafka: created producer and consumer of kafka to produce and consume massages.
kafka cluster : locally created.


postman collection below:

{
	"info": {
		"_postman_id": "219ad3e7-03ac-48f2-a419-10a24150ac49",
		"name": "synchronyTest",
		"schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json",
		"_exporter_id": "1445100"
	},
	"item": [
		{
			"name": "images",
			"protocolProfileBehavior": {
				"disableBodyPruning": true
			},
			"request": {
				"auth": {
					"type": "basic",
					"basic": [
						{
							"key": "password",
							"value": "password",
							"type": "string"
						},
						{
							"key": "username",
							"value": "udayan",
							"type": "string"
						}
					]
				},
				"method": "GET",
				"header": [
					{
						"warning": "This is a duplicate header and will be overridden by the Authorization header generated by Postman.",
						"key": "Authorization",
						"value": "basic user password",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\r\n    \"id\": 1,\r\n    \"firstName\": \"udayan\",\r\n    \"lastName\": \"bhowmik\",\r\n    \"emailId\": \"bhowmik.udayan@gmail.com\",\r\n    \"userName\":\"bhowmik_udayan\",\r\n    \"password\" :\"password123\"\r\n    \r\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8080/api/v1/images",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"v1",
						"images"
					]
				}
			},
			"response": []
		},
		{
			"name": "get one image with id",
			"protocolProfileBehavior": {
				"disableBodyPruning": true
			},
			"request": {
				"auth": {
					"type": "basic",
					"basic": [
						{
							"key": "password",
							"value": "password",
							"type": "string"
						},
						{
							"key": "username",
							"value": "udayan",
							"type": "string"
						}
					]
				},
				"method": "GET",
				"header": [
					{
						"warning": "This is a duplicate header and will be overridden by the Authorization header generated by Postman.",
						"key": "Authorization",
						"value": "basic user password",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\r\n    \"id\": 1,\r\n    \"firstName\": \"udayan\",\r\n    \"lastName\": \"bhowmik\",\r\n    \"emailId\": \"bhowmik.udayan@gmail.com\",\r\n    \"userName\":\"bhowmik_udayan\",\r\n    \"password\" :\"password123\"\r\n    \r\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8080/api/v1/image/rY6JNe8",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"v1",
						"image",
						"rY6JNe8"
					]
				}
			},
			"response": []
		},
		{
			"name": "greetings",
			"protocolProfileBehavior": {
				"disableBodyPruning": true
			},
			"request": {
				"auth": {
					"type": "basic",
					"basic": [
						{
							"key": "password",
							"value": "123",
							"type": "string"
						},
						{
							"key": "username",
							"value": "udayan",
							"type": "string"
						}
					]
				},
				"method": "GET",
				"header": [
					{
						"warning": "This is a duplicate header and will be overridden by the Authorization header generated by Postman.",
						"key": "Authorization",
						"value": "basic user password",
						"type": "text"
					}
				],
				"body": {
					"mode": "raw",
					"raw": "{\r\n    \"id\": 1,\r\n    \"firstName\": \"udayan\",\r\n    \"lastName\": \"bhowmik\",\r\n    \"emailId\": \"bhowmik.udayan@gmail.com\",\r\n    \"userName\":\"bhowmik_udayan\",\r\n    \"password\" :\"password123\"\r\n    \r\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8080/api/v1/greeting",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"v1",
						"greeting"
					]
				}
			},
			"response": []
		},
		{
			"name": "register",
			"request": {
				"auth": {
					"type": "basic",
					"basic": [
						{
							"key": "password",
							"value": "password",
							"type": "string"
						},
						{
							"key": "username",
							"value": "udayan",
							"type": "string"
						}
					]
				},
				"method": "POST",
				"header": [],
				"body": {
					"mode": "raw",
					"raw": "{\r\n    \"firstName\": \"udayan1\",\r\n    \"lastName\": \"bhowmik1\",\r\n    \"email\": \"bhowmik.udayan@gmail1.com\",\r\n    \"userName\" : \"bhowmik_udayan\",\r\n    \"password\": \"password\"\r\n}",
					"options": {
						"raw": {
							"language": "json"
						}
					}
				},
				"url": {
					"raw": "http://localhost:8080/api/v1/register",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"v1",
						"register"
					]
				}
			},
			"response": []
		},
		{
			"name": "upload",
			"protocolProfileBehavior": {
				"disabledSystemHeaders": {}
			},
			"request": {
				"auth": {
					"type": "basic",
					"basic": [
						{
							"key": "password",
							"value": "password",
							"type": "string"
						},
						{
							"key": "username",
							"value": "udayan",
							"type": "string"
						}
					]
				},
				"method": "POST",
				"header": [
					{
						"key": "Content-Type",
						"value": "multipart/form-data",
						"type": "text"
					}
				],
				"body": {
					"mode": "formdata",
					"formdata": [
						{
							"key": "file",
							"type": "file",
							"src": "/D:/Synchrony assignment/kingfisher-2046453_1280.jpg"
						},
						{
							"key": "",
							"value": "",
							"type": "text",
							"disabled": true
						}
					]
				},
				"url": {
					"raw": "http://localhost:8080/api/v1/uploadImage",
					"protocol": "http",
					"host": [
						"localhost"
					],
					"port": "8080",
					"path": [
						"api",
						"v1",
						"uploadImage"
					]
				}
			},
			"response": []
		}
	]
}
