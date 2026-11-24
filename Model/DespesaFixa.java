
package Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DespesaFixa extends Despesa {

    private static final long serialVersionUID = 1L;

    public DespesaFixa(String descricao, BigDecimal valor) {
        super(descricao, valor);
    }

    @Override
    public Map<Integer, BigDecimal> calcularValorPorPessoa(
            List<Participante> participantes) {

        Map<Integer, BigDecimal> resultado = new LinkedHashMap<>();

        if (participantes == null || participantes.isEmpty()) {
            throw new IllegalArgumentException("Selecione participantes!");
        }

        Set<Integer> codigos = new HashSet<>();

        for (Participante participante : participantes) {

            if (participante == null) {
                throw new IllegalArgumentException("Participante inválido!");
            }

            if (!codigos.add(participante.getCodigo())) {
                throw new IllegalArgumentException("Participante repetido!");
            }
        }

        List<Participante> ordenados = new ArrayList<>(participantes);

        ordenados.sort((p1, p2) ->
                Integer.compare(p1.getCodigo(), p2.getCodigo()));

        BigDecimal valorCentavos = getValor().movePointRight(2);

        long totalCentavos = valorCentavos.longValueExact();
        long quantidade = ordenados.size();

        long valorBase = totalCentavos / quantidade;
        long resto = totalCentavos % quantidade;

        for (int i = 0; i < ordenados.size(); i++) {

            long centavos = valorBase;

            if (i < resto) {
                centavos++;
            }

            BigDecimal valorIndividual =
                    BigDecimal.valueOf(centavos, 2);

            resultado.put(
                    ordenados.get(i).getCodigo(),
                    valorIndividual
            );
        }

        return resultado;
    }
}
