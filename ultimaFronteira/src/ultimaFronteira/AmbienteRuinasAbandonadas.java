package ultimaFronteira;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AmbienteRuinasAbandonadas extends Ambiente {
    private boolean estruturasInstaveis;
    private boolean presencaOutrosSobreviventes;
    private boolean baixoRiscoClimatico;

    public AmbienteRuinasAbandonadas() {
        super("Ruínas Abandonadas", "Restos de antigas construções que podem conter suprimentos valiosos ou armadilhas.",
              25, // Dificuldade de exploração
              Arrays.asList(
                  new Ferramenta("Alavanca de Ferro", 2.0, 80, "ferramenta", 10),
                  new Arma("Faca de Caça", 1.0, 100, "corpo a corpo", 15, 1, 0),
                  new Alimento("Enlatado Velho", 0.3, 1, 20, "enlatado", 5) // Pode estar estragado
              ),
              0.7, // Probabilidade de eventos
              "Seco e Protegido");
        this.estruturasInstaveis = true;
        this.presencaOutrosSobreviventes = true;
        this.baixoRiscoClimatico = true;
    }

    // Getters adicionais
    public boolean isEstruturasInstaveis() { return estruturasInstaveis; }
    public boolean isPresencaOutrosSobreviventes() { return presencaOutrosSobreviventes; }
    public boolean isBaixoRiscoClimatico() { return baixoRiscoClimatico; }

    @Override
    public void explorar(Personagem jogador) {
        System.out.println("\n" + jogador.getNome() + " está vasculhando as Ruínas Abandonadas...");
        jogador.gastarEnergia(getDificuldadeDeExploracao());
        jogador.afetarSanidade(-5); // Pode ser um local um pouco perturbador

        Random rand = new Random();
        if (rand.nextDouble() < 0.6) {
            Item encontrado = getRecursosDisponiveis().get(rand.nextInt(getRecursosDisponiveis().size()));
            System.out.println("Você encontrou: " + encontrado.getNome() + "!");
            jogador.getInventario().adicionarItem(encontrado);
        } else {
            System.out.println("As ruínas estão vazias, ou você não encontrou nada de valor.");
        }
    }

    @Override
    public Evento gerarEvento(GerenciadorDeEventos gerenciadorDeEventos) {
        List<Evento> eventosRuinas = gerenciadorDeEventos.getListaDeEventosPossiveis().stream()
                .filter(e -> e.getCondicaoDeAtivacao().equalsIgnoreCase("Ruínas Abandonadas") || e.getCondicaoDeAtivacao().equalsIgnoreCase("Qualquer Ambiente"))
                .toList();
        return gerenciadorDeEventos.sortearEvento(this, eventosRuinas);
    }
}