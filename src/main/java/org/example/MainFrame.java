package org.example;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    // Поля для ввода начальных условий
    private JTextField tfX, tfY, tfZ;
    private JTextField tfVx, tfVy, tfVz;
    private JTextField tfT0, tfTk, tfH;
    private JComboBox<String> cbIntegrator;
    private JButton btnCalculate, btnClear;

    // Панели для графиков (пока пустые)
    private JPanel panelChart1, panelChart2, panelChart3, panelChart4;

    // Текстовые области для вывода информации (опционально)
    private JTextArea textAreaInfo;

    public MainFrame() {
        // Настройка главного окна
        setTitle("Моделирование движения космического аппарата");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null); // Центрируем окно на экране

        // Устанавливаем менеджер компоновки
        setLayout(new BorderLayout(10, 10));

        // Создаем все компоненты интерфейса
        createInputPanel();
        createChartsPanel();
        createStatusBar();

        // Устанавливаем минимальный размер окна
        setMinimumSize(new Dimension(1000, 700));
    }

    //Создание панели ввода данных (северная часть окна)
    private void createInputPanel() {
        // Основная панель с рамкой и заголовком
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Параметры моделирования",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
        ));

        // Используем GridBagLayout для гибкого размещения
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10); // Отступы между компонентами

        // Создаем поля ввода с начальными значениями
        tfX = new JTextField("7000000.0", 12);
        tfY = new JTextField("0.0", 12);
        tfZ = new JTextField("0.0", 12);

        tfVx = new JTextField("0.0", 12);
        tfVy = new JTextField("7500.0", 12);
        tfVz = new JTextField("0.0", 12);

        tfT0 = new JTextField("0.0", 12);
        tfTk = new JTextField("10000.0", 12);
        tfH = new JTextField("10.0", 12);

        // Добавляем подсказки (tooltips)
        tfX.setToolTipText("Начальная координата X в метрах");
        tfY.setToolTipText("Начальная координата Y в метрах");
        tfZ.setToolTipText("Начальная координата Z в метрах");
        tfVx.setToolTipText("Начальная скорость Vx в м/с");
        tfVy.setToolTipText("Начальная скорость Vy в м/с");
        tfVz.setToolTipText("Начальная скорость Vz в м/с");
        tfT0.setToolTipText("Начальное время в секундах");
        tfTk.setToolTipText("Конечное время в секундах");
        tfH.setToolTipText("Шаг интегрирования в секундах");

        // Создаем выпадающий список для выбора метода интегрирования
        cbIntegrator = new JComboBox<>(new String[]{
                "Метод Эйлера",
                "Метод Рунге-Кутты 4-го порядка"
        });
        cbIntegrator.setToolTipText("Выберите метод численного интегрирования");

        // Создаем кнопки
        btnCalculate = new JButton("Выполнить расчет");
        btnCalculate.setToolTipText("Начать моделирование с указанными параметрами");

        btnClear = new JButton("Очистить поля");
        btnClear.setToolTipText("Сбросить все поля ввода к значениям по умолчанию");

        // Добавляем обработчики для кнопок
        btnCalculate.addActionListener(new CalculateListener());
        btnClear.addActionListener(e -> resetInputFields());

        // Размещаем компоненты на панели

        // Строка 0: Заголовки для координат
        gbc.gridy = 0;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Начальные координаты (м):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(new JLabel("X"), gbc);
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Y"), gbc);
        gbc.gridx = 3;
        inputPanel.add(new JLabel("Z"), gbc);

        // Строка 1: Поля для координат
        gbc.gridy = 1;
        gbc.gridx = 1;
        inputPanel.add(tfX, gbc);
        gbc.gridx = 2;
        inputPanel.add(tfY, gbc);
        gbc.gridx = 3;
        inputPanel.add(tfZ, gbc);

        // Строка 2: Заголовки для скоростей
        gbc.gridy = 2;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Начальные скорости (м/с):"), gbc);
        gbc.gridx = 1;
        inputPanel.add(new JLabel("Vx"), gbc);
        gbc.gridx = 2;
        inputPanel.add(new JLabel("Vy"), gbc);
        gbc.gridx = 3;
        inputPanel.add(new JLabel("Vz"), gbc);

        // Строка 3: Поля для скоростей
        gbc.gridy = 3;
        gbc.gridx = 1;
        inputPanel.add(tfVx, gbc);
        gbc.gridx = 2;
        inputPanel.add(tfVy, gbc);
        gbc.gridx = 3;
        inputPanel.add(tfVz, gbc);

        // Строка 4: Параметры интегрирования
        gbc.gridy = 4;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Параметры интегрирования:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(new JLabel("t0 (с)"), gbc);
        gbc.gridx = 2;
        inputPanel.add(new JLabel("tk (с)"), gbc);
        gbc.gridx = 3;
        inputPanel.add(new JLabel("Шаг h (с)"), gbc);

        // Строка 5: Поля параметров
        gbc.gridy = 5;
        gbc.gridx = 1;
        inputPanel.add(tfT0, gbc);
        gbc.gridx = 2;
        inputPanel.add(tfTk, gbc);
        gbc.gridx = 3;
        inputPanel.add(tfH, gbc);

        // Строка 6: Выбор метода
        gbc.gridy = 6;
        gbc.gridx = 0;
        inputPanel.add(new JLabel("Метод интегрирования:"), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = 2; // Занимает 2 колонки
        inputPanel.add(cbIntegrator, gbc);

        // Строка 7: Кнопки
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        inputPanel.add(btnCalculate, gbc);
        gbc.gridx = 1;
        inputPanel.add(btnClear, gbc);

        // Добавляем панель ввода в верхнюю часть главного окна
        add(inputPanel, BorderLayout.NORTH);
    }

    //Создание панели с графиками (центральная часть окна)
    private void createChartsPanel() {
        // Основная панель для графиков с рамкой
        JPanel chartsContainer = new JPanel(new BorderLayout());
        chartsContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Результаты моделирования",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
        ));

        // Панель для 4 графиков в сетке 2x2
        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Создаем 4 пустые панели для графиков с заголовками
        panelChart1 = createEmptyChartPanel("Эволюция координат X, Y, Z от времени");
        panelChart2 = createEmptyChartPanel("Эволюция скоростей Vx, Vy, Vz от времени");
        panelChart3 = createEmptyChartPanel("Траектория движения в плоскости XY");
        panelChart4 = createEmptyChartPanel("Траектория движения в плоскости XZ");

        // Добавляем панели в сетку
        gridPanel.add(panelChart1);
        gridPanel.add(panelChart2);
        gridPanel.add(panelChart3);
        gridPanel.add(panelChart4);

        chartsContainer.add(gridPanel, BorderLayout.CENTER);

        // Добавляем небольшую информационную панель справа (опционально)
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(200, 0));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Информация"));

        textAreaInfo = new JTextArea(10, 20);
        textAreaInfo.setEditable(false);
        textAreaInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textAreaInfo.setText("Информация о расчете:\n" +
                "--------------------\n" +
                "Метод: не выбран\n" +
                "Статус: ожидание\n" +
                "Шагов: 0\n" +
                "Время: 0 с");

        JScrollPane scrollPane = new JScrollPane(textAreaInfo);
        infoPanel.add(scrollPane, BorderLayout.CENTER);

        // Добавляем информационную панель справа (можно закомментировать, если не нужна)
        chartsContainer.add(infoPanel, BorderLayout.EAST);

        add(chartsContainer, BorderLayout.CENTER);
    }

    //Создание пустой панели для графика с заголовком
    private JPanel createEmptyChartPanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                title,
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.PLAIN, 12)
        ));
        panel.setBackground(Color.WHITE);

        // Добавляем временное содержимое, чтобы показать, что здесь будет график
        JLabel placeholder = new JLabel("Здесь будет отображаться график", SwingConstants.CENTER);
        placeholder.setForeground(Color.GRAY);
        placeholder.setFont(new Font("Arial", Font.ITALIC, 14));
        panel.add(placeholder, BorderLayout.CENTER);

        // Добавляем изображение осей координат (простая отрисовка)
        JPanel axesPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Рисуем оси координат
                int width = getWidth();
                int height = getHeight();

                g2.setColor(Color.LIGHT_GRAY);
                // Горизонтальная линия (ось X)
                g2.drawLine(20, height / 2, width - 20, height / 2);
                // Вертикальная линия (ось Y)
                g2.drawLine(width / 2, 20, width / 2, height - 20);

                // Подписи осей
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Arial", Font.PLAIN, 10));
                g2.drawString("X", width - 15, height / 2 - 5);
                g2.drawString("Y", width / 2 + 5, 25);
            }
        };
        axesPanel.setOpaque(false);
        panel.add(axesPanel, BorderLayout.CENTER);

        return panel;
    }

    //Создание строки состояния (южная часть окна)
    private void createStatusBar() {
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(BorderFactory.createEtchedBorder());

        JLabel statusLabel = new JLabel("Готов к работе. Введите параметры и нажмите 'Выполнить расчет'");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));

        JLabel versionLabel = new JLabel("Версия 1.0");
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        versionLabel.setForeground(Color.GRAY);

        statusBar.add(statusLabel);
        statusBar.add(Box.createHorizontalGlue());
        statusBar.add(versionLabel);

        add(statusBar, BorderLayout.SOUTH);
    }

    //Сброс полей ввода к значениям по умолчанию
    private void resetInputFields() {
        tfX.setText("7000000.0");
        tfY.setText("0.0");
        tfZ.setText("0.0");
        tfVx.setText("0.0");
        tfVy.setText("7500.0");
        tfVz.setText("0.0");
        tfT0.setText("0.0");
        tfTk.setText("10000.0");
        tfH.setText("10.0");
        cbIntegrator.setSelectedIndex(1); // Выбираем Рунге-Кутту по умолчанию
    }


    //Внутренний класс-слушатель для кнопки расчета
    //Пока просто выводит сообщение и обновляет информацию
    private class CalculateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Пробуем прочитать значения (для проверки, что это числа)
                double x = Double.parseDouble(tfX.getText());
                double y = Double.parseDouble(tfY.getText());
                double z = Double.parseDouble(tfZ.getText());
                double vx = Double.parseDouble(tfVx.getText());
                double vy = Double.parseDouble(tfVy.getText());
                double vz = Double.parseDouble(tfVz.getText());
                double t0 = Double.parseDouble(tfT0.getText());
                double tk = Double.parseDouble(tfTk.getText());
                double h = Double.parseDouble(tfH.getText());

                // Получаем выбранный метод
                String method = (String) cbIntegrator.getSelectedItem();

                // Вычисляем количество шагов
                int steps = (int) Math.ceil((tk - t0) / h);

                // Обновляем информационное поле
                String info = String.format(
                        "Информация о расчете:\n" +
                                "--------------------\n" +
                                "Метод: %s\n" +
                                "Статус: выполнен (имитация)\n" +
                                "Начальное положение: (%.1f, %.1f, %.1f) м\n" +
                                "Начальная скорость: (%.1f, %.1f, %.1f) м/с\n" +
                                "Шагов: %d\n" +
                                "Время моделирования: %.1f с\n" +
                                "Шаг: %.2f с\n\n" +
                                "ВНИМАНИЕ: Графики временно не реализованы!",
                        method, x, y, z, vx, vy, vz, steps, tk - t0, h
                );

                if (textAreaInfo != null) {
                    textAreaInfo.setText(info);
                }

                // Показываем сообщение пользователю
                JOptionPane.showMessageDialog(MainFrame.this,
                        "Расчет выполнен успешно (в демо-режиме).\n" +
                                "Для реального расчета необходимо подключить JFreeChart.",
                        "Информация",
                        JOptionPane.INFORMATION_MESSAGE);

                // Здесь можно изменить текст на панелях графиков
                updateChartPlaceholders();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this,
                        "Ошибка ввода: все поля должны содержать числа.",
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        /**
         * Обновляет текст на панелях-заглушках графиков
         */
        private void updateChartPlaceholders() {
            // Получаем все компоненты на панелях графиков и обновляем текст
            updatePlaceholderText(panelChart1, "График координат (данные получены)");
            updatePlaceholderText(panelChart2, "График скоростей (данные получены)");
            updatePlaceholderText(panelChart3, "Траектория XY (данные получены)");
            updatePlaceholderText(panelChart4, "Траектория XZ (данные получены)");
        }

        private void updatePlaceholderText(JPanel panel, String newText) {
            // Ищем компонент JLabel внутри панели и обновляем его текст
            for (Component comp : panel.getComponents()) {
                if (comp instanceof JLabel) {
                    ((JLabel) comp).setText(newText);
                }
            }
        }
    }
}