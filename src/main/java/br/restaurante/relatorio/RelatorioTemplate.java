package br.restaurante.relatorio;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class RelatorioTemplate {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Template method — esqueleto fixo
    public final void gerar() {
        imprimirCabecalho();
        imprimirCorpo();
        imprimirRodape();
    }

    protected abstract String titulo();
    protected abstract void imprimirCorpo();

    private void imprimirCabecalho() {
        String linha = "=".repeat(50);
        System.out.println(linha);
        System.out.println("RESTAURANTE AS-SARAIVA");
        System.out.println(titulo());
        System.out.println("Gerado em: " + LocalDateTime.now().format(FORMATTER));
        System.out.println(linha);
    }

    private void imprimirRodape() {
        System.out.println("=".repeat(50));
        System.out.println("-- Fim do relatório --\n");
    }
}
