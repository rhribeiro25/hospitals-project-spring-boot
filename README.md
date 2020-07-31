> # APIs construídas no treinamento

1. [Exame](./documentation/examination.md)
2. [Laboratório](./documentation/laboratory.md)
3. [Associação](./documentation/association.md)

> # Credenciais admin para acesso as APIs

- **Login:** admin
- **Password:** manageLabs@2020

> # Configuração de Ambiente

- [Configuração de Ambiente](./documentation/environment-configuration.md)

> # Instruções de API

> # Arquitetura do projeto

> # Arquitetura do AWS

> ### Rodando a aplicação Spring Boot localmente

1. Atalizar Gradle para baixar as dependências
2. Clicar com o Botão direito em cima do arquivo **ManageLabsApplication** e depois selecionar **Run**

> ### Rodando a aplicação com Docker

1. Usar o comando **$ sudo systemctl start docker** para executar o docker
2. Usar o comando **$ sudo systemctl enable docker** para executar o docker juntamente com o sistema
3. Acessar menu **Gradle** do canto direito, após **projectName -> Tasks -> Docker**
4. Usar a opção **DockerClean** para limpar o cache
5. Usar a opção **Docker** para gerar nova imagem no Docker
6. Acessar o menu **View -> Tool Windows -> Services
7. Criar conexão com o Docker do tipo **Unix Socket**
8. Criar e executar um container através da imagem gerada

> ### Rodando a aplicação através da imagem Docker

1. Usar o comando **$ sudo systemctl start docker** para executar o docker
2. Usar o comando **$ sudo systemctl enable docker** para executar o docker juntamente com o sistema
3. Executar o seguinte comando **$ docker run -it -p 9090:9090 rhribeiro25/manage-labs:1.0.3**

> # Instruções para utilização da API

- [GitHub](https://github.com/rhribeiro25/manageLabs): A aplicação pode ser executada facilmente como uma aplicação Spring Boot tradicional, na porta 9090, o banco de dados configurado é o MySQL contido no Amazon RDS.
- [Docker Hub](https://hub.docker.com/repository/docker/rhribeiro25/manage-labs): Como alternativa podemos executada facilmente a aplicação pela imagem Docker contida no Docker Hub, também na porta 9090, o banco de dados configurado é o MySQL contido no Amazon RDS.
- [Swagger UI](http://localhost:9090/swagger-ui.html): Após rodar a aplicação pode-se acessar a documentação Swagger para melhor esclarecimento das funcionalidades podendo ser realizado os testes pela própria ferramenta.
- [Postman Collections](src/main/resources/postmanCollection/manage-labs-postman-collections.json): Arquivo de coleção Postman para realizar o teste da API
- [exams-create](src/main/resources/files/csv/exams-create.csv): Arquivo para criação de Exames em lote.
- [exams-update](src/main/resources/files/csv/exams-update.csv): Arquivo para atualização de Exames em lote.
- [exams-delete](src/main/resources/files/csv/exams-delete.csv): Arquivo para remoção lógica de Exames em lote.
- [labs-create](src/main/resources/files/csv/labs-create.csv): Arquivo para criação de Laboratórios em lote.
- [labs-update](src/main/resources/files/csv/labs-update.csv): Arquivo para atualização de Laboratórios em lote.
- [labs-delete](src/main/resources/files/csv/labs-delete.csv): Arquivo para remoção lógica de Laboratórios em lote.

> # Proposta para Amazon Web Services Architecture

![Diagrama da aplicação e serviços AWS utilizados](./documentation/manage-labs-architecture.jpg)




“O dia que você acreditar ter atingido todo o seu potencial é o dia que não aconteceu. Por que você ainda tem o HOJE.” – Autor: Nick Vujicic

