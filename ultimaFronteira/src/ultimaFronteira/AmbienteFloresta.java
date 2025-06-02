package ultimaFronteira;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AmbienteFloresta extends Ambiente {
    private boolean vegetacaoDensa;
    private boolean faunaAbundante;
    private boolean climaUmido;

    public AmbienteFloresta() {
        super("Floresta", "Uma área rica em recursos naturais, mas também habitada por predadores.",
              20, // Dificuldade de exploração
              Arrays.asList(
                  new Alimento("Bagas", 0.1, 5, 10, "fruta", 3),
                  new Material("Madeira", 2.0, 100, "madeira", 10),
                  new Agua("Riacho Limpo", 0.5, 10, "potavel", 20.0)
              ),
              0.6, // Probabilidade de eventos
              "Úmido");
        this.vegetacaoDensa = true;
        this.faunaAbundante = true;
        this.climaUmido = true;
    }

    // Getters adicionais
    public boolean isVegetacaoDensa() { return vegetacaoDensa; }
    public boolean isFaunaAbundante() { return faunaAbundante; }
    public boolean isClimaUmido() { return climaUmido; }

    @Override
    public void explorar(Personagem jogador) {
        System.out.println("\n" + jogador.getNome() + " está explorando a Floresta...");
        jogador.gastarEnergia(getDificuldadeDeExploracao());

        Random rand = new Random();
        if (rand.nextDouble() < 0.7) { // 70% de chance de encontrar um recurso
            Item encontrado = getRecursosDisponiveis().get(rand.nextInt(getRecursosDisponiveis().size()));
            System.out.println("Você encontrou: " + encontrado.getNome() + "!");
            jogador.getInventario().adicionarItem(encontrado);
        } else {
            System.out.println("Você explorou, mas não encontrou nada de imediato.");
        }
    }

    @Override
    public Evento gerarEvento(GerenciadorDeEventos gerenciadorDeEventos) {
        // Filtra e retorna um evento específico para a floresta
        List<Evento> eventosFloresta = gerenciadorDeEventos.getListaDeEventosPossiveis().stream()
                .filter(e -> e.getCondicaoDeAtivacao().equalsIgnoreCase("Floresta") || e.getCondicaoDeAtivacao().equalsIgnoreCase("Qualquer Ambiente"))
                .toList();
        return gerenciadorDeEventos.sortearEvento(this, eventosFloresta);
    }
}
