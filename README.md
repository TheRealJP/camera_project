# Camera Project
started on 21 sept 18

## About
Spring boot Java project for school. 
Simulates incoming camera messages that are stored on a rabbitmq queue and handled by a service layer. This service layer checks if the camera picked up an emission violaton or a speeding violation. It makes a fine for both types of violations. The admin can view these violations and fines in a simple thymeleaf overview page.

## Architecture
![alt text](https://github.com/TheRealJonathanPeers/camera_project/blob/master/cameraproject_architectuur.png)

