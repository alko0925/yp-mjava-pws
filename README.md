### sprint3-my-blog-app
Practical work for yp - sprint 3.

1. Для сборки выполнить: **mvn clean package**
2. Результирующий **api.war** будет доступен здесь: **${project.basedir}/docker-compose/tomcat**
3. Для деплоя выполнить: **docker compose -p "sprin3-pw" up**
   В результате будет развернуто 4 сервиса:
   
   -**yp-db** - это PostgreSQL, connection к бд можно в docker-compose.yaml посмотреть как и все остальные параметры..
    Доступен во внутр сети докера по yp-db:5434
    И внешне по: localhost:5434

   -**yp-pgadmin** - UI для PostgreSQL
   Внешний доступ: localhost/pgadmin4 (прокси через yp-gw), креды в UI смотрим в docker-compose.yaml
   
   -**yp-tomcat** - tomcat 11.0.13 с установленным api.war
   Внешний доступ: localhost:8080
   
   -**yp-gw** - nginx + front-app
   Внешний досуп: localhost
   
### После деплоя можно проверять функциональность:
   
[!screenshot 1](https://github.com/alko0925/yp-mjava-pws/tree/module_one_sprint_three_branch/images/s3_pw_1.png)   
[!screenshot 2](https://github.com/alko0925/yp-mjava-pws/tree/module_one_sprint_three_branch/images/s3_pw_2.png)   
[!screenshot 3](https://github.com/alko0925/yp-mjava-pws/tree/module_one_sprint_three_branch/images/s3_pw_3.png)   
[!screenshot 4](https://github.com/alko0925/yp-mjava-pws/tree/module_one_sprint_three_branch/images/s3_pw_4.png)   
[!screenshot 5](https://github.com/alko0925/yp-mjava-pws/tree/module_one_sprint_three_branch/images/s3_pw_5.png)   
[!screenshot 6](https://github.com/alko0925/yp-mjava-pws/tree/module_one_sprint_three_branch/images/s3_pw_6.png)   
   
### Доработки на которые не хватило времени, хотел уложиться в мягкий дедлайн, доделаю в рамках след спринтов:
1. Из явного: H2PostRepository VS PostgreSQLPostRepository - функционал схож, нарушаем принцип DRY -> вынести общее на уровени абстракции.
2. application.properties - тут боролся с томкат-ом, приложение не мачит переменные среды на проперти в файле.. -> как минимум добавить шифрование.
