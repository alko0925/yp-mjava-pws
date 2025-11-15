### sprint4-my-blog-app
Practical work for yp - sprint 4.

1. Для сборки выполнить: **gradle bootJar**
2. Результирующий **yp-mjava-pws-0.0.1-SNAPSHOT.jar** будет доступен здесь: **${project.basedir}/docker-compose/back-app**
3. Для деплоя выполнить: **docker compose -p "sprin4-pw" up**
   В результате будет развернуто 4 сервиса:
   
   -**yp-db** - это PostgreSQL, connection к бд можно в docker-compose.yaml посмотреть как и все остальные параметры.. Доступен во внутр сети докера по **yp-db:5432** и внешне по: **localhost:5432**

   -**yp-pgadmin** - UI для PostgreSQL. Внешний доступ: **localhost/pgadmin4** (прокси через yp-gw), креды в UI смотрим в docker-compose.yaml
   
   -**yp-app** - само backend приложение. Внешний доступ: **localhost:8080**.
   
   -**yp-gw** - nginx + front-app. Внешний досуп: **localhost**.
   
### После деплоя можно проверять функциональность:
   
[!screenshot 1](https://github.com/alko0925/yp-mjava-pws/tree/module_one_sprint_three_branch/images/s4_pw_1.png)   
[!screenshot 2](https://github.com/alko0925/yp-mjava-pws/tree/module_one_sprint_three_branch/images/s4_pw_2.png)   
[!screenshot 3](https://github.com/alko0925/yp-mjava-pws/tree/module_one_sprint_three_branch/images/s4_pw_3.png)   
[!screenshot 4](https://github.com/alko0925/yp-mjava-pws/tree/module_one_sprint_three_branch/images/s4_pw_4.png)   
[!screenshot 5](https://github.com/alko0925/yp-mjava-pws/tree/module_one_sprint_three_branch/images/s4_pw_5.png)   
[!screenshot 6](https://github.com/alko0925/yp-mjava-pws/tree/module_one_sprint_three_branch/images/s4_pw_6.png)   
   
### Правки проблем 3го спринта:
1. Из явного: H2PostRepository VS PostgreSQLPostRepository - функционал схож, нарушаем принцип DRY -> вынести общее на уровени абстракции.
   Поправил: теперь есть общий JdbcPostRepository - инициализация нужного драйвера и схемы настроена по профилю.
   
2. application.properties - тут боролся с томкат-ом, приложение не мачит переменные среды на проперти в файле.. -> как минимум добавить шифрование.
   Поправил: изменил формат на YAML + значения конекшина к базе формируются из значений переменных окружения
   
3. Комментарий от ревьюера - что в репозитории использовал конкатинацию для построения sql запроса и подстановки параметров что потенциально ведет к SQL injection vulnerability.
   Поправил: использовал подход с параметризованными запросами.
