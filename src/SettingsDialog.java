import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {

    private AnimatedPanel animatedPanel;
    private JLabel statusLabel; // Adicionar referência ao statusLabel

    public SettingsDialog(JFrame parent, AnimatedPanel animatedPanel) {
        super(parent, "Configurações de Padrões", true);
        this.animatedPanel = animatedPanel;
        this.statusLabel = ((MainWindow) parent).getStatusLabel(); // Obter statusLabel da MainWindow
        this.setSize(300, 200);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);

        String[] patterns = {"Padrão 1", "Padrão 2", "Padrão 3"};
        JComboBox<String> patternComboBox = new JComboBox<>(patterns);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(patternComboBox);

        JButton applyButton = new JButton("Aplicar");
        applyButton.addActionListener(e -> {
            int selectedPattern = patternComboBox.getSelectedIndex();
            animatedPanel.setPatternType(selectedPattern); // Mudar o padrão no painel
            statusLabel.setText("Status: " + patterns[selectedPattern] + " selecionado"); // Atualizar o status
            dispose();
        });

        this.setLayout(new BorderLayout());
        this.add(new JLabel("Selecione um padrão: "), BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(applyButton, BorderLayout.SOUTH);
    }
}
