# Instruções para executar a aplicação

> ### Executando a aplicação Spring Boot localmente

1. Atalizar Gradle para baixar as dependências
2. Clicar com o Botão direito em cima do arquivo **ManageLabsApplication** e depois selecionar **Run**

> ### Executando a aplicação com Docker

1. Usar o comando **$ sudo systemctl start docker** para executar o docker
2. Usar o comando **$ sudo systemctl enable docker** para executar o docker juntamente com o sistema
3. Acessar menu **Gradle** do canto direito, após **projectName -> Tasks -> Docker**
4. Usar a opção **DockerClean** para limpar o cache
5. Usar a opção **Docker** para gerar nova imagem no Docker
6. Acessar o menu **View -> Tool Windows -> Services
7. Criar conexão com o Docker do tipo **Unix Socket**
8. Criar e executar um container através da imagem gerada

> ### Executando a aplicação através da imagem Docker

1. Usar o comando **$ sudo systemctl start docker** para executar o docker
2. Usar o comando **$ sudo systemctl enable docker** para executar o docker juntamente com o sistema
3. Executar o seguinte comando **$ docker run -it -p 9090:9090 rhribeiro25/manage-labs:1.0.3**
