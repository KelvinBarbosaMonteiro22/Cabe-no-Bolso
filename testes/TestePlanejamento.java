
package testes;

import Model.Planejamento;
import Model.Participante;
import Model.Despesa;
import Model.DespesaFixa;
import Model.DespesaPorPessoa;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class TestePlanejamento {

    public static void main(String[] args) {

        // 1. Criar o planejamento
        Planejamento planejamento = new Planejamento();

        // 2. Cadastrar participantes
        Participante ana = new Participante(
            1, "Ana", new BigDecimal("70.00")
        );

        Participante bruno = new Participante(
            2, "Bruno", new BigDecimal("60.00")
        );

        Participante carla = new Participante(
            3, "Carla", new BigDecimal("70.00")
        );

        planejamento.adicionarParticipante(ana);
        planejamento.adicionarParticipante(bruno);
        planejamento.adicionarParticipante(carla);

        // 3. Criar a lista de participantes
        List<Participante> todos = Arrays.asList(
            ana, bruno, carla
        );

        // 4. Cadastrar as despesas

        // Transporte: R$ 90 dividido entre todos
        Despesa transporte = new DespesaFixa(
            "Transporte", new BigDecimal("90.00")
        );

        planejamento.adicionarDespesa(transporte, todos);

        // Almoço: R$ 20 para cada pessoa
        Despesa almoco = new DespesaPorPessoa(
            "Almoço", new BigDecimal("20.00")
        );

        planejamento.adicionarDespesa(almoco, todos);

        // Atividade: R$ 30 dividido entre Ana e Carla
        Despesa atividade = new DespesaFixa(
            "Atividade recreativa", new BigDecimal("30.00")
        );

        planejamento.adicionarDespesa(
            atividade, Arrays.asList(ana, carla)
        );

        // Sobremesa: R$ 10 para cada pessoa
        Despesa sobremesa = new DespesaPorPessoa(
            "Sobremesa", new BigDecimal("10.00")
        );

        planejamento.adicionarDespesa(sobremesa, todos);

        // 5. Mostrar os resultados
        System.out.println("=== CABE NO BOLSO ===");

        for (Participante participante :
                planejamento.getParticipantes()) {

            int codigo = participante.getCodigo();

            BigDecimal total =
                planejamento.calcularTotalParticipante(codigo);

            BigDecimal excedente =
                planejamento.calcularExcedente(codigo);

            System.out.println("\nParticipante: "
                + participante.getNome());

            System.out.println("Total: R$ "
                + total.toPlainString());

            System.out.println("Orçamento: R$ "
                + participante.getOrcamento().toPlainString());

            if (excedente.compareTo(BigDecimal.ZERO) > 0) {
                System.out.println("Excedente: R$ "
                    + excedente.toPlainString());
            } else {
                System.out.println("Situação: Dentro do orçamento");
            }
        }

        System.out.println("\nCusto total: R$ "
            + planejamento.calcularCustoTotal().toPlainString());
    }
}
