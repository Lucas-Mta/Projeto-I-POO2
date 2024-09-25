import javax.swing.*;
import java.awt.*;

public class ColorDialog extends JDialog{

    public ColorDialog(JFrame parent) {
        super(parent, "Configurações de Cor", true);
        this.setSize(300, 200);
        this.setLocationRelativeTo(parent);
        this.setResizable(false);

        JButton colorButton = new JButton("Escolher Cor");
        colorButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this,
                    "Escolha uma cor", Color.WHITE);
            if (color != null) {
                // Implementar a mudança de cor do fundo
                System.out.println("Cor selecionada: " + color);
            }
        });

        this.setLayout(new BorderLayout());
        this.add(new JLabel("Escolha a cor de fundo"), BorderLayout.NORTH);
        this.add(colorButton, BorderLayout.CENTER);
    }
}
