package FinalManagement.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import FinalManagement.Controller.MongoDBFunction;

public class CancelBooking {
    public static JFrame frame;
    private static final MongoDBFunction mongoDBFunction = new MongoDBFunction();


    public CancelBooking(List<String[]> rooms, int qty) {
        initializeFrame(rooms, qty);
    }

    private void initializeFrame(List<String[]> rooms, int qty) {
        frame = new JFrame();
        frame.setTitle("Cancel Booking Confirmation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(480, 800);

        ImageIcon iconTopLeft = new ImageIcon("G:\\My Drive\\1 Fredly Sukrata\\1 Kuliah\\Semester 3\\3 Pratikum Kuliah\\Programming Berbasis Objek\\FirstProject-Java-MongoDB-\\Hotel\\src\\Hotel Logo.png");
        frame.setIconImage(iconTopLeft.getImage());

        frame.getContentPane().setBackground(Color.LIGHT_GRAY);

        JLabel text = new JLabel("Cancel Booking Confirmation", SwingConstants.CENTER);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Arial", Font.BOLD, 24));
        text.setBounds(40, 120, 400, 50);
        frame.add(text);

        JPanel rectanglePanel = getRectanglePanel(rooms, qty);
        frame.add(rectanglePanel);

        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void showFrame() {
        frame.setVisible(true);
    }

    private static JPanel getRectanglePanel(List<String[]> room, int qty) {
        JPanel rectanglePanel = getPanel();

        int bigRectWidth = 400;
        int bigRectHeight = 600;

        int inputWidth = 320;
        int spacing = 30;

        int startX = (bigRectWidth - inputWidth) / 2;
        int startY = 100;

        rectanglePanel.setLayout(null);

        System.out.println(room);
        String[] roomsDetails = room.getFirst();
        JLabel filmLabel = new JLabel(roomsDetails[0]);
        filmLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        filmLabel.setBounds(startX, startY + 5 * spacing + 28, inputWidth, 20);
        rectanglePanel.add(filmLabel);

        JLabel ratingLabel = new JLabel("Room Type: " + roomsDetails[1]);
        ratingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        ratingLabel.setBounds(startX, startY + 6 * spacing + 28, inputWidth, 20);
        rectanglePanel.add(ratingLabel);

        JLabel priceLabel = new JLabel("Price: $" + roomsDetails[3]);
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        priceLabel.setBounds(startX, startY + 7 * spacing + 28, inputWidth, 20);
        rectanglePanel.add(priceLabel);

        JLabel availabilityLabel = new JLabel("Your Booking: " + qty);
        availabilityLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        availabilityLabel.setBounds(startX, startY + 8 * spacing + 28, inputWidth, 20);
        rectanglePanel.add(availabilityLabel);

        JLabel numberOfBooking = new JLabel("Quantity: ");
        numberOfBooking.setFont(new Font("Arial", Font.PLAIN, 16));
        numberOfBooking.setBounds(startX, startY + 9 * spacing + 28, inputWidth, 20);
        rectanglePanel.add(numberOfBooking);

        JTextField numberOfCancel = createRoundedInputField();
        numberOfCancel.setBounds(startX + 70, startY + 9 * spacing + 28, inputWidth - 90, 25);
        rectanglePanel.add(numberOfCancel);

        ImageIcon roomImageIcon = new ImageIcon(roomsDetails[5]);
        Image roomImage = roomImageIcon.getImage().getScaledInstance(125, 175, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(roomImage));
        imageLabel.setBounds(140, 80, 125, 175);
        rectanglePanel.add(imageLabel);

        JButton confirmButton = createRoundedButton("Confirm", () -> {
            int cancel = Integer.parseInt(numberOfCancel.getText());
            if (cancel > qty || cancel <= 0) {
                JOptionPane.showMessageDialog(null, "Please Input valid Number !!!");
                numberOfCancel.setText("");
            }else{
                boolean updateSuccess = mongoDBFunction.updateAvailabilityCancel(Collections.singletonList(roomsDetails), numberOfCancel.getText());
                mongoDBFunction.cancelBookingFromUser(numberOfCancel.getText());
                if (updateSuccess) {
                    JOptionPane.showMessageDialog(frame, "Booking cancelled successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    Menu menu = new Menu();
                    menu.showFrame();
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to confirm booking. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        confirmButton.setBounds(startX + 30, bigRectHeight - 80, 120, 40);
        rectanglePanel.add(confirmButton);

        confirmButton.setBounds(startX + 30, bigRectHeight - 80, 120, 40);
        rectanglePanel.add(confirmButton);

        JButton cancelButton = createRoundedButton("Cancel", () -> {
            frame.dispose();
            Menu menu = new Menu();
            menu.showFrame();
        });
        cancelButton.setBounds(startX + 170, bigRectHeight - 80, 120, 40);
        rectanglePanel.add(cancelButton);

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
}
