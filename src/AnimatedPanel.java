import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;

public class AnimatedPanel extends JPanel implements Runnable {
    private Color backgroundColor;
    private Color shapeColor;
    private int patternType; // 0 = Círculo, 1 = Estrela, 2 = Hexágono
    private int speed; // Velocidade de animação
    private int step; // Passo atual na formação da forma
    private int maxSteps; // Máximo de passos para completar a forma
    private boolean running; // Controle da thread de animação
    private boolean isDrawing; // Se a animação está em execução

    public AnimatedPanel() {
        this.backgroundColor = Color.WHITE;
        this.shapeColor = Color.RED;
        this.patternType = 0;
        this.speed = 5;
        this.step = 0;
        this.maxSteps = 100; // Definição de steps para completar cada padrão
        this.running = true;
        this.isDrawing = true;
        new Thread(this).start();
    }

    // Métodos de configuração para o painel animado
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
        this.step = 0; // Reiniciar o desenho ao trocar de padrão
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

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        setBackground(backgroundColor);

        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(2)); // Ajuste estético para suavizar as linhas

        // Definir a cor da forma
        g2d.setColor(shapeColor);

        switch (patternType) {
            case 0:
                drawCircle(g2d);
                break;
            case 1:
                drawStar(g2d);
                break;
            case 2:
                drawHexagon(g2d);
                break;
        }
    }

    // Método para desenhar o círculo em segmentos
    private void drawCircle(Graphics2D g2d) {
        int diameter = 200;
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        double startAngle = 0;
        double angleExtent = (double) step / maxSteps * 360; // Ângulo atual baseado no step

        g2d.draw(new Arc2D.Double(x, y, diameter, diameter, startAngle, angleExtent, Arc2D.OPEN));
    }

    // Método para desenhar a estrela ponta a ponta
    private void drawStar(Graphics2D g2d) {
        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;
        double outerRadius = 100;
        double innerRadius = 40;

        // Configurar um caminho para desenhar a estrela
        Path2D star = new Path2D.Double();
        int points = 5; // Número de pontos na estrela

        // Desenhar a estrela um ponto de cada vez até completar
        for (int i = 0; i <= step && i <= points * 2; i++) {
            double angle = Math.toRadians(i * 360.0 / (points * 2)); // Ângulo para cada ponto
            double radius = (i % 2 == 0) ? outerRadius : innerRadius; // Alternar entre raio externo e interno
            double x = centerX + radius * Math.cos(angle);
            double y = centerY - radius * Math.sin(angle); // Eixo y invertido no sistema de coordenadas

            if (i == 0) {
                star.moveTo(x, y);
            } else {
                star.lineTo(x, y);
            }
        }

        if (step >= points * 2) {
            star.closePath(); // Fechar o caminho se a estrela estiver completa
            step = 0; // Reiniciar o desenho ao completar a estrela
        }

        g2d.draw(star);
    }

    // Método para desenhar um hexágono construído lado a lado
    private void drawHexagon(Graphics2D g2d) {
        double centerX = getWidth() / 2.0;
        double centerY = getHeight() / 2.0;
        double radius = 80; // Raio do hexágono

        Path2D hexagon = new Path2D.Double();
        int sides = 6; // Número de lados do hexágono

        // Desenhar o hexágono um lado de cada vez até completar
        for (int i = 0; i <= step && i <= sides; i++) {
            double angle = Math.toRadians(60 * i - 30); // Ângulo para cada vértice do hexágono (-30 para ajuste de rotação)
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);

            if (i == 0) {
                hexagon.moveTo(x, y);
            } else {
                hexagon.lineTo(x, y);
            }
        }

        if (step >= sides) {
            hexagon.closePath(); // Fechar o caminho se o hexágono estiver completo
            step = 0; // Reiniciar o desenho ao completar o hexágono
        }

        g2d.draw(hexagon);
    }

    @Override
    public void run() {
        while (running) {
            if (isDrawing) {
                step++;
                if (step > maxSteps) {
                    step = 0; // Reinicia o desenho quando completar
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
