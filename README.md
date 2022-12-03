# CryptoExchange

Repositório criado para o projeto final Full Stack do curso Transforma Tec

## Consumindo a API do BitPreco

Api pode ser encontrada no endereço: `https://api.bitpreco.com/`

### Recursos do projeto

#### Back-end

##### `Consumo da API do bitPreco`
    Obter dados atualizados de criptmoedas;
    Utilizacao do FeignClient para consumo de API externa;

##### `Implementacao do BD`
    Utilizado o H2Database;
    Utilizacao do JPA Repository para gerenciamento do BD;

##### `Implementacao do CRUD API do bitPreco`
    Cria a moeda;
    Busca a moeda;
    Atualiza a moeda;
    Deleta a moeda;

##### `Criada API para transacoes`
    Responsavel por realizar transacoes de compra/venda de criptomoedas e salva-las no BD;
    CRUD sobre as transacoes;

##### `Documentacao Swagger`
    Docket;
    ApiInfo;

##### `Autenticacao`
    Foi utilizado criptografia de senha;
    Os usuarios sao cadastrados com permissoes de acesso;
    Foi utilizado Token JWT para login de usuario atraves de Http Cookies;
    Tambem foi utulizado refreshToken para o caso de expiracao de tempo do Token JWT;

##### `Bibliotecas usadas`
    Spring Boot;
    Spring Data;
    Spring Security;
    Spring Cloud;
    Spring Web;
    Lombook;

#### Front-end

##### `Criar os servicos`
    Authentication Service;
    Data Service;

##### `Paginas de autenticacao`
    Pagina de Login;
    Pagina de Registro;
    Pagina de Perfil;

##### `Paginas de Edicao`
    Pagina Board Admin
    Pagina Board Premium

##### `Demais paginas`
    Pagina Home e Home public
    Pagina de compra/venda de criptomoedas
    Pagina de exibicao das transacoes

##### `Bibliotecas usadas`

    Axios
    React-routes
    React-tabs
    Bootstrap
    Validator
    Middleware
