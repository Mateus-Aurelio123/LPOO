package ultimaFronteira;

import java.util.ArrayList;
import java.util.List;

public class Personagem {
    private String nome;
    private int vida;
    private int fome;
    private int sede;
    private int energia;
    private int sanidade;
    private Inventario inventario;
    private String localizacao;
    private List<String> habilidades;
    private String classe; // Opcional, para classes mais específicas

    public Personagem(String nome, int vida, int fome, int sede, int energia, int sanidade, String localizacao, String classe) {
        this.nome = nome;
        this.vida = vida;
        this.fome = fome;
        this.sede = sede;
        this.energia = energia;
        this.sanidade = sanidade;
        this.inventario = new Inventario();
        this.localizacao = localizacao;
        this.habilidades = new ArrayList<>();
        this.classe = classe;
    }

    // Getters
    public String getNome() { return nome; }
    public int getVida() { return vida; }
    public int getFome() { return fome; }
    public int getSede() { return sede; }
    public int getEnergia() { return energia; }
    public int getSanidade() { return sanidade; }
    public Inventario getInventario() { return inventario; }
    public String getLocalizacao() { return localizacao; }
    public List<String> getHabilidades() { return habilidades; }
    public String getClasse() { return classe; }

    // Setters (com validação básica)
    public void setVida(int vida) { this.vida = Math.max(0, vida); }
    public void setFome(int fome) { this.fome = Math.max(0, fome); }
    public void setSede(int sede) { this.sede = Math.max(0, sede); }
    public void setEnergia(int energia) { this.energia = Math.max(0, energia); }
    public void setSanidade(int sanidade) { this.sanidade = Math.max(0, sanidade); }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }

    // Métodos Principais
    public void adicionarHabilidade(String habilidade) {
        if (!habilidades.contains(habilidade)) {
            this.habilidades.add(habilidade);
            System.out.println(nome + " adquiriu a habilidade: " + habilidade);
        }
    }

    public void usarItem(String nomeItem) {
        Item item = inventario.getItem(nomeItem);
        if (item != null) {
            item.usar(this); // Chama o método usar polimórfico do item
            // Lógica para remover o item se for consumível (Alimento, Agua, Remedio)
            if (item instanceof Alimento || item instanceof Agua || item instanceof Remedio) {
                 inventario.removerItem(nomeItem); // Remova o item após o uso
            } else if (item instanceof Ferramenta || item instanceof Arma) {
                // Reduzir durabilidade para ferramentas e armas
                item.setDurabilidade(item.getDurabilidade() - 1);
                if (item.getDurabilidade() <= 0) {
                    System.out.println(item.getNome() + " quebrou!");
                    inventario.removerItem(nomeItem);
                }
            }
        } else {
            System.out.println(nome + ": Item '" + nomeItem + "' não encontrado no inventário.");
        }
    }

    public void receberDano(int quantidade) {
        this.setVida(this.getVida() - quantidade);
        System.out.println(nome + " recebeu " + quantidade + " de dano. Vida atual: " + vida);
    }

    public void curar(int quantidade) {
        this.setVida(this.getVida() + quantidade);
        System.out.println(nome + " curou " + quantidade + " de vida. Vida atual: " + vida);
    }

    public void consumirAlimento(int valorNutricional) {
        this.setFome(this.getFome() - valorNutricional);
        System.out.println(nome + " consumiu alimento. Fome atual: " + fome);
    }

    public void beberAgua(int volume) {
        this.setSede(this.getSede() - volume);
        System.out.println(nome + " bebeu água. Sede atual: " + sede);
    }

    public void gastarEnergia(int quantidade) {
        this.setEnergia(this.getEnergia() - quantidade);
        System.out.println(nome + " gastou " + quantidade + " de energia. Energia atual: " + energia);
    }

    public void restaurarEnergia(int quantidade) {
        this.setEnergia(this.getEnergia() + quantidade);
        System.out.println(nome + " restaurou " + quantidade + " de energia. Energia atual: " + energia);
    }

    public void afetarSanidade(int quantidade) {
        this.setSanidade(this.getSanidade() + quantidade); // Pode ser positivo ou negativo
        System.out.println(nome + " teve a sanidade alterada em " + quantidade + ". Sanidade atual: " + sanidade);
    }

    public String verificarStatus() {
        return String.format(
            "--- Status de %s ---\n" +
            "Vida: %d\n" +
            "Fome: %d\n" +
            "Sede: %d\n" +
            "Energia: %d\n" +
            "Sanidade: %d\n" +
            "Localização: %s\n" +
            "Habilidades: %s\n" +
            "Inventário: %s itens (Peso: %.2f/%.2f)\n" +
            "---------------------\n",
            nome, vida, fome, sede, energia, sanidade, localizacao,
            habilidades.isEmpty() ? "Nenhuma" : String.join(", ", habilidades),
            inventario.getItens().size(), inventario.getPesoTotal(), inventario.getCapacidadeMaxima()
        );
    }
}
