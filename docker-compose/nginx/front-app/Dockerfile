FROM nginx
COPY ./dist /usr/share/nginx/html
COPY yp-nginx.conf /etc/nginx/conf.d/default.conf
CMD ["nginx", "-g", "daemon off;"]
