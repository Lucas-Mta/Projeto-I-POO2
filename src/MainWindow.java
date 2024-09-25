import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

public class MainWindow extends JFrame {

   @Serial
   private static final long serialVersionUID = 1L;
   private JPanel statusPanel;
   private  JLabel statusLabel;
   private JMenuBar menuBar;
   private String title;

   public MainWindow(String title) throws HeadlessException {
       super(title);
       this.title = title;
       configureWindow();
       initializeComponents();
       addComponents();
   }

   private void configureWindow() {
       // Define o tamanho da janela em proporção da tela
       this.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.5),
               (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.45));

       // Centraliza a janela
       this.setLocationRelativeTo(null);

       // Fecha a aplicação quando a janela for fechada
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       // Define layout da janela
       this.setLayout(new BorderLayout(5, 5));
   }

   private void initializeComponents() {
       // Inicializa a barra de status
       this.statusPanel = new JPanel();
       this.statusLabel = new JLabel(this.title);
       this.statusPanel.add(statusLabel);
       this.statusPanel.setBackground(Color.LIGHT_GRAY);
       this.statusPanel.setBorder(BorderFactory.createEtchedBorder());

       // Inicializa a barra de menus
       menuBar = new JMenuBar();

       // Menu Arquivo
       JMenu fileMenu = new JMenu("Arquivo");
       JMenuItem openItem = new JMenuItem("Abrir Arquivo");
       JMenuItem closeItem = new JMenuItem("Fechar Arquivo");
       JMenuItem exitItem = new JMenuItem("Sair");

       fileMenu.add(openItem);
       fileMenu.add(closeItem);
       fileMenu.addSeparator();
       fileMenu.add(exitItem);

       // Ouvintes para itens do menu
       exitItem.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               System.exit(0);  // Fecha a aplicação
           }
       });

       // Menu Configurações
       JMenu configMenu = new JMenu("Configuração");
       JMenuItem defaultItem = new JMenuItem("Padrões");
       JMenuItem colorItem = new JMenuItem("Cores");
       JMenuItem speedItem = new JMenuItem("Velocidade");

       configMenu.add(defaultItem);
       configMenu.add(colorItem);
       configMenu.add(speedItem);

       // Menu Ajuda
       JMenu helpMenu = new JMenu("Ajuda");
       JMenuItem helpItem = new JMenuItem("Ajuda");
       JMenuItem aboutItem = new JMenuItem("Sobre");

       helpMenu.add(helpItem);
       helpMenu.add(aboutItem);

       // Adicionar menus à barra de menus
       menuBar.add(fileMenu);
       menuBar.add(configMenu);
       menuBar.add(helpMenu);
   }

   private void addComponents() {
       // Adiciona barra de status na parte inferior da janela
       this.add(statusPanel, BorderLayout.SOUTH);
        // Adicionar a barra de menus na janela
       this.setJMenuBar(menuBar);
   }

    public static void main(String[] args) {
        // Executar a aplicação
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainWindow mainWindow = new MainWindow("Minha Aplicação Java");
                mainWindow.setVisible(true);
            }
        });
    }


}
