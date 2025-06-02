package ultimaFronteira;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GerenciadorDeEventos {
    private List<Evento> listaDeEventosPossiveis;
    private List<Evento> historicoDeEventos; // Opcional, para controlar repetições
    private Random random;

    public GerenciadorDeEventos(List<Evento> eventosPossiveis) {
        this.listaDeEventosPossiveis = eventosPossiveis;
        this.historicoDeEventos = new ArrayList<>();
        this.random = new Random();
    }

    // Getter
    public List<Evento> getListaDeEventosPossiveis() {
        return listaDeEventosPossiveis;
    }

    // Métodos Principais
    public Evento sortearEvento(Ambiente local) {
        // Filtra eventos que podem ocorrer no ambiente atual
        List<Evento> eventosCandidatos = listaDeEventosPossiveis.stream()
                .filter(e -> e.getCondicaoDeAtivacao().equalsIgnoreCase(local.getNome()) || e.getCondicaoDeAtivacao().equalsIgnoreCase("Qualquer Ambiente"))
                .collect(Collectors.toList());

        if (eventosCandidatos.isEmpty()) {
            System.out.println("Nenhum evento aplicável a este ambiente.");
            return null;
        }

        for (Evento evento : eventosCandidatos) {
            if (random.nextDouble() < evento.getProbabilidadeDeOcorrencia()) {
                historicoDeEventos.add(evento); // Adiciona ao histórico
                return evento;
            }
        }
        return null; // Nenhum evento ocorreu neste turno
    }

    // Sobrecarga para sortear eventos com uma lista de eventos específica
    public Evento sortearEvento(Ambiente local, List<Evento> eventosEspecificos) {
        if (eventosEspecificos.isEmpty()) {
            return null;
        }
        for (Evento evento : eventosEspecificos) {
            if (random.nextDouble() < evento.getProbabilidadeDeOcorrencia()) {
                historicoDeEventos.add(evento);
                return evento;
            }
        }
        return null;
    }

    public void aplicarEvento(Evento evento, Personagem jogador, Ambiente local) {
        if (evento != null) {
            evento.executar(jogador, local);
        }
    }

    public void removerEvento(Evento evento) {
        // Implemente lógica para remover eventos de duração limitada se necessário
        historicoDeEventos.remove(evento);
    }
}
