# Cabe no Bolso — Planejador de Despesas Compartilhadas

## 1. Sobre o projeto

O Cabe no Bolso é uma aplicação desktop desenvolvida em Java para organizar despesas compartilhadas, calcular gastos individuais e verificar se os participantes estão dentro de seus respectivos orçamentos.

O projeto foi desenvolvido como atividade final da disciplina de Programação Orientada a Objetos do curso de Análise e Desenvolvimento de Sistemas da Universidade de Fortaleza (Unifor).

## 2. Objetivo

Facilitar o planejamento financeiro de atividades em grupo, permitindo distribuir despesas de maneira organizada e identificar participantes que ultrapassaram seus limites de orçamento.

## 3. Funcionalidades

- Cadastro, edição e exclusão de participantes.
- Definição de orçamento individual.
- Cadastro, edição e exclusão de despesas.
- Despesas fixas e despesas por pessoa.
- Seleção dos participantes envolvidos em cada despesa.
- Distribuição precisa dos centavos.
- Cálculo dos gastos individuais.
- Cálculo do custo total do planejamento.
- Identificação dos valores excedidos.
- Salvamento e recuperação dos dados.
- Validação de entradas inválidas.
- Bloqueio da exclusão de participantes vinculados a despesas.
- Preservação do arquivo inválido em backup e início de um novo planejamento.
- Bloqueio das operações quando não é possível preservar o arquivo com segurança.

## 4. Tecnologias utilizadas

- Java 21 LTS.
- Eclipse Temurin JDK 21.0.13.1.
- Java Swing.
- Programação Orientada a Objetos.
- Arquitetura MVC.
- BigDecimal.
- Coleções genéricas.
- Serialização de objetos.
- Visual Studio Code.
- Git e GitHub.

## 5. Requisitos e dependências

Para executar o projeto, recomenda-se:

- JDK 21.
- Git, caso utilize a clonagem do repositório.
- Visual Studio Code com suporte a Java ou outra IDE Java.

O projeto utiliza bibliotecas padrão do Java, sem necessidade de dependências externas.

Versão utilizada no desenvolvimento e nos testes:

- Java: 21.0.13.1 LTS.
- Javac: 21.0.13.1.

Para verificar a instalação:

```powershell
java -version
javac -version
```

## 6. Organização do projeto

```text
Cabe-no-Bolso/
├── Model/
│   ├── Participante.java
│   ├── Planejamento.java
│   ├── Despesa.java
│   ├── DespesaFixa.java
│   └── DespesaPorPessoa.java
├── controller/
│   ├── ParticipanteController.java
│   └── DespesaController.java
├── persistence/
│   ├── Persistencia.java
│   └── PersistenciaArquivo.java
├── view/
│   ├── TelaPrincipal.java
│   ├── TelaParticipantes.java
│   ├── TelaDespesas.java
│   └── TelaResumo.java
├── testes/
│   ├── TesteParticipante.java
│   ├── TestePlanejamento.java
│   ├── TesteDespesaFixa.java
│   ├── TesteDespesaPorPessoa.java
│   ├── TestePersistencia.java
│   ├── TesteCarregar.java
│   └── TesteArquivoInvalido.java
├── dados/ (gerada localmente)
├── evidencias/ (30 imagens)
├── TESTES.md
├── Cabe_no_Bolso_UML.pdf
├── .gitignore
└── README.md
```

## 7. Arquitetura MVC

### Model

Responsável pelos dados, cálculos financeiros, validações e regras de negócio.

### Controller

Responsável por encaminhar as operações solicitadas pela interface ao modelo e acionar a persistência.

### View

Responsável pelas telas gráficas, recebimento das entradas e apresentação dos resultados ao usuário.

### Persistence

Responsável pelo salvamento e carregamento dos objetos em arquivo local.

## 8. Programação Orientada a Objetos

### Encapsulamento

Os atributos das classes são protegidos por modificadores de acesso privado. O acesso ocorre por meio de métodos públicos e operações controladas.

