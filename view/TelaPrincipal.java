
package view;

import Model.Planejamento;
import controller.DespesaController;
import controller.ParticipanteController;
import persistence.PersistenciaArquivo;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TelaPrincipal extends JFrame {

    private Planejamento planejamento;
    private PersistenciaArquivo persistencia;

    private boolean carregamentoComErro = false;

    public TelaPrincipal() {

        persistencia = new PersistenciaArquivo("dados/cabenoBolso.dat");

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

        // Bloqueia o acesso em caso de erro no carregamento
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

        File arquivo = new File("dados/cabenoBolso.dat");

        // Verifica se o arquivo existe
        if (!arquivo.exists()) {

            if (planejamento == null) {
                planejamento = new Planejamento();
            }

            JOptionPane.showMessageDialog(
                this,
                "Arquivo não encontrado. Iniciando um novo planejamento."
            );

            return;
        }

        try {

            // Carrega os dados antes de substituir o planejamento
            Planejamento dadosCarregados = persistencia.carregar();

            planejamento = dadosCarregados;

            carregamentoComErro = false;

            System.out.println("Dados carregados com sucesso!");

        } catch (IOException | ClassNotFoundException erro) {

            carregamentoComErro = true;

            JOptionPane.showMessageDialog(
                this,
                "Erro ao carregar os dados: "
                + erro.getMessage()
                + "\n\nO arquivo foi preservado."
                + "\nAs funcionalidades foram bloqueadas."
                + "\nVerifique o arquivo antes de reiniciar o sistema.",
                "Erro de persistência",
                JOptionPane.ERROR_MESSAGE
            );

            // Preserva o planejamento existente
            if (planejamento == null) {
                planejamento = new Planejamento();
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
