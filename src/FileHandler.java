import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.awt.*;

public class FileHandler {

    private JTextArea textArea;
    private MainWindow mainWindow;
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
                textArea.setText("");
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }
                showTextArea();
                mainWindow.getStatusLabel().setText("Status: Arquivo " + file + " aberto"); // Atualizar status
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeFile() {
        textArea.setText("");
        hideTextArea();
        mainWindow.getStatusLabel().setText("Status: Arquivo fechado. Desenhando padrão..."); // Atualizar status para o padrão
    }

    public void showTextArea() {
        if (centerPanel == null) {
            centerPanel = new JPanel(new GridBagLayout());
            JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            centerPanel.add(scrollPane, new GridBagConstraints());
        }

        mainWindow.removeAnimatedPanel();
        mainWindow.add(centerPanel, BorderLayout.CENTER);
        mainWindow.revalidate();
        mainWindow.repaint();
    }

    public void hideTextArea() {
        if (centerPanel != null) {
            mainWindow.remove(centerPanel);
        }
        mainWindow.addAnimatedPanel();
        mainWindow.revalidate();
        mainWindow.repaint();
    }
}
