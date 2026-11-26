# Documentação de Testes — Cabe no Bolso

## 1. Objetivo

Verificar o funcionamento do sistema Cabe no Bolso, considerando as regras de negócio, os cálculos financeiros, as validações, a interface gráfica e a persistência dos dados.

## 2. Ambiente de testes

- Sistema operacional: Windows.
- Java: Eclipse Temurin JDK 21.0.13.1 LTS.
- Editor: Visual Studio Code.
- Interface gráfica: Java Swing.
- Persistência: serialização de objetos.
- Terminal: PowerShell.

## 3. Compilação e execução

Na pasta principal do projeto, execute:

```powershell
javac -encoding UTF-8 Model/*.java controller/*.java persistence/*.java view/*.java testes/*.java
```

Para iniciar o sistema:

```powershell
java -cp . view.TelaPrincipal
```

## 4. Cenário de referência

### Participantes

| Código | Nome | Orçamento |
|---|---|---:|
| 1 | Ana | R$ 70,00 |
| 2 | Bruno | R$ 60,00 |
| 3 | Carla | R$ 70,00 |

### Despesas

| Descrição | Tipo | Valor | Participantes |
|---|---|---:|---|
| Transporte | Fixa | R$ 90,00 | Todos |
| Almoço | Por pessoa | R$ 20,00 | Todos |
| Atividade recreativa | Fixa | R$ 30,00 | Ana e Carla |
| Sobremesa | Por pessoa | R$ 10,00 | Todos |

### Resultados esperados e obtidos

| Participante | Esperado | Obtido | Excedente |
|---|---:|---:|---:|
| Ana | R$ 75,00 | R$ 75,00 | R$ 5,00 |
| Bruno | R$ 60,00 | R$ 60,00 | R$ 0,00 |
| Carla | R$ 75,00 | R$ 75,00 | R$ 5,00 |
| Total | R$ 210,00 | R$ 210,00 | |

Resultado: aprovado.

Evidências:

- [Tela principal](evidencias/01-tela-principal.png)
- [Cadastro de participantes](evidencias/02-participantes.png)
- [Gerenciamento de despesas](evidencias/03-despesas.png)
- [Resumo financeiro](evidencias/04-resumo-financeiro.png)

## 5. Testes complementares

### Teste 1 — Distribuição de centavos

Entrada: despesa fixa de R$ 100,00 dividida entre três participantes com códigos 1, 2 e 3.

Comando:

```powershell
java -cp . testes.TesteDespesaFixa
```

| Participante | Esperado | Obtido |
|---|---:|---:|
| 1 | R$ 33,34 | R$ 33,34 |
| 2 | R$ 33,33 | R$ 33,33 |
| 3 | R$ 33,33 | R$ 33,33 |

O centavo restante foi atribuído ao participante de menor código, preservando o valor total de R$ 100,00.

Resultado: aprovado.

[Evidência do rateio](evidencias/05-rateio-centavos.png)

### Teste 2 — Validação de despesas inválidas

Na tela de gerenciamento de despesas, foram realizadas três tentativas de cadastro inválido.

| Entrada | Resultado esperado | Resultado obtido |
|---|---|---|
| Valor de -50 | Rejeitar cadastro | Valor inválido! |
| Descrição vazia | Rejeitar cadastro | Descrição inválida! |
| Nenhum participante selecionado | Rejeitar cadastro | Selecione pelo menos um participante! |

As quatro despesas originais permaneceram na tabela.

Resultado: aprovado.

Evidências:

- [Valor negativo](evidencias/06a-validacao-valor-negativo.png)
- [Descrição vazia](evidencias/06b-validacao-descricao-vazia.png)
- [Sem participantes](evidencias/06c-validacao-sem-participantes.png)

### Teste 3 — Alteração de orçamento

Procedimento: alterar o orçamento de Ana de R$ 70,00 para R$ 80,00 e consultar o resumo financeiro.

Resultado esperado:

- Gastos de Ana: R$ 75,00.
- Excedente: R$ 0,00.
- Situação: dentro do limite.
- Total geral: R$ 210,00.

Resultado obtido: todos os valores corresponderam ao esperado.

Resultado: aprovado.

[Evidência da alteração de orçamento](evidencias/07-alteracao-orcamento.png)

Após o teste, o orçamento original foi restaurado.

### Teste 4 — Exclusão de despesa

Procedimento: excluir a despesa Atividade recreativa e consultar o resumo financeiro.

| Participante | Esperado | Obtido |
|---|---:|---:|
| Ana | R$ 60,00 | R$ 60,00 |
| Bruno | R$ 60,00 | R$ 60,00 |
| Carla | R$ 60,00 | R$ 60,00 |
| Total | R$ 180,00 | R$ 180,00 |

O sistema recalculou os gastos individuais e o custo total.

Resultado: aprovado.

[Evidência da exclusão](evidencias/08-exclusao-despesa.png)

Após o teste, a despesa foi cadastrada novamente com seus participantes originais.

### Teste 5 — Salvamento e recuperação dos dados

Procedimento:

1. Cadastrar os participantes e as despesas do cenário de referência.
2. Fechar o sistema.
3. Executar novamente a aplicação.
4. Consultar o resumo financeiro.
5. Selecionar cada despesa e utilizar o botão Carregar edição para verificar os participantes vinculados.

