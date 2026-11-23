package Model;

import java.math.BigDecimal;

public class Participante {


    private int codigo;

    private String nome; 

    private BigDecimal orcamento; 

     public Participante(int codigo, String nome, BigDecimal orcamento) {


        if (codigo <= 0) {
        throw new IllegalArgumentException("Código inválido!");
    }

    if (nome == null || nome.trim().isEmpty()) {
        throw new IllegalArgumentException("Nome não pode ficar vazio!");
    }

    if (orcamento == null || orcamento.compareTo(BigDecimal.ZERO) < 0) {
        throw new IllegalArgumentException("Orçamento inválido!");
    }


        this.codigo = codigo;
        this.nome = nome;
        this.orcamento = orcamento;

    
}

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getOrcamento() {
        return orcamento;
    }

    public void setNome(String nome) {


        if (nome == null || nome.trim().isEmpty()) {
        throw new IllegalArgumentException("Nome inválido!");
        }

        this.nome = nome;

    }


    public void setOrcamento(BigDecimal orcamento) {

        if (orcamento == null || orcamento.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Orçamento inválido!");
        }

        this.orcamento = orcamento;

    }

}