### Abstração

A classe abstrata Despesa representa as características comuns das despesas e define o contrato de cálculo dos valores individuais.

### Herança

As classes DespesaFixa e DespesaPorPessoa herdam características da classe Despesa.

### Polimorfismo

Cada especialização de Despesa implementa sua própria forma de calcular o valor por participante.

O planejamento trabalha com referências do tipo Despesa, permitindo utilizar os dois tipos de cálculo.

### Interface

A interface Persistencia define as operações de salvar e carregar, implementadas pela classe PersistenciaArquivo.

## 9. Coleções utilizadas

- List: armazenamento de participantes e despesas.
- ArrayList: organização de listas dinâmicas.
- Map: associação entre despesas, participantes e valores individuais.
- LinkedHashMap: armazenamento de associações com ordem de inserção.
- Set: identificação de códigos repetidos.

Essas coleções facilitam a organização, consulta e validação dos dados.

## 10. Diagrama UML

O diagrama apresenta os atributos, métodos, visibilidades e relacionamentos das classes utilizadas no projeto.

[Consultar diagrama de classes UML](Cabe_no_Bolso_UML.pdf)

## 11. Como instalar e executar

### Baixar o projeto

Clone o repositório utilizando:

```powershell
git clone https://github.com/KelvinBarbosaMonteiro22/Cabe-no-Bolso.git
```

Entre na pasta:

```powershell
cd Cabe-no-Bolso
```

Também é possível baixar o projeto pelo GitHub utilizando a opção Download ZIP e extrair os arquivos.

### Compilar

Execute na pasta principal do projeto:

```powershell
javac -encoding UTF-8 Model/*.java controller/*.java persistence/*.java view/*.java testes/*.java
```

### Executar

```powershell
java -cp . view.TelaPrincipal
```

A tela principal apresentará as opções:

- Gerenciar participantes.
- Gerenciar despesas.
- Consultar resumo.

Na primeira execução, caso não exista um arquivo de dados, o sistema apresentará uma mensagem e iniciará um planejamento vazio.

## 12. Regras de negócio

### Despesa fixa

O valor total é dividido entre os participantes selecionados.

Exemplo:

R$ 90,00 divididos entre três participantes resultam em R$ 30,00 para cada pessoa.

### Despesa por pessoa

Cada participante selecionado recebe o valor integral informado.

Exemplo:

Uma despesa de R$ 20,00 por pessoa, envolvendo três participantes, possui custo total de R$ 60,00.

### Distribuição dos centavos

Os valores financeiros são representados utilizando BigDecimal.

Quando uma divisão fixa possui centavos restantes, eles são distribuídos pela ordem crescente dos códigos dos participantes.

Exemplo:

| Código | Valor individual |
|---|---:|
| 1 | R$ 33,34 |
| 2 | R$ 33,33 |
| 3 | R$ 33,33 |
| Total | R$ 100,00 |

### Limite de orçamento

Quando o total de despesas é igual ou inferior ao orçamento, o participante está dentro do limite.

Quando ultrapassa o orçamento, o sistema apresenta o valor excedido.

## 13. Caso de referência

Para reproduzir o caso apresentado na atividade, utilize um planejamento vazio.

Cadastre os seguintes participantes:

| Código | Participante | Orçamento |
|---|---|---:|
| 1 | Ana | R$ 70,00 |
| 2 | Bruno | R$ 60,00 |
| 3 | Carla | R$ 70,00 |

Cadastre as despesas:

| Despesa | Tipo | Valor | Participantes |
|---|---|---:|---|
| Transporte | Fixa | R$ 90,00 | Ana, Bruno e Carla |
| Almoço | Por pessoa | R$ 20,00 | Ana, Bruno e Carla |
| Atividade recreativa | Fixa | R$ 30,00 | Ana e Carla |
| Sobremesa | Por pessoa | R$ 10,00 | Ana, Bruno e Carla |

