# EasyMiner-Scorer
EasyMiner scorer for classifications and evaluations

## Docker 

- [Docker](https://www.docker.com/)
  * tested with Docker 1.12+

```bash
# build a docker image
docker build -t kizi/easyminer-scorer .
# or pull from docker hub
docker pull kizi/easyminer-scorer
# run 
docker run -d -p 8080:8080 --name easyminer-scorer kizi/easyminer-scorer
```

Other Docker commands:
```bash
# interactive mode
docker run -it -p 8080:8080 kizi/easyminer-scorer
# interactive mode + bash
docker run -it -p 8080:8080 --entrypoint=/bin/bash kizi/easyminer-scorer -i
```

## API description

Swagger UI with description of API:

```
http://127.0.0.1:8080/swagger-ui.html
```

## License 
[Apache License](LICENSE)