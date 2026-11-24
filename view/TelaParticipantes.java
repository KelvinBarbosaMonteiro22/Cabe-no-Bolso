
package view;

import controller.ParticipanteController;
import Model.Participante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;

public class TelaParticipantes extends JFrame {

    private JTextField campoCodigo;
    private JTextField campoNome;
    private JTextField campoOrcamento;

    private DefaultTableModel modeloTabela;
    private JTable tabela;

    private ParticipanteController controller;

    private Integer codigoSelecionado = null;

    public TelaParticipantes(ParticipanteController controller) {

        this.controller = controller;

        setTitle("Gerenciar participantes");
        setSize(750, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 15));
        painelPrincipal.setBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        JLabel titulo = new JLabel(
            "Cadastro de Participantes",
            SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel painelFormulario = new JPanel(
            new GridLayout(3, 2, 10, 10)
        );

        campoCodigo = new JTextField();
        campoNome = new JTextField();
        campoOrcamento = new JTextField();

        painelFormulario.add(new JLabel("Código:"));
        painelFormulario.add(campoCodigo);

        painelFormulario.add(new JLabel("Nome:"));
        painelFormulario.add(campoNome);

        painelFormulario.add(new JLabel("Orçamento (R$):"));
        painelFormulario.add(campoOrcamento);

        JPanel painelSuperior = new JPanel(new BorderLayout(10, 15));

        painelSuperior.add(titulo, BorderLayout.NORTH);
        painelSuperior.add(painelFormulario, BorderLayout.CENTER);

        modeloTabela = new DefaultTableModel(
            new Object[]{"Código", "Nome", "Orçamento"}, 0
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

        JScrollPane painelTabela = new JScrollPane(tabela);

        JButton botaoCadastrar = new JButton("Cadastrar");
        JButton botaoEditar = new JButton("Editar");
        JButton botaoExcluir = new JButton("Excluir");
        JButton botaoLimpar = new JButton("Limpar");

        JPanel painelBotoes = new JPanel(
            new GridLayout(1, 4, 10, 10)
        );

        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(botaoEditar);
        painelBotoes.add(botaoExcluir);
        painelBotoes.add(botaoLimpar);

        painelPrincipal.add(painelSuperior, BorderLayout.NORTH);
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);

        botaoCadastrar.addActionListener(e -> cadastrar());

        botaoEditar.addActionListener(e -> editar());

        botaoExcluir.addActionListener(e -> excluir());

        botaoLimpar.addActionListener(e -> limparCampos());

        tabela.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                preencherCampos();
            }

        });

        atualizarTabela();
    }

    private void cadastrar() {

        try {

            int codigo = Integer.parseInt(
                campoCodigo.getText().trim()
            );

            String nome = campoNome.getText().trim();

            String textoOrcamento =
                campoOrcamento.getText().trim().replace(",", ".");

            BigDecimal orcamento =
                new BigDecimal(textoOrcamento);

            controller.cadastrar(codigo, nome, orcamento);

            atualizarTabela();
            limparCampos();

            JOptionPane.showMessageDialog(
                this,
                "Participante cadastrado com sucesso!"
            );

        } catch (NumberFormatException erro) {

            JOptionPane.showMessageDialog(
                this,
                "Informe um código inteiro e um orçamento válido."
            );

        } catch (IllegalArgumentException erro) {

            JOptionPane.showMessageDialog(
                this,
                erro.getMessage()
            );
        }
    }

    private void preencherCampos() {

        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            return;
        }

        codigoSelecionado = Integer.parseInt(
            modeloTabela.getValueAt(linha, 0).toString()
        );

        campoCodigo.setText(
            modeloTabela.getValueAt(linha, 0).toString()
        );

        campoNome.setText(
            modeloTabela.getValueAt(linha, 1).toString()
        );

        String orcamento = modeloTabela
            .getValueAt(linha, 2)
            .toString()
            .replace("R$ ", "")
            .replace(",", ".");

        campoOrcamento.setText(orcamento);

        campoCodigo.setEditable(false);
    }

    private void editar() {

        if (codigoSelecionado == null) {

            JOptionPane.showMessageDialog(
                this,
                "Selecione um participante na tabela."
            );

            return;
        }

        try {

            String nome = campoNome.getText().trim();

            String textoOrcamento =
                campoOrcamento.getText().trim().replace(",", ".");

            BigDecimal orcamento =
                new BigDecimal(textoOrcamento);

            controller.editar(
                codigoSelecionado,
                nome,
                orcamento
            );

            atualizarTabela();
            limparCampos();

            JOptionPane.showMessageDialog(
                this,
                "Participante atualizado com sucesso!"
            );

        } catch (NumberFormatException erro) {

            JOptionPane.showMessageDialog(
                this,
                "Informe um orçamento válido."
            );

        } catch (IllegalArgumentException erro) {

            JOptionPane.showMessageDialog(
                this,
                erro.getMessage()
            );
        }
    }

    private void excluir() {

        if (codigoSelecionado == null) {

            JOptionPane.showMessageDialog(
                this,
                "Selecione um participante na tabela."
            );

            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir este participante?",
            "Confirmar exclusão",
            JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION) {

            try {

                controller.excluir(codigoSelecionado);

                atualizarTabela();
                limparCampos();

                JOptionPane.showMessageDialog(
                    this,
                    "Participante excluído com sucesso!"
                );

            } catch (IllegalArgumentException erro) {

                JOptionPane.showMessageDialog(
                    this,
                    erro.getMessage()
                );
            }
        }
    }

    private void limparCampos() {

        campoCodigo.setText("");
        campoNome.setText("");
        campoOrcamento.setText("");

        campoCodigo.setEditable(true);

        codigoSelecionado = null;

        tabela.clearSelection();
    }

    private void atualizarTabela() {

        modeloTabela.setRowCount(0);

        for (Participante p : controller.listarParticipantes()) {

            modeloTabela.addRow(new Object[]{
                p.getCodigo(),
                p.getNome(),
                "R$ " + p.getOrcamento().toPlainString()
            });
        }
    }
}
