# API de E-commerce Quarkus

API REST para sistema de e-commerce desenvolvida com Quarkus Framework.

## 🚀 Tecnologias Utilizadas

- **Java 21**
- **Quarkus Framework 3.26.4**
- **Hibernate ORM com Panache**
- **Banco de dados H2 (em memória)**
- **Bean Validation**
- **OpenAPI/Swagger UI**
- **Maven**

## 📋 Funcionalidades

### Entidades e Relacionamentos
- **Customer (Cliente)**: Gerenciamento de clientes
- **Product (Produto)**: Catálogo de produtos
- **Order (Pedido)**: Sistema de pedidos com rastreamento
- **Profile (Perfil)**: Informações de contato e endereço dos clientes

### Relacionamentos Implementados
- **One-to-One**: Customer ↔ Profile
- **One-to-Many**: Customer → Orders
- **Many-to-Many**: Orders ↔ Products

### Endpoints Disponíveis

#### Customers (Clientes)
- `GET /customers` - Listar todos os clientes
- `GET /customers/{id}` - Buscar cliente por ID
- `POST /customers` - Criar novo cliente
- `PUT /customers/{id}` - Atualizar cliente
- `DELETE /customers/{id}` - Deletar cliente
- `GET /customers/search?name={nome}` - Buscar clientes por nome

#### Products (Produtos)
- `GET /products` - Listar todos os produtos
- `GET /products/{id}` - Buscar produto por ID
- `POST /products` - Criar novo produto
- `PUT /products/{id}` - Atualizar produto
- `DELETE /products/{id}` - Deletar produto
- `GET /products/search?name={nome}` - Buscar produtos por nome
- `GET /products/price-range?minPrice={min}&maxPrice={max}` - Buscar por faixa de preço

#### Orders (Pedidos)
- `GET /orders` - Listar todos os pedidos
- `GET /orders/{id}` - Buscar pedido por ID
- `POST /orders` - Criar novo pedido
- `PUT /orders/{id}` - Atualizar pedido
- `DELETE /orders/{id}` - Deletar pedido
- `GET /orders/customer/{customerId}` - Buscar pedidos por cliente
- `GET /orders/status/{status}` - Buscar pedidos por status
- `POST /orders/{orderId}/products/{productId}` - Adicionar produto ao pedido

#### Profiles (Perfis)
- `GET /profiles` - Listar todos os perfis
- `GET /profiles/{id}` - Buscar perfil por ID
- `POST /profiles` - Criar novo perfil
- `PUT /profiles/{id}` - Atualizar perfil
- `DELETE /profiles/{id}` - Deletar perfil
- `GET /profiles/customer/{customerId}` - Buscar perfil por cliente
- `GET /profiles/city/{city}` - Buscar perfis por cidade
- `GET /profiles/state/{state}` - Buscar perfis por estado

## 🛠️ Como Executar

### Pré-requisitos
- Java 11 ou superior
- Maven 3.6+
- Git

### Configuração do Repositório Git
```bash
# Clone o repositório
git clone <url-do-repositorio>

# Navegue para o diretório do projeto
cd entrega_API_Quarkus

# Verifique o status do repositório
git status

# Para fazer commit das alterações
git add .
git commit -m "Implementação completa da API de E-commerce Quarkus"
git push origin main
```

### Executando em Modo Desenvolvimento
```bash
# Execute em modo desenvolvimento
./mvnw quarkus:dev
```

### Executando com Maven
```bash
# Compilar o projeto
./mvnw clean compile

# Executar em modo desenvolvimento
./mvnw quarkus:dev

# Gerar executável JAR
./mvnw clean package

# Executar o JAR gerado
java -jar target/quarkus-app/quarkus-run.jar
```

## 📚 Documentação da API

Após iniciar a aplicação, acesse:

- **Swagger UI**: http://localhost:8080/swagger
- **OpenAPI JSON**: http://localhost:8080/q/openapi

## 🚀 Coleção do Postman

Uma coleção completa do Postman está disponível no arquivo `postman_collection.json` com exemplos de todas as chamadas da API.

### Como importar no Postman:

1. Abra o Postman
2. Clique em "Import"
3. Selecione o arquivo `postman_collection.json`
4. A coleção será importada com todas as requisições configuradas

### Variáveis da coleção:
- `baseUrl`: http://localhost:8080
- `customerId`: 1 (ID de exemplo do cliente)
- `productId`: 1 (ID de exemplo do produto)
- `orderId`: 1 (ID de exemplo do pedido)
- `profileId`: 1 (ID de exemplo do perfil)

### Endpoints incluídos na coleção:
- **28 requisições** organizadas em 4 pastas
- **Customers**: 6 endpoints
- **Products**: 7 endpoints  
- **Orders**: 8 endpoints
- **Profiles**: 7 endpoints

## 🗄️ Banco de Dados

O projeto utiliza H2 em modo memória com dados de exemplo pré-carregados:

- **3 clientes** com perfis completos
- **4 produtos** variados
- **3 pedidos** em diferentes status
- **Relacionamentos** entre todas as entidades

### Status dos Pedidos
- `NEW` - Novo pedido
- `PROCESSING` - Em processamento
- `SHIPPED` - Enviado
- `DELIVERED` - Entregue
- `CANCELLED` - Cancelado

## ✅ Validações Implementadas

### Customer
- Nome obrigatório (2-100 caracteres)
- Email obrigatório com formato válido e único

### Product
- Nome obrigatório (2-100 caracteres)
- Preço obrigatório (maior que 0)
- Descrição opcional (máximo 500 caracteres)

### Order
- Cliente obrigatório
- Status com valores válidos do enum
- Cálculo automático do valor total

