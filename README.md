# Nginx and Websocket on HTTPS

> I used source files for websocket server and client application from http://www.devglan.com/spring-boot/spring-boot-angular-websocket and I just amended a little for HTTPS. I deployed an Nginx and the SpringBoot application on Docker Container.
 

---
## Introduction

This sample has three components
- Spring Boot Application(WebSocket Server)
- Nginx (SSL configured)
- Angular JS Application(WebSocket Client)

![docs/images/introduction.png](docs/images/introduction.png)

---
### Implementation

![docs/images/implementation.png](docs/images/implementation.png)

---
## How to set up 

```sh
$ git clone https://github.com/credemol/websocket-https-nginx-springboot.git
$ cd websocket-https-nginx-springboot
```

---
### Build WebSocket Server(Spring Boot)

```sh
$ cd websocket
$ gradle clean build

# make sure that websocket-0.0.1-SNAPSHOT.jar has been created
$ ls build/libs/websocket-0.0.1-SNAPSHOT.jar

```

---
### WebSocket Server

- [WebSocketConfig.java](websocket/src/main/java/com/iclinicemr/sample/websocket/config/WebSocketConfig.java)
- [WebSocketController.java](websocket/src/main/java/com/iclinicemr/sample/websocket/controller/WebSocketController.java)
- [HelloController.java](websocket/src/main/java/com/iclinicemr/sample/websocket/controller/HelloController.java)
- [build.gradle](websocket/build.gradle)
- [Dockerfile](docker/websocket-server/Dockerfile)

---
### Nginx

- [nginx.conf](docker/nginx/nginx.conf)
- [Dockerfile](docker/nginx/Dockerfile)

---
#### nginx.conf

```conf
upstream websocket-server {
	server websocket-app:8080 weight=1;
}

server {
#    listen              80 default_server;
    listen              [::]:443 ssl;
    listen              443 ssl;
    ssl_verify_client off;

    ssl_certificate /etc/nginx/certs/nginx-selfsigned.crt;
    ssl_certificate_key /etc/nginx/certs/nginx-selfsigned.key;

    ssl on;
    ssl_session_cache  builtin:1000  shared:SSL:10m;
    ssl_protocols  TLSv1 TLSv1.1 TLSv1.2;
    ssl_ciphers HIGH:!aNULL:!eNULL:!EXPORT:!CAMELLIA:!DES:!MD5:!PSK:!RC4;
    ssl_prefer_server_ciphers on;

    location / {
        proxy_pass http://websocket-server;
        proxy_http_version 1.1;
        proxy_set_header Upgrade $http_upgrade;
        proxy_set_header Connection "Upgrade";        
    }
}
```

---
### Web Socket Endpoint 

```javascript
let socket = new WebSocket("wss://localhost/greeting");
```
[angular-5-websocket/src/app/app.component.ts](angular-5-websocket/src/app/app.component.ts#L27)

---
### Dockerize Nginx and Spring Boot application

```sh
$ docker-compose build
$ docker-compose up

# open a new terminal window to see two containers are running
$ docker container ls

# make a request to check if you can call an API through HTTPS
$ curl -k https://localhost/hello
```

---
### Run Angular JS client

```sh
$ cd angular-5-websocket/
$ npm install
$ ng serve

$ open http://localhost:4200
```

---
### Web Socket Test

![docs/images/websocket-connect.png](docs/images/websocket-connect.png)

![docs/images/websocket-send.png](docs/images/websocket-send.png)


---
### WebBrowser-Certificate Management

If your browser has a problem connecting to Nginx server, please check your certificate is self signed, If then, you should add this certificate as an exception like below. 

![docs/images/firefox-certificate-1.png](docs/images/firefox-certificate-1.png)

![docs/images/firefox-certificate-2.png](docs/images/firefox-certificate-2.png)

---
## Resources

- [https://www.digitalocean.com/community/tutorials/how-to-create-a-self-signed-ssl-certificate-for-nginx-in-ubuntu-16-04](https://www.digitalocean.com/community/tutorials/how-to-create-a-self-signed-ssl-certificate-for-nginx-in-ubuntu-16-04)

- [https://github.com/only2dhir/angular-5-websocket](https://github.com/only2dhir/angular-5-websocket)

- [http://www.devglan.com/spring-boot/spring-boot-angular-websocket](http://www.devglan.com/spring-boot/spring-boot-angular-websocket)

---
## Appendix

- Create the SSL Certificate

---
### Create the SSL Certificate

```sh
$ mkdir -p ./ssl/private
$ mkdir -p ./ssl/certs

$ openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout ./ssl/private/qc1.iclinicemr.com.key -out ./ssl/certs/qc1.iclinicemr.com.crt

Country Name (2 letter code) []:CA
State or Province Name (full name) []:British Columbia
Locality Name (eg, city) []:Vancouver
Organization Name (eg, company) []:iclinic
Organizational Unit Name (eg, section) []:Dev
Common Name (eg, fully qualified host name) []:qc1.iclinicemr.com
Email Address []:credemol@gmail.com
```

---
### create a strong Diffie-Hellman group
**_(Deprecated)_**

```sh
$ openssl dhparam -out ./ssl/certs/dhparam.pem 2048

This is going to take a long time
```