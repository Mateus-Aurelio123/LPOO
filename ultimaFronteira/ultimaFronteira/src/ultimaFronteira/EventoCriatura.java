package ultimaFronteira;

import java.util.List;

public class EventoCriatura extends Evento {
    private String tipoDeCriatura; // "Lobo", "Urso", "Cobra", "Corvo"
    private int nivelDePerigo; // 1-5
    private List<String> opcoesDeAcao; // Ex: "Evitar", "Combater", "Observar"

    public EventoCriatura(String nome, String descricao, double probabilidadeDeOcorrencia, String impacto, String condicaoDeAtivacao, String tipoDeCriatura, int nivelDePerigo, List<String> opcoesDeAcao) {
        super(nome, descricao, probabilidadeDeOcorrencia, impacto, condicaoDeAtivacao);
        this.tipoDeCriatura = tipoDeCriatura;
        this.nivelDePerigo = nivelDePerigo;
        this.opcoesDeAcao = opcoesDeAcao;
    }

    // Getters adicionais
    public String getTipoDeCriatura() { return tipoDeCriatura; }
    public int getNivelDePerigo() { return nivelDePerigo; }
    public List<String> getOpcoesDeAcao() { return opcoesDeAcao; }

    @Override
    public void executar(Personagem jogador, Ambiente local) {
        System.out.println("\n*** Evento de Criatura: " + getNome() + " ***");
        System.out.println(getDescricao());

        // Lógica de interação com a criatura
        // Este é um exemplo simplificado, na prática o jogador escolheria uma ação
        switch (tipoDeCriatura) {
            case "Lobo":
                System.out.println("Um lobo selvagem se aproxima! Você teme por sua vida.");
                jogador.receberDano(nivelDePerigo * 5); // Exemplo de dano
                jogador.afetarSanidade(-10);
                System.out.println(jogador.getNome() + " foi atacado por um lobo! Perdeu vida e sanidade.");
                break;
            case "Cobra Venenosa":
                System.out.println("Uma cobra venenosa te picou! Você precisa de um antídoto rápido.");
                jogador.receberDano(nivelDePerigo * 10);
                // Poderia adicionar um efeito de veneno que reduz vida a cada turno
                System.out.println(jogador.getNome() + " foi picado por uma cobra venenosa!");
                break;
            case "Corvo Furtivo":
                System.out.println("Corvos estranhos voam sobre você, causando arrepios.");
                jogador.afetarSanidade(-15);
                System.out.println(jogador.getNome() + " está começando a ter alucinações.");
                break;
            default:
                System.out.println("A criatura não parece ser um perigo imediato.");
                break;
        }
    }
}
