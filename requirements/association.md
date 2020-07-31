# Associação

> ## Funcionalidades

1.  ✅ Associar um exame ativo à um laboratório ativo;
2.  ✅ Desassociar um exame ativo de um laboratório ativo;

> ## Casos de sucesso

> ### Associar

1.  ✅ Recebe uma requisição do tipo **PATCH** na rota **/manage-labs/associations/create**
2.  ✅ **Associa** exame à laboratório
3.  ✅ Retorna **200** com mensagem de sucesso

> ### Desassociar

1.  ✅ Recebe uma requisição do tipo **PATCH** na rota **/manage-labs/associations/delete**
2.  ✅ **Desassocia** exame de laboratório
3.  ✅ Retorna **200** com mensagem de sucesso


> ## Exceções

1.  ✅ Retorna erro **404** se a API não existir
2.  ✅ Retorna erro **400** se ID do exame ou ID do Laboratório não forem fornecidos pelo client
3.  ✅ Retorna erro **400** se o campo ID do exame ou ID do Laboratório forém inválidos
4.  ✅ Retorna erro **401** se não for fornecido as credenciais para a consulta
5.  ✅ Retorna erro **500** se der algum erro interno no servidor ao criar, atualizar, deletar ou buscar.