### Profile
- Endereço obrigatório (10-200 caracteres)
- Telefone obrigatório com formato válido
- CEP opcional com formato válido
- Cidade e estado opcionais

## 🔧 Configurações

### application.properties
```properties
# Banco de dados H2
quarkus.datasource.db-kind=h2
quarkus.datasource.username=sa
quarkus.datasource.password=sa

# Configurações do Hibernate
quarkus.hibernate-orm.database.generation=drop-and-create
quarkus.hibernate-orm.sql-load-script=import.sql

# Swagger/OpenAPI
quarkus.swagger-ui.always-include=true
quarkus.swagger-ui.path=/swagger
```

## 🧪 Testando a API

### Exemplos de Requisições

#### Criar um cliente:
```bash
curl -X POST http://localhost:8080/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@email.com"
  }'
```

#### Criar um produto:
```bash
curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Smartphone",
    "price": 1500.00,
    "description": "Smartphone Android 128GB"
  }'
```

#### Criar um pedido:
```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customer": {"id": 1},
    "products": [{"id": 1}, {"id": 2}]
  }'
```

## 📊 Estrutura do Projeto

```
src/
├── main/
│   ├── java/entrega/
│   │   ├── controllers/     # Controladores REST
│   │   ├── dtos/           # Data Transfer Objects
│   │   ├── exceptions/     # Tratamento de exceções
│   │   ├── models/         # Entidades JPA
│   │   │   └── enums/      # Enumeradores
│   │   ├── repositories/   # Repositórios Panache
│   │   └── services/       # Lógica de negócio
│   └── resources/
│       ├── application.properties
│       ├── import.sql      # Dados iniciais
│       └── openapi.yml     # Documentação OpenAPI
└── test/                   # Testes unitários
```

## 🎯 Recursos Implementados

- ✅ **Framework Quarkus** (versão mais recente)
- ✅ **Java 21** (superior ao Java 11)
- ✅ **Maven** como gerenciador de dependências
- ✅ **Banco H2** para persistência
- ✅ **Hibernate+Panache** para ORM
- ✅ **3 entidades** com relacionamentos (One-to-One, One-to-Many, Many-to-Many)
- ✅ **Validações Bean Validation** completas
- ✅ **Enum OrderStatus** implementado
- ✅ **15+ endpoints REST** (5+ por entidade)
- ✅ **Operações CRUD** completas
- ✅ **Consultas personalizadas** por entidade
- ✅ **Códigos de status HTTP** apropriados
- ✅ **Documentação OpenAPI** completa
- ✅ **Swagger UI** personalizado
- ✅ **Tratamento de exceções** global

## 📝 Notas Importantes

- O banco de dados é recriado a cada reinicialização da aplicação
- Dados de exemplo são carregados automaticamente via `import.sql`
- A documentação da API está disponível em `/swagger`
- Todos os endpoints retornam JSON
- Validações são aplicadas automaticamente via Bean Validation
- Tratamento de erros global com mensagens em português

## 📁 Estrutura de Arquivos para Entrega

```
entrega_API_Quarkus/
├── src/                          # Código-fonte
│   ├── main/java/entrega/
│   │   ├── controllers/          # Controladores REST
│   │   ├── dtos/                # Data Transfer Objects
│   │   ├── exceptions/          # Tratamento de exceções
│   │   ├── models/              # Entidades JPA
│   │   ├── repositories/        # Repositórios Panache
│   │   └── services/            # Lógica de negócio
│   └── resources/
│       ├── application.properties
│       ├── import.sql           # Dados iniciais
│       └── openapi.yml          # Documentação OpenAPI
├── pom.xml                      # Configuração Maven
├── README.md                    # Documentação do projeto
├── postman_collection.json      # Coleção do Postman
└── mvnw / mvnw.cmd             # Maven Wrapper
```

## 🚀 Deploy no Render

### Configuração para Render

O projeto está configurado para deploy automático no Render com os seguintes arquivos:

- `Dockerfile` - Configuração de container
- `render.yaml` - Configuração específica do Render
- `application-prod.properties` - Configurações de produção

### Como fazer deploy no Render:

1. **Subir o código para o GitHub:**
```bash
# Adicionar todos os arquivos
git add .

# Fazer commit
git commit -m "Configuração para deploy no Render"

# Enviar para o GitHub
git push origin main
```

2. **Configurar no Render (Recomendado - Sem Docker):**
   - Acesse [render.com](https://render.com)
   - Faça login com sua conta GitHub
   - Clique em "New +" → "Web Service"
   - Conecte seu repositório GitHub
   - Configure:
     - **Name**: `api-ecommerce-quarkus`
     - **Environment**: `Node.js` (ou `Python`)
     - **Build Command**: `./mvnw clean package -DskipTests`
     - **Start Command**: `java -jar target/code-with-quarkus-1.0.0-SNAPSHOT-runner.jar`
     - **Port**: `8080`
   - Clique em "Create Web Service"

3. **Alternativa com Docker:**
   - **Environment**: `Docker`
   - **Dockerfile Path**: `./Dockerfile`
   - **Build Command**: `./mvnw clean package -DskipTests`
   - **Start Command**: `java -jar target/code-with-quarkus-1.0.0-SNAPSHOT-runner.jar`

3. **Variáveis de ambiente no Render:**
   - `QUARKUS_PROFILE` = `prod`
   - `JAVA_OPTS` = `-Xmx512m -Xms256m`

### URLs após deploy:
- **API**: `https://api-ecommerce-quarkus.onrender.com`
- **Swagger UI**: `https://api-ecommerce-quarkus.onrender.com/swagger`
- **Health Check**: `https://api-ecommerce-quarkus.onrender.com/q/health`

---

**Desenvolvido com ❤️ usando Quarkus Framework**