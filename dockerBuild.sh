# one docker image
docker build -t mtgczbe:v1 .
docker run -d -p 8087:8087 mtgczbe:v1