
package testes;

import Model.Participante;
import Model.DespesaFixa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TesteDespesaFixa {

    public static void main(String[] args) {

        // 1. Criar os participantes
        Participante p1 = new Participante(
            1, "Ana", new BigDecimal("500.00")
        );

        Participante p2 = new Participante(
            2, "Bruno", new BigDecimal("500.00")
        );

        Participante p3 = new Participante(
            3, "Carla", new BigDecimal("500.00")
        );

        // 2. Criar a lista de participantes
        List<Participante> participantes = new ArrayList<>();

        participantes.add(p1);
        participantes.add(p2);
        participantes.add(p3);

        // 3. Criar uma despesa fixa de R$ 100,00
        DespesaFixa despesa = new DespesaFixa(
            "Aluguel", new BigDecimal("100.00")
        );

        // 4. Calcular a divisão da despesa
        Map<Integer, BigDecimal> resultado =
            despesa.calcularValorPorPessoa(participantes);

        // 5. Mostrar os resultados
        System.out.println("=== TESTE DE DESPESA FIXA ===");

        for (Map.Entry<Integer, BigDecimal> entrada
                : resultado.entrySet()) {

            System.out.println(
                "Participante " + entrada.getKey()
                + ": R$ " + entrada.getValue()
            );
        }
    }
}
