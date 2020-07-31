> # APIs construídas no treinamento

1. [Exame](./requirements/examination.md)
2. [Laboratório](./requirements/laboratory.md)
3. [Associação](./requirements/association.md)

> # Credenciais admin para acesso as APIs

- **Login:** admin
- **Password:** manageLabs@2020

> # Instruções para configuração do ambiente de desenvolvimento

> ### Softwares a serem instalados

1. [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html): Instalar o Java JDK 14
2. [Maven](https://maven.apache.org/download.cgi?Preferred=ftp://ftp.osuosl.org/pub/apache): Instalar Maven
3. [Docker Engine](https://docs.docker.com/docker-for-windows/install): Instalar Docker Engine
4. [Docker Machine](https://docs.docker.com/machine/install-machine/): Instalar Docker Machine
4. [Intellij IDE](https://www.jetbrains.com/pt-br/idea/download/#section=windows): Instalar IDE Intellij

> ### Confirando IDE

1. Baixar projeto Git
2. Importar em Intellij IDE
3. Instalar plugins **Lombok** e **Docker** em **file -> settings, opção plugins**

> ### Configurando Banco de Dados

1. Alterar configurações de banco de dados no arquivo **application.properties**
2. Criar um banco de dados chamado **manage-labs-db**

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

![Diagrama da aplicação e serviços AWS utilizados](src/main/resources/images/manage-labs-architecture.jpg)




“O dia que você acreditar ter atingido todo o seu potencial é o dia que não aconteceu. Por que você ainda tem o HOJE.” – Autor: Nick Vujicic

Obrigado...
