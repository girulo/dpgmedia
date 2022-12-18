# DPG Media Coding Test
- This is the README file of a coding test for DPG Media

## Design Choices
- I have chosen for controller-service-model layout since i think is quite a good standard and everything gets organized and easy to read and understand  


- Decided to use Rest Template since it comes with Spring by default, however i have decided to use a custom RestTemplate Bean where I have set up the **_GOT main url_**, and a timeout of **_10 seconds_**. The main GOT url is given by **application.properties** file, however it can be externalized, and the app could use spring cloud to fetch it, but to make it easier to set up I have decided to place it there and not start a spring cloud server to fetch the file for example from GitHub.  


- I have as well decided to return my own and custom errors and exception in the API. This means that the app will return a custom json with an errorId and an error message when something in the API fails instead of returning the default JVM Exception. The Exception will be printed in the app logs, but the API response is customized


- I have decided to do testing only in the GotService. The reason is that the Controller delegates in the service and only does 2 things: validate api version and return the date with and http status wrapeed into a ResponseEntity, quite a standard thing nowadays. So saying that I have considered that is not needed to test the Controller status responses


##Extras thing done or half-done or ideas.

- I have implemented a kubernetes profile in the pom. With that profile we could compile the app, create a docker image out of it and upload it to dockerhub, as well as create a helm chart and upload it to our chartmuseum repository. In that way we will be ready to deploy easily in kubernetes that can be spin up with a few clicks in AWS, Google, Azure or ouw own k8s cluster

- I have as well include jenkins file for release and snapshot. Those Jenkins file define the stages that Jenkins will do in order to release a service (includes upload the jar, upload the docker image and upload the helm chart) and the same for creating a snapshot. We can also make Jenkins to deploy services, but we will have to set it up to have access to the environment and that can vary regarding the network rules, so I have left it for other moment. Anyway adding a new stage after the creation of the chart with the command helm upgrade "serviceName" chartmuseum/serviceName -f valuesFile.yaml will be more than enough

- I have implemented validation for API in the Controller forcing it to be from 0 till 2 since I have realized that the GOT API have them available



