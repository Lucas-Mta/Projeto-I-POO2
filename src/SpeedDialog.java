import javax.swing.*;
import java.awt.*;

public class SpeedDialog extends JDialog {
    public SpeedDialog(JFrame parent) {
        super(parent, "Configurações de Velocidade", true);
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
            // Implementar a mudança de velocidade aqui
            System.out.println("Velocidade selecionada: " + speed);
            dispose();
        });

        this.setLayout(new BorderLayout());
        this.add(new JLabel("Ajuste a Velocidade:"), BorderLayout.NORTH);
        this.add(speedSlider, BorderLayout.CENTER);
        this.add(applyButton, BorderLayout.SOUTH);
    }
}
