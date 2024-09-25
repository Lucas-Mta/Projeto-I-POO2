import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {

    public AboutDialog(JFrame parent) {
        // Define o título do diálogo, o pai da janela e a modalidade
        // (bloqueia a janela pai enquanto o diálogo está aberto):
        super(parent, "Sobre", true);
        setSize(300, 200);
        setResizable(false);
        setLocationRelativeTo(parent);

        // Cria uma área de texto contendo informações de sobre
        JTextArea aboutText = getjTextArea();

        // Adiciona a permisão pra rolagem vertical
        JScrollPane scrollPane = new JScrollPane(aboutText);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JButton closeButton = new JButton("Fechar");
        // Adiciona um listener ao botão para fechar o diálogo quando clicado
        closeButton.addActionListener(e -> dispose());

        add(scrollPane, BorderLayout.CENTER);
        add(closeButton, BorderLayout.SOUTH);
    }

    private static JTextArea getjTextArea() {
        JTextArea aboutText = new JTextArea(
                """
                Versão: 1.0
                Autores: Lucas Mota
                Descrição: Aplicação de manipulação de arquivos com interface gráfica.
                """
        );
        aboutText.setWrapStyleWord(true);       // Quebra de linha no limite das palavras
        aboutText.setLineWrap(true);            // Ativa a quebra de linha automática
        aboutText.setEditable(false);           // O texto não pode ser editado
        aboutText.setMargin(new Insets(10, 10, 10, 10));  // Margens internas
        return aboutText;
    }
}
