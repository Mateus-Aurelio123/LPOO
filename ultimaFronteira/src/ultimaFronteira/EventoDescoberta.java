package ultimaFronteira;

import java.util.List;

public class EventoDescoberta extends Evento {
    private String tipoDeDescoberta; // "Caverna", "Abrigo", "Suprimentos Abandonados"
    private List<Item> recursosEncontrados;
    private String condicaoEspecial; // Ex: "Exige habilidade 'Mecânico'"

    public EventoDescoberta(String nome, String descricao, double probabilidadeDeOcorrencia, String impacto, String condicaoDeAtivacao, String tipoDeDescoberta, List<Item> recursosEncontrados, String condicaoEspecial) {
        super(nome, descricao, probabilidadeDeOcorrencia, impacto, condicaoDeAtivacao);
        this.tipoDeDescoberta = tipoDeDescoberta;
        this.recursosEncontrados = recursosEncontrados;
        this.condicaoEspecial = condicaoEspecial;
    }

    // Getters adicionais
    public String getTipoDeDescoberta() { return tipoDeDescoberta; }
    public List<Item> getRecursosEncontrados() { return recursosEncontrados; }
    public String getCondicaoEspecial() { return condicaoEspecial; }

    @Override
    public void executar(Personagem jogador, Ambiente local) {
        System.out.println("\n*** Evento de Descoberta: " + getNome() + " ***");
        System.out.println(getDescricao());

        boolean podeExplorar = true;
        if (condicaoEspecial != null && !condicaoEspecial.isEmpty()) {
            if (!jogador.getHabilidades().contains(condicaoEspecial.replace("Exige habilidade '", "").replace("'", ""))) {
                podeExplorar = false;
                System.out.println("Você não possui a habilidade necessária: " + condicaoEspecial + " para explorar totalmente esta descoberta.");
            }
        }

        if (podeExplorar) {
            System.out.println("Você encontrou os seguintes recursos:");
            for (Item item : recursosEncontrados) {
                if (jogador.getInventario().adicionarItem(item)) {
                    System.out.println(" - " + item.getNome());
                }
            }
            // Lógica para abrigos seguros ou pistas
            if (tipoDeDescoberta.equals("Abrigo Abandonado")) {
                System.out.println("Este abrigo pode ser um bom local para descansar.");
                jogador.restaurarEnergia(20);
                jogador.afetarSanidade(10);
                System.out.println(jogador.getNome() + " descansou um pouco no abrigo.");
            }
        }
    }
}
