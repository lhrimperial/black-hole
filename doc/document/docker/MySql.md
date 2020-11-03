#### Docker MySql

docker pull mysql/mysql-server:5.7
docker run --name mysql -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -v /docker/mysql_data:/var/lib/mysql mysql/mysql-server:5.7

docker exec -it mysql bash

select host,user,plugin,authentication_string from mysql.user; 
grant all privileges on *.* to root@"%" identified by "123456" with grant option;

update user set host='%' where user ='root';



##### 挂载配置文件

docker run -d -p 3306:3306 --privileged=true -v /docker/mysql/conf/my.cnf:/etc/mysql/my.cnf -v /docker/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 --name mysql mysql/mysql-server:5.7



```shell
先将文件导入到容器
#docker cp **.sql 【容器名】:/root/
进入容器
#docker exec -ti 【容器名/ID】sh
将文件导入数据库
# mysql -uroot -p 【数据库名】 < ***.sql
```

Docker MySql中导入test_db

test_db打包tar->拷贝到docker容器->容器中解压->登陆mysql->执行 source path/*.sql