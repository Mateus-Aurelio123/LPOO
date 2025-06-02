package ultimaFronteira;

public class Remedio extends Item {
    private String tipoRemedio; // "bandagem", "antibiotico", "analgesico"
    private String efeito; // "cura ferimentos", "alivia dor", "trata infeccoes"
    private int valorEfeito; // Quanto de vida, sanidade, etc.

    public Remedio(String nome, double peso, int durabilidade, String tipoRemedio, String efeito, int valorEfeito) {
        super(nome, peso, durabilidade);
        this.tipoRemedio = tipoRemedio;
        this.efeito = efeito;
        this.valorEfeito = valorEfeito;
    }

    // Getters adicionais
    public String getTipoRemedio() { return tipoRemedio; }
    public String getEfeito() { return efeito; }
    public int getValorEfeito() { return valorEfeito; }

    @Override
    public void usar(Personagem personagem) {
        if (getDurabilidade() > 0) { // Durabilidade pode representar doses ou uso único
            System.out.println(personagem.getNome() + " usou " + getNome() + ".");
            switch (efeito) {
                case "cura ferimentos":
                    personagem.curar(valorEfeito);
                    System.out.println("Ferimentos curados em " + valorEfeito + " pontos.");
                    break;
                case "alivia dor":
                    personagem.restaurarEnergia(valorEfeito);
                    System.out.println("Dores aliviadas, energia restaurada em " + valorEfeito + " pontos.");
                    break;
                case "trata infeccoes":
                    personagem.afetarSanidade(valorEfeito); // Infeções podem afetar sanidade
                    System.out.println("Infecção tratada. Sanidade restaurada em " + valorEfeito + " pontos.");
                    break;
                default:
                    System.out.println("Efeito desconhecido do remédio.");
            }
            // A durabilidade é reduzida em Personagem.usarItem()
        } else {
            System.out.println(getNome() + " está esgotado ou ineficaz.");
        }
    }
}