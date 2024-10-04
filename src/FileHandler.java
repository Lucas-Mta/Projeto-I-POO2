import javax.swing.*;
import java.io.*;

public class FileHandler {

    private JTextArea textArea;
    private File selectedFile; // O arquivo aberto agora

    public FileHandler(JTextArea textArea) {
        this.textArea = textArea;
    }

    // Abrir e ler o conteúdo do arquivo
    public void openFile(JFrame parent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Procurar arquivo");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Filtro para mostrar apenas arquivos .txt
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Texto", "txt"));

        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Arquivo selecionado: " + selectedFile.getAbsolutePath());
            readFileContent(selectedFile);
        } else {
            System.out.println("Nenhum arquivo selecionado.");
        }
    }

    // Metodo para ler o conteúdo do arquivo e exibir no JTextArea
    private void readFileContent(File selectedFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
            textArea.setText(""); // Limpa a área de texto antes de exibir
            String line;

            // Mostra todas as linhas
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    // Metodo para fechar o arquivo
    public void closeFile() {
        if (selectedFile != null) {
            textArea.setText(""); // Limpa
            selectedFile = null; // Reseta o arquvio
        }
    }
}
