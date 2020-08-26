# Sefaz status

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

### Instalação

É preciso [Node.js](https://nodejs.org/) para executar o projeto.

É preciso ter o maven ou então utilizar o mvmw.cmd.

É preciso ter o java na versão 14.

Execute os seguintes comandos na pasta raiz do repositório para executar o projeto:

```sh
$ cd backend
$ mvn clean install
$ java -jar target\backend-0.0.1-SNAPSHOT.jar

$ cd ..\frontend
$ npm install
$ npm start
```

### O que foi usado?

#### Projeto Frontend
Angular 10

PrimeNG 10

#### Projeto Backend
Spring Boot 2.3.3.RELEASE

H2 Embedded

jsoup

lombok

guava

flyway-core