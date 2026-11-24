package view;

import controller.DespesaController;
import Model.Despesa;
import Model.DespesaFixa;
import Model.Participante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TelaDespesas extends JFrame {

    private JTextField campoDescricao;
    private JTextField campoValor;

    private JComboBox<String> comboTipo;

    private JPanel painelParticipantes;
    private List<JCheckBox> caixasParticipantes;

    private DefaultTableModel modeloTabela;
    private JTable tabela;

    private DespesaController controller;

    // Despesa carregada para edição (null quando não há edição em andamento)
    private Despesa despesaEmEdicao;

    public TelaDespesas(DespesaController controller) {

        this.controller = controller;
        caixasParticipantes = new ArrayList<>();

        setTitle("Gerenciar despesas");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal =
            new JPanel(new BorderLayout(10, 15));

        painelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        JLabel titulo = new JLabel(
            "Gerenciamento de Despesas",
            SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel formulario = new JPanel(
            new GridLayout(3, 2, 10, 10)
        );

        campoDescricao = new JTextField();
        campoValor = new JTextField();

        comboTipo = new JComboBox<>(
            new String[]{"Fixa", "Por pessoa"}
        );

        formulario.add(new JLabel("Descrição:"));
        formulario.add(campoDescricao);

        formulario.add(new JLabel("Valor (R$):"));
        formulario.add(campoValor);

        formulario.add(new JLabel("Tipo de despesa:"));
        formulario.add(comboTipo);

        painelParticipantes = new JPanel(
            new GridLayout(0, 2, 10, 5)
        );

        painelParticipantes.setBorder(
            BorderFactory.createTitledBorder(
                "Selecione os participantes"
            )
        );

        atualizarParticipantes();

        modeloTabela = new DefaultTableModel(
            new Object[]{"Descrição", "Valor", "Tipo"}, 0
        ) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

        tabela = new JTable(modeloTabela);

        tabela.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane painelTabela =
            new JScrollPane(tabela);

        JButton botaoCadastrar =
            new JButton("Cadastrar");

        JButton botaoCarregarEdicao =
            new JButton("Carregar edição");

        JButton botaoSalvarEdicao =
            new JButton("Salvar edição");

        JButton botaoExcluir =
            new JButton("Excluir");

        JButton botaoLimpar =
            new JButton("Limpar");

        JPanel painelBotoes = new JPanel(
            new GridLayout(1, 5, 8, 8)
        );

        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(botaoCarregarEdicao);
        painelBotoes.add(botaoSalvarEdicao);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoLimpar);

        JPanel painelSuperior =
            new JPanel(new BorderLayout(10, 10));

        painelSuperior.add(titulo, BorderLayout.NORTH);
        painelSuperior.add(formulario, BorderLayout.CENTER);

        JPanel painelCentral =
            new JPanel(new BorderLayout(10, 10));

        painelCentral.add(
            painelParticipantes,
            BorderLayout.NORTH
        );

        painelCentral.add(
            painelTabela,
            BorderLayout.CENTER
        );

        painelPrincipal.add(
            painelSuperior,
            BorderLayout.NORTH
        );

        painelPrincipal.add(
            painelCentral,
            BorderLayout.CENTER
        );

        painelPrincipal.add(
            painelBotoes,
            BorderLayout.SOUTH
        );

        add(painelPrincipal);

        botaoCadastrar.addActionListener(
            e -> cadastrarDespesa()
        );

        botaoCarregarEdicao.addActionListener(
            e -> carregarEdicao()
        );

        botaoSalvarEdicao.addActionListener(
            e -> salvarEdicao()
        );

        botaoExcluir.addActionListener(
            e -> excluirDespesa()
        );

        botaoLimpar.addActionListener(
            e -> limparCampos()
        );

        atualizarTabela();
    }

    private void atualizarParticipantes() {

        painelParticipantes.removeAll();
        caixasParticipantes.clear();

        List<Participante> participantes =
            controller.listarParticipantes();

        if (participantes.isEmpty()) {

            painelParticipantes.add(
                new JLabel("Nenhum participante cadastrado.")
            );

        } else {

            for (Participante participante : participantes) {

                JCheckBox caixa = new JCheckBox(
                    participante.getCodigo()
                    + " - "
                    + participante.getNome()
                );

                caixasParticipantes.add(caixa);
                painelParticipantes.add(caixa);
            }
        }

        painelParticipantes.revalidate();
        painelParticipantes.repaint();
    }

    private List<Participante> obterSelecionados() {

        List<Participante> selecionados =
            new ArrayList<>();

        List<Participante> participantes =
            controller.listarParticipantes();

        for (int i = 0; i < caixasParticipantes.size(); i++) {

            if (caixasParticipantes.get(i).isSelected()) {
                selecionados.add(participantes.get(i));
            }
        }

        return selecionados;
    }

    private void cadastrarDespesa() {

        try {

            String descricao =
                campoDescricao.getText().trim();

            String textoValor =
                campoValor.getText().trim().replace(",", ".");

            BigDecimal valor =
                new BigDecimal(textoValor);

            List<Participante> selecionados =
                obterSelecionados();

            if (comboTipo.getSelectedItem().equals("Fixa")) {

                controller.cadastrarDespesaFixa(
                    descricao, valor, selecionados
                );

            } else {

                controller.cadastrarDespesaPorPessoa(
                    descricao, valor, selecionados
                );
            }

            atualizarTabela();
            limparCampos();

            JOptionPane.showMessageDialog(
                this,
                "Despesa cadastrada com sucesso!"
            );

        } catch (NumberFormatException erro) {

            JOptionPane.showMessageDialog(
                this,
                "Informe um valor válido."
            );

        } catch (IllegalArgumentException |
                 IllegalStateException erro) {

            JOptionPane.showMessageDialog(
                this,
                erro.getMessage()
            );
        }
    }

    private void carregarEdicao() {

        int linha = tabela.getSelectedRow();

        if (linha == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Selecione uma despesa na tabela."
            );

            return;
        }

        despesaEmEdicao =
            controller.listarDespesas().get(linha);

        campoDescricao.setText(
            despesaEmEdicao.getDescricao()
        );

        campoValor.setText(
            despesaEmEdicao.getValor().toPlainString()
        );

        if (despesaEmEdicao instanceof DespesaFixa) {
            comboTipo.setSelectedItem("Fixa");
        } else {
            comboTipo.setSelectedItem("Por pessoa");
        }

        List<Participante> vinculados =
            controller.listarParticipantesDaDespesa(
                despesaEmEdicao
            );

        List<Participante> cadastrados =
            controller.listarParticipantes();

        for (int i = 0; i < caixasParticipantes.size(); i++) {

            boolean vinculado = false;

            for (Participante participante : vinculados) {

                if (participante.getCodigo()
                        == cadastrados.get(i).getCodigo()) {
                    vinculado = true;
                    break;
                }
            }

            caixasParticipantes.get(i).setSelected(vinculado);
        }

        JOptionPane.showMessageDialog(
            this,
            "Dados carregados. Altere o que desejar e clique em Salvar edição."
        );
    }

    private void salvarEdicao() {

        if (despesaEmEdicao == null) {

            JOptionPane.showMessageDialog(
                this,
                "Primeiro selecione uma despesa e clique em Carregar edição."
            );

            return;
        }

        try {

            String descricao =
                campoDescricao.getText().trim();

            String textoValor =
                campoValor.getText().trim().replace(",", ".");

            BigDecimal valor =
                new BigDecimal(textoValor);

            boolean fixa =
                comboTipo.getSelectedItem().equals("Fixa");

            List<Participante> selecionados =
                obterSelecionados();

            controller.editarDespesa(
                despesaEmEdicao,
                descricao,
                valor,
                fixa,
                selecionados
            );

            atualizarTabela();
            limparCampos();

            JOptionPane.showMessageDialog(
                this,
                "Despesa atualizada com sucesso!"
            );

        } catch (NumberFormatException erro) {

            JOptionPane.showMessageDialog(
                this,
                "Informe um valor válido."
            );

        } catch (IllegalArgumentException |
                 IllegalStateException erro) {

            JOptionPane.showMessageDialog(
                this,
                erro.getMessage()
            );
        }
    }

    private void excluirDespesa() {

        int linha = tabela.getSelectedRow();

        if (linha == -1) {

            JOptionPane.showMessageDialog(
                this,
                "Selecione uma despesa na tabela."
            );

            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
            this,
            "Deseja excluir esta despesa?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION) {

            try {

                Despesa despesa =
                    controller.listarDespesas().get(linha);

                controller.excluirDespesa(despesa);

                atualizarTabela();
                limparCampos();

                JOptionPane.showMessageDialog(
                    this,
                    "Despesa excluída com sucesso!"
                );

            } catch (IllegalArgumentException |
                     IllegalStateException erro) {

                JOptionPane.showMessageDialog(
                    this,
                    erro.getMessage()
                );
            }
        }
    }

    private void atualizarTabela() {

        modeloTabela.setRowCount(0);

        for (Despesa despesa : controller.listarDespesas()) {

            String tipo;

            if (despesa instanceof DespesaFixa) {
                tipo = "Fixa";
            } else {
                tipo = "Por pessoa";
            }

            modeloTabela.addRow(new Object[]{
                despesa.getDescricao(),
                "R$ " + despesa.getValor().toPlainString(),
                tipo
            });
        }
    }

    private void limparCampos() {

        campoDescricao.setText("");
        campoValor.setText("");

        comboTipo.setSelectedIndex(0);

        despesaEmEdicao = null;

        tabela.clearSelection();

        atualizarParticipantes();
    }
}
