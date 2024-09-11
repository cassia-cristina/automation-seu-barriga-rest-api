# Imagem base do Maven com OpenJDK 11
FROM maven:3.8-openjdk-11 AS build

# Definindo o fuso horário local
ENV TZ=America/Sao_Paulo
RUN apt-get update && apt-get install -y tzdata \
    && cp /usr/share/zoneinfo/$TZ /etc/localtime \
    && echo $TZ > /etc/timezone \
    && apt-get clean

# Definindo o diretório de trabalho
WORKDIR /app

# Copiando o arquivo POM e o código-fonte para o container
COPY pom.xml .
COPY src /app/src

# Construindo o projeto e executando os testes
RUN mvn clean install

# Instalando o Allure Commandline
RUN curl -o allure-commandline.zip -L "https://github.com/allure-framework/allure2/releases/download/2.20.0/allure-2.20.0.zip" \
    && unzip allure-commandline.zip -d /opt \
    && rm allure-commandline.zip

# Adicionando Allure ao PATH
ENV PATH="/opt/allure-2.20.0/bin:${PATH}"

# Gerando o report Allure
RUN mvn allure:report

EXPOSE 8081

CMD ["allure", "serve", "target/allure-results", "--port", "8081"]
