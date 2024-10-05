import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.awt.*;

public class FileHandler {

    private JTextArea textArea;
    private MainWindow mainWindow; // Referência à janela principal
    private JPanel centerPanel;

    public FileHandler(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        this.textArea = mainWindow.getTextArea();
    }

    public void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

        int result = fileChooser.showOpenDialog(mainWindow);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                textArea.setText(""); // Limpa a área de texto
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }
                showTextArea(); // Exibir o painel de texto ao abrir o arquivo
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeFile() {
        textArea.setText(""); // Limpar a área de texto
        hideTextArea(); // Esconder a área de texto e mostrar a animação de fundo
    }

    // Mostrar a área de texto no centro da janela e parar a animação
    public void showTextArea() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new GridBagLayout());
            JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            centerPanel.add(scrollPane, new GridBagConstraints());
        }

        mainWindow.removeAnimatedPanel(); // Remove a animação de fundo
        mainWindow.add(centerPanel, BorderLayout.CENTER);
        mainWindow.revalidate();
        mainWindow.repaint();
    }

    // Esconder a área de texto e voltar a animação de fundo
    public void hideTextArea() {
        if (centerPanel != null) {
            mainWindow.remove(centerPanel); // Remove o painel de texto
        }
        mainWindow.addAnimatedPanel(); // Adiciona de volta a animação de fundo
        mainWindow.revalidate();
        mainWindow.repaint();
    }
}
