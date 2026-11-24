
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

        // Abre a tela de resumo
        botaoResumo.addActionListener(e -> {

            TelaResumo telaResumo = new TelaResumo(planejamento);

            telaResumo.setVisible(true);
        });

        painel.add(titulo);
        painel.add(botaoParticipantes);
        painel.add(botaoDespesas);
        painel.add(botaoResumo);

        add(painel);

    }

    private void carregarDados() {

        File arquivo = new File("dados/cabenoBolso.dat");

        if (arquivo.exists()) {

            try {

                planejamento = persistencia.carregar();

                System.out.println("Dados carregados com sucesso!");

            } catch (IOException | ClassNotFoundException erro) {

                JOptionPane.showMessageDialog(
                    this,
                    "Erro ao carregar os dados: " + erro.getMessage()
                );

                planejamento = new Planejamento();
            }

        } else {

            planejamento = new Planejamento();
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            TelaPrincipal tela = new TelaPrincipal();

            tela.setVisible(true);
        });
    }
}
