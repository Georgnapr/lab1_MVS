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

    // Панели для графиков
    private final JPanel[] chartPanels = new JPanel[5];

    // Текстовые области для вывода информации
    private JTextArea textAreaInfo;

    public MainFrame() {

        // Настройка главного окна
        setTitle("Моделирование движения космического аппарата");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 900);
        setLocationRelativeTo(null); // Центрируем окно на экране

        // Устанавливаем менеджер компоновки
        setLayout(new BorderLayout(10, 10));

        // Создаем все компоненты интерфейса
        createInputPanel();
        createChartsPanel();

        // Устанавливаем минимальный размер окна
        setMinimumSize(new Dimension(900, 600));
    }

    //Создание панели ввода данных (северная часть окна)
    private void createInputPanel() {

        // Основная панель с рамкой и заголовком
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Параметры моделирования"
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

        // Создаем кнопки
        JButton btnCalculate = new JButton("Выполнить расчет");
        JButton btnClear = new JButton("Очистить поля");

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

        // --- информационная панель (справа от полей ввода) ---
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setPreferredSize(new Dimension(315, 315));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Информация"));

        textAreaInfo = new JTextArea();
        textAreaInfo.setEditable(false);
        textAreaInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane infoScroll = new JScrollPane(textAreaInfo);
        infoPanel.add(infoScroll, BorderLayout.CENTER);

        // Контейнер верхней области: слева поля и кнопки, справа информация
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(inputPanel, BorderLayout.CENTER);
        topContainer.add(infoPanel, BorderLayout.EAST);

        // Добавляем верхний контейнер в верхнюю часть главного окна
        add(topContainer, BorderLayout.NORTH);
    }

    //Создание панели с графиками (центральная часть окна)
    private void createChartsPanel() {

        JPanel chartsContainer = new JPanel(new BorderLayout());
        chartsContainer.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Результаты моделирования"
        ));

        String[] titles = {
                "Эволюция координат X,Y,Z",
                "Эволюция скоростей Vx,Vy,Vz",
                "Траектория XY",
                "Траектория XZ",
                "Траектория YZ"
        };

        JPanel chartsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        chartsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < titles.length; i++) {
            chartPanels[i] = createEmptyChartPanel(titles[i]);
            chartPanels[i].setPreferredSize(new Dimension(500,500));
            chartsPanel.add(chartPanels[i]);
        }

        chartsContainer.add(new JScrollPane(chartsPanel), BorderLayout.CENTER);
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

        return panel;
    }

    private void updateChart(int index, JComponent chart) {
        chartPanels[index].removeAll();
        chartPanels[index].add(chart, BorderLayout.CENTER);
        chartPanels[index].revalidate();
        chartPanels[index].repaint();
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
        cbIntegrator.setSelectedIndex(0);

    }

    //Внутренний класс-слушатель для кнопки расчета
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

                //РЕАЛИЗАЦИЯ ИНТЕГРИРОВАНИЯ
                TVector initialState = new TVector(x, y, z, vx, vy, vz);
                TDynamicModel model = new TSpaceCraft();
                TAbstractIntegrator integrator;

                if (cbIntegrator.getSelectedIndex() == 0) {
                    integrator = new TEuler();
                } else {
                    integrator = new TRungeKutta();
                }

                integrator.t0 = t0;
                integrator.tk = tk;
                integrator.h = h;
                integrator.SetRightParts(model);
                integrator.setInitialState(initialState);
                integrator.MoveTo();

                java.util.List<TVector> trajectory = integrator.trajectory;
                java.util.List<Double> time = integrator.time;

                updateChart(0, ChartCreator.createPositionTimeChart(trajectory, time));

                updateChart(1, ChartCreator.createVelocityTimeChart(trajectory, time));

                updateChart(2, ChartCreator.createPhaseChart(
                        "X","Y",trajectory,v -> v.x,v -> v.y));

                updateChart(3, ChartCreator.createPhaseChart(
                        "X","Z",trajectory,v -> v.x,v -> v.z));

                updateChart(4, ChartCreator.createPhaseChart(
                        "Y","Z",trajectory,v -> v.y,v -> v.z));

                TVector result = integrator.state;

                // Получаем выбранный метод
                String method = (String) cbIntegrator.getSelectedItem();

                // Вычисляем количество шагов
                int steps = (int) Math.ceil((tk - t0) / h);

                // Обновляем информационное поле
                String info = String.format(
                        "Информация о расчете:\n" +
                        "--------------------\n" +
                        "Метод: %s\n" +
                        "Статус: выполнен\n\n" +
                        "Конечное положение:\n" +
                        "(%.1f; %.1f; %.1f) м\n\n" +
                        "Конечная скорость:\n" +
                        "(%.1f; %.1f; %.1f) м/с\n\n" +
                        "Шаги:\n" +
                        "%s шагов",
                        method,
                        result.x, result.y, result.z,
                        result.vx, result.vy, result.vz,
                        steps
                );

                if (textAreaInfo != null) {
                    textAreaInfo.setText(info);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(MainFrame.this,
                        "Ошибка ввода: все поля должны содержать числа.",
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}