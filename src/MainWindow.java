import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.Serial;

public class MainWindow extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    private JPanel statusPanel;
    private JLabel statusLabel;
    private JMenuBar menuBar;
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
        // Definir o tamanho da janela
        this.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.6),
                (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.6));

        // Centralizar a janela
        this.setLocationRelativeTo(null);

        // Fechar a aplicação quando a janela for fechada
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Definir layout da janela
        this.setLayout(new BorderLayout(5, 5));
    }

    private void initializeComponents() {
        // Inicializa a BARRA DE STATUS
        statusPanel = new JPanel();
        statusLabel = new JLabel(this.title); // Mudar depois
        statusPanel.add(statusLabel);
        statusPanel.setBackground(Color.LIGHT_GRAY);
        statusPanel.setBorder(BorderFactory.createEtchedBorder());

        // Inicializa a ÁREA DE TEXTO
        textArea = new JTextArea();
        textArea.setMargin(new Insets(10, 10, 10, 10));
        textArea.setEditable(false);
        textArea.setLineWrap(true); // Quebra de linha automática
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Serif", Font.PLAIN, 16));

        // Inicializa o MANIPULADOR DE ARQUIVOS
        fileHandler = new FileHandler(textArea);

        // Inicializa a BARRA DE MENUS
        menuBar = new JMenuBar();

        // MENU ARQUIVOS
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem openItem = new JMenuItem("Abrir Arquivo");
        JMenuItem closeItem = new JMenuItem("Fechar Arquivo");
        JMenuItem exitItem = new JMenuItem("Sair");

        // Ações do menu Arquivos
        openItem.addActionListener(e -> fileHandler.openFile(this));
        closeItem.addActionListener(e -> fileHandler.closeFile());
        exitItem.addActionListener(e -> System.exit(0));

        // Adiciona os itens no menu Arquivos:
        fileMenu.add(openItem);
        fileMenu.add(closeItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // MENU CONFIGURAÇÕES
        JMenu configMenu = new JMenu("Configurações");
        JMenuItem defaultItem = new JMenuItem("Padrões");
        JMenuItem colorItem = new JMenuItem("Cores");
        JMenuItem speedItem = new JMenuItem("Velocidade");

        // Ações dos itens do menu Configurações:
        defaultItem.addActionListener(e -> new SettingsDialog(this).setVisible(true));
        colorItem.addActionListener(e -> new ColorDialog(this).setVisible(true));
        speedItem.addActionListener(e -> new SpeedDialog(this).setVisible(true));

        // Adiciona os itens no menu Configurações:
        configMenu.add(defaultItem);
        configMenu.add(colorItem);
        configMenu.add(speedItem);

        // MENU AJUDA
        JMenu helpMenu = new JMenu("Ajuda");
        JMenuItem helpItem = new JMenuItem("Ajuda");
        JMenuItem aboutItem = new JMenuItem("Sobre");

        // Ações(Diálogos) do menu Ajuda:
        helpItem.addActionListener(e -> new HelpDialog(this).setVisible(true));
        aboutItem.addActionListener(e -> new AboutDialog(this).setVisible(true));

        // Adiciona os itens no menu Ajuda:
        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);

        // Adiciona os menus criados na barra de menus
        menuBar.add(fileMenu);
        menuBar.add(configMenu);
        menuBar.add(helpMenu);
    }

    private void addComponents() {
        // Criar um painel para centralizar a área de texto
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBackground(Color.LIGHT_GRAY);  // Definir cor de fundo visível ao redor do texto

        // Configurar a área de texto só com scroll vertical
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        centerPanel.add(scrollPane, new GridBagConstraints());

        // Adicionar o painel central à janela principal
        this.add(centerPanel, BorderLayout.CENTER);

        // Adicionar barra de status na parte inferior da janela
        this.add(statusPanel, BorderLayout.SOUTH);

        // Adicionar a barra de menus na janela
        this.setJMenuBar(menuBar);
    }
}
