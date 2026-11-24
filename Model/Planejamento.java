
package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.io.Serializable;
import java.math.BigDecimal;

public class Planejamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Participante> participantes;
    private List<Despesa> despesas;

    // Guarda os participantes relacionados a cada despesa
    private Map<Despesa, List<Participante>> participantesPorDespesa;

    public Planejamento() {
        this.participantes = new ArrayList<>();
        this.despesas = new ArrayList<>();
        this.participantesPorDespesa = new LinkedHashMap<>();
    }

    // CADASTRAR PARTICIPANTE
    public void adicionarParticipante(Participante participante) {

        if (participante == null) {
            throw new IllegalArgumentException(
                "Participante inválido!"
            );
        }

        for (Participante p : participantes) {
            if (p.getCodigo() == participante.getCodigo()) {
                throw new IllegalArgumentException(
                    "Já existe um participante com esse código!"
                );
            }
        }

        participantes.add(participante);
    }

    // VALIDAR PARTICIPANTES SELECIONADOS
    private List<Participante> validarParticipantes(
            List<Participante> selecionados) {

        if (selecionados == null || selecionados.isEmpty()) {
            throw new IllegalArgumentException(
                "Selecione pelo menos um participante!"
            );
        }

        List<Participante> participantesValidos = new ArrayList<>();

        for (Participante selecionado : selecionados) {

            if (selecionado == null) {
                throw new IllegalArgumentException(
                    "Participante inválido!"
                );
            }

            Participante cadastrado = null;

            for (Participante p : participantes) {
                if (p.getCodigo() == selecionado.getCodigo()) {
                    cadastrado = p;
                    break;
                }
            }

            if (cadastrado == null) {
                throw new IllegalArgumentException(
                    "Participante não cadastrado no planejamento!"
                );
            }

            for (Participante p : participantesValidos) {
                if (p.getCodigo() == cadastrado.getCodigo()) {
                    throw new IllegalArgumentException(
                        "Participante repetido!"
                    );
                }
            }

            participantesValidos.add(cadastrado);
        }

        return participantesValidos;
    }

    // ADICIONAR DESPESA
    public void adicionarDespesa(
            Despesa despesa,
            List<Participante> selecionados) {

        if (despesa == null) {
            throw new IllegalArgumentException(
                "Despesa inválida!"
            );
        }

        if (despesas.contains(despesa)) {
            throw new IllegalArgumentException(
                "Essa despesa já foi adicionada!"
            );
        }

        List<Participante> participantesValidos =
            validarParticipantes(selecionados);

        despesas.add(despesa);

        participantesPorDespesa.put(
            despesa,
            participantesValidos
        );
    }

    // ATUALIZAR DESPESA
    public void atualizarDespesa(
            Despesa despesaAntiga,
            Despesa novaDespesa,
            List<Participante> selecionados) {

        int indice = despesas.indexOf(despesaAntiga);

        if (indice == -1) {
            throw new IllegalArgumentException(
                "Despesa não encontrada!"
            );
        }

        if (novaDespesa == null) {
            throw new IllegalArgumentException(
                "Nova despesa inválida!"
            );
        }

        if (!despesaAntiga.equals(novaDespesa)
                && despesas.contains(novaDespesa)) {
            throw new IllegalArgumentException(
                "Essa nova despesa já está cadastrada!"
            );
        }

        List<Participante> participantesValidos =
            validarParticipantes(selecionados);

        despesas.set(indice, novaDespesa);

        participantesPorDespesa.remove(despesaAntiga);

        participantesPorDespesa.put(
            novaDespesa,
            participantesValidos
        );
    }

    // BUSCAR PARTICIPANTE
    public Participante buscarParticipante(int codigo) {

        for (Participante p : participantes) {
            if (p.getCodigo() == codigo) {
                return p;
            }
        }

        throw new IllegalArgumentException(
            "Participante não encontrado!"
        );
    }

    // ATUALIZAR PARTICIPANTE
    public void atualizarParticipante(
            int codigo,
            String nome,
            BigDecimal orcamento) {

        Participante participante = buscarParticipante(codigo);

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Nome não pode ficar vazio!"
            );
        }

        if (orcamento == null ||
                orcamento.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                "Orçamento inválido!"
            );
        }

        participante.setNome(nome.trim());
        participante.setOrcamento(orcamento);
    }

    // REMOVER PARTICIPANTE
    public void removerParticipante(int codigo) {

        Participante participante = buscarParticipante(codigo);

        for (List<Participante> lista :
                participantesPorDespesa.values()) {

            for (Participante p : lista) {

                if (p.getCodigo() == codigo) {
                    throw new IllegalArgumentException(
                        "Não é possível excluir participante vinculado a despesas!"
                    );
                }
            }
        }

        participantes.remove(participante);
    }

    // REMOVER DESPESA
    public void removerDespesa(Despesa despesa) {

        if (despesa == null || !despesas.contains(despesa)) {
            throw new IllegalArgumentException(
                "Despesa não encontrada!"
            );
        }

        despesas.remove(despesa);
        participantesPorDespesa.remove(despesa);
    }

    // LISTAR PARTICIPANTES
    public List<Participante> getParticipantes() {
        return new ArrayList<>(participantes);
    }

    // LISTAR DESPESAS
    public List<Despesa> getDespesas() {
        return new ArrayList<>(despesas);
    }

    // BUSCAR PARTICIPANTES DE UMA DESPESA
    public List<Participante> getParticipantesDaDespesa(
            Despesa despesa) {

        List<Participante> selecionados =
            participantesPorDespesa.get(despesa);

        if (selecionados == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(selecionados);
    }

    // CALCULAR TOTAL DE UM PARTICIPANTE
    public BigDecimal calcularTotalParticipante(int codigo) {

        // Verifica se o participante existe
        buscarParticipante(codigo);

        BigDecimal total = BigDecimal.ZERO;

        for (Despesa despesa : despesas) {

            List<Participante> selecionados =
                participantesPorDespesa.get(despesa);

            if (selecionados == null) {
                continue;
            }

            Map<Integer, BigDecimal> valores =
                despesa.calcularValorPorPessoa(selecionados);

            BigDecimal valor = valores.get(codigo);

            if (valor != null) {
                total = total.add(valor);
            }
        }

        return total;
    }

    // CALCULAR CUSTO TOTAL
    public BigDecimal calcularCustoTotal() {

        BigDecimal total = BigDecimal.ZERO;

        for (Participante participante : participantes) {

            total = total.add(
                calcularTotalParticipante(
                    participante.getCodigo()
                )
            );
        }

        return total;
    }

    // CALCULAR EXCEDENTE DO ORÇAMENTO
    public BigDecimal calcularExcedente(int codigo) {

        Participante participante = buscarParticipante(codigo);

        BigDecimal total = calcularTotalParticipante(codigo);

        BigDecimal orcamento = participante.getOrcamento();

        BigDecimal excedente = total.subtract(orcamento);

        if (excedente.compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }

        return excedente;
    }
}
