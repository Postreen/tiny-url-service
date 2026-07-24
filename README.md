# TinyURL Service

Учебный pet-проект, реализующий сервис сокращения ссылок по аналогии с TinyURL. Основная цель проекта — демонстрация навыков проектирования Spring Boot приложения, построения архитектуры, работы с JPA, Liquibase, Docker и тестированием.

---

## Стек технологий

- Java 21
- Spring Boot 4
- Spring Web
- Spring Data JPA
- PostgreSQL
- Liquibase
- Docker
- Docker Compose
- JUnit 5
- Mockito
- Testcontainers
- Spring MockMvc

---

## Выполнено

- Создание коротких ссылок
- Переход по короткой ссылке с HTTP-редиректом
- Пользовательский alias (shortCode)
- Время жизни ссылки (TTL)
- Постоянные ссылки
- Проверка срока действия ссылки
- Обработка ошибок
- Swagger/OpenAPI
- Docker и Docker Compose
- Liquibase для управления схемой БД
- Unit, Integration и WebMvc тесты

---

## Запуск локально

### 1. Запустить PostgreSQL

```bash
  docker compose up postgres
```

### 2. Запустить приложение

```bash
  ./gradlew bootRun
```

После запуска приложение будет доступно по адресу

```
http://localhost:8080
```

---

## Запуск через Docker

Собрать и запустить приложение:

```bash
  docker compose up --build
```

Остановить:

```bash
  docker compose down
```

Удалить контейнеры вместе с данными БД:

```bash
  docker compose down -v
```

---

## Swagger

После запуска документация доступна по адресу

```
http://localhost:8080/swagger-ui/index.html
```

---

## API

### Создание ссылки

```
POST /api/v1/links
```

Пример запроса

```json
{
  "url": "https://google.com",
  "shortCode": "google",
  "ttlMinutes": 60
}
```

или

```json
{
  "url": "https://google.com"
}
```

---

### Переход по ссылке

```
GET /{shortCode}
```

Пример

```
GET /google
```

Ответ

```
302 Found
```

---

## Коды ответов

### Создание ссылки

| Код | Описание |
|------|----------|
|200|Ссылка успешно создана|
|400|Некорректный запрос|
|409|ShortCode уже существует|

### Редирект

| Код | Описание |
|------|----------|
|302|Редирект выполнен|
|404|Ссылка не найдена|
|410|Срок действия ссылки истек|

---

## Запуск тестов

```bash
 ./gradlew test
```

