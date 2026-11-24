
package controller;

import Model.Participante;
import Model.Planejamento;
import persistence.Persistencia;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class ParticipanteController {

    private Planejamento planejamento;
    private Persistencia persistencia;

    public ParticipanteController(
            Planejamento planejamento,
            Persistencia persistencia) {

        this.planejamento = planejamento;
        this.persistencia = persistencia;
    }

    public void cadastrar(
            int codigo, String nome, BigDecimal orcamento) {

        Participante participante =
            new Participante(codigo, nome, orcamento);

        planejamento.adicionarParticipante(participante);

        salvarDados();
    }

    public List<Participante> listarParticipantes() {
        return planejamento.getParticipantes();
    }

    public void editar(
            int codigo, String nome, BigDecimal orcamento) {

        planejamento.atualizarParticipante(
            codigo, nome, orcamento
        );

        salvarDados();
    }

    public void excluir(int codigo) {

        planejamento.removerParticipante(codigo);

        salvarDados();
    }

    private void salvarDados() {

        try {
            persistencia.salvar(planejamento);

        } catch (IOException erro) {

            throw new IllegalStateException(
                "Não foi possível salvar os dados: "
                + erro.getMessage()
            );
        }
    }
}
