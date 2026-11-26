package view;

import Model.Planejamento;
import controller.DespesaController;
import controller.ParticipanteController;
import persistence.PersistenciaArquivo;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class TelaPrincipal extends JFrame {

    private Planejamento planejamento;
    private PersistenciaArquivo persistencia;

    private boolean carregamentoComErro = false;

    private static final String CAMINHO_ARQUIVO =
        "dados/cabenoBolso.dat";

    public TelaPrincipal() {

        persistencia = new PersistenciaArquivo(CAMINHO_ARQUIVO);

        carregarDados();

        setTitle("Cabe no Bolso - Planejador de Despesas");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(4, 1, 10, 10));

        JLabel titulo = new JLabel(
            "Cabe no Bolso",
            SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 24));

        JButton botaoParticipantes =
            new JButton("Gerenciar participantes");

        JButton botaoDespesas =
            new JButton("Gerenciar despesas");

        JButton botaoResumo =
            new JButton("Consultar resumo");

        botaoParticipantes.addActionListener(e -> {

            ParticipanteController controller =
                new ParticipanteController(planejamento, persistencia);

            TelaParticipantes tela =
                new TelaParticipantes(controller);

            tela.setVisible(true);
        });

        botaoDespesas.addActionListener(e -> {

            DespesaController controller =
                new DespesaController(planejamento, persistencia);

            TelaDespesas tela =
                new TelaDespesas(controller);

            tela.setVisible(true);
        });

        botaoResumo.addActionListener(e -> {

            TelaResumo telaResumo =
                new TelaResumo(planejamento);

            telaResumo.setVisible(true);
        });

        // Bloqueia os botões somente se não for possível
        // preservar o arquivo inválido com segurança.
        if (carregamentoComErro) {

            botaoParticipantes.setEnabled(false);
            botaoDespesas.setEnabled(false);
            botaoResumo.setEnabled(false);
        }

        painel.add(titulo);
        painel.add(botaoParticipantes);
        painel.add(botaoDespesas);
        painel.add(botaoResumo);

        add(painel);
    }

    private void carregarDados() {

        File arquivo = new File(CAMINHO_ARQUIVO);

        if (!arquivo.exists()) {

            if (planejamento == null) {
                planejamento = new Planejamento();
            }

            JOptionPane.showMessageDialog(
                this,
                "Arquivo não encontrado.\n"
                + "Iniciando um novo planejamento."
            );

            return;
        }

        try {

            Planejamento dadosCarregados =
                persistencia.carregar();

            planejamento = dadosCarregados;

            carregamentoComErro = false;

            System.out.println(
                "Dados carregados com sucesso!"
            );

        } catch (IOException | ClassNotFoundException erro) {

            // Preserva um planejamento que já esteja em memória.
            if (planejamento != null) {

                carregamentoComErro = true;

                JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar os dados.\n"
                    + "O planejamento existente foi preservado.\n"
                    + "As funcionalidades foram bloqueadas.",
                    "Erro de persistência",
                    JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            planejamento = new Planejamento();

            try {

                Path origem = arquivo.toPath();

                Path backup = origem.resolveSibling(
                    "cabenoBolso-invalido-"
                    + UUID.randomUUID()
                    + ".bak"
                );

                // Preserva o arquivo inválido.
                Files.move(origem, backup);

                carregamentoComErro = false;

                JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar os dados: "
                    + erro.getMessage()
                    + "\n\nArquivo inválido preservado em:\n"
                    + backup
                    + "\n\nIniciando um novo planejamento.",
                    "Aviso de persistência",
                    JOptionPane.WARNING_MESSAGE
                );

            } catch (IOException erroBackup) {

                carregamentoComErro = true;

                JOptionPane.showMessageDialog(
                    this,
                    "Não foi possível preservar o arquivo inválido."
                    + "\n" + erroBackup.getMessage()
                    + "\nAs funcionalidades foram bloqueadas"
                    + " para proteger os dados.",
                    "Erro de persistência",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            TelaPrincipal tela = new TelaPrincipal();

            tela.setVisible(true);
        });
    }
}