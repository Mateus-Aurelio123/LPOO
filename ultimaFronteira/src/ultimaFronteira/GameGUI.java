package ultimaFronteira;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Random; // Para simular algumas ações iniciais

public class GameGUI extends JFrame implements ActionListener {

    private Personagem jogador;
    private GerenciadorDeAmbientes gerenciadorDeAmbientes;
    private GerenciadorDeEventos gerenciadorDeEventos;
    private int turnoAtual;

    // Componentes da GUI
    private JTextArea logArea;
    private JScrollPane scrollPane;
    private JTextField inputField;
    private JButton btnExplorar, btnDescansar, btnInventario, btnMover;
    private JLabel lblVida, lblFome, lblSede, lblEnergia, lblSanidade, lblLocalizacao;
    private JLabel lblImagemAmbiente; // Para exibir a imagem do ambiente

    public GameGUI() {
        setTitle("Última Fronteira - Jogo de Sobrevivência");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Margem entre os componentes
        setLocationRelativeTo(null); // Centraliza a janela

        // --- Inicialização dos Componentes do Jogo ---
        jogador = new Personagem("Aventureiro", 100, 50, 40, 80, 90, "Floresta", "Explorador");
        jogador.adicionarHabilidade("Rastreador");
        jogador.getInventario().adicionarItem(new Ferramenta("Machadinha", 1.5, 80, "machado", 10));
        jogador.getInventario().adicionarItem(new Alimento("Ração Enlatada", 0.5, 1, 25, "enlatado", 999));
        jogador.getInventario().adicionarItem(new Agua("Cantil com Água", 0.7, 10, "potavel", 15.0));

        List<Ambiente> ambientes = Arrays.asList(
                new AmbienteFloresta(),
                new AmbienteMontanha(),
                new AmbienteCaverna(),
                new AmbienteLagoRio(),
                new AmbienteRuinasAbandonadas()
        );
        gerenciadorDeAmbientes = new GerenciadorDeAmbientes(ambientes, ambientes.get(0)); // Inicia na Floresta

        List<Evento> eventos = Arrays.asList(
                new EventoClimatico("Chuva Forte", "Uma chuva torrencial começa a cair.", 0.3, "Energia, Fome, Sede", "Qualquer Ambiente", "Chuva", 3, "Dificulta acender fogueiras"),
                new EventoCriatura("Ataque de Lobo", "Um lobo selvagem te ataca de surpresa!", 0.2, "Vida", "Floresta", "Lobo", 3, Arrays.asList("Combater", "Fugir")),
                new EventoDescoberta("Abrigo Abandonado", "Você encontrou um abrigo antigo.", 0.15, "Inventário, Sanidade", "Qualquer Ambiente", "Abrigo Abandonado", Arrays.asList(new Remedio("Bandagem", 0.1, 3, "bandagem", "cura ferimentos", 15)), null),
                new EventoDoencaFerimento("Hipotermia", "O frio intenso começa a causar hipotermia.", 0.1, "Vida, Energia", "Montanha", "Hipotermia", "Vida, Energia", "Fogueira", 10)
        );
        gerenciadorDeEventos = new GerenciadorDeEventos(eventos);

        turnoAtual = 0;

        // --- Configuração da Interface Gráfica ---

        // Painel de Status (Topo)
        JPanel statusPanel = new JPanel(new GridLayout(2, 6, 5, 5)); // 2 linhas, 6 colunas, espaçamento
        statusPanel.setBorder(BorderFactory.createTitledBorder("Status do Personagem"));
        lblVida = new JLabel("Vida: ");
        lblFome = new JLabel("Fome: ");
        lblSede = new JLabel("Sede: ");
        lblEnergia = new JLabel("Energia: ");
        lblSanidade = new JLabel("Sanidade: ");
        lblLocalizacao = new JLabel("Localização: ");
        statusPanel.add(lblVida);
        statusPanel.add(lblFome);
        statusPanel.add(lblSede);
        statusPanel.add(lblEnergia);
        statusPanel.add(lblSanidade);
        statusPanel.add(lblLocalizacao);
        add(statusPanel, BorderLayout.NORTH);

        // Painel Central (Log e Imagem do Ambiente)
        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        logArea = new JTextArea(20, 50);
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(logArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registro de Eventos"));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        lblImagemAmbiente = new JLabel();
        lblImagemAmbiente.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagemAmbiente.setPreferredSize(new Dimension(300, 200)); // Tamanho fixo para a imagem
        lblImagemAmbiente.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        centerPanel.add(lblImagemAmbiente, BorderLayout.EAST); // Imagem à direita do log
        add(centerPanel, BorderLayout.CENTER);

        // Painel de Ações (Fundo)
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnExplorar = new JButton("Explorar");
        btnDescansar = new JButton("Descansar");
        btnInventario = new JButton("Inventário (Console)"); // Por simplicidade, para o console
        btnMover = new JButton("Mover para...");

        btnExplorar.addActionListener(this);
        btnDescansar.addActionListener(this);
        btnInventario.addActionListener(this);
        btnMover.addActionListener(this);

        actionPanel.add(btnExplorar);
        actionPanel.add(btnDescansar);
        actionPanel.add(btnInventario);
        actionPanel.add(btnMover);
        add(actionPanel, BorderLayout.SOUTH);

        // Mensagem inicial e atualização da GUI
        logArea.append("Bem-vindo à Última Fronteira! Tente sobreviver o máximo possível.\n");
        logArea.append("Turno 0: Você acorda na " + jogador.getLocalizacao() + ".\n");
        atualizarInterface();

        setVisible(true);
    }

    // --- Métodos de Lógica do Jogo ---

    private void proximoTurno() {
        turnoAtual++;
        logArea.append("\n--- Início do Turno " + turnoAtual + " ---\n");

        // Fase de Manutenção (consumo de fome, sede, etc.)
        jogador.setFome(jogador.getFome() + 5);
        jogador.setSede(jogador.getSede() + 7);
        jogador.setSanidade(jogador.getSanidade() - 1); // Sanidade diminui lentamente
        jogador.setEnergia(jogador.getEnergia() - 2); // Pequeno gasto de energia natural

        // Efeitos de fome/sede/sanidade críticos
        if (jogador.getFome() > 80) {
            jogador.receberDano(3);
            logArea.append("Você está com muita fome! Sua vida está diminuindo.\n");
        }
        if (jogador.getSede() > 70) {
            jogador.receberDano(5);
            logArea.append("Você está com muita sede! Sua vida está diminuindo mais rápido.\n");
        }
        if (jogador.getSanidade() < 20) {
            logArea.append("Sua sanidade está baixa! Você está começando a ver coisas...\n");
            jogador.receberDano(2); // Dano psicológico
        }

        // Fase de Evento Aleatório
        Evento eventoOcorrido = gerenciadorDeEventos.sortearEvento(gerenciadorDeAmbientes.getAmbienteAtual());
        gerenciadorDeEventos.aplicarEvento(eventoOcorrido, jogador, gerenciadorDeAmbientes.getAmbienteAtual());

        // Verificar condições de derrota
        verificarCondicoesDerrota();
        atualizarInterface();
    }

    private void explorarAmbiente() {
        if (jogador.getEnergia() < gerenciadorDeAmbientes.getAmbienteAtual().getDificuldadeDeExploracao()) {
            logArea.append("Você está muito exausto para explorar este ambiente. Descanse um pouco.\n");
            return;
        }
        gerenciadorDeAmbientes.getAmbienteAtual().explorar(jogador);
        proximoTurno();
    }

    private void descansar() {
        int energiaRestaurada = 30;
        jogador.restaurarEnergia(energiaRestaurada);
        logArea.append(jogador.getNome() + " descansou e restaurou " + energiaRestaurada + " de energia.\n");
        proximoTurno();
    }

    private void exibirInventario() {
        logArea.append("\n--- Inventário de " + jogador.getNome() + " ---\n");
        if (jogador.getInventario().getItens().isEmpty()) {
            logArea.append("Seu inventário está vazio.\n");
        } else {
            for (Item item : jogador.getInventario().getItens()) {
                logArea.append(" - " + item.getNome() + " (Peso: " + item.getPeso() + "kg, Durab.: " + item.getDurabilidade() + ")\n");
            }
        }
        logArea.append("Peso Total: " + String.format("%.2f", jogador.getInventario().getPesoTotal()) + "/" + String.format("%.2f", jogador.getInventario().getCapacidadeMaxima()) + "kg\n");
        logArea.append("-----------------------------\n");
        logArea.append("Para usar um item, digite 'usar [nome do item]' no campo de texto abaixo.\n");
        // No futuro, isso seria uma nova janela ou painel com botões para usar os itens
    }

    private void solicitarMover() {
        String[] ambientesDisponiveis = gerenciadorDeAmbientes.getListaDeAmbientesDisponiveis().stream()
                .map(Ambiente::getNome)
                .toArray(String[]::new);

        String novoAmbiente = (String) JOptionPane.showInputDialog(
                this,
                "Para qual ambiente você deseja se mover?",
                "Mover",
                JOptionPane.QUESTION_MESSAGE,
                null,
                ambientesDisponiveis,
                gerenciadorDeAmbientes.getAmbienteAtual().getNome());

        if (novoAmbiente != null && !novoAmbiente.isEmpty() && !novoAmbiente.equalsIgnoreCase(gerenciadorDeAmbientes.getAmbienteAtual().getNome())) {
            gerenciadorDeAmbientes.mudarAmbiente(jogador, novoAmbiente);
            proximoTurno(); // Mover gasta um turno
        } else {
            logArea.append("Movimento cancelado ou ambiente inválido.\n");
        }
    }


    private void verificarCondicoesDerrota() {
        if (jogador.getVida() <= 0) {
            logArea.append("\n--- GAME OVER ---\nSua vida chegou a zero. Você sucumbiu aos perigos da Última Fronteira.\n");
            desabilitarAcoes();
        } else if (jogador.getFome() >= 100 && jogador.getSede() >= 100 && turnoAtual > 10) { // Exemplo: se fome e sede muito altas por X turnos
            logArea.append("\n--- GAME OVER ---\nVocê morreu de exaustão e desnutrição. Não conseguiu gerenciar seus recursos.\n");
            desabilitarAcoes();
        } else if (jogador.getSanidade() <= 0) {
            logArea.append("\n--- GAME OVER ---\nSua sanidade se esgotou. Você sucumbiu à loucura da Última Fronteira.\n");
            desabilitarAcoes();
        }
        // Adicionar outras condições de derrota aqui
    }

    private void desabilitarAcoes() {
        btnExplorar.setEnabled(false);
        btnDescansar.setEnabled(false);
        btnInventario.setEnabled(false);
        btnMover.setEnabled(false);
        inputField.setEnabled(false);
    }

    // --- Métodos da Interface Gráfica ---

    private void atualizarInterface() {
        lblVida.setText("Vida: " + jogador.getVida());
        lblFome.setText("Fome: " + jogador.getFome());
        lblSede.setText("Sede: " + jogador.getSede());
        lblEnergia.setText("Energia: " + jogador.getEnergia());
        lblSanidade.setText("Sanidade: " + jogador.getSanidade());
        lblLocalizacao.setText("Localização: " + jogador.getLocalizacao());
        // Aqui você carregaria a imagem do ambiente
        // setImagemAmbiente(gerenciadorDeAmbientes.getAmbienteAtual().getNome());
    }

    private void setImagemAmbiente(String nomeAmbiente) {
        // Exemplo: Carregar imagem do ambiente
        // Lembre-se que as imagens devem estar em um diretório acessível pelo JAR
        // ou no classpath. Ex: /resources/imagens/floresta.jpg
        String path = "/imagens/" + nomeAmbiente.toLowerCase().replace(" ", "") + ".jpg"; // Exemplo de nomeclatura
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            if (icon.getIconWidth() == -1) { // Verifica se a imagem foi carregada
                System.err.println("Imagem não encontrada: " + path);
                lblImagemAmbiente.setText("Sem Imagem");
                lblImagemAmbiente.setIcon(null);
            } else {
                Image image = icon.getImage().getScaledInstance(lblImagemAmbiente.getWidth(), lblImagemAmbiente.getHeight(), Image.SCALE_SMOOTH);
                lblImagemAmbiente.setIcon(new ImageIcon(image));
                lblImagemAmbiente.setText("");
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem para " + nomeAmbiente + ": " + e.getMessage());
            lblImagemAmbiente.setText("Erro ao Carregar Imagem");
            lblImagemAmbiente.setIcon(null);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnExplorar) {
            explorarAmbiente();
        } else if (e.getSource() == btnDescansar) {
            descansar();
        } else if (e.getSource() == btnInventario) {
            exibirInventario();
        } else if (e.getSource() == btnMover) {
            solicitarMover();
        } else if (e.getSource() == inputField) { // Para comandos de texto (como usar item)
            String comando = inputField.getText().trim();
            inputField.setText(""); // Limpa o campo
            if (comando.startsWith("usar ")) {
                String nomeItem = comando.substring(5).trim();
                jogador.usarItem(nomeItem);
                proximoTurno(); // Usar item gasta um turno
            } else {
                logArea.append("Comando inválido: '" + comando + "'. Digite 'usar [nome do item]'.\n");
            }
        }
    }

    public static void main(String[] args) {
        // Garante que a GUI seja criada na thread de despacho de eventos do Swing
        SwingUtilities.invokeLater(GameGUI::new);
    }
}