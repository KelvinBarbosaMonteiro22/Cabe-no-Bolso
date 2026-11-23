package controller;

import Model.Planejamento;

import Model.Participante;

public class ControlePlanejamento {

    private Planejamento planejamento;

    public ControlePlanejamento() {
        this.planejamento = new Planejamento();
    }

public void adicionarParticipante(Participante participante) {
    planejamento.adicionarParticipante(participante);
}

}
