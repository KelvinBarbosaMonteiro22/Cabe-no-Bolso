
package testes;

import Model.Planejamento;
import Model.Participante;
import Model.Despesa;
import Model.DespesaFixa;
import Model.DespesaPorPessoa;

import persistence.PersistenciaArquivo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.io.IOException;

public class TestePersistencia {

    public static void main(String[] args) {

        try {

            // 1. Criar o planejamento
            Planejamento planejamento = new Planejamento();

            // 2. Criar os participantes
            Participante ana = new Participante(
                1, "Ana", new BigDecimal("70.00")
            );

            Participante bruno = new Participante(
                2, "Bruno", new BigDecimal("60.00")
            );

            Participante carla = new Participante(
                3, "Carla", new BigDecimal("70.00")
            );

            // 3. Adicionar participantes ao planejamento
            planejamento.adicionarParticipante(ana);
            planejamento.adicionarParticipante(bruno);
            planejamento.adicionarParticipante(carla);

            // 4. Criar as despesas
            Despesa transporte = new DespesaFixa(
                "Transporte", new BigDecimal("90.00")
            );

            Despesa almoco = new DespesaPorPessoa(
                "Almoço", new BigDecimal("20.00")
            );

            // 5. Adicionar despesas ao planejamento
            planejamento.adicionarDespesa(
                transporte, Arrays.asList(ana, bruno, carla)
            );

            planejamento.adicionarDespesa(
                almoco, Arrays.asList(ana, bruno, carla)
            );

            // 6. Definir arquivo exclusivo para testes
            PersistenciaArquivo persistencia =
                new PersistenciaArquivo(
                    "dados/testePersistencia.dat"
                );

            // 7. Salvar o planejamento
            persistencia.salvar(planejamento);

            System.out.println(
                "Planejamento salvo com sucesso!"
            );

            // 8. Carregar os dados salvos
            Planejamento recuperado = persistencia.carregar();

            System.out.println(
                "Planejamento carregado com sucesso!"
            );

            // 9. Mostrar os dados recuperados
            System.out.println(
                "Participantes: "
                + recuperado.getParticipantes().size()
            );

            System.out.println(
                "Despesas: "
                + recuperado.getDespesas().size()
            );

            System.out.println(
                "Custo total: R$ "
                + recuperado.calcularCustoTotal()
            );

        } catch (IOException | ClassNotFoundException e) {

            System.out.println(
                "Erro ao salvar ou carregar: " + e.getMessage()
            );

        }
    }
}
