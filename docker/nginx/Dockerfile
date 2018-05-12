FROM nginx
RUN rm /etc/nginx/conf.d/default.conf
COPY ./docker/nginx/wait-for-it.sh /etc/nginx/wait-for-it.sh
RUN chmod a+x /etc/nginx/wait-for-it.sh
COPY ./docker/nginx/nginx.conf /etc/nginx/conf.d/default.conf
COPY ./ssl/certs/nginx-selfsigned.crt /etc/nginx/certs/nginx-selfsigned.crt
COPY ./ssl/private/nginx-selfsigned.key /etc/nginx/certs/nginx-selfsigned.key