# Projeto de Automação API Rest

Projeto final do curso Testando API Rest com REST-assured (com Wagner Costa);
* Rest Assured
* JUnit 5
* Lombok
* Allure Report
* Maven

##  Requisitos
* Java 11+ JDK
* Maven

## Como executar a aplicação localmente:
Na raiz do projeto, através de seu Prompt de Comando/Terminal/Console execute o comando:
```bash
mvn clean install
```

## Como executar a aplicação com Dockerfile e acessar Allure Report:
```bash
docker build -t my-maven-allure-project .
```
```bash
docker run --name my-maven-allure-container -p 8080:8081 my-maven-allure-project
```
- Acesse http://localhost:8080/index.html

* Também foi configurado GitHub Actions.
* Acessar Report no GitHub: https://cassia-cristina.github.io/barriga-rest-api/