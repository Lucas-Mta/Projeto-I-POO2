import javax.swing.*;
import java.awt.*;

public class ColorDialog extends JDialog {

    private AnimatedPanel animatedPanel;
    private JLabel statusLabel;
    private Color selectedBackgroundColor;
    private Color selectedShapeColor;

    public ColorDialog(JFrame parent, AnimatedPanel animatedPanel, JLabel statusLabel) {
        super(parent, "Configurações de Cor", true);
        this.animatedPanel = animatedPanel;
        this.statusLabel = statusLabel;
        this.selectedBackgroundColor = animatedPanel.getBackgroundColor();
        this.selectedShapeColor = animatedPanel.getShapeColor();

        this.setSize(300, 250);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);

        // Botão para escolher a cor de fundo
        JButton backgroundColorButton = new JButton("Escolher Cor de Fundo");
        backgroundColorButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Escolha a cor de fundo", selectedBackgroundColor);
            if (color != null) {
                selectedBackgroundColor = color;
                // Atualiza o statusLabel para exibir a cor de fundo selecionada
                statusLabel.setText("Status: Cor de fundo " + color + " selecionada (não aplicada)");
            }
        });

        // Botão para escolher a cor dos desenhos
        JButton shapeColorButton = new JButton("Escolher Cor do Desenho");
        shapeColorButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Escolha a cor do desenho", selectedShapeColor);
            if (color != null) {
                selectedShapeColor = color;
                // Atualiza o statusLabel para exibir a cor do desenho selecionada
                statusLabel.setText("Status: Cor do desenho " + color + " selecionada (não aplicada)");
            }
        });

        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.setLayout(new GridLayout(2, 1, 10, 10));
        centerPanel.add(backgroundColorButton);
        centerPanel.add(shapeColorButton);

        this.setLayout(new BorderLayout());
        this.add(new JLabel("Escolha as cores:"), BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);

        // Botão aplicar para aplicar as cores selecionadas
        JButton applyButton = new JButton("Aplicar");
        applyButton.addActionListener(e -> {
            animatedPanel.setBackgroundColor(selectedBackgroundColor);
            animatedPanel.setShapeColor(selectedShapeColor);
            statusLabel.setText("Status: Cor de fundo " + selectedBackgroundColor + "  |  Cor do desenho " + selectedShapeColor + " aplicadas");
            dispose();
        });

        this.add(applyButton, BorderLayout.SOUTH);
    }
}
