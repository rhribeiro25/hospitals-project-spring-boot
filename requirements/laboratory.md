# Laboratório

> ## Modelo

1.  ✅ nome
2.  ✅ endereço
3.  ✅ status [ativo, inativo]

> ## Funcionalidades

1.  ✅ Cadastrar um novo laboratório;
2.  ✅ Obter uma lista de laboratórios ativos;
3.  ✅ Atualizar um laboratório existente;
4.  ✅ Remover logicamente um laboratório ativo.
5.  ✅ O cadastro de um laboratório é considerado ativo e recebe um id gerado automaticamente.
6.  ✅ Possibilidade de executar cadastro, atualização e remoção em lote;

> ## Casos de sucesso

> ### Salvar

1.  ✅ Recebe uma requisição do tipo **POST** na rota **/manage-labs/laboratories/create**
2.  ✅ Valida dados obrigatórios **nome** e **address**
3.  ✅ Valida o tamanho do campo **nome** sendo de 7 a 63 caracteres
4.  ✅ **Cadastra** um novo laboratório
5.  ✅ Retorna **201** e o laboratório persistido

> ### Salvar em lote

1.  ✅ Recebe uma requisição do tipo **POST** na rota **/manage-labs/laboratories/create-by-batch**
2.  ✅ Valida dados obrigatórios **nome** e **address**
3.  ✅ Valida o tamanho do campo **nome** sendo de 7 a 63 caracteres
4.  ✅ **Cadastra** laboratórios através de arquivo CSV
5.  ✅ Retorna **201** com COMPLETED

> ### Atualizar

1.  ✅ Recebe uma requisição do tipo **UPDATE** na rota **/manage-labs/laboratories/update**
2.  ✅ Valida dados obrigatórios **nome** e **address**
3.  ✅ Valida o tamanho do campo **nome** sendo de 7 a 63 caracteres
4.  ✅ **Atualiza** determinado laboratório
5.  ✅ Retorna **200** e o laboratório atualizado

> ### Atualizar em lote

1.  ✅ Recebe uma requisição do tipo **UPDATE** na rota **/manage-labs/laboratories/update-by-batch**
2.  ✅ Valida dados obrigatórios **nome** e **address**
3.  ✅ Valida o tamanho do campo **nome** sendo de 7 a 63 caracteres
4.  ✅ **Atualiza** laboratórios através de arquivo CSV
5.  ✅ Retorna **200** com COMPLETED

> ### Deletar

1.  ✅ Recebe uma requisição do tipo **DELETE** na rota **/manage-labs/laboratories/delete**
2.  ✅ **Deleta** logicamente determinado laboratório
3.  ✅ Retorna **200** com a mensagem de sucesso

> ### Deletar em lote

1.  ✅ Recebe uma requisição do tipo **DELETE** na rota **/manage-labs/laboratories/delete-by-batch**
2.  ✅ **Deleta** logicamente laboratórios através de arquivo CSV
3.  ✅ Retorna **200** com COMPLETED

> ### Buscar por ID

1.  ✅ Recebe uma requisição do tipo **GET** na rota **/manage-labs/laboratories/find-by-id**
2.  ✅ **Busca** o laboratório através de um id válido
3.  ✅ Retorna **200** e o laboratório consultado

> ### Buscar todos

1.  ✅ Recebe uma requisição do tipo **GET** na rota **/manage-labs/laboratories/find-all**
2.  ✅ **Busca** todos os laboratórios com paginação
3.  ✅ Retorna **200** e a lista de laboratórios

> ### Buscar por status

1.  ✅ Recebe uma requisição do tipo **GET** na rota **/manage-labs/laboratories/find-by-status**
2.  ✅ **Busca** todos os laboratórios com paginação através de um deterninado status válido
3.  ✅ Retorna **200** e a lista de laboratórios

> ### Buscar laboratorios por nome de laboratório

1.  ✅ Recebe uma requisição do tipo **GET** na rota **/manage-labs/laboratories/find-labs-by-exam-name**
2.  ✅ **Busca** todos os laboratórios com paginação através de um deterninado nome de laboratório válido
3.  ✅ Retorna **200** e a lista de laboratórios

> ### Buscar por data de criação "DE" e "ATE"

1.  ✅ Recebe uma requisição do tipo **GET** na rota **/manage-labs/laboratories/find-by-createdat-between**
2.  ✅ **Busca** todos os laboratórios com paginação através de deterninadas datas **DE** e **ATÉ**
3.  ✅ Retorna **200** e a lista de laboratórios

> ## Exceções

1.  ✅ Retorna erro **404** se a API não existir
2.  ✅ Retorna erro **400** se nome ou tipo não forem fornecidos pelo client
3.  ✅ Retorna erro **400** se o campo email ou tipo forém inválidos
4.  ✅ Retorna erro **401** se for fornecido as credenciais para a consulta
5.  ✅ Retorna erro **500** se der algum erro interno do servidor na criaçao, updte, delete ou busca.