Resultado esperado:

- Recuperação dos três participantes.
- Recuperação das quatro despesas.
- Preservação dos valores e tipos.
- Preservação dos vínculos.
- Ausência de registros duplicados.
- Total geral de R$ 210,00.

Resultado obtido: todos os itens foram confirmados nas telas do sistema.

Resultado: aprovado.

Evidências:

- [Recuperação do planejamento](evidencias/09-persistencia.png)
- [Vínculo da atividade recreativa](evidencias/09b-vinculos-recuperados.png)
- [Vínculo da sobremesa](evidencias/09c-vinculo-sobremesa.png)
- [Vínculo do almoço](evidencias/09d-vinculo-almoco.png)
- [Vínculo do transporte](evidencias/09e-vinculo-transporte.png)

### Teste 6 — Arquivo ausente ou inválido

#### 6.1. Arquivo ausente

Para realizar o teste sem modificar os dados originais, foi utilizada uma pasta temporária.

Comandos executados a partir da pasta principal do projeto:

```powershell
$projeto = (Get-Location).Path
$pastaTeste = Join-Path $env:TEMP ("CabeNoBolso-Ausente-" + [guid]::NewGuid().ToString())
New-Item -ItemType Directory -Path $pastaTeste
Push-Location $pastaTeste
java -cp "$projeto" view.TelaPrincipal
Pop-Location
```

Resultado esperado: informar a ausência do arquivo e permitir iniciar um planejamento vazio.

Resultado obtido: o sistema apresentou a mensagem de arquivo não encontrado e iniciou um planejamento com total de R$ 0,00.

Resultado: aprovado.

Evidências:

- [Mensagem de arquivo ausente](evidencias/10-arquivo-ausente.png)
- [Planejamento vazio](evidencias/10b-planejamento-vazio.png)

#### 6.2. Arquivo inválido

Foi criado um arquivo incompatível em uma pasta temporária, preservando os dados originais.

```powershell
$projeto = (Get-Location).Path
$pastaTeste = Join-Path $env:TEMP ("CabeNoBolso-Invalido-" + [guid]::NewGuid().ToString())
New-Item -ItemType Directory -Path "$pastaTeste\dados"
Set-Content -Path "$pastaTeste\dados\cabenoBolso.dat" -Value "Arquivo invalido para teste"
Push-Location $pastaTeste
java -cp "$projeto" view.TelaPrincipal
Get-Content ".\dados\cabenoBolso.dat"
Pop-Location
```

Resultado esperado: detectar a falha de leitura, apresentar uma mensagem e impedir a sobrescrita do arquivo pela interface.

Resultado obtido: o sistema apresentou uma mensagem de erro de persistência, bloqueou os botões da tela principal e preservou o conteúdo do arquivo inválido.

Também foi executado o teste:

```powershell
java -cp . testes.TesteArquivoInvalido
```

Saída obtida:

```text
Erro detectado corretamente!
Mensagem: invalid stream header: 41727175
Arquivo de teste liberado com sucesso!
TESTE APROVADO!
```

Evidências:

- [Erro de persistência](evidencias/11-arquivo-invalido.png)
- [Bloqueio dos botões](evidencias/12-bloqueio-botoes.png)
- [Preservação do arquivo e teste aprovado](evidencias/13-teste-aprovado.png)

Resultado: aprovado para a detecção de arquivo inválido na inicialização e para a proteção contra sobrescrita pela interface.

Limitação: a preservação de um planejamento previamente aberto durante uma nova tentativa de carregamento não foi testada diretamente.

## 6. Resumo dos resultados

| Teste | Situação |
|---|---|
| Cenário de referência | Aprovado |
| Distribuição de centavos | Aprovado |
| Validação de valor negativo | Aprovado |
| Validação de descrição vazia | Aprovado |
| Validação sem participantes | Aprovado |
| Alteração de orçamento | Aprovado |
| Exclusão de despesa | Aprovado |
| Salvamento e recuperação | Aprovado |
| Recuperação dos vínculos | Aprovado |
| Arquivo ausente | Aprovado |
| Arquivo inválido na inicialização | Aprovado |
| Preservação de planejamento previamente aberto | Não testado diretamente |

## 7. Correções realizadas

Durante os testes de persistência, foi identificada a necessidade de garantir o fechamento dos fluxos de leitura mesmo quando ocorre uma falha na criação do ObjectInputStream.

A implementação foi ajustada utilizando try-with-resources, declarando separadamente os fluxos de arquivo e de objetos.

Após a correção e a recompilação do projeto, o teste de arquivo inválido confirmou a liberação do arquivo utilizado.

## 8. Conclusão

Os testes realizados demonstraram o funcionamento das principais funcionalidades do Cabe no Bolso, incluindo o rateio de despesas, o controle de orçamento, as validações de entrada, o recálculo dos valores e a recuperação dos dados.

As evidências foram organizadas na pasta evidencias do repositório para permitir a conferência dos resultados.

A limitação relacionada ao carregamento de um planejamento previamente aberto foi registrada para avaliação e possíveis melhorias futuras.