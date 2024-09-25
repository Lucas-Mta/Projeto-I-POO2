import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {

    public SettingsDialog(JFrame parent) {
        super(parent, "Configurações de Padrões", true);
        this.setSize(300, 200);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);

        // Exemplo de escolha de padrão
        String[] patterns = {"Padrão 1", "Padrão 2", "Padrão 3"};
        JComboBox<String> patternCombox = new JComboBox<>(patterns);

        JButton applyButton = new JButton("Aplicar");
        applyButton.addActionListener(e -> {
            String selectedPattern = (String) patternCombox.getSelectedItem();
            // Implementar
            System.out.println("Padrão selecionado: " + selectedPattern);
            dispose();
        });

        this.setLayout(new BorderLayout());
        this.add(new JLabel("Selecione um padrão: "), BorderLayout.NORTH);
        this.add(patternCombox, BorderLayout.CENTER);
        this.add(applyButton, BorderLayout.SOUTH);
    }
}