### Resultados do rateio

| Participante | Transporte | Almoço | Atividade | Sobremesa | Total |
|---|---:|---:|---:|---:|---:|
| Ana | 30,00 | 20,00 | 15,00 | 10,00 | 75,00 |
| Bruno | 30,00 | 20,00 | 0,00 | 10,00 | 60,00 |
| Carla | 30,00 | 20,00 | 15,00 | 10,00 | 75,00 |

### Resumo financeiro obtido

| Participante | Orçamento | Despesas | Excedente | Situação |
|---|---:|---:|---:|---|
| Ana | R$ 70,00 | R$ 75,00 | R$ 5,00 | Acima do limite |
| Bruno | R$ 60,00 | R$ 60,00 | R$ 0,00 | Dentro do limite |
| Carla | R$ 70,00 | R$ 75,00 | R$ 5,00 | Acima do limite |

Custo total esperado: R$ 210,00.

Custo total obtido: R$ 210,00.

Os resultados do resumo financeiro correspondem aos valores esperados no caso de referência.

## 14. Persistência dos dados

O planejamento é armazenado localmente no arquivo:

```text
dados/cabenoBolso.dat
```

O sistema utiliza:

- Serializable.
- serialVersionUID.
- ObjectOutputStream.
- ObjectInputStream.
- FileOutputStream.
- FileInputStream.
- Try-with-resources.

Os dados são recuperados quando a aplicação é iniciada novamente.

Os arquivos de dados, backups e arquivos compilados são ignorados pelo Git.

Na inicialização, se o carregamento falhar e não houver planejamento em memória, a TelaPrincipal tenta mover o arquivo original para `dados/cabenoBolso-invalido-<UUID>.bak`. Se a operação for bem-sucedida, inicia um planejamento vazio e mantém o caminho `dados/cabenoBolso.dat` para os novos salvamentos. O backup preserva o conteúdo original, mas não é restaurado automaticamente. Se a movimentação falhar, os botões são bloqueados.

## 15. Tratamento de erros

O sistema possui validações para:

- Código de participante duplicado.
- Campos vazios.
- Orçamento negativo.
- Despesa com valor inválido.
- Despesa sem participantes.
- Participantes repetidos.
- Exclusão de participantes vinculados a despesas.
- Arquivo inexistente.
- Arquivo inválido.

Quando um arquivo inválido é encontrado durante a inicialização, o sistema apresenta um aviso, tenta preservar o arquivo em backup e permite iniciar um planejamento vazio se a operação for bem-sucedida. Caso não consiga preservar o arquivo, bloqueia os botões para evitar sobrescrita. O código também prevê manter um planejamento já existente em memória em caso de falha, mas esse fluxo não foi testado diretamente.

## 16. Execução dos testes

Compile o projeto antes de executar os testes.

### Teste de participante

```powershell
java -cp . testes.TesteParticipante
```

### Teste de planejamento

```powershell
java -cp . testes.TestePlanejamento
```

### Teste de despesa fixa

```powershell
java -cp . testes.TesteDespesaFixa
```

### Teste de despesa por pessoa

```powershell
java -cp . testes.TesteDespesaPorPessoa
```

### Teste de persistência

```powershell
java -cp . testes.TestePersistencia
```

### Teste de carregamento

Execute após o TestePersistencia:

```powershell
java -cp . testes.TesteCarregar
```

### Teste de arquivo inválido

```powershell
java -cp . testes.TesteArquivoInvalido
```

Resultado obtido:

```text
Erro detectado corretamente!
Mensagem: invalid stream header: 41727175
Arquivo de teste liberado com sucesso!
TESTE APROVADO!
```

## 17. Roteiro de testes obrigatórios

Utilize o caso de referência para realizar os testes manuais.

| Teste | Entrada ou operação | Resultado esperado |
|---|---|---|
| Rateio de centavos | Despesa fixa de R$ 100,00 para os códigos 1, 2 e 3 | R$ 33,34, R$ 33,33 e R$ 33,33 |
| Entrada inválida | Valor negativo, campo vazio ou despesa sem participantes | Mensagem de erro sem alteração dos dados |
| Alteração de orçamento | Alterar orçamento de Ana para R$ 80,00 | Despesas de R$ 75,00, dentro do limite |
| Exclusão de despesa | Excluir atividade recreativa | R$ 60,00 por pessoa e total de R$ 180,00 |
| Salvar e reiniciar | Fechar e abrir o sistema | Recuperar dados sem duplicação |
| Arquivo ausente | Executar sem arquivo de dados | Mensagem e novo planejamento |
| Arquivo inválido | Executar com arquivo de teste inválido | Aviso, backup preservado e novo planejamento; bloqueio se a movimentação falhar |

Restaure o caso de referência entre os testes que modificam os dados.

Realize os testes de arquivo ausente e inválido em uma pasta isolada, sem modificar o arquivo original do planejamento.

### Resultados confirmados

- Rateio de R$ 100,00: R$ 33,34, R$ 33,33 e R$ 33,33.
- Caso de referência: total de R$ 210,00.
- Recuperação dos dados após reiniciar.
- Detecção de arquivo ausente.
- Detecção e liberação de arquivo inválido.
- Na versão anterior, bloqueio dos botões após falha de carregamento (evidências 11 e 12).
- Na versão atual, início de planejamento vazio após preservar o arquivo inválido, cadastro de Lucas e recuperação após reiniciar (evidências 14 a 19).
- Cenário próprio com quatro participantes: total de R$ 350,00 antes e após reiniciar.
- Preservação do conteúdo do arquivo inválido utilizado no teste isolado.

## 18. Problemas identificados e corrigidos

Durante os testes, foi identificado um problema no fechamento dos fluxos de leitura quando um arquivo inválido era carregado.

A correção consistiu em declarar FileInputStream e ObjectInputStream separadamente no try-with-resources.

Após a recompilação, o teste confirmou que o arquivo temporário foi liberado corretamente.

Inicialmente, foi acrescentado um controle de erro na TelaPrincipal para bloquear as funcionalidades após falha de carregamento. Na versão atual, o arquivo inválido é movido com Files.move para um nome único gerado por UUID, permitindo iniciar um planejamento vazio sem sobrescrever o conteúdo original. O bloqueio permanece como proteção caso a movimentação falhe. O diagrama UML foi atualizado com a constante CAMINHO_ARQUIVO.

## 19. Limitações e melhorias futuras

- Suporte a apenas um planejamento por vez.
- Possibilidade de exportar o resumo financeiro em PDF.
- Melhoria da apresentação visual das telas.
- Implementação de um simulador de alternativas.
- Ampliação dos testes automatizados.
- Implementação de gravação atômica e restauração assistida de backups.
- Teste específico da falha na movimentação do arquivo para backup.
- Validação de uma falha de carregamento com planejamento já em uso.

## 20. Objetivo acadêmico

O projeto busca aplicar os conhecimentos estudados na disciplina de Programação Orientada a Objetos, relacionando conceitos teóricos com o desenvolvimento de uma aplicação desktop.

## 21. Referências

- Material da disciplina de Programação Orientada a Objetos — Unifor.
- Proposta da atividade final Cabe no Bolso, 2026.
- Documentação oficial do Java: https://docs.oracle.com/en/java/javase/21/

## 22. Cenário próprio — passeio fictício

Em um planejamento vazio, cadastre:

| Código | Participante | Orçamento |
|---|---|---:|
| 1 | Lucas | R$ 100,00 |
| 2 | Pedro | R$ 80,00 |
| 3 | Júlia | R$ 120,00 |
| 4 | Maria | R$ 90,00 |

