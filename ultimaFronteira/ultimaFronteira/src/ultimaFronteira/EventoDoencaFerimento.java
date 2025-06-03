package ultimaFronteira;

public class EventoDoencaFerimento extends Evento {
    private String tipoDeCondicao; // "Infeccao", "Febre", "Desidratacao", "Fratura"
    private String impacto; // "Reduz vida", "Reduz energia", "Reduz sanidade"
    private String curaDisponivel; // Ex: "Bandagem", "Antibiotico"
    private int severidade; // Impacto nos atributos

    public EventoDoencaFerimento(String nome, String descricao, double probabilidadeDeOcorrencia, String impacto, String condicaoDeAtivacao, String tipoDeCondicao, String impactoAtributo, String curaDisponivel, int severidade) {
        super(nome, descricao, probabilidadeDeOcorrencia, impacto, condicaoDeAtivacao);
        this.tipoDeCondicao = tipoDeCondicao;
        this.impacto = impactoAtributo;
        this.curaDisponivel = curaDisponivel;
        this.severidade = severidade;
    }

    // Getters adicionais
    public String getTipoDeCondicao() { return tipoDeCondicao; }
    public String getCuraDisponivel() { return curaDisponivel; }
    public int getSeveridade() { return severidade; }

    @Override
    public void executar(Personagem jogador, Ambiente local) {
        System.out.println("\n*** Evento de Doença/Ferimento: " + getNome() + " ***");
        System.out.println(getDescricao());

        // Lógica para aplicar os efeitos no personagem
        switch (tipoDeCondicao) {
            case "Hipotermia":
                jogador.receberDano(severidade);
                jogador.gastarEnergia(severidade);
                System.out.println(jogador.getNome() + " está sofrendo de hipotermia! Vida e energia sendo drenadas.");
                break;
            case "Infeccao":
                jogador.receberDano(severidade);
                jogador.afetarSanidade(-severidade);
                System.out.println(jogador.getNome() + " desenvolveu uma infecção! Vida e sanidade afetadas.");
                break;
            case "Desidratacao":
                jogador.receberDano(severidade);
                jogador.afetarSanidade(-severidade);
                jogador.gastarEnergia(severidade);
                System.out.println(jogador.getNome() + " está desidratado! Todos os atributos sofrem.");
                break;
            case "Fratura":
                jogador.gastarEnergia(severidade * 2); // Dificulta movimentos
                jogador.afetarSanidade(-severidade);
                System.out.println(jogador.getNome() + " sofreu uma fratura! Movimentos dificultados e dor.");
                break;
            default:
                break;
        }
        System.out.println("Você precisa de '" + curaDisponivel + "' para tratar isso.");
    }
}
