# one docker image
docker build -t mtgCZBe:v1 .
docker run -d -p 8087:8087 mtgCZBe:v1