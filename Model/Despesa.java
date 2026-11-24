
package Model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.io.Serializable;

public abstract class Despesa implements Serializable {

    private static final long serialVersionUID = 1L;

    private String descricao;
    private BigDecimal valor;

    public Despesa(String descricao, BigDecimal valor) {

        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição inválida!");
        }

        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inválido!");
        }

        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public abstract Map<Integer, BigDecimal> calcularValorPorPessoa(
        List<Participante> participantes);
}
