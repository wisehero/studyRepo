## Nginx 기본 개념

### Nginx를 왜 배우는 걸까?

Nginx는 다양한 기능을 가지고 있다.

- 정적 컨텐츠 제공
- SSL 처리
- 로드 밸런싱
- 장애 대응
- 캐싱
- 보안 처리(IP 차단, 요청 수 제한)


### Apache가 아닌 Nginx를 쓰는 이유?
간단하게 말하면, Nginx를 더 많이 사용하는 이유는 Apache에 비해 훨씬 많은 트래픽을 처리할 수 있는 구조를 가지고 있기 때문이다.  
https://www.youtube.com/watch?v=6FAwAXXj5N0&t=799s

---

## Nginx 다루기

### 아마존 리눅스에서 설치
```bash
# 패키지 리스트 최신화
sudo dnf update -y

# nginx 설치에 필요한 패키지 설치
sudo dnf install -y curl gnupg2 ca-certificates lsb-release

# nginx 공식 GPG 키 가져오기 및 등록
curl -fsSL https://nginx.org/keys/nginx_signing.key | sudo gpg --dearmor -o /etc/pki/rpm-gpg/nginx_signing_key.gpg

# nginx 저장소 추가
echo "[nginx-stable]
name=nginx stable repo
baseurl=http://nginx.org/packages/amazon/2023/\$basearch/
gpgcheck=1
enabled=1
gpgkey=file:///etc/pki/rpm-gpg/nginx_signing_key.gpg" | sudo tee /etc/yum.repos.d/nginx.repo

# 패키지 리스트 업데이트
sudo dnf update -y

# nginx 설치
sudo dnf install -y nginx

# nginx 서비스 실행 및 부팅 시 자동 시작 설정
sudo systemctl start nginx
sudo systemctl enable nginx
```

### 우분투에서 설치
```bash
# apt에서 설치 가능한 패키지 리스트(최신 패키지, 버전 등)를 최신화시킨다.
# apt는 리눅스에서 사용되는 소프트웨어 패키지를 설치 및 관리할 수 있게 도와주는 툴이다.
# npm 또는 gradle과 같은 패키지 관리 도구와 비슷하다고 생각하면 된다. 
$ sudo apt update

# nginx 설치에 필요한 라이브러리 설치
$ sudo apt install curl gnupg2 ca-certificates lsb-release ubuntu-keyring

# nginx 공식 패키지를 안전하게 설치하기 위한 보안 조치이다. 자세한 코드 의미는 몰라도 된다. 
# 다만, curl, gpg, tee, |, >, /dev/null, echo가 무슨 기능을 하는 명령어인지는 정리해두자. 
$ curl https://nginx.org/keys/nginx_signing.key | gpg --dearmor \
    | sudo tee /usr/share/keyrings/nginx-archive-keyring.gpg >/dev/null
$ gpg --dry-run --quiet --no-keyring --import --import-options import-show /usr/share/keyrings/nginx-archive-keyring.gpg
$ echo "deb [signed-by=/usr/share/keyrings/nginx-archive-keyring.gpg] \
http://nginx.org/packages/ubuntu `lsb_release -cs` nginx" \
    | sudo tee /etc/apt/sources.list.d/nginx.list

# nginx 설치
$ sudo apt update
$ sudo apt install nginx
```

### Nginx의 설정 파일 위치
```bash
/etc/nginx/nginx.conf
```

### Nginx 명령어
```bash
# nginx -t : nginx 설정 파일의 문법을 검사한다.
$ sudo nginx -t

# nginx -s reload : nginx 설정 파일을 다시 읽어들인다.
$ sudo nginx -s reload
```