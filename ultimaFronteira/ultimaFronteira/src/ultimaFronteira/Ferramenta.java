package ultimaFronteira;

public class Ferramenta extends Item {
    private String tipoFerramenta; // "machado", "faca", "isqueiro", "lanterna"
    private int eficiencia;

    public Ferramenta(String nome, double peso, int durabilidade, String tipoFerramenta, int eficiencia) {
        super(nome, peso, durabilidade);
        this.tipoFerramenta = tipoFerramenta;
        this.eficiencia = eficiencia;
    }

    // Getters adicionais
    public String getTipoFerramenta() { return tipoFerramenta; }
    public int getEficiencia() { return eficiencia; }

    @Override
    public void usar(Personagem personagem) {
        if (getDurabilidade() > 0) {
            System.out.println(personagem.getNome() + " usou a ferramenta " + getNome() + ".");
            // Lógica de uso da ferramenta
            switch (tipoFerramenta) {
                case "machado":
                    System.out.println("Você cortou madeira com eficiência!");
                    // Personagem ganha madeira
                    break;
                case "faca":
                    System.out.println("Você usou a faca para coletar carne ou peles!");
                    // Personagem ganha carne/peles
                    break;
                case "isqueiro":
                    System.out.println("Você acendeu uma fogueira!");
                    personagem.restaurarEnergia(10); // Exemplo de efeito
                    break;
                case "lanterna":
                    System.out.println("Você iluminou o ambiente.");
                    // Reduz sanidade se for muito tempo no escuro (inverso)
                    break;
                default:
                    System.out.println("Nenhum efeito especial para esta ferramenta.");
            }
            // A durabilidade é reduzida em Personagem.usarItem()
        } else {
            System.out.println(getNome() + " está quebrada e não pode ser usada.");
        }
    }
}
