package ultimaFronteira;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AmbienteMontanha extends Ambiente {
    private boolean terrenoAcidentado;
    private boolean climaInstavel;
    private boolean baixaVegetacao;

    public AmbienteMontanha() {
        super("Montanha", "Uma região de difícil acesso, mas rica em minérios e pedras preciosas.",
              30, // Dificuldade de exploração
              Arrays.asList(
                  new Material("Pedra", 3.0, 100, "pedra", 15),
                  new Material("Minério de Ferro", 5.0, 100, "metal", 20),
                  new Agua("Água de Degelo", 0.5, 10, "contaminada", 15.0) // Geralmente precisa purificar
              ),
              0.7, // Probabilidade de eventos
              "Frio");
        this.terrenoAcidentado = true;
        this.climaInstavel = true;
        this.baixaVegetacao = true;
    }

    // Getters adicionais
    public boolean isTerrenoAcidentado() { return terrenoAcidentado; }
    public boolean isClimaInstavel() { return climaInstavel; }
    public boolean isBaixaVegetacao() { return baixaVegetacao; }

    @Override
    public void explorar(Personagem jogador) {
        System.out.println("\n" + jogador.getNome() + " está escalando a Montanha...");
        jogador.gastarEnergia(getDificuldadeDeExploracao());
        // Aumenta fome/sede devido ao esforço
        jogador.setFome(jogador.getFome() + 5);
        jogador.setSede(jogador.getSede() + 10);

        Random rand = new Random();
        if (rand.nextDouble() < 0.6) {
            Item encontrado = getRecursosDisponiveis().get(rand.nextInt(getRecursosDisponiveis().size()));
            System.out.println("Você encontrou: " + encontrado.getNome() + "!");
            jogador.getInventario().adicionarItem(encontrado);
        } else {
            System.out.println("O terreno é árduo e você não encontrou nada valioso desta vez.");
        }
    }

    @Override
    public Evento gerarEvento(GerenciadorDeEventos gerenciadorDeEventos) {
        List<Evento> eventosMontanha = gerenciadorDeEventos.getListaDeEventosPossiveis().stream()
                .filter(e -> e.getCondicaoDeAtivacao().equalsIgnoreCase("Montanha") || e.getCondicaoDeAtivacao().equalsIgnoreCase("Qualquer Ambiente"))
                .toList();
        return gerenciadorDeEventos.sortearEvento(this, eventosMontanha);
    }
}
