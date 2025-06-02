package ultimaFronteira;

import java.util.Collection;
import java.util.List;

public class GerenciadorDeAmbientes {
    private List<Ambiente> listaDeAmbientesDisponiveis;
    private Ambiente ambienteAtual;
    private String climaGlobal; // Ex: "Chuvoso", "Ensolarado", "Neve"

    public GerenciadorDeAmbientes(List<Ambiente> ambientesDisponiveis, Ambiente ambienteInicial) {
        this.listaDeAmbientesDisponiveis = ambientesDisponiveis;
        this.ambienteAtual = ambienteInicial;
        this.climaGlobal = "Ensolarado"; // Clima inicial
    }

    // Getters
    public Ambiente getAmbienteAtual() { return ambienteAtual; }
    public String getClimaGlobal() { return climaGlobal; }

    // Setters
    public void setClimaGlobal(String climaGlobal) { this.climaGlobal = climaGlobal; }

    // Métodos Principais
    public void mudarAmbiente(Personagem jogador, String nomeNovoAmbiente) {
        for (Ambiente ambiente : listaDeAmbientesDisponiveis) {
            if (ambiente.getNome().equalsIgnoreCase(nomeNovoAmbiente)) {
                this.ambienteAtual = ambiente;
                jogador.setLocalizacao(nomeNovoAmbiente);
                System.out.println("\n" + jogador.getNome() + " se moveu para " + nomeNovoAmbiente + ".");
                // Aplicar efeitos de mudança de ambiente (ex: gastar mais energia em montanhas)
                jogador.gastarEnergia(ambiente.getDificuldadeDeExploracao() / 2); // Custo de movimento
                return;
            }
        }
        System.out.println("Ambiente '" + nomeNovoAmbiente + "' não encontrado ou inacessível.");
    }

    public Evento gerarEventoAtual(GerenciadorDeEventos gerenciadorDeEventos) {
        return gerenciadorDeEventos.sortearEvento(ambienteAtual);
    }

    public void modificarRecursos(Ambiente local) {
        // Esta lógica geralmente está dentro do método explorar() do Ambiente,
        // mas pode ser um método aqui para regeneração ou esgotamento global
        System.out.println("Recursos em " + local.getNome() + " foram modificados (se aplicável).");
    }

}