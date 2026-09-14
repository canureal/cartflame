# CartFlame

> I made cartflame to be a fake/testing e-commerce api.

## What about application?

- It has one admin user that can add,delete products and changing the amount of products(can only decrease now.) that are in the stock
- Users can signup/siginin all the passwords are encrypted before going into the db.
- Has rate limiting which uses redis.
- Emails admin(role in database) whenever new product is added or deleted.

## Stack
- Spring boot as framework
- Java and kotlin as pl
- Spring JPA as orm
- PostgreSQL as database
- Docker as deploying development stack
- RabbitMQ for message queueing(i used at emailing admin for product changes)
- Redis for rate limiting 

| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/auth/signup` | Sign up |
| POST | `/api/auth/signin` | Sign in |
| GET | `/api/products` | List all products (public) |
| GET | `/api/products/{id}` | Get product by id (public) |
| POST | `/api/products` | Add product (admin only) |
| DELETE | `/api/me/delete` | Delete own account (authenticated) |

## How to run?

First, write a `.env` file matching with .env.example file if i wrote one.

and then run
```bash
docker compose up -d
```
in project root for running the whole application.
