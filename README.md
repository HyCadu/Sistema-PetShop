# Sistema de gestão de Petshop

Este é um sistema básico de gerenciamento de petshop, desenvolvido em Java utilizando Java Swing para a interface gráfica e SQLite como banco de dados. O sistema permite cadastrar proprietários de pets e seus respectivos animais, além de cadastrar vacinas aplicadas nos pets. Todas as informações são armazenadas no banco de dados SQLite.

## Funcionalidades

- Cadastrar proprietários de pets com informações como Nome, Sexo, CPF, E-mail e Celular.
- Cadastrar pets vinculados a um proprietário, com informações como Nome, Peso, Idade, Sexo, Espécie e Raça.
- Inserir vacinas aplicadas em um pet, incluindo informações sobre a data de aplicação e descrição da vacina.
- Listar dados dos pets e seus proprietários.
- Validação para impedir a duplicação de CPF, E-mail ou Celular de um proprietário já cadastrado.
- Alteração de dados de proprietários e pets.

## Tecnologias Utilizadas

- **Java**: Linguagem principal utilizada no projeto.
- **Java Swing**: Utilizado para a interface gráfica.
- **SQLite**: Banco de dados relacional embutido.
- **JDBC**: Interface para conectar e executar operações no banco de dados SQLite.
