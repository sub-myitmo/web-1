# Запуск

поднимаем конфиг на гелиосе: httpd -f ~/web/web-1/httpd-root/conf/httpd.conf -k start

запускаем java сервер: java -DFCGI_PORT=14488 -jar ~/web/web-1/httpd-root/fcgi-bin/app.jar


пробрасываем порты на локаль и радуемся: ssh -p 2222 s409331@helios.cs.ifmo.ru -L 8080:localhost:11677

запускаем http://localhost:8080/

Если что-то меняем, то обязательно сначала стопаем: httpd -f ~/web/web-1/httpd-root/conf/httpd.conf -k stop

Если вы наплодили httpd, то пишем команду top (чтобы проверить это)

Чтобы их убить - killall httpd

Порты мои не пиздить!