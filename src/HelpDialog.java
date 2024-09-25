import javax.swing.*;
import java.awt.*;

public class HelpDialog extends JDialog {

    public HelpDialog(JFrame parent) {
        // Define o título do diálogo, o pai da janela e a modalidade
        // (bloqueia a janela pai enquanto o diálogo está aberto):
        super(parent, "Ajuda", true);
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(parent);

        // Cria uma área de texto contendo informações de ajuda
        JTextArea helpText = getjTextArea();

        // Adiciona a permisão pra rolagem vertical
        JScrollPane scrollPane = new JScrollPane(helpText);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton closeButton = new JButton("Fechar");
        // Adiciona um listener ao botão para fechar o diálogo quando clicado
        closeButton.addActionListener(e -> dispose());

        add(scrollPane, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);
    }

    private static JTextArea getjTextArea() {
        JTextArea helpText = new JTextArea(
                """
                Essa aplicação em Java permite a manipulação de arquivos de texto.
                Além disso, ela possui configurações personalizadas para animações no fundo.
                """
        );
        helpText.setWrapStyleWord(true);    // Quebra de linha no limite das palavras
        helpText.setLineWrap(true);         // Ativa a quebra de linha automática
        helpText.setEditable(false);        // O texto não pode ser editado
        helpText.setMargin(new Insets(10, 10, 10, 10));  // Margens internas
        return helpText;
    }
}
