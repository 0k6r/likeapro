FROM gradle:jdk11

COPY . /app
WORKDIR /app

CMD ["gradle", "build"]