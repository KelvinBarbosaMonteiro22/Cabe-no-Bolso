
package testes;

import Model.Participante;
import java.math.BigDecimal;

public class TesteParticipante {

    public static void main(String[] args) {

        Participante ana = new Participante(
            1,
            "Ana",
            new BigDecimal("70.00")
        );

        System.out.println("Código: " + ana.getCodigo());
        System.out.println("Nome: " + ana.getNome());
        System.out.println("Orçamento: " + ana.getOrcamento());
    }
}
