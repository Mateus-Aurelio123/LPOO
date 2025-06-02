package ultimaFronteira;

public class Arma extends Item {
    private String tipoArma; // "corpo a corpo", "a distancia"
    private int dano;
    private int alcance;
    private int municaoAtual; // Para armas a distancia

    public Arma(String nome, double peso, int durabilidade, String tipoArma, int dano, int alcance, int municaoAtual) {
        super(nome, peso, durabilidade);
        this.tipoArma = tipoArma;
        this.dano = dano;
        this.alcance = alcance;
        this.municaoAtual = municaoAtual;
    }

    // Getters e Setters adicionais
    public String getTipoArma() { return tipoArma; }
    public int getDano() { return dano; }
    public int getAlcance() { return alcance; }
    public int getMunicaoAtual() { return municaoAtual; }
    public void setMunicaoAtual(int municaoAtual) { this.municaoAtual = Math.max(0, municaoAtual); }


    @Override
    public void usar(Personagem personagem) {
        System.out.println(personagem.getNome() + " empunha " + getNome() + ". Para usar, você precisa atacar um alvo.");
        // A ação de "usar" uma arma é geralmente um "atacar"
    }

    public void atacar(Personagem inimigo) { // Poderia ser um alvo genérico 'Alvo'
        if (getDurabilidade() > 0) {
            if (tipoArma.equals("a distancia") && municaoAtual <= 0) {
                System.out.println(getNome() + " está sem munição!");
                return;
            }

            System.out.println(getNome() + " atacou " + inimigo.getNome() + " causando " + dano + " de dano!");
            inimigo.receberDano(dano);

            if (tipoArma.equals("a distancia")) {
                setMunicaoAtual(getMunicaoAtual() - 1);
            }
            // A durabilidade é reduzida em Personagem.usarItem()
        } else {
            System.out.println(getNome() + " está quebrada e não pode atacar.");
        }
    }
}
