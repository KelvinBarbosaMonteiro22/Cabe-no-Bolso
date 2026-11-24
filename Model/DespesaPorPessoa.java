
package Model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DespesaPorPessoa extends Despesa {

    private static final long serialVersionUID = 1L;
    
    public DespesaPorPessoa(String descricao, BigDecimal valor) {
        super(descricao, valor);
    }

    @Override
    public Map<Integer, BigDecimal> calcularValorPorPessoa(
            List<Participante> participantes) {

        Map<Integer, BigDecimal> resultado = new LinkedHashMap<>();
        Set<Integer> codigos = new HashSet<>();

        if (participantes == null || participantes.isEmpty()) {
            throw new IllegalArgumentException(
                "Selecione participantes!");
        }

        for (Participante participante : participantes) {

            if (participante == null) {
                throw new IllegalArgumentException(
                    "Participante inválido!");
            }

            if (!codigos.add(participante.getCodigo())) {
                throw new IllegalArgumentException(
                    "Participante repetido!");
            }

            resultado.put(
                participante.getCodigo(),
                getValor()
            );
        }

        return resultado;
    }
}
