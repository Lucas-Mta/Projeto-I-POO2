import javax.swing.*;
import java.awt.*;
import java.io.Serial;

public class MainWindow extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    private JPanel statusPanel;
    private JLabel statusLabel;
    private JMenuBar menuBar;
    private AnimatedPanel animatedPanel; // Painel animado de fundo
    private JTextArea textArea; // Área de texto para exibir o conteúdo do arquivo
    private FileHandler fileHandler; // Objeto manipulador dos arquivos
    private String title;

    public MainWindow(String title) {
        super(title);
        this.title = title;
        configureWindow();
        initializeComponents();
        addComponents();
    }

    private void configureWindow() {
        this.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.6),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.6));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(5, 5));
    }

    private void initializeComponents() {
        statusPanel = new JPanel();
        statusLabel = new JLabel(this.title);
        statusPanel.add(statusLabel);
        statusPanel.setBackground(Color.LIGHT_GRAY);
        statusPanel.setBorder(BorderFactory.createEtchedBorder());

        textArea = new JTextArea();
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Serif", Font.PLAIN, 16));

        animatedPanel = new AnimatedPanel();
        fileHandler = new FileHandler(this);

        menuBar = new JMenuBar();

        // Menu Arquivo
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem openItem = new JMenuItem("Abrir Arquivo");
        JMenuItem closeItem = new JMenuItem("Fechar Arquivo");
        JMenuItem exitItem = new JMenuItem("Sair");
        openItem.addActionListener(e -> fileHandler.openFile());
        closeItem.addActionListener(e -> fileHandler.closeFile());
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(openItem);
        fileMenu.add(closeItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // Menu Configurações
        JMenu configMenu = new JMenu("Configurações");
        JMenuItem defaultItem = new JMenuItem("Padrões");
        JMenuItem colorItem = new JMenuItem("Cores");
        JMenuItem speedItem = new JMenuItem("Velocidade");
        defaultItem.addActionListener(e -> new SettingsDialog(this, animatedPanel).setVisible(true));
        colorItem.addActionListener(e -> new ColorDialog(this, animatedPanel).setVisible(true));
        speedItem.addActionListener(e -> new SpeedDialog(this, animatedPanel).setVisible(true));
        configMenu.add(defaultItem);
        configMenu.add(colorItem);
        configMenu.add(speedItem);

        // Menu Ajuda
        JMenu helpMenu = new JMenu("Ajuda");
        JMenuItem helpItem = new JMenuItem("Ajuda");
        JMenuItem aboutItem = new JMenuItem("Sobre");
        helpItem.addActionListener(e -> new HelpDialog(this).setVisible(true));
        aboutItem.addActionListener(e -> new AboutDialog(this).setVisible(true));
        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(configMenu);
        menuBar.add(helpMenu);
    }

    private void addComponents() {
        this.add(animatedPanel, BorderLayout.CENTER);
        this.add(statusPanel, BorderLayout.SOUTH);
        this.setJMenuBar(menuBar);
    }

    // Métodos para adicionar/remover o painel animado
    public void removeAnimatedPanel() {
        this.remove(animatedPanel);
    }

    public void addAnimatedPanel() {
        this.add(animatedPanel, BorderLayout.CENTER);
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}
