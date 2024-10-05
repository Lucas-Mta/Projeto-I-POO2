import javax.swing.*;
import java.awt.*;

public class ColorDialog extends JDialog{

    private AnimatedPanel animatedPanel;

    public ColorDialog(JFrame parent, AnimatedPanel animatedPanel) {
        super(parent, "Configurações de Cor", true);
        this.animatedPanel = animatedPanel;
        this.setSize(300, 200);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);

        JButton colorButton = new JButton("Escolher Cor");
        colorButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Escolha uma cor", Color.WHITE);
            if (color != null) {
                animatedPanel.setBackgroundColor(color); // Mudar a cor de fundo do painel
                dispose();
            }
        });

        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.add(colorButton);

        this.setLayout(new BorderLayout());
        this.add(new JLabel("Escolha a cor de fundo"), BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
    }


}

