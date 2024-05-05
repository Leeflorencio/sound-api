# Sound API 

:sparkling_heart: *Juntei duas coisas que sou apaixonada: Música e Tecnologia* :sparkling_heart:
<p>O aplicativo permite que o usuário cadastre suas músicas e artistas favoritos, crie playlist personalizadas e compartilhe as letras que mais gosta. </p>

*EM CONSTRUÇÃO*

### Features 
### Artista
- GET /artistas/listarTodos: Solicita a quantidade de registros através de paginação, e retorna todos os artistas cadastrados.
- GET /artistas/buscarPorId/{id}: Solicita o identificador do artista, retorna um registro específico da entidade pelo ID.
- GET /artistas/buscarPorNome: Solicita o nome do artista, retorna um registro específico da entidade pelo nome.
- POST /artistas/cadastro: Cria um novo artista no banco de dados.
- PUT /artistas/atualizar/{id}: Atualiza um registro existente de um artista pelo ID.
- DELETE /artistas/deletar/{id}: Exclui um registro existente de um artista pelo ID.

### Música 
- GET /musicas/listarTodos: Solicita a quantidade de registros através de paginação, e retorna todos os músicas cadastrados.
- GET /musicas/buscarPorId/{id}: Solicita o identificador da música, e retorna um registro específico da entidade pelo ID.
- GET /musicas/buscarPorNome: Solicita o nome da música, e retorna um registro específico da entidade pelo nome.
- POST /musicas/cadastro: Cria uma nova música no banco de dados.
- PUT /musicas/atualizar/{id}: Atualiza um registro existente de uma música pelo ID.
- DELETE /musicas/deletar/{id}: Exclui um registro existente de uma música pelo ID.

### Álbum
- GET /album/listarTodos: Solicita a quantidade de registros através de paginação, e retorna todos os álbuns cadastrados.
- GET /musicas/buscarPorId/{id}: Solicita o identificador do álbum, e retorna um registro específico com todas as músicas do álbum.
- POST /musicas/cadastro: Cria um novo álbum no banco de dados.
- PUT /musicas/atualizar/{id}: Atualiza um registro existente de um álbum pelo ID.
- DELETE /musicas/deletar/{id}: Exclui um registro existente de um álbum pelo ID.

### 🛠 Tecnologias
Java 17 - MySQL - Spring Data JPA - Hibernate - Spring Boot - API Docs 

### Pré-requisitos e Testes
Utilizei a API Docs para documentação e testes -> http://localhost:8080/swagger-ui/index.html#/
Por questões de estudos, optei pela utilização do banco de dados MySQL, neste caso, é necessário a instalação e configuração. 
Nome da database = sound. 
