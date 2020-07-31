# Instruções para configuração do ambiente de desenvolvimento

> ## Softwares a serem instalados

- [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html): Instalar o Java JDK 14
- [Maven](https://maven.apache.org/download.cgi?Preferred=ftp://ftp.osuosl.org/pub/apache): Instalar Maven
- [Docker Engine](https://docs.docker.com/docker-for-windows/install): Instalar Docker Engine
- [Docker Machine](https://docs.docker.com/machine/install-machine/): Instalar Docker Machine
- [Intellij IDE](https://www.jetbrains.com/pt-br/idea/download/#section=windows): Instalar IDE Intellij

> ## Confirando IDE

1. Baixar projeto Git
2. Importar em Intellij IDE
3. Instalar plugins **Lombok** e **Docker** em **file -> settings, opção plugins**

> ## Configurando Banco de Dados

1. Alterar configurações de banco de dados no arquivo **application.properties**
2. Criar um banco de dados chamado **manage-labs-db**

# Instruções para execução da aplicação

> ## Executando a aplicação Spring Boot localmente

1. Atalizar Gradle para baixar as dependências
2. Clicar com o Botão direito em cima do arquivo **ManageLabsApplication** e depois selecionar **Run**

> ## Executando a aplicação com Docker

1. Usar o comando **$ sudo systemctl start docker** para executar o docker
2. Usar o comando **$ sudo systemctl enable docker** para executar o docker juntamente com o sistema
3. Acessar menu **Gradle** do canto direito, após **projectName -> Tasks -> Docker**
4. Usar a opção **DockerClean** para limpar o cache
5. Usar a opção **Docker** para gerar nova imagem no Docker
6. Acessar o menu **View -> Tool Windows -> Services
7. Criar conexão com o Docker do tipo **Unix Socket**
8. Criar e executar um container através da imagem gerada

> ## Executando a aplicação através da imagem Docker

1. Usar o comando **$ sudo systemctl start docker** para executar o docker
2. Usar o comando **$ sudo systemctl enable docker** para executar o docker juntamente com o sistema
3. Executar o seguinte comando **$ docker run -it -p 9090:9090 rhribeiro25/manage-labs:1.0.3**
