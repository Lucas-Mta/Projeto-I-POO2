import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;

public class AnimatedPanel extends JPanel implements Runnable {
    private Color backgroundColor;
    private Color shapeColor;
    private int patternType; // 0 = Círculo, 1 = Estrela, 2 = Hexágono
    private int speed;
    private int[] steps;
    private int maxSteps;
    private boolean running;
    private boolean isDrawing;

    // Coordenadas para os três desenhos
    private Point[] positions;

    public AnimatedPanel() {
        this.backgroundColor = Color.WHITE;
        this.shapeColor = Color.RED;
        this.patternType = 0;
        this.speed = 5;
        this.steps = new int[]{0, 0, 0}; // Independente para cada forma
        this.maxSteps = 100;
        this.running = true;
        this.isDrawing = true;

        // Definir as três posições para os desenhos (são atualizadas dinamicamente)
        this.positions = new Point[3];
        calculatePositions();

        new Thread(this).start();
    }

    // Configuração para o painel
    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    public void setShapeColor(Color color) {
        this.shapeColor = color;
        repaint();
    }

    public void setPatternType(int patternType) {
        this.patternType = patternType;
        this.steps = new int[]{0, 0, 0}; // Reinicia o desenho ao trocar de padrão
        repaint();
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void stopDrawing() {
        isDrawing = false;
    }

    public void resumeDrawing() {
        isDrawing = true;
        repaint();
    }

    // Recalcula posições com base no tamanho do painel para centralizar
    private void calculatePositions() {
        int width = getWidth();
        int height = getHeight();
        int spacing = width / 4; // Espaço horizontal entre as formas

        // Calcula posições centralizadas pra as formas
        this.positions[0] = new Point(spacing, height / 2);
        this.positions[1] = new Point(width / 2, height / 2);
        this.positions[2] = new Point(width - spacing, height / 2);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        setBackground(backgroundColor);

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(3));

        // Define a cor da forma
        g2d.setColor(shapeColor);

        // Recalcula as posições antes de desenhar para garantir centralização
        calculatePositions();

        // Desenhar três formas nas posições definidas
        for (int i = 0; i < positions.length; i++) {
            switch (patternType) {
                case 0:
                    drawCircle(g2d, positions[i], i);
                    break;
                case 1:
                    drawStar(g2d, positions[i], i);
                    break;
                case 2:
                    drawHexagon(g2d, positions[i], i);
                    break;
            }
        }
    }

    // Metodo para desenhar o círculo em segmentos, recebendo posição como parâmetro
    private void drawCircle(Graphics2D g2d, Point position, int index) {
        int diameter = 100;
        int x = position.x - diameter / 2;
        int y = position.y - diameter / 2;

        double startAngle = 0;
        double angleExtent = (double) steps[index] / (maxSteps - 1) * 360; // Ajustado para garantir 360º completos

        g2d.draw(new Arc2D.Double(x, y, diameter, diameter, startAngle, angleExtent, Arc2D.OPEN));
    }

    // Metodo para desenhar a estrela ponta a ponta - recebe uma posição como parâmetro
    private void drawStar(Graphics2D g2d, Point position, int index) {
        double centerX = position.x;
        double centerY = position.y;
        double outerRadius = 50;
        double innerRadius = 20;

        // Configurar um caminho para desenhar a estrela
        Path2D star = new Path2D.Double();
        int points = 5; // Número de pontass da estrela

        // Desenhar a estrela um ponto de cada vez até completar
        for (int i = 0; i <= steps[index] && i <= points * 2; i++) {
            double angle = Math.toRadians(i * 360.0 / (points * 2)); // Ângulo para cada ponto
            double radius = (i % 2 == 0) ? outerRadius : innerRadius;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY - radius * Math.sin(angle);

            if (i == 0) {
                star.moveTo(x, y);
            } else {
                star.lineTo(x, y);
            }
        }

        if (steps[index] >= points * 2) {
            star.closePath(); // Fechar o caminho se a estrela estiver completa
        }

        g2d.draw(star);
    }

    // Metodo para desenhar um hexágono construído lado a lado, recebendo posição como parâmetro
    private void drawHexagon(Graphics2D g2d, Point position, int index) {
        double centerX = position.x;
        double centerY = position.y;
        double radius = 40;

        Path2D hexagon = new Path2D.Double();
        int sides = 6; // Número de lados do hexágono

        // Desenhar o hexágono um lado de cada vez
        for (int i = 0; i <= steps[index] && i <= sides; i++) {
            double angle = Math.toRadians(60 * i - 30);
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            if (i == 0) {
                hexagon.moveTo(x, y);
            } else {
                hexagon.lineTo(x, y);
            }
        }

        if (steps[index] >= sides) {
            hexagon.closePath(); // Fechar o caminho se o hexágono estiver completo
        }

        g2d.draw(hexagon);
    }

    @Override
    public void run() {
        while (running) {
            if (isDrawing) {
                boolean allComplete = true;
                for (int i = 0; i < steps.length; i++) {
                    steps[i]++;
                    int maxForPattern = (patternType == 1) ? 11 : (patternType == 2) ? 7 : maxSteps;

                    if (steps[i] < maxForPattern) {
                        allComplete = false;
                    }
                }

                if (allComplete) {
                    // Reinicia o processo para todas as formas
                    steps = new int[]{0, 0, 0};
                }

                repaint();
            }
            try {
                Thread.sleep(1000 / speed); // Ajuste de velocidade com base no slider de velocidade
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopAnimation() {
        running = false;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getShapeColor() {
        return shapeColor;
    }
}
