# Like a pro blog

## Installation

### Database

Install PostreSQL using Docker:
```bash
docker pull postgres:11
docker run --name dev-postgres -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres:11
docker exec dev-postgres psql -U postgres -c"CREATE DATABASE likeapro" postgres
```