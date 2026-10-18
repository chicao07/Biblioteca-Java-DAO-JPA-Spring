# 📚 Biblioteca — Jornada de Aprendizado Java (DAO → JPA → Spring Boot → REST)

Projeto acadêmico desenvolvido como estudo progressivo de Java backend, evoluindo
de JDBC puro até uma API REST completa com Spring Boot. Cada fase foi implementada,
testada e documentada antes de avançar para a próxima, com foco em entender
**o que cada camada/ferramenta resolve** e **por que ela existe**.

## 🎯 Objetivo

Construir uma aplicação de gerenciamento de livros e autores, passando por
praticamente todo o ecossistema padrão de um backend Java moderno:

JBDC puro -> DAO -> Services -> Testes -> ORM(JPA/Hibernate) -> Spring Boot -> API REST


## 🛠️ Tecnologias utilizadas

- **Java 21**
- **Maven** (gerenciamento de dependências e build)
- **MySQL** (banco de dados relacional)
- **JDBC** + **HikariCP** (connection pool)
- **JPA / Hibernate** (ORM)
- **Spring Boot** (Injeção de Dependência, autoconfiguração)
- **Spring Data JPA** (repositórios)
- **Spring Web** (API REST)
- **Bean Validation** (`jakarta.validation`)
- **JUnit 5** (testes unitários e de integração)
- **Mockito** (testes com dublês/mocks)
- **Log4j2** (logging)

## 🧭 Jornada de evolução (fases implementadas)

| # | Fase | Status |
|---|------|--------|
| 1 | CRUD básico com padrão DAO (JDBC puro) | ✅ |
| 2 | Configurações sensíveis externalizadas via `.properties` | ✅ |
| 3 | Exceções customizadas | ✅ |
| 4 | Validações de regras de negócio | ✅ |
| 5 | Camada de Service (injeção de dependência manual) | ✅ |
| 6 | Menu interativo via Scanner | ✅ |
| 7 | Connection Pool com HikariCP | ✅ |
| 8 | Testes unitários e de integração (JUnit 5) | ✅ |
| 9 | Testes com Mockito (mocks) | ✅ |
| 10 | Relacionamento entre entidades (Livro ↔ Autor, FK) | ✅ |
| 11 | Migração para JPA/Hibernate (ORM) | ✅ |
| 12 | Migração para Spring Boot (Injeção de Dependência via framework) | ✅ |
| 13 | API REST com Spring Web + Bean Validation | ✅ |

Cada fase pode ser conferida isoladamente no histórico de commits do projeto,
com mensagens detalhando o que foi implementado e por quê.

## 📂 Estrutura do projeto
src/main/java
┣ 📁 model → Entidades JPA (Autor, Livro)
┣ 📁 dao → Implementações JDBC e JPA do padrão DAO (fase de estudo)
┣ 📁 repository → Spring Data JPA (JpaRepository)
┣ 📁 service → Camada de serviço manual (DAO + DI manual)
┣ 📁 serviceSpring → Camada de serviço gerenciada pelo Spring (@Service)
┣ 📁 controller → API REST (@RestController)
┣ 📁 validator → Validações manuais (fase pré-Spring)
┣ 📁 exception → Exceções customizadas
┣ 📁 connection → ConnectionFactory (JDBC+HikariCP) e JPAUtil (JPA)
┗ 📁 ProjetoDAO.ppoo → Classes de entrada (Main.java, BibliotecaApplication.java)

src/main/resources
┣ 📄 application.properties.example → Molde de configuração Spring Boot
┣ 📄 database.properties.example → Molde de configuração JDBC
┣ 📄 log4j2.xml → Configuração de logging
┗ 📁 META-INF/persistence.xml.example → Molde de configuração JPA

src/test/java
┣ 📁 validator → Testes de validação
┣ 📁 dao → Testes de integração com banco real
┗ 📁 service → Testes com Mockito


> **Nota:** o projeto mantém, deliberadamente, camadas paralelas de DAO/Service
> em JDBC puro e em JPA/Spring — isso foi intencional, para fins de comparação
> e estudo entre as abordagens, não representa redundância por erro.

## ⚙️ Como rodar o projeto

### Pré-requisitos
- Java 21
- Maven (ou Eclipse com M2Eclipse integrado)
- MySQL rodando localmente

### 1. Clone o repositório
```bash
git clone https://github.com/SEU_USUARIO/biblioteca-java-dao-jpa-spring.git
cd biblioteca-java-dao-jpa-spring


### 2. Crie o banco de dados
Execute o script ```schema.sql no seu MySQL:
Bash
mysql -u root -p < schema.sql
Isso cria os bancos biblioteca_db (produção) e biblioteca_test_db (testes).

### 3. Configure as credenciais
Copie os arquivos de exemplo e preencha com suas credenciais reais:

```bash

cp src/main/resources/database.properties.example src/main/resources/database.properties
cp src/test/resources/database.properties.example src/test/resources/database.properties
cp src/main/resources/application.properties.example src/main/resources/application.properties
cp src/main/resources/META-INF/persistence.xml.example src/main/resources/META-INF/persistence.xml
Edite cada um desses arquivos, substituindo sua_senha_aqui pela senha real do seu MySQL.

### 4. Rode a aplicação
- Via Eclipse: botão direito em BibliotecaApplication.java → Run As → Java Application
- Via Maven: mvn spring-boot:run
A API sobe em ```http://localhost:8080.

- 5. Rode os testes
Bash

```mvn test

### 🌐 Endpoints da API REST

#### Autores

Método	Rota	Descrição
GET	/autores	Lista todos os autores
GET	/autores/{id}	Busca autor por id
POST	/autores	Cria um novo autor
PUT	/autores/{id}	Atualiza um autor
DELETE	/autores/{id}	Remove um autor
Livros
Método	Rota	Descrição
GET	/livros	Lista todos os livros
GET	/livros/{id}	Busca livro por id
POST	/livros	Cria um novo livro
PUT	/livros/{id}	Atualiza um livro
DELETE	/livros/{id}	Remove um livro

Exemplo de requisição (POST /livros)
JSON

{
  "titulo": "Dom Casmurro",
  "preco": 15.90,
  "anoPublicacao": 1899,
  "autor": { "id": 1 }
}
Exemplo de resposta de erro de validação (400 Bad Request)
JSON

{
  "titulo": "O título do livro não pode ser vazio!",
  "preco": "O preço do livro deve ser maior que zero!",
  "anoPublicacao": "O ano de publicação não deve ser no futuro!"
}

### 📖 Principais aprendizados por fase

- JDBC puro: entendimento de PreparedStatement, ResultSet, prevenção de
SQL Injection, e por que o padrão DAO existe (separar acesso a dados do
restante da aplicação).
- Exceções customizadas: diferenciar erros de negócio (ValidacaoException)
de erros de infraestrutura (BancoDeDadosException).
- Testes (JUnit + Mockito): diferença entre teste unitário (isolado, com
mocks) e teste de integração (com banco real); uso de @BeforeEach para
isolar o estado entre testes.
- JPA/Hibernate: como um ORM traduz anotações (@Entity, @ManyToOne,
@JoinColumn) em SQL gerado automaticamente; entendimento do problema de
N+1 queries.
- Spring Boot: Inversão de Controle e Injeção de Dependência na prática —
substituição de new Objeto() manual por beans gerenciados pelo container.
- API REST: verbos HTTP, status codes, @Valid + Bean Validation, e
tratamento global de exceções com @RestControllerAdvice.

### 🚧 Próximos passos (em estudo)
 Tratamento de erros mais granular (exceções customizadas também na camada REST)
 DTOs para desacoplar API da estrutura interna das entidades
 Documentação automática com Swagger/OpenAPI
 Testes da camada REST com @WebMvcTest + MockMvc
 Reorganização de pacotes sob uma raiz única
 Spring Security (autenticação/autorização)

### 👤 Autor
Projeto desenvolvido como estudo pessoal/acadêmico de Java backend.

Este README é atualizado a cada nova fase concluída do projeto.
