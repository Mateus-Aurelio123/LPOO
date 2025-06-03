package ultimaFronteira;

import java.util.List;
import java.util.Random;

public abstract class Ambiente {
    private String nome;
    private String descricao;
    private int dificuldadeDeExploracao; // Consumo de energia
    private List<Item> recursosDisponiveis; // Itens que podem ser coletados
    private double probabilidadeDeEventos; // Frequência e tipo de eventos
    private String condicoesClimaticasPredominantes; // Ex: "Úmido", "Frio", "Seco"

    public Ambiente(String nome, String descricao, int dificuldadeDeExploracao, List<Item> recursosDisponiveis, double probabilidadeDeEventos, String condicoesClimaticasPredominantes) {
        this.nome = nome;
        this.descricao = descricao;
        this.dificuldadeDeExploracao = dificuldadeDeExploracao;
        this.recursosDisponiveis = recursosDisponiveis;
        this.probabilidadeDeEventos = probabilidadeDeEventos;
        this.condicoesClimaticasPredominantes = condicoesClimaticasPredominantes;
    }

    // Getters
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public int getDificuldadeDeExploracao() { return dificuldadeDeExploracao; }
    public List<Item> getRecursosDisponiveis() { return recursosDisponiveis; }
    public double getProbabilidadeDeEventos() { return probabilidadeDeEventos; }
    public String getCondicoesClimaticasPredominantes() { return condicoesClimaticasPredominantes; }

    // Métodos Abstratos
    public abstract void explorar(Personagem jogador);
    public abstract Evento gerarEvento(GerenciadorDeEventos gerenciadorDeEventos);

    // Método Comum
    public void modificarClima() {
        // Lógica genérica para simular mudanças climáticas,
        // subclasses podem sobrescrever para mais especificidade
        System.out.println("O clima em " + nome + " está " + condicoesClimaticasPredominantes + ".");
    }
}
