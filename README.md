# Cabe no Bolso — Planejador de Despesas Compartilhadas

## Sobre o projeto

O **Cabe no Bolso** é um sistema desenvolvido em Java para organizar despesas compartilhadas entre participantes, permitindo acompanhar os gastos individuais e verificar se cada pessoa está dentro do orçamento disponível.

O projeto foi desenvolvido como atividade acadêmica da disciplina de Programação Orientada a Objetos.

## Funcionalidades

- Cadastrar, editar e excluir participantes.
- Definir um orçamento individual para cada participante.
- Cadastrar despesas fixas e despesas por pessoa.
- Selecionar os participantes envolvidos em cada despesa.
- Editar e excluir despesas.
- Dividir despesas fixas entre os participantes selecionados.
- Calcular automaticamente os gastos individuais.
- Identificar participantes que ultrapassaram o orçamento.
- Salvar e carregar os dados em arquivo local.

## Tecnologias utilizadas

- Java
- Java Swing
- Programação Orientada a Objetos
- Arquitetura MVC
- Serialização de objetos

## Organização do projeto

- `Model`: classes responsáveis pelos dados e pelas regras de negócio.
- `controller`: classes responsáveis por coordenar as operações do sistema.
- `view`: telas gráficas desenvolvidas com Java Swing.
- `persistence`: classes responsáveis pelo salvamento e carregamento dos dados.

## Como executar

1. Instale o JDK (Java Development Kit).
2. Abra a pasta do projeto no VS Code com suporte a Java.
3. Compile o projeto.
4. Execute a classe `view.TelaPrincipal`, que contém o método `main`.
5. Utilize os botões da tela principal para gerenciar participantes, despesas e consultar o resumo financeiro.

## Exemplo de funcionamento

O sistema permite cadastrar Ana, Bruno e Carla, definir seus orçamentos e distribuir despesas compartilhadas.

Após os cálculos, apresenta os gastos de cada participante e indica quem ultrapassou o orçamento.

## Objetivo acadêmico

Aplicar conceitos de programação orientada a objetos, incluindo encapsulamento, herança, polimorfismo, classes abstratas, interfaces, coleções, tratamento de exceções e persistência de dados.