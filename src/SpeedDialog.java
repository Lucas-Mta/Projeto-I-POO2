import javax.swing.*;
import java.awt.*;

public class SpeedDialog extends JDialog {

    private AnimatedPanel animatedPanel;
    private JLabel statusLabel; // Adicionar referência ao statusLabel

    public SpeedDialog(JFrame parent, AnimatedPanel animatedPanel) {
        super(parent, "Configurações de Velocidade", true);
        this.animatedPanel = animatedPanel;
        this.statusLabel = ((MainWindow) parent).getStatusLabel(); // Obter statusLabel da MainWindow
        this.setSize(300, 200);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);

        JSlider speedSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 5);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        JButton applyButton = new JButton("Aplicar");
        applyButton.addActionListener(e -> {
            int speed = speedSlider.getValue();
            animatedPanel.setSpeed(speed); // Mudar a velocidade do painel
            statusLabel.setText("Status: Velocidade ajustada para " + speed); // Atualizar o status
            dispose();
        });

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(speedSlider, BorderLayout.CENTER);

        this.setLayout(new BorderLayout());
        this.add(new JLabel("Ajuste a Velocidade:"), BorderLayout.NORTH);
        this.add(speedSlider, BorderLayout.CENTER);
        this.add(applyButton, BorderLayout.SOUTH);
    }
}
