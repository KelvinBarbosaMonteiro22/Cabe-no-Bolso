
package testes;

import persistence.PersistenciaArquivo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TesteArquivoInvalido {

    public static void main(String[] args) throws IOException {

        // 1. Criar um arquivo temporario
        Path arquivo = Files.createTempFile(
            "testeInvalido", ".dat"
        );

        // 2. Inserir um conteudo invalido
        Files.writeString(
            arquivo, "Arquivo invalido para teste"
        );

        PersistenciaArquivo persistencia =
            new PersistenciaArquivo(arquivo.toString());

        boolean erroDetectado = false;

        // 3. Testar o carregamento
        try {

            persistencia.carregar();

            System.out.println(
                "FALHA: arquivo invalido foi aceito!"
            );

        } catch (IOException e) {

            erroDetectado = true;

            System.out.println(
                "Erro detectado corretamente!"
            );

            System.out.println(
                "Mensagem: " + e.getMessage()
            );

        } catch (ClassNotFoundException e) {

            System.out.println(
                "Erro inesperado: " + e.getMessage()
            );
        }

        // 4. Verificar se o arquivo foi liberado
        try {

            Files.delete(arquivo);

            System.out.println(
                "Arquivo de teste liberado com sucesso!"
            );

            if (erroDetectado) {
                System.out.println("TESTE APROVADO!");
            } else {
                System.out.println("TESTE REPROVADO!");
            }

        } catch (IOException e) {

            System.out.println("TESTE REPROVADO!");

            System.out.println(
                "Tipo do erro: " + e.getClass().getName()
            );

            System.out.println(
                "Motivo: " + e.getMessage()
            );

            System.out.println(
                "Caminho do arquivo: " + arquivo
            );

            e.printStackTrace();
        }
    }
}
