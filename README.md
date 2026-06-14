# Marketplace API (SelfPraktik)

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.10-brightgreen.svg)](https://spring.io/projects/spring-boot)

## О проекте

**Marketplace API (SelfPraktik)** — REST API приложение интернет-магазина (маркетплейса) для управления товарами, категориями, корзиной, заказами и пользователями.

Проект реализует ролевую модель доступа (**USER / ADMIN**), JWT-аутентификацию, загрузку изображений товаров и полный цикл оформления заказов.

Разработан в учебных целях для демонстрации навыков работы с:

* Java 21
* Spring Boot
* Spring Security
* Hibernate / JPA
* PostgreSQL
* Docker
* Unit и Integration Testing

---

## Используемые технологии

* **Java 21**
* **Spring Boot 3.5.10**
* **Spring Data JPA (Hibernate)**
* **Spring Security**
* **JWT Authentication**
* **BCrypt Password Encoder**
* **PostgreSQL**
* **Docker / Docker Compose**
* **Lombok**
* **Jakarta Validation**
* **Thymeleaf**
* **Maven**
* **JUnit 5**
* **Mockito**
* **@DataJpaTest**

---

## Архитектура проекта

```text
SelfPraktik/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/com/marketplace/SelfPraktik/
│   │   │   ├── Config/
│   │   │   ├── Controllers/
│   │   │   ├── DTO/
│   │   │   ├── Entities/
│   │   │   ├── Mappers/
│   │   │   ├── Repositories/
│   │   │   ├── Security/
│   │   │   ├── Services/
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   └── SelfPraktikApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       ├── templates/
│   │       └── application.properties
│   └── test/
│       └── java/com/marketplace/SelfPraktik/
├── target/
├── .env
├── .gitignore
└── pom.xml
```

---

## Основной функционал

### Аутентификация и авторизация

* Регистрация пользователей
* Авторизация по JWT
* Ролевая модель USER / ADMIN
* Шифрование паролей через BCrypt

### Управление товарами

* Получение списка товаров
* Поиск товаров по категориям
* Создание, обновление и удаление товаров
* Загрузка изображений товаров

### Корзина

* Добавление товаров
* Удаление товаров
* Очистка корзины
* Просмотр текущего содержимого

### Заказы

* Создание заказа из корзины
* Просмотр собственных заказов
* Отмена заказа
* Изменение статуса доставки

### Категории

* Создание категорий
* Обновление категорий
* Удаление категорий

### Пользователи

* Просмотр пользователей
* Управление ролями
* Изменение данных пользователей

---

## Запуск проекта

### 1. Клонирование репозитория

```bash
git clone https://github.com/your-username/SelfPraktik.git
cd SelfPraktik
```

### 2. Настройка переменных окружения

Перед запуском необходимо указать следующие переменные:

| Переменная  | Описание                       |
| ----------- | ------------------------------ |
| DB_PASSWORD | Пароль PostgreSQL              |
| JWT_SECRET  | Секретный ключ для подписи JWT |

Пример для Linux/macOS:

```bash
export DB_PASSWORD=mysecretpassword
export JWT_SECRET=your_super_secret_key
```

---

### 3. Запуск через Docker Compose

```bash
docker-compose up -d
```

После запуска:

* PostgreSQL будет доступен на порту `5432`
* Приложение будет доступно по адресу:

```text
http://localhost:8080
```

---

### 4. Запуск без Docker

Убедитесь, что:

* PostgreSQL установлен локально
* Создана база данных `practik`
* Настройки указаны в `application.properties`

Запуск приложения:

```bash
./mvnw spring-boot:run
```

---

## API Endpoints

Все запросы начинаются с:

```text
/api
```

Для защищённых эндпоинтов требуется JWT-токен:

```text
Authorization: Bearer <token>
```

---

### Authentication

| Method | Endpoint           | Description              |
| ------ | ------------------ | ------------------------ |
| POST   | /api/auth/register | Регистрация пользователя |
| POST   | /api/auth/login    | Получение JWT токена     |

---

### Products

#### Доступно всем пользователям

| Method | Endpoint                              |
| ------ | ------------------------------------- |
| GET    | /api/products                         |
| GET    | /api/products/{id}                    |
| GET    | /api/products/category/{categoryName} |
| GET    | /api/products/{id}/image              |

#### Только ADMIN

| Method | Endpoint                 |
| ------ | ------------------------ |
| POST   | /api/products            |
| PATCH  | /api/products/{id}       |
| DELETE | /api/products/{id}       |
| POST   | /api/products/{id}/image |

---

### Cart

| Method | Endpoint                     |
| ------ | ---------------------------- |
| GET    | /api/cart                    |
| POST   | /api/cart/add/{productId}    |
| DELETE | /api/cart/remove/{productId} |
| DELETE | /api/cart/clear              |

---

### Orders

| Method | Endpoint                      |
| ------ | ----------------------------- |
| POST   | /api/orders                   |
| GET    | /api/orders/my                |
| GET    | /api/orders/{orderId}         |
| PATCH  | /api/orders/{orderId}/cancel  |
| PATCH  | /api/orders/{orderId}/deliver |
| GET    | /api/orders                   |
| GET    | /api/orders/users/{userId}    |

---

### Categories

| Method | Endpoint           |
| ------ | ------------------ |
| GET    | /api/category      |
| POST   | /api/category      |
| PATCH  | /api/category/{id} |
| DELETE | /api/category/{id} |

---

### Users

| Method | Endpoint             |
| ------ | -------------------- |
| GET    | /api/users           |
| GET    | /api/users/{id}      |
| PATCH  | /api/users/{id}      |
| PATCH  | /api/users/{id}/role |
| DELETE | /api/users/{id}      |
| GET    | /api/users/me        |

---

## Тестирование

Запуск всех тестов:

```bash
./mvnw test
```

### Покрытие тестами

**Repository Layer**

* Интеграционные тесты с использованием `@DataJpaTest`
* Тестирование работы репозиториев и запросов

**Service Layer**

* Unit-тесты на базе `Mockito`
* Проверка бизнес-логики сервисов

---

## Docker

Для запуска базы данных:

```bash
docker-compose up --build
```

Будет поднят контейнер PostgreSQL.

На текущем этапе Docker используется для запуска БД. Dockerfile для приложения планируется добавить в следующих версиях проекта.

---

## Возможные улучшения

* Добавление Dockerfile для приложения
* Полная контейнеризация проекта
* Swagger / OpenAPI документация
* Пагинация и сортировка данных
* Интеграционные тесты контроллеров (`@WebMvcTest`)
* Кэширование часто используемых запросов
* Вынесение секретов в Vault или Secret Manager
* Использование `@Transactional(readOnly = true)` для операций чтения

---

## Лицензия

Проект распространяется под лицензией MIT.

---

## Автор

Разработано в рамках самостоятельного изучения Java Backend Development.

GitHub: `https://github.com/your-username`
