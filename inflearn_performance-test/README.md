# shorten-url-initial
단축 URL 서비스 강의 시작 상태

아래의 도커 명령어를 실행해서 실습한다.
```
docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=somepassword -e MYSQL_DATABASE=shortenurl -p 3306:3306 -d mysql:latest
```
