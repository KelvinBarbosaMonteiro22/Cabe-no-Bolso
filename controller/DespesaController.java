
package controller;

import Model.Despesa;
import Model.DespesaFixa;
import Model.DespesaPorPessoa;
import Model.Participante;
import Model.Planejamento;
import persistence.Persistencia;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class DespesaController {

    private Planejamento planejamento;
    private Persistencia persistencia;

    public DespesaController(
            Planejamento planejamento,
            Persistencia persistencia) {

        this.planejamento = planejamento;
        this.persistencia = persistencia;
    }

    public void cadastrarDespesaFixa(
            String descricao,
            BigDecimal valor,
            List<Participante> participantes) {

        Despesa despesa =
            new DespesaFixa(descricao, valor);

        planejamento.adicionarDespesa(
            despesa, participantes
        );

        salvarDados();
    }

    public void cadastrarDespesaPorPessoa(
            String descricao,
            BigDecimal valor,
            List<Participante> participantes) {

        Despesa despesa =
            new DespesaPorPessoa(descricao, valor);

        planejamento.adicionarDespesa(
            despesa, participantes
        );

        salvarDados();
    }

    public List<Despesa> listarDespesas() {
        return planejamento.getDespesas();
    }

    public List<Participante> listarParticipantes() {
        return planejamento.getParticipantes();
    }


public List<Participante> listarParticipantesDaDespesa(
        Despesa despesa) {

    return planejamento.getParticipantesDaDespesa(despesa);
}




    
    public void editarDespesa(
        Despesa despesaAntiga,
        String descricao,
        BigDecimal valor,
        boolean fixa,
        List<Participante> participantes) {

    Despesa novaDespesa;

    if (fixa) {
        novaDespesa = new DespesaFixa(descricao, valor);
    } else {
        novaDespesa = new DespesaPorPessoa(descricao, valor);
    }

    planejamento.atualizarDespesa(
        despesaAntiga,
        novaDespesa,
        participantes
    );

    salvarDados();
}




    public void excluirDespesa(Despesa despesa) {

        planejamento.removerDespesa(despesa);

        salvarDados();
    }

    private void salvarDados() {

        try {
            persistencia.salvar(planejamento);

        } catch (IOException erro) {

            throw new IllegalStateException(
                "Erro ao salvar despesa: " + erro.getMessage()
            );
        }
    }
}
