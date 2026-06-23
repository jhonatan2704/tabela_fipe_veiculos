🚗 API Tabela FIPE

API REST desenvolvida com Java e Spring Boot para consulta de marcas e modelos de veículos da Tabela FIPE.

Tecnologias

- Java 21
- Spring Boot
- PostgreSQL
- Spring Data JPA / Hibernate
- Docker
- Render

Funcionalidades

- Listar marcas
- Listar modelos
- Buscar marcas por nome
- Buscar modelos por nome
- Buscar veículos por tipo
- Persistir dados em banco PostgreSQL

Endpoints

GET /buscar/marcas
GET /buscar/modelos
GET /buscar/marcas/{marca}
GET /buscar/modelos/{modelo}
GET /buscar/tipo/{tipo}

Deploy

API publicada no Render.

Autor

Jhonatan Vicente
