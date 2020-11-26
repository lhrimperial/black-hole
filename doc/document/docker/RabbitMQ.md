#### RabbitMQ

docker run -d --hostname my-rabbit --name rabbit -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin -p 15673:15672 -p 5673:5672 -p 25673:25672 -p 61614:61613 -p 1884:1883 rabbitmq:management



搭建集群

https://michael728.github.io/2019/06/07/docker-rabbitmq-env/

https://linbei.top/Docker%E6%90%AD%E5%BB%BARabbitMq%E9%9B%86%E7%BE%A4%E5%8F%8AHaProxy%E8%B4%9F%E8%BD%BD%E5%9D%87%E8%A1%A1/