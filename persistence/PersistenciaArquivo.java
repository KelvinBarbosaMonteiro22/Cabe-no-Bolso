
package persistence;

import Model.Planejamento;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class PersistenciaArquivo implements Persistencia {

    private String caminhoArquivo;

    public PersistenciaArquivo(String caminhoArquivo) {

        if (caminhoArquivo == null ||
                caminhoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Caminho do arquivo inválido!"
            );
        }

        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public void salvar(Planejamento planejamento)
            throws IOException {

        if (planejamento == null) {
            throw new IllegalArgumentException(
                "Planejamento inválido!"
            );
        }

        File arquivo = new File(caminhoArquivo);

        File pasta = arquivo.getParentFile();

        if (pasta != null && !pasta.exists()) {
            pasta.mkdirs();
        }

        try (ObjectOutputStream saida =
                new ObjectOutputStream(
                    new FileOutputStream(arquivo))) {

            saida.writeObject(planejamento);
        }
    }

    @Override
    public Planejamento carregar()
            throws IOException, ClassNotFoundException {

        File arquivo = new File(caminhoArquivo);

        if (!arquivo.exists()) {
            throw new IOException(
                "Arquivo de planejamento não encontrado!"
            );
        }

        try (ObjectInputStream entrada =
                new ObjectInputStream(
                    new FileInputStream(arquivo))) {

            Object objeto = entrada.readObject();

            if (!(objeto instanceof Planejamento)) {
                throw new IOException(
                    "O arquivo não contém um planejamento válido!"
                );
            }

            return (Planejamento) objeto;
        }
    }
}
