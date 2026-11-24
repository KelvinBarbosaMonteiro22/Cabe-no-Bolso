
package view;

import Model.Participante;
import Model.Planejamento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;

public class TelaResumo extends JFrame {

    private Planejamento planejamento;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private JLabel labelTotal;

    public TelaResumo(Planejamento planejamento) {

        this.planejamento = planejamento;

        setTitle("Resumo das Despesas - Cabe no Bolso");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        modeloTabela = new DefaultTableModel(
            new Object[]{
                "Código",
                "Participante",
                "Orçamento",
                "Total de despesas",
                "Situação"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);

        JScrollPane painelRolagem = new JScrollPane(tabela);

        labelTotal = new JLabel("Total geral: R$ 0,00");
        labelTotal.setFont(new Font("Arial", Font.BOLD, 16));

        JButton botaoAtualizar = new JButton("Atualizar resumo");

        botaoAtualizar.addActionListener(e -> atualizarResumo());

        JPanel painelInferior = new JPanel(new BorderLayout());

        painelInferior.add(labelTotal, BorderLayout.WEST);
        painelInferior.add(botaoAtualizar, BorderLayout.EAST);

        JLabel titulo = new JLabel(
            "Resumo financeiro dos participantes",
            SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        add(titulo, BorderLayout.NORTH);
        add(painelRolagem, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        atualizarResumo();
    }

    private void atualizarResumo() {

        modeloTabela.setRowCount(0);

        for (Participante participante : planejamento.getParticipantes()) {

            BigDecimal orcamento = participante.getOrcamento();

            BigDecimal despesas =
            planejamento.calcularTotalParticipante(participante.getCodigo());

            BigDecimal excedente =
            planejamento.calcularExcedente(participante.getCodigo());

            String situacao;

            if (excedente.compareTo(BigDecimal.ZERO) > 0) {
                situacao = "Acima do limite";
            } else {
                situacao = "Dentro do limite";
            }

            modeloTabela.addRow(new Object[]{
                participante.getCodigo(),
                participante.getNome(),
                "R$ " + orcamento.toPlainString(),
                "R$ " + despesas.toPlainString(),
                situacao
            });
        }

        BigDecimal totalGeral = planejamento.calcularCustoTotal();

        labelTotal.setText(
            "Total geral: R$ " + totalGeral.toPlainString()
        );
    }
}