| Despesa | Tipo | Valor | Participantes |
|---|---|---:|---|
| Transporte | Fixa | R$ 120,00 | Todos |
| Alimentação | Por pessoa | R$ 35,00 | Todos |
| Cinema | Por pessoa | R$ 25,00 | Lucas e Júlia |
| Lanche | Fixa | R$ 40,00 | Todos |

| Participante | Transporte | Alimentação | Cinema | Lanche | Total esperado e obtido |
|---|---:|---:|---:|---:|---:|
| Lucas | 30,00 | 35,00 | 25,00 | 10,00 | 100,00 |
| Pedro | 30,00 | 35,00 | 0,00 | 10,00 | 75,00 |
| Júlia | 30,00 | 35,00 | 25,00 | 10,00 | 100,00 |
| Maria | 30,00 | 35,00 | 0,00 | 10,00 | 75,00 |

O total esperado e obtido foi R$ 350,00, com orçamento agregado de R$ 390,00 e saldo de R$ 40,00. Todos ficaram dentro do limite, inclusive Lucas, cujo gasto foi igual ao orçamento. Após fechar e abrir novamente o sistema, o resumo manteve os mesmos valores. A conferência individual dos vínculos após reiniciar não foi repetida neste cenário.

## 23. Reprodução da recuperação de arquivo inválido

Com o projeto compilado, execute no PowerShell, a partir da pasta principal:

```powershell
$projeto = (Get-Location).Path
$pastaTeste = Join-Path $env:TEMP ("CabeNoBolso-Invalido-" + [guid]::NewGuid().ToString())
New-Item -ItemType Directory -Path "$pastaTeste\dados"
Set-Content -Path "$pastaTeste\dados\cabenoBolso.dat" -Value "Arquivo invalido para teste"
Push-Location $pastaTeste
try { java -cp "$projeto" view.TelaPrincipal } finally { Pop-Location }
```

Confirme o aviso, abra o resumo vazio e cadastre Lucas (código 1, orçamento 100.00). Feche a aplicação e, no mesmo terminal, execute:

```powershell
Get-ChildItem "$pastaTeste\dados" -Name
Get-Content "$pastaTeste\dados\*.bak"
```

Devem existir o novo arquivo `.dat` e o backup `.bak`, contendo o texto `Arquivo invalido para teste`. Reinicie na mesma pasta temporária:

```powershell
Push-Location $pastaTeste
try { java -cp "$projeto" view.TelaPrincipal } finally { Pop-Location }
```

Confirme o cadastro de Lucas. Para reproduzir o cenário próprio, complete os cadastros e despesas da seção 22 nessa mesma sessão, confira o resumo e repita a reinicialização. Não execute o teste de arquivo inválido sobre os dados originais.

## 24. Evidências

A pasta [evidencias](evidencias/) contém 30 imagens. As evidências 11 e 12 registram o comportamento anterior à correção, não o comportamento atual.

- [14 — Aviso de recuperação](evidencias/14-recuperacao-arquivo-invalido.png)
- [15 — Botões habilitados](evidencias/15-botoes-habilitados-apos-erro.png)
- [16 — Planejamento vazio](evidencias/16-planejamento-vazio-apos-erro.png)
- [17 — Cadastro após recuperação](evidencias/17-cadastro-apos-recuperacao.png)
- [18 — Backup preservado](evidencias/18-backup-preservado.png)
- [19 — Cadastro recuperado](evidencias/19-dados-recuperados-apos-erro.png)
- [20 — Participantes do cenário próprio](evidencias/20-cenario-proprio-participantes.png)
- [21 — Despesas do cenário próprio](evidencias/21-cenario-proprio-despesas.png)
- [22 — Resumo do cenário próprio](evidencias/22-cenario-proprio-resumo.png)
- [23 — Resumo após reiniciar](evidencias/23-cenario-proprio-persistencia.png)

Consulte também [TESTES.md](TESTES.md) para o registro detalhado dos testes.

## 25. Repositório

https://github.com/KelvinBarbosaMonteiro22/Cabe-no-Bolso