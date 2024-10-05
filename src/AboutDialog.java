import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URI;

public class AboutDialog extends JDialog {
    private static final String APP_NAME = "Projeto I";
    private static final String VERSION = "1.0.0";
    private static final String GITHUB_URL = "https://github.com/Lucas-Mta/Projeto-I-POO2";
    private static final Color LINK_COLOR = new Color(51, 102, 187);

    public AboutDialog(JFrame parent) {
        super(parent, "Sobre " + APP_NAME, true);
        initializeDialog();
        createComponents();
        pack();
        setLocationRelativeTo(parent);
    }

    private void initializeDialog() {
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout(10, 10));
    }

    private void createComponents() {
        // Panel principal com layout personalizado
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel superior com logo e título
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Painel central com informações
        JPanel contentPanel = createContentPanel();
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Painel inferior com botões
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout(10, 10));
        headerPanel.setBackground(Color.WHITE);

        // Logo da aplicação (um ícone simples de arquivo)
        ImageIcon appIcon = createAppIcon();
        JLabel iconLabel = new JLabel(appIcon);
        headerPanel.add(iconLabel, BorderLayout.WEST);

        // Título e versão
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(APP_NAME);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel versionLabel = new JLabel("Versão " + VERSION);
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        titlePanel.add(titleLabel);
        titlePanel.add(versionLabel);
        headerPanel.add(titlePanel, BorderLayout.CENTER);

        return headerPanel;
    }

    private ImageIcon createAppIcon() {
        // Cria um ícone simples programaticamente
        int iconSize = 64;
        BufferedImage image = new BufferedImage(iconSize, iconSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // Configurações de renderização
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Desenha um ícone de arquivo estilizado
        g2d.setColor(new Color(41, 128, 185));
        int margin = 8;
        int fold = 16;

        // Corpo do arquivo
        g2d.fillRect(margin, margin + fold, iconSize - 2 * margin, iconSize - 2 * margin - fold);

        // Dobra do arquivo
        int[] xPoints = {margin, margin + fold, iconSize - margin};
        int[] yPoints = {margin + fold, margin, margin};
        g2d.fillPolygon(xPoints, yPoints, 3);

        g2d.dispose();
        return new ImageIcon(image);
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(Color.WHITE);

        // Área de texto com informações
        JTextArea infoArea = getjTextArea();

        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(400, 200));

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Link para o GitHub
        JLabel githubLink = createHyperlinkLabel();
        contentPanel.add(githubLink, BorderLayout.SOUTH);

        return contentPanel;
    }

    private static JTextArea getjTextArea() {
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setWrapStyleWord(true);
        infoArea.setLineWrap(true);
        infoArea.setBackground(Color.WHITE);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 12));
        infoArea.setText(String.format("""
        	%s é uma aplicação Java de manipulação de arquivos com interface gráfica.
       	 
        	Características principais:
        	• Interface gráfica intuitiva
        	• Suporte a múltiplos formatos de arquivo
        	• Animações dinâmicas de fundo
        	• Personalização completa
       	 
        	Desenvolvido por: Lucas Mota
        	Copyright © 2024. Todos os direitos reservados.
        	""", APP_NAME));
        return infoArea;
    }

    private JLabel createHyperlinkLabel() {
        String text="Visite o projeto no GitHub";
        JLabel label = new JLabel("<html><u>" + text + "</u></html>");
        label.setForeground(LINK_COLOR);
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(AboutDialog.GITHUB_URL));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AboutDialog.this,
                            "Erro ao abrir o navegador: " + ex.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(LINK_COLOR.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(LINK_COLOR);
            }
        });

        return label;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton creditsButton = new JButton("Créditos");
        creditsButton.addActionListener(e -> showCredits());

        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dispose());

        // Estiliza os botões
        for (JButton button : new JButton[]{creditsButton, closeButton}) {
            button.setFocusPainted(false);
            button.setBackground(new Color(41, 128, 185));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

            // Efeito hover
            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent evt) {
                    button.setBackground(new Color(52, 152, 219));
                }

                public void mouseExited(MouseEvent evt) {
                    button.setBackground(new Color(41, 128, 185));
                }
            });
        }

        buttonPanel.add(creditsButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(closeButton);

        return buttonPanel;
    }

    private void showCredits() {
        JDialog creditsDialog = new JDialog(this, "Créditos", true);
        creditsDialog.setLayout(new BorderLayout(10, 10));

        JTextArea creditsText = new JTextArea("""
        	Equipe de Desenvolvimento:
        	Lucas Mota - Desenvolvedor Principal
        	Amanda Sales
        	Pedro Conceição
       	 
        	Equipe de teste:
       	 
        	Bibliotecas utilizadas:
        	• Java Swing
        	• Java AWT
        	• Java AWT Image
        	• Java AWT Event
        	• Java File I/O
        	• Java FileChooser
        	• Java Net URL
        	""");

        creditsText.setEditable(false);
        creditsText.setBackground(Color.WHITE);
        creditsText.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(creditsText);
        creditsDialog.add(scrollPane, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> creditsDialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        creditsDialog.add(buttonPanel, BorderLayout.SOUTH);

        creditsDialog.setSize(300, 250);
        creditsDialog.setLocationRelativeTo(this);
        creditsDialog.setVisible(true);
    }
}
