package ultimaFronteira;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AmbienteLagoRio extends Ambiente {
    private boolean aguaAbundante;
    private boolean possibilidadePesca;
    private boolean terrenoLamacento;

    public AmbienteLagoRio() {
        super("Lago e Rio", "Regiões ricas em água, mas que podem esconder riscos como afogamento ou criaturas aquáticas.",
              15, // Dificuldade de exploração (mais fácil)
              Arrays.asList(
                  new Agua("Água de Rio", 0.5, 10, "contaminada", 30.0), // Maior volume
                  new Alimento("Peixe Fresco", 0.5, 1, 30, "carne", 1),
                  new Material("Alga Comestível", 0.1, 5, "planta", 5)
              ),
              0.5, // Probabilidade de eventos
              "Úmido");
        this.aguaAbundante = true;
        this.possibilidadePesca = true;
        this.terrenoLamacento = true;
    }

    // Getters adicionais
    public boolean isAguaAbundante() { return aguaAbundante; }
    public boolean isPossibilidadePesca() { return possibilidadePesca; }
    public boolean isTerrenoLamacento() { return terrenoLamacento; }

    @Override
    public void explorar(Personagem jogador) {
        System.out.println("\n" + jogador.getNome() + " está explorando as margens do Lago/Rio...");
        jogador.gastarEnergia(getDificuldadeDeExploracao());

        Random rand = new Random();
        if (rand.nextDouble() < 0.8) { // Alta chance de encontrar água ou alimento
            Item encontrado = getRecursosDisponiveis().get(rand.nextInt(getRecursosDisponiveis().size()));
            System.out.println("Você encontrou: " + encontrado.getNome() + "!");
            jogador.getInventario().adicionarItem(encontrado);
        } else {
            System.out.println("Você não encontrou nada de valor perto da água desta vez.");
        }
    }

    @Override
    public Evento gerarEvento(GerenciadorDeEventos gerenciadorDeEventos) {
        List<Evento> eventosLagoRio = gerenciadorDeEventos.getListaDeEventosPossiveis().stream()
                .filter(e -> e.getCondicaoDeAtivacao().equalsIgnoreCase("Lago e Rio") || e.getCondicaoDeAtivacao().equalsIgnoreCase("Qualquer Ambiente"))
                .toList();
        return gerenciadorDeEventos.sortearEvento(this, eventosLagoRio);
    }
}
