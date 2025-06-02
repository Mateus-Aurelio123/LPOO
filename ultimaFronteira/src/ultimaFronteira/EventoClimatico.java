package ultimaFronteira;

public class EventoClimatico extends Evento {
    private String tipoDeClima; // "Nevasca", "Tempestade", "Calor Extremo"
    private int duracao; // Em turnos
    private String efeitoNoAmbiente; // Ex: "Dificulta acender fogueiras", "Aumenta consumo de água"

    public EventoClimatico(String nome, String descricao, double probabilidadeDeOcorrencia, String impacto, String condicaoDeAtivacao, String tipoDeClima, int duracao, String efeitoNoAmbiente) {
        super(nome, descricao, probabilidadeDeOcorrencia, impacto, condicaoDeAtivacao);
        this.tipoDeClima = tipoDeClima;
        this.duracao = duracao;
        this.efeitoNoAmbiente = efeitoNoAmbiente;
    }

    // Getters adicionais
    public String getTipoDeClima() { return tipoDeClima; }
    public int getDuracao() { return duracao; }
    public String getEfeitoNoAmbiente() { return efeitoNoAmbiente; }

    @Override
    public void executar(Personagem jogador, Ambiente local) {
        System.out.println("\n*** Evento Climático: " + getNome() + " ***");
        System.out.println(getDescricao());
        System.out.println("Efeito no ambiente: " + getEfeitoNoAmbiente());

        // Lógica para aplicar os efeitos do clima no jogador
        switch (tipoDeClima) {
            case "Nevasca":
                jogador.gastarEnergia(15);
                jogador.receberDano(5); // Por hipotermia
                System.out.println(jogador.getNome() + " sente o frio da nevasca. Energia gasta e um pouco de vida perdida.");
                break;
            case "Tempestade":
                jogador.afetarSanidade(-10); // Pelo barulho e perigo
                System.out.println(jogador.getNome() + " está assustado com a tempestade. Sanidade afetada.");
                break;
            case "Calor Extremo":
                jogador.setSede(jogador.getSede() + 20); // Aumenta sede rapidamente
                System.out.println(jogador.getNome() + " sente o calor extremo. Sede aumentou rapidamente.");
                break;
            default:
                break;
        }
        // local.modificarClima(this); // Poderia passar o evento para o ambiente para modificar seus estados
    }
}