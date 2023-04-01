clean:
	./mvnw clean

install:
	./mvnw install

docker-build:
	docker build -t sccon-teste:teste-nilso .

docker-run:
	docker run -p 8080:8080 sccon-teste:teste-nilso .

run:
	java -jar target/testenilso.jar
	