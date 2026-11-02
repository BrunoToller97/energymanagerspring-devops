
# language: pt

Funcionalidade: Cadastro e autenticação de usuários
  Como usuário do Energy Manager Spring
  Quero realizar cadastro e autenticação
  Para acessar o sistema com segurança

  Cenário: Cadastrar usuário com dados válidos
    Dado que informo dados válidos para cadastro de usuário
    Quando envio a requisição de cadastro
    Então a API deve responder com status 200
    E a resposta deve conter o email cadastrado

  Cenário: Rejeitar cadastro com dados inválidos
    Dado que informo dados inválidos para cadastro de usuário
    Quando envio a requisição de cadastro
    Então a API deve responder com status 400
    E a resposta deve indicar erros no cadastro

  Cenário: Realizar login com credenciais válidas
    Dado que existe um usuário registrado
    Quando envio a requisição de login
    Então a API deve responder com status 200
    E a resposta deve conter um token JWT

  Cenário: Rejeitar login com email inválido
    Dado que informo um email inválido para login
    Quando envio a requisição de login
    Então a API deve responder com status 400
    E a resposta deve indicar o email inválido
