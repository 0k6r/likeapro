# Like a pro blog

[![Build Status](https://travis-ci.com/0k6r/likeapro.svg?branch=master)](https://travis-ci.com/0k6r/likeapro)
[![codecov](https://codecov.io/gh/0k6r/likeapro/branch/master/graph/badge.svg)](https://codecov.io/gh/0k6r/likeapro)

## Installation

### Database

Install PostgreSQL using Docker:
```bash
docker pull postgres:11
docker run --name dev-postgres -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres:11
docker exec dev-postgres psql -U postgres -c"CREATE DATABASE likeapro" postgres
```

Install in local PostgreSQL:
```postgresql
CREATE ROLE likeapro_user WITH LOGIN PASSWORD 'password'; 
CREATE DATABASE likeapro;
GRANT ALL PRIVILEGES ON DATABASE likeapro TO likeapro_user;
```