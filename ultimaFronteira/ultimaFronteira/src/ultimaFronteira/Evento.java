package ultimaFronteira;

public abstract class Evento {
    private String nome;
    private String descricao;
    private double probabilidadeDeOcorrencia;
    private String impacto;
    private String condicaoDeAtivacao; // Ex: "Floresta", "Noite", "Chuva"

    public Evento(String nome, String descricao, double probabilidadeDeOcorrencia, String impacto, String condicaoDeAtivacao) {
        this.nome = nome;
        this.descricao = descricao;
        this.probabilidadeDeOcorrencia = probabilidadeDeOcorrencia;
        this.impacto = impacto;
        this.condicaoDeAtivacao = condicaoDeAtivacao;
    }

    // Getters
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getProbabilidadeDeOcorrencia() { return probabilidadeDeOcorrencia; }
    public String getImpacto() { return impacto; }
    public String getCondicaoDeAtivacao() { return condicaoDeAtivacao; }

    // Método Abstrato
    public abstract void executar(Personagem jogador, Ambiente local);
}
