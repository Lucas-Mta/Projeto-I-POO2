import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HelpDialog extends JDialog {
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private CardLayout cardLayout;
    private int currentCard = 0;
    private List<String> helpTexts;
    private JButton prevButton;
    private JButton nextButton;
    private JLabel pageIndicator;

    public HelpDialog(JFrame parent) {
        super(parent, "Ajuda", true);

        // Configurar o ícone de interrogação no título
        JLabel titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setIcon(UIManager.getIcon("OptionPane.questionIcon"));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(titleLabel);

        initializeComponents();
        setupLayout();
        setupActions();

        // Adiciona o painel do título
        add(titlePanel, BorderLayout.NORTH);

        // Configuração final da janela
        setSize(600, 500);
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    private void initializeComponents() {
        // Inicializa os textos de ajuda
        helpTexts = new ArrayList<>();
        helpTexts.add("""
            <html>
                <h1>Bem-vindo ao Sistema de Manipulação de Arquivos</h1>
                <p>Esta aplicação permite que você manipule arquivos de texto e customize animações de fundo. Use o menu 'Arquivo' para abrir e gerenciar seus arquivos de texto.</p>
            </html>""");

        helpTexts.add("""
            <html>
                <h1>Manipulação de Arquivos</h1>
                <h2>1. Abrir Arquivo:</h2>
                <img src="%s" alt="Image 1" width="400" height="300">
                <p>Para abrir um arquivo, vá até o menu 'Arquivo' no topo da janela e selecione a opção 'Abrir Arquivo'. Será aberta uma janela de seleção onde você poderá navegar pelo diretório do seu sistema e escolher o arquivo desejado. Após selecionar o arquivo, o conteúdo será carregado e exibido na área central de texto.</p>
                <h2>2. Fechar Arquivo:</h2>
                <img src="%s" alt="Image 2" width="400" height="300">
                <p>Se desejar fechar o arquivo atual, selecione 'Fechar Arquivo' no menu 'Arquivo'. Isso limpará o conteúdo exibido na área de texto, deixando-a em branco. Essa ação não altera ou deleta o arquivo original.</p>
                <p>O conteúdo do arquivo selecionado será exibido na área central da janela, logo abaixo da barra de menus. A área de texto suporta rolagem vertical para facilitar a leitura de arquivos grandes, além de ajuste automático de quebras de linha, garantindo que o texto se adapte à largura da janela.</p>
            </html>""".formatted(getClass().getResource("/images/AbrirArquivo.jpg").toExternalForm(),
                                getClass().getResource("/images/FecharArquivo.jpg").toExternalForm()));

        helpTexts.add("""
            <html>
                <h1>Configurações de Animação</h1>
                <img src="%s" alt="Image 3" width="400" height="300">
                <p>No menu 'Configurações', você pode ajustar diferentes aspectos das animações da interface. Abaixo estão as opções disponíveis:</p>
                <h2>1. Padrões:</h2>
                <img src="%s" alt="Image 4" width="400" height="300">
                <p>Esta opção permite que você selecione diferentes padrões para o comportamento das animações de fundo. Os padrões controlam como as animações são desenhadas e exibidas, alterando a dinâmica visual da aplicação. Para ajustar, vá ao menu 'Configurações' > 'Padrões' e selecione o padrão desejado.</p>
                <h2>2. Cores:</h2>
                <img src="%s" alt="Image 5" width="400" height="300">
                <p>Se você deseja alterar as cores de fundo das animações, utilize a opção 'Cores' no menu 'Configurações'. Isso permitirá escolher novas combinações de cores para personalizar a aparência visual das animações exibidas na janela principal.</p>
                <h2>3. Velocidade:</h2>
                <img src="%s" alt="Image 6" width="400" height="300">
                <p>Para controlar a velocidade com que as animações de fundo são executadas, utilize a opção 'Velocidade' no menu 'Configurações'. Aqui, você pode ajustar a rapidez com que os padrões de animação se movem, criando uma experiência mais suave ou dinâmica, conforme sua preferência.</p>
                <p>Essas configurações permitem personalizar a interface de acordo com suas preferências visuais, tornando a experiência mais agradável e adaptada ao seu gosto.</p>
            </html>""".formatted(getClass().getResource("/images/Configuracoes.jpg").toExternalForm(),
                                getClass().getResource("/images/Padroes.jpg").toExternalForm(),
                                getClass().getResource("/images/Cores.jpg").toExternalForm(),
                                getClass().getResource("/images/Velocidade.jpg").toExternalForm()));

        // Inicializa os painéis
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Cria os cards com conteúdo
        for (int i = 0; i < helpTexts.size(); i++) {
            mainPanel.add(createHelpCard(i), "card" + i);
        }

        // Inicializa o painel de botões
        buttonPanel = new JPanel(new FlowLayout());
        prevButton = new JButton("← Anterior");
        nextButton = new JButton("Próximo →");
        pageIndicator = new JLabel("1/" + helpTexts.size());

        // Configura estado inicial dos botões
        prevButton.setEnabled(false);
        updateNavigationButtons();
    }

    private JPanel createHelpCard(int index) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Cria o painel superior com ícone ou título
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Adiciona um ícone de título estilizado
        JLabel titleLabel = createStylizedTitleLabel(index);
        topPanel.add(titleLabel);

        // Cria área de texto com scroll
        JEditorPane editorPane = new JEditorPane("text/html", helpTexts.get(index));
        editorPane.setEditable(false);
        editorPane.setFont(new Font("SansSerif", Font.PLAIN, 14));
        editorPane.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        card.add(topPanel, BorderLayout.NORTH);
        card.add(scrollPane, BorderLayout.CENTER);

        return card;
    }

    private JLabel createStylizedTitleLabel(int index) {
        String title = "";
        Color iconColor = new Color(51, 153, 255); // Azul padrão
        Icon icon = null;

        switch (index) {
            case 0:
                title = "  Início  ";
                iconColor = new Color(51, 153, 255); // Azul
                icon = UIManager.getIcon("FileView.homeFolder"); // Ícone de home
                if (icon == null) { // Fallback se o ícone não estiver disponível
                    icon = UIManager.getIcon("Tree.openIcon");
                }
                break;
            case 1:
                title = "  Arquivos  ";
                iconColor = new Color(76, 175, 80); // Verde
                icon = UIManager.getIcon("FileView.fileIcon"); // Ícone de arquivo
                if (icon == null) {
                    icon = UIManager.getIcon("Tree.leafIcon");
                }
                break;
            case 2:
                title = "  Configurações  ";
                iconColor = new Color(255, 152, 0); // Laranja
                // Como não há um ícone de engrenagem nativo, usamos um ícone de propriedades
                icon = UIManager.getIcon("OptionPane.warningIcon");
                if (icon == null) {
                    icon = UIManager.getIcon("FileView.computerIcon");
                }
                break;
        }

        JLabel label = new JLabel(title);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));
        label.setForeground(iconColor);
        label.setIcon(icon);
        label.setIconTextGap(10); // Espaço entre o ícone e o texto

        // Adiciona uma borda composta: linha colorida externa e padding interno
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(iconColor, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        return label;
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Adiciona os botões ao painel
        buttonPanel.add(prevButton);
        buttonPanel.add(pageIndicator);
        buttonPanel.add(nextButton);

        // Adiciona um botão de fechar
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dispose());

        // Adiciona todos os componentes ao diálogo
        add(mainPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupActions() {
        prevButton.addActionListener(e -> {
            if (currentCard > 0) {
                currentCard--;
                cardLayout.previous(mainPanel);
                updateNavigationButtons();
            }
        });

        nextButton.addActionListener(e -> {
            if (currentCard < helpTexts.size() - 1) {
                currentCard++;
                cardLayout.next(mainPanel);
                updateNavigationButtons();
            }
        });
    }

    private void updateNavigationButtons() {
        prevButton.setEnabled(currentCard > 0);
        nextButton.setEnabled(currentCard < helpTexts.size() - 1);
        pageIndicator.setText((currentCard + 1) + "/" + helpTexts.size());
    }
}