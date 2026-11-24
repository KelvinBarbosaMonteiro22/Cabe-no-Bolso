
package testes;

import Model.Planejamento;
import Model.Participante;
import Model.Despesa;

import persistence.PersistenciaArquivo;

import java.io.IOException;

public class TesteCarregar {

    public static void main(String[] args) {

        try {

            // 1. Localizar o arquivo de teste
            PersistenciaArquivo persistencia =
                new PersistenciaArquivo(
                    "dados/testePersistencia.dat"
                );

            // 2. Recuperar o planejamento salvo
            Planejamento planejamento = persistencia.carregar();

            System.out.println("Dados recuperados do arquivo!");

            // 3. Mostrar os participantes
            System.out.println("\n--- PARTICIPANTES ---");

            for (Participante participante :
                    planejamento.getParticipantes()) {

                System.out.println(
                    participante.getCodigo()
                    + " - "
                    + participante.getNome()
                );
            }

            // 4. Mostrar as despesas
            System.out.println("\n--- DESPESAS ---");

            for (Despesa despesa :
                    planejamento.getDespesas()) {

                System.out.println(
                    despesa.getDescricao()
                    + " - R$ "
                    + despesa.getValor()
                );
            }

            // 5. Mostrar o custo total
            System.out.println(
                "\nCusto total: R$ "
                + planejamento.calcularCustoTotal()
            );

        } catch (IOException | ClassNotFoundException e) {

            System.out.println(
                "Erro ao carregar os dados: "
                + e.getMessage()
            );
        }
    }
}
