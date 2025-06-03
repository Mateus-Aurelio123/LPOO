package ultimaFronteira;

public class Material extends Item {
    private String tipo; // "madeira", "pedra", "metal"
    private int resistencia;

    public Material(String nome, double peso, int durabilidade, String tipo, int resistencia) {
        super(nome, peso, durabilidade);
        this.tipo = tipo;
        this.resistencia = resistencia;
    }

    // Getters adicionais
    public String getTipo() { return tipo; }
    public int getResistencia() { return resistencia; }

    @Override
    public void usar(Personagem personagem) {
        System.out.println(personagem.getNome() + " pegou o material " + getNome() + ".");
        // Materiais geralmente não são "usados" diretamente, mas sim combinados ou para crafting
    }

    public Item combinar(Material outroMaterial) {
        System.out.println("Tentando combinar " + this.getNome() + " com " + outroMaterial.getNome());
        // Lógica de crafting: Exemplo básico de combinação
        if (this.getTipo().equals("madeira") && outroMaterial.getTipo().equals("pedra")) {
            System.out.println("Você pode criar uma machadinha rústica!");
            return new Ferramenta("Machadinha Rústica", 1.5, 50, "machado", 5);
        }
        // Adicione mais receitas de combinação aqui
        System.out.println("Combinação não resultou em nada novo.");
        return null;
    }
}