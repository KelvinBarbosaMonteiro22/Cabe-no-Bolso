package persistence;

import Model.Planejamento;
import java.io.IOException;

public interface Persistencia {

    void salvar(Planejamento planejamento)
            throws IOException;

    Planejamento carregar()
            throws IOException, ClassNotFoundException;
}
