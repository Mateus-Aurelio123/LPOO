package ultimaFronteira;

public class Alimento extends Item {
    private int valorNutricional;
    private String tipo;
    private int prazoValidade; // Em número de turnos

    public Alimento(String nome, double peso, int durabilidade, int valorNutricional, String tipo, int prazoValidade) {
        super(nome, peso, durabilidade);
        this.valorNutricional = valorNutricional;
        this.tipo = tipo;
        this.prazoValidade = prazoValidade;
    }

    // Getters adicionais
    public int getValorNutricional() { return valorNutricional; }
    public String getTipo() { return tipo; }
    public int getPrazoValidade() { return prazoValidade; }

    @Override
    public void usar(Personagem personagem) {
        if (getDurabilidade() > 0) { // Durabilidade pode ser usada para representar prazo de validade
            personagem.consumirAlimento(valorNutricional);
            System.out.println(personagem.getNome() + " consumiu " + getNome() + ". Fome restaurada em " + valorNutricional + " pontos.");
            // Lógica para intoxicação alimentar se prazoValidade for baixo ou item estragado
            if (getDurabilidade() < 5 && Math.random() < 0.2) { // 20% de chance se quase estragado
                personagem.receberDano(5);
                personagem.afetarSanidade(-5);
                System.out.println(personagem.getNome() + " sentiu-se mal após comer " + getNome() + "!");
            }
            // Não remove o item aqui, o Personagem.usarItem() faz isso se for consumível
        } else {
            System.out.println(getNome() + " está estragado e não pode ser consumido.");
        }
    }
}