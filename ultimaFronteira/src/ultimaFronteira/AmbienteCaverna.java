package ultimaFronteira;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AmbienteCaverna extends Ambiente {
    private boolean poucaLuz;
    private boolean presencaCriaturasDesconhecidas;
    private boolean aguaGotejamento;

    public AmbienteCaverna() {
        super("Caverna", "Um ambiente subterrâneo que pode oferecer abrigo, mas esconde perigos desconhecidos.",
              25, // Dificuldade de exploração
              Arrays.asList(
                  new Material("Rochas Afiadas", 1.0, 100, "pedra", 10),
                  new Material("Minério Raro", 4.0, 100, "metal", 25),
                  new Agua("Lago Subterrâneo", 0.5, 10, "potavel", 25.0)
              ),
              0.8, // Probabilidade de eventos (mais perigoso)
              "Úmido e Frio");
        this.poucaLuz = true;
        this.presencaCriaturasDesconhecidas = true;
        this.aguaGotejamento = true;
    }

    // Getters adicionais
    public boolean isPoucaLuz() { return poucaLuz; }
    public boolean isPresencaCriaturasDesconhecidas() { return presencaCriaturasDesconhecidas; }
    public boolean isAguaGotejamento() { return aguaGotejamento; }

    @Override
    public void explorar(Personagem jogador) {
        System.out.println("\n" + jogador.getNome() + " está explorando a Caverna escura...");
        jogador.gastarEnergia(getDificuldadeDeExploracao());

        if (poucaLuz && !jogador.getInventario().contemItem("Lanterna")) {
            System.out.println("É muito escuro aqui sem uma lanterna! Sua sanidade é afetada.");
            jogador.afetarSanidade(-15);
        }

        Random rand = new Random();
        if (rand.nextDouble() < 0.5) {
            Item encontrado = getRecursosDisponiveis().get(rand.nextInt(getRecursosDisponiveis().size()));
            System.out.println("Você encontrou: " + encontrado.getNome() + "!");
            jogador.getInventario().adicionarItem(encontrado);
        } else {
            System.out.println("As profundezas da caverna não revelaram nada novo desta vez.");
        }
    }

    @Override
    public Evento gerarEvento(GerenciadorDeEventos gerenciadorDeEventos) {
        List<Evento> eventosCaverna = gerenciadorDeEventos.getListaDeEventosPossiveis().stream()
                .filter(e -> e.getCondicaoDeAtivacao().equalsIgnoreCase("Caverna") || e.getCondicaoDeAtivacao().equalsIgnoreCase("Qualquer Ambiente"))
                .toList();
        return gerenciadorDeEventos.sortearEvento(this, eventosCaverna);
    }
}
