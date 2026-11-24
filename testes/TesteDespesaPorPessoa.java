
package testes;

import Model.Participante;
import Model.DespesaPorPessoa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TesteDespesaPorPessoa {

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

        // 3. Criar uma despesa de R$ 20 por pessoa
        DespesaPorPessoa despesa = new DespesaPorPessoa(
            "Almoço", new BigDecimal("20.00")
        );

        // 4. Calcular o valor de cada participante
        Map<Integer, BigDecimal> resultado =
            despesa.calcularValorPorPessoa(participantes);

        // 5. Mostrar os resultados
        System.out.println("=== TESTE DE DESPESA POR PESSOA ===");

        for (Map.Entry<Integer, BigDecimal> entrada
                : resultado.entrySet()) {

            System.out.println(
                "Participante " + entrada.getKey()
                + ": R$ " + entrada.getValue()
            );
        }
    }
}
