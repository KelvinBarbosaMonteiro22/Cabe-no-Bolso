package Model;

import java.util.ArrayList;
import java.util.List;

public class Planejamento {

    private List<Participante> participantes;
    private List<Despesa> despesas;

    public Planejamento() {
        this.participantes = new ArrayList<>();
        this.despesas = new ArrayList<>();
    }
public void adicionarParticipante(Participante participante) {
    if (participante == null) {
        throw new IllegalArgumentException("Participante inválido!");
    }

    participantes.add(participante);
    }

public void adicionarDespesa(Despesa despesa) {
    if (despesa == null) {
        throw new IllegalArgumentException("Despesa inválida!");
    }

    despesas.add(despesa);
    }

public List<Participante> getParticipantes() {
    return participantes;
    }

public List<Despesa> getDespesas() {
    return despesas;
    }

}
