# Exame

> ## Modelo

1.  ✅ nome
2.  ✅ tipo [analise clinica, imagem]
3.  ✅ status [ativo, inativo]

> ## Funcionalidades

1.  ✅ Cadastrar um novo exame;
2.  ✅ Obter uma lista de exames ativos;
3.  ✅ Atualizar um exame existente;
4.  ✅ Remover logicamente um exame ativo.
5.  ✅ Um exame pode estar associado a mais de um laboratório;
6.  ✅ O cadastro de exame é considerado ativo e recebe um id gerado automaticamente.
7.  ✅ Associar um exame ativo à um laboratório ativo;
8.  ✅ Desassociar um exame ativo de um laboratório ativo;
9.  ✅ Possibilidade de executar cadastro, atualização e remoção em lote;
10. ✅ Endpoint que faz a busca por nome do exame e retorna todos os laboratórios associados a esse exame.

> ## Casos de sucesso

1.  ✅ Recebe uma requisição do tipo **POST** na rota **/manage-labs/examinations/create**
2.  ✅ Valida dados obrigatórios **nome** e **tipo**
3.  ✅ Valida o tamanho do campo **nome** sendo de 7 a 63 caracteres
4.  ✅ **Cadastra** um novo exame
5.  ✅ Retorna **201** e o exame persistido

1.  ✅ Recebe uma requisição do tipo **POST** na rota **/manage-labs/examinations/create-by-batch**
2.  ✅ Valida dados obrigatórios **nome** e **tipo**
3.  ✅ Valida o tamanho do campo **nome** sendo de 7 a 63 caracteres
4.  ✅ **Cadastra** exames através de arquivo CSV
5.  ✅ Retorna **201** com COMPLETED

1.  ✅ Recebe uma requisição do tipo **UPDATE** na rota **/manage-labs/examinations/update**
2.  ✅ Valida dados obrigatórios **nome** e **tipo**
3.  ✅ Valida o tamanho do campo **nome** sendo de 7 a 63 caracteres
4.  ✅ **Atualiza** determinado exame
5.  ✅ Retorna **200** e o exame atualizado

1.  ✅ Recebe uma requisição do tipo **UPDATE** na rota **/manage-labs/examinations/update-by-batch**
2.  ✅ Valida dados obrigatórios **nome** e **tipo**
3.  ✅ Valida o tamanho do campo **nome** sendo de 7 a 63 caracteres
4.  ✅ **Atualiza** exames através de arquivo CSV
5.  ✅ Retorna **200** com COMPLETED

1.  ✅ Recebe uma requisição do tipo **DELETE** na rota **/manage-labs/examinations/delete**
2.  ✅ **Deleta** logicamente determinado exame
3.  ✅ Retorna **200** com a mensagem de sucesso

1.  ✅ Recebe uma requisição do tipo **DELETE** na rota **/manage-labs/examinations/delete-by-batch**
2.  ✅ **Deleta** logicamente exames através de arquivo CSV
3.  ✅ Retorna **200** com COMPLETED

1.  ✅ Recebe uma requisição do tipo **GET** na rota **/manage-labs/examinations/find-by-id**
2.  ✅ **Busca** o exame através de um id válido
3.  ✅ Retorna **200** e o exame consultado

1.  ✅ Recebe uma requisição do tipo **GET** na rota **/manage-labs/examinations/find-all**
2.  ✅ **Busca** todos os exames com paginação
3.  ✅ Retorna **200** e a lista de exames

1.  ✅ Recebe uma requisição do tipo **GET** na rota **/manage-labs/examinations/find-by-status**
2.  ✅ **Busca** todos os exames com paginação através de um deterninado status válido
3.  ✅ Retorna **200** e a lista de exames

1.  ✅ Recebe uma requisição do tipo **GET** na rota **/manage-labs/examinations/find-labs-by-exam-name**
2.  ✅ **Busca** todos os laboratórios com paginação através de um deterninado nome de exame válido
3.  ✅ Retorna **200** e a lista de exames

1.  ✅ Recebe uma requisição do tipo **GET** na rota **/manage-labs/examinations/find-by-createdat-between**
2.  ✅ **Busca** todos os exames com paginação através de deterninadas datas **DE** e **ATÉ**
3.  ✅ Retorna **200** e a lista de exames

> ## Exceções

1.  ✅ Retorna erro **404** se a API não existir
2.  ✅ Retorna erro **400** se nome ou tipo não forem fornecidos pelo client
3.  ✅ Retorna erro **400** se o campo email ou tipo forém inválidos
4.  ✅ Retorna erro **401** se for fornecido as credenciais para a consulta
5.  ✅ Retorna erro **500** se der algum erro interno do servidor na criaçao, updte, delete ou busca.
