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

#### 6.2. Arquivo inválido — versão anterior (registro histórico)

Na versão anterior, foi criado um arquivo incompatível em uma pasta temporária, preservando os dados originais.

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

Resultado histórico: detecção e proteção contra sobrescrita aprovadas; início de um novo planejamento não atendido nessa versão. Os comandos desta subseção registram o procedimento antigo, não o roteiro da versão atual.

Limitação: a preservação de um planejamento previamente aberto durante uma nova tentativa de carregamento não foi testada diretamente.

#### 6.3. Arquivo inválido — recuperação na versão atual

Execute na raiz do projeto, após compilar, em um mesmo terminal PowerShell. O procedimento usa uma pasta temporária e não altera os dados originais do projeto.

```powershell
$projeto = (Get-Location).Path
$pastaTeste = Join-Path $env:TEMP ("CabeNoBolso-Invalido-" + [guid]::NewGuid().ToString())
New-Item -ItemType Directory -Path "$pastaTeste\dados"
Set-Content -Path "$pastaTeste\dados\cabenoBolso.dat" -Value "Arquivo invalido para teste"
Push-Location $pastaTeste
try {
    java -cp "$projeto" view.TelaPrincipal
} finally {
    Pop-Location
}
```

Ao abrir a aplicação, confirme o aviso, consulte o resumo vazio e cadastre Lucas (código 1, orçamento R$ 100,00). Feche todas as janelas e execute:

```powershell
Get-ChildItem "$pastaTeste\dados" -Name
Get-Content "$pastaTeste\dados\*.bak"
```

| Verificação | Esperado | Obtido |
|---|---|---|
| Falha de leitura | Aviso de arquivo inválido | Aviso apresentado |
| Backup | Arquivo .bak com conteúdo original | Arquivo encontrado; conteúdo: Arquivo invalido para teste |
| Interface | Botões habilitados | Botões habilitados |
| Planejamento inicial | Vazio, total zero | Tabela vazia, R$ 0 |
| Novo cadastro | Lucas, código 1, R$ 100,00 | Cadastro apresentado |
| Novo arquivo | cabenoBolso.dat | Arquivo encontrado |
| Reinicialização | Recuperar Lucas | Lucas recuperado com orçamento de R$ 100,00 |

Para reiniciar na mesma pasta temporária:

```powershell
Push-Location $pastaTeste
try {
    java -cp "$projeto" view.TelaPrincipal
} finally {
    Pop-Location
}
```

Consulte Gerenciar participantes e confira o cadastro. Resultado: aprovado para a recuperação na inicialização, preservação do backup e salvamento e recuperação dos novos dados. Uma falha de carregamento com planejamento já aberto em memória não foi testada diretamente.

Evidências:

- [Aviso de recuperação](evidencias/14-recuperacao-arquivo-invalido.png)
- [Botões habilitados](evidencias/15-botoes-habilitados-apos-erro.png)
- [Planejamento vazio](evidencias/16-planejamento-vazio-apos-erro.png)
- [Cadastro após recuperação](evidencias/17-cadastro-apos-recuperacao.png)
- [Backup preservado](evidencias/18-backup-preservado.png)
- [Dados recuperados após erro](evidencias/19-dados-recuperados-apos-erro.png)

### Teste 7 — Cenário próprio: passeio fictício da turma

Utilize o planejamento temporário do teste 6.3, mantendo Lucas, ou inicie um planejamento vazio isolado e cadastre os quatro participantes.

| Código | Participante | Orçamento |
|---|---|---:|
| 1 | Lucas | R$ 100,00 |
| 2 | Pedro | R$ 80,00 |
| 3 | Júlia | R$ 120,00 |
| 4 | Maria | R$ 90,00 |

| Despesa | Tipo | Valor informado | Participantes |
|---|---|---:|---|
| Transporte | Fixa | R$ 120,00 | Todos |
| Alimentação | Por pessoa | R$ 35,00 | Todos |
| Cinema | Por pessoa | R$ 25,00 | Lucas e Júlia |
| Lanche | Fixa | R$ 40,00 | Todos |

Rateio esperado (em reais):

| Participante | Transporte | Alimentação | Cinema | Lanche | Total |
|---|---:|---:|---:|---:|---:|
| Lucas | 30 | 35 | 25 | 10 | 100 |
| Pedro | 30 | 35 | 0 | 10 | 75 |
| Júlia | 30 | 35 | 25 | 10 | 100 |
| Maria | 30 | 35 | 0 | 10 | 75 |

| Participante | Esperado | Obtido | Excedente obtido | Situação obtida |
|---|---:|---:|---:|---|
| Lucas | R$ 100,00 | R$ 100,00 | R$ 0,00 | Dentro do limite |
| Pedro | R$ 75,00 | R$ 75,00 | R$ 0,00 | Dentro do limite |
| Júlia | R$ 100,00 | R$ 100,00 | R$ 0,00 | Dentro do limite |
| Maria | R$ 75,00 | R$ 75,00 | R$ 0,00 | Dentro do limite |
| Total | R$ 350,00 | R$ 350,00 | R$ 0,00 | |

O orçamento agregado é R$ 390,00 e a diferença calculada é R$ 40,00. Lucas está exatamente no limite. Os totais individuais são compatíveis com o rateio seletivo do cinema.

Feche todas as janelas, reinicie na mesma pasta temporária com o comando do teste 6.3 e consulte o resumo. Após a reinicialização, foram observados os mesmos quatro participantes, valores individuais e total geral de R$ 350,00. Não foi feita uma inspeção individual dos vínculos de cada despesa após essa reinicialização neste cenário; essa inspeção foi realizada no cenário de referência (teste 5).

Resultado: aprovado para os cálculos, limites e recuperação do resumo.

Evidências:

- [Participantes](evidencias/20-cenario-proprio-participantes.png)
- [Despesas](evidencias/21-cenario-proprio-despesas.png)
- [Resumo financeiro](evidencias/22-cenario-proprio-resumo.png)
- [Resumo após reiniciar](evidencias/23-cenario-proprio-persistencia.png)

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
| Arquivo inválido na inicialização e backup | Aprovado |
| Cadastro e recuperação após arquivo inválido | Aprovado |
| Cenário próprio: cálculos e orçamento | Aprovado |
| Cenário próprio: recuperação do resumo | Aprovado |
| Preservação de planejamento previamente aberto | Não testado diretamente |

## 7. Correções realizadas

Durante os testes de persistência, foi identificada a necessidade de garantir o fechamento dos fluxos de leitura mesmo quando ocorre uma falha na criação do ObjectInputStream.

A implementação foi ajustada utilizando try-with-resources, declarando separadamente os fluxos de arquivo e de objetos.

Após a correção e a recompilação do projeto, o teste de arquivo inválido confirmou a liberação do arquivo utilizado.

Na versão anterior, os botões eram bloqueados após falha de carregamento. Na versão atual, a recuperação testada preserva o arquivo inválido em um backup e permite iniciar um novo planejamento, cadastrar e recuperar novos dados.

## 8. Conclusão

Os testes realizados demonstraram o funcionamento das principais funcionalidades do Cabe no Bolso, incluindo o rateio de despesas, o controle de orçamento, as validações de entrada, o recálculo dos valores e a recuperação dos dados.

As 30 evidências foram organizadas na pasta evidencias do projeto para permitir a conferência dos resultados.

A limitação relacionada ao carregamento de um planejamento previamente aberto foi registrada para avaliação e possíveis melhorias futuras.