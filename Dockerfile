FROM amazoncorretto:17
# build/libs/ 폴더 안에 있는 .jar로 끝나는 파일을 app.jar라는 이름으로 복사하라는 뜻입니다.
COPY build/libs/Trip-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]