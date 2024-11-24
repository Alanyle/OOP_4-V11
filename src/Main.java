import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private String text = "Двигающийся текст";
    private int x = 0; // начальная координата X текста
    private int y = 100; // начальная координата Y текста
    private int dx = 2; // шаг перемещения по X
    private Color textColor = Color.BLACK; // начальный цвет текста
    private JComboBox<String> colorSelector;

    public Main() {
        // Установка заголовка и размера окна
        setTitle("Двигающийся текст");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Создание панели для текста
        TextPanel textPanel = new TextPanel();
        add(textPanel, BorderLayout.CENTER);

        // Создание выпадающего списка для выбора цвета
        String[] colors = {"Черный", "Красный", "Синий", "Зеленый"};
        colorSelector = new JComboBox<>(colors);
        colorSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedColor = (String) colorSelector.getSelectedItem();
                switch (selectedColor) {
                    case "Красный":
                        textPanel.setTextColor(Color.RED);
                        break;
                    case "Синий":
                        textPanel.setTextColor(Color.BLUE);
                        break;
                    case "Зеленый":
                        textPanel.setTextColor(Color.GREEN);
                        break;
                    default:
                        textPanel.setTextColor(Color.BLACK);
                        break;
                }
            }
        });

        // Добавление выпадающего списка на панель
        JPanel panel = new JPanel();
        panel.add(colorSelector);
        add(panel, BorderLayout.SOUTH);
    }

    private class TextPanel extends JPanel {
        private Color textColor = Color.BLACK;

        public TextPanel() {
            Timer timer = new Timer(30, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    x += dx; // Перемещаем текст

                    // Отражение от границ панели
                    if (x < 0 || x + getFontMetrics(getFont()).stringWidth(text) > getWidth()) {
                        dx = -dx; // меняем направление
                    }

                    repaint(); // Перерисовываем только эту панель
                }
            });
            timer.start();
        }

        public void setTextColor(Color color) {
            this.textColor = color;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(textColor);
            g.drawString(text, x, y);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}
