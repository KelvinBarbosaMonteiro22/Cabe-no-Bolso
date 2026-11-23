package Model;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DespesaPorPessoa extends Despesa {

    public DespesaPorPessoa(String descricao, BigDecimal valor) {
        super(descricao, valor);
    }
    
@Override
public Map<Integer, BigDecimal> calcularValorPorPessoa(
        List<Participante> participantes) {

    Map<Integer, BigDecimal> resultado = new LinkedHashMap<>();

    if (participantes == null || participantes.isEmpty()) {
        throw new IllegalArgumentException("Selecione participantes!");
    }

    for (Participante participante : participantes) {

        resultado.put(
                participante.getCodigo(),
                getValor()
        );
    }

    return resultado;
}
}

