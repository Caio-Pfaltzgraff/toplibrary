# 📚 Top Library
Top Library é uma API REST de gerenciamento de uma biblioteca. Foi construída utilizando Java 17 e Spring Boot.

- URL de Produção: https://toplibrary.up.railway.app/books

---

## 🔎 Especificações da Aplicação
A API permite a criação de usuários com dois tipos de autorizações: `user` e `admin`.

Usuários com autorização `user` podem:

- Fazer login
- Verificar livros disponíveis
- Alugar livros

Usuários com autorização `admin` têm todas as permissões de um `user`, além de poderem:

- Adicionar, alterar, deletar e buscar `Livros`, `Gêneros`, `Autores` e `Editoras`
- Criar outros usuários `admin`

Para utilizar a aplicação, é necessário fazer login e recuperar o token de segurança JWT. Este token deve ser inserido em todas as requisições para validação. 
O token tem duração de 2 horas após o login. Após esse período, é necessário fazer login novamente para obter um novo token.

Para melhoria de performance, a aplicação utiliza cache para diminuir as consultas ao banco, e a cada dado alterado ou salvo o cache é atualizado.

---

## 🛠 Tecnologias
Este projeto foi desenvolvido com as seguintes tecnologias:
- **[Java 17](https://www.oracle.com/java)**
- **[Spring Boot 3](https://spring.io/projects/spring-boot)**
- **[Gradle](https://gradle.org/)**
- **[Hibernate](https://hibernate.org)**
- **[Lombok](https://projectlombok.org)**
- **[JWT](https://jwt.io)**
- **[H2 Database](https://www.h2database.com/)**
- **[Postgres](https://www.postgresql.org/)**
- **[OpenApi (Swagger)](https://www.openapis.org/)**
- **[Railway](https://railway.app/)**

---

## 📄 Documentação da API
- **[Documentação em JSON](https://toplibrary.up.railway.app/v3/api-docs)**
- **[Documentação no Swagger](https://toplibrary.up.railway.app/swagger-ui/index.html)**

---

## 📊 Diagrama de Classes

```mermaid
classDiagram
  class User {
    + id: UUID
    + username: string
    + name: string
    + password: string
    + email: string
    + ativo: boolean
    + role: string
  }

  class Book {
    + id: UUID
    + title: string
    + isbn: string
    + publicationYear: int
    + quantity: int
  }

  class PublishingCompany {
    + id: UUID
    + name: string
  }

  class Author {
    + id: UUID
    + name: string
  }

  class Genre {
    + id: UUID
    + name: string
  }
  
  class BookGenre {
      + idLivro: UUID
      + idGenre: UUID
  }

  class Rental {
    + id: UUID
    + rentalDate: Date
    + devolutionDate: Date
  }

  User "1" -- "N" Rental
  Book "1" -- "N" Rental
  Book "N" -- "1" PublishingCompany
  Book "N" -- "1" Author
  BookGenre "N" -- "1" Book
  BookGenre "N" -- "1" Genre
```
