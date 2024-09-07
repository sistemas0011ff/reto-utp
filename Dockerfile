# Establece la imagen base para Java
#FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
#WORKDIR /app

# Copia el archivo JAR ya generado al contenedor
#COPY build/libs/utp-api-0.0.1-SNAPSHOT.jar /app/utp-api-0.0.1-SNAPSHOT.jar

# Expone el puerto en el que la aplicación correrá
#EXPOSE 8083

# Ejecuta la aplicación
#CMD ["java", "-jar", "/app/utp-api-0.0.1-SNAPSHOT.jar"]
# Establece la imagen base para Java
FROM openjdk:17-jdk-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de construcción de Gradle
COPY build.gradle settings.gradle /app/

# Copia los scripts de Gradle Wrapper al contenedor
COPY gradlew /app/
COPY gradle /app/gradle

# Copia el código fuente al contenedor
COPY src /app/src

# Asigna permisos de ejecución al Gradle Wrapper
RUN chmod +x ./gradlew

# Ejecuta Gradle para compilar el proyecto y empaquetarlo como un archivo JAR
RUN ./gradlew bootJar --no-daemon

# Expone el puerto en el que la aplicación correrá
EXPOSE 8083

# Ejecuta el archivo JAR generado
CMD ["java", "-jar", "/app/build/libs/utp-api-0.0.1-SNAPSHOT.jar"]
