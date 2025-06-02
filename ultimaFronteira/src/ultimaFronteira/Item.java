package ultimaFronteira;

public abstract class Item {
    private String nome;
    private double peso;
    private int durabilidade;

    public Item(String nome, double peso, int durabilidade) {
        this.nome = nome;
        this.peso = peso;
        this.durabilidade = durabilidade;
    }

    // Getters
    public String getNome() { return nome; }
    public double getPeso() { return peso; }
    public int getDurabilidade() { return durabilidade; }

    // Setter para durabilidade (útil para ferramentas/armas)
    public void setDurabilidade(int durabilidade) {
        this.durabilidade = Math.max(0, durabilidade);
    }

    // Método Abstrato
    public abstract void usar(Personagem personagem);
}

