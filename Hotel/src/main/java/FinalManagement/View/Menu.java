package FinalManagement.View;

import FinalManagement.Controller.Button;

import javax.swing.*;
import java.awt.*;
<<<<<<< HEAD
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
=======
>>>>>>> master

public class Menu {
    public static JFrame frame;

    public Menu() {
        initializeFrame();
    }

    private void initializeFrame() {
        frame = new JFrame();
        frame.setTitle("Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(480, 800);

        ImageIcon iconTopLeft = new ImageIcon("G:\\My Drive\\1 Fredly Sukrata\\1 Kuliah\\Semester 3\\3 Pratikum Kuliah\\Programming Berbasis Objek\\PBO6_Source Code\\Hotel\\src\\Hotel Logo.png");
        frame.setIconImage(iconTopLeft.getImage());

        frame.getContentPane().setBackground(Color.lightGray);

        JLabel text = new JLabel("Menu", SwingConstants.CENTER);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Arial", Font.BOLD, 24));
        text.setBounds(40, 120, 400, 50);
        frame.add(text);

        JPanel rectanglePanel = getRectanglePanel();
        frame.add(rectanglePanel);
    }

    public void showFrame() {
        frame.setVisible(true);
    }

    private static JPanel getRectanglePanel() {
        JPanel rectanglePanel = getPanel();

        int bigRectWidth = 400;
        int bigRectHeight = 600;

        int inputWidth = 320;
        int inputHeight = 40;
        int spacing = 30;

        int startX = (bigRectWidth - inputWidth) / 2;
        int startY = (bigRectHeight - (2 * inputHeight + spacing)) / 2;

        JButton yourBooking = createRoundedButton("Your Booking", Button::handleMenuToListRoomOrder
        );
        yourBooking.setBounds(startX + 30, startY + spacing * 3, inputWidth, inputHeight);

        JButton findHotel = createRoundedButton("Find Hotel", Button::handleMenuToDestination
        );
        findHotel.setBounds(startX + 30, startY + spacing * 5, inputWidth, inputHeight);

        rectanglePanel.add(yourBooking);
        rectanglePanel.add(findHotel);
        return rectanglePanel;
    }

    private static JPanel getPanel() {
        JPanel rectanglePanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(Color.WHITE);

                int rectWidth = 400;
                int rectHeight = 600;
                int arcWidth = 30;
                int arcHeight = 30;

                int x = (getWidth() - rectWidth) / 2;
                int y = (getHeight() - rectHeight) / 2;

                g2d.fillRoundRect(x, y, rectWidth, rectHeight, arcWidth, arcHeight);
            }
        };
        rectanglePanel.setBounds(40, 100, 400, 600);
        rectanglePanel.setOpaque(false);
        return rectanglePanel;
    }

    private static JButton createRoundedButton(String text, Runnable action) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();

                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getForeground());
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2d.dispose();
            }
        };

        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));

        button.addActionListener(e -> action.run());
        return button;
    }

<<<<<<< HEAD
    private static JTextField createRoundedInputField() {
        JTextField textField = new JTextField("") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g2d);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.GRAY);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        textField.setOpaque(false);
        textField.setBackground(Color.WHITE);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText("");
                    textField.setForeground(Color.GRAY);
                }
            }
        });

        textField.setForeground(Color.GRAY);
        return textField;
    }
=======
>>>>>>> master
}
