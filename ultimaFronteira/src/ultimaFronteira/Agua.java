package ultimaFronteira;

public class Agua extends Item {
    private String pureza; // "potavel", "contaminada"
    private double volume;

    public Agua(String nome, double peso, int durabilidade, String pureza, double volume) {
        super(nome, peso, durabilidade);
        this.pureza = pureza;
        this.volume = volume;
    }

    // Getters adicionais
    public String getPureza() { return pureza; }
    public double getVolume() { return volume; }

    @Override
    public void usar(Personagem personagem) {
        if (getDurabilidade() > 0) { // Durabilidade pode representar quantidade de uso ou pureza
            personagem.beberAgua((int)volume);
            System.out.println(personagem.getNome() + " bebeu " + getNome() + ". Sede restaurada em " + (int)volume + " pontos.");

            if (pureza.equals("contaminada")) {
                personagem.receberDano(10);
                personagem.afetarSanidade(-10);
                System.out.println(personagem.getNome() + " ficou doente por beber água contaminada!");
            }
            // Não remove o item aqui, o Personagem.usarItem() faz isso
        } else {
            System.out.println(getNome() + " está vazio ou impróprio para consumo.");
        }
    }
}
