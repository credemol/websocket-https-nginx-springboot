# Nginx and Websocket

---
## Resources

- [https://www.digitalocean.com/community/tutorials/how-to-create-a-self-signed-ssl-certificate-for-nginx-in-ubuntu-16-04](https://www.digitalocean.com/community/tutorials/how-to-create-a-self-signed-ssl-certificate-for-nginx-in-ubuntu-16-04)

---
## Set up Nginx

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

```sh
$ openssl dhparam -out ./ssl/certs/dhparam.pem 2048

This is going to take a long time
```