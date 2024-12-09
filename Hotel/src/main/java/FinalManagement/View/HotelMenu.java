package FinalManagement.View;

import FinalManagement.Controller.MongoDBFunction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HotelMenu {
    private JFrame frame;
    private static final MongoDBFunction mongoDBFunction = new MongoDBFunction();


    public HotelMenu(List<String[]> hotels) {
        initializeFrame(hotels);
    }

    private void initializeFrame(List<String[]> hotels) {
        frame = new JFrame();
        frame.setTitle("Hotel Menu Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(480, 800);

        ImageIcon iconTopLeft = new ImageIcon("G:\\My Drive\\1 Fredly Sukrata\\1 Kuliah\\Semester 3\\3 Pratikum Kuliah\\Programming Berbasis Objek\\PBO6_Source Code\\Hotel\\src\\Hotel Logo.png");
        frame.setIconImage(iconTopLeft.getImage());

        frame.getContentPane().setBackground(Color.lightGray);

        JLabel text = new JLabel("Result", SwingConstants.CENTER);
        text.setForeground(Color.BLACK);
        text.setFont(new Font("Arial", Font.BOLD, 24));
        text.setBounds(-110, 35, 400, 50);
        frame.add(text);

        JPanel rectanglePanel = getRectanglePanel(hotels);
        frame.add(rectanglePanel);
    }

    public void showFrame() {
        frame.setVisible(true);
    }

    private JPanel getRectanglePanel(List<String[]> hotels) {
        JPanel rectanglePanel = getPanel();
        rectanglePanel.setLayout(new BorderLayout());

        JPanel hotelsPanel = new JPanel(new GridBagLayout());
        hotelsPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 0, 10, 0);

        for (String[] hotel : hotels) {
            JButton hotelButton = createHotelButton(hotel);
            hotelsPanel.add(hotelButton, gbc);
        }

        JScrollPane scrollPane = new JScrollPane(hotelsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        rectanglePanel.add(scrollPane, BorderLayout.CENTER);

        return rectanglePanel;
    }


    private JPanel getPanel() {
        JPanel rectanglePanel = getJPanel();
        rectanglePanel.setOpaque(false);

        JButton backButton = createRoundedButton("Back");
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                frame.dispose();
                DestinationPage destinationPage = new DestinationPage();
                destinationPage.showFrame();
            }
        });
        backButton.setBounds(330,40, 90, 40);

        rectanglePanel.add(backButton);

        return rectanglePanel;
    }

    private static JPanel getJPanel() {
        JPanel rectanglePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.WHITE);
                int rectWidth = 400;
                int rectHeight = 700;
                int arcWidth = 30;
                int arcHeight = 30;
                int x = (getWidth() - rectWidth) / 2;
                int y = (getHeight() - rectHeight) / 2;
                g2d.fillRoundRect(x, y, rectWidth, rectHeight, arcWidth, arcHeight);
            }
        };
        rectanglePanel.setBounds(40, 100, 400, 600);
        return rectanglePanel;
    }

    private JButton createHotelButton(String[] hotel) {
        JButton button = new JButton("<html>" +
                "<b>" + hotel[0] + "</b><br>" +
                "Rating: " + hotel[1] + "<br>") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2d.dispose();

                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2d.setColor(Color.lightGray);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2d.dispose();
            }
        };

        button.setForeground(Color.BLACK);

        button.setFont(new Font("Arial", Font.BOLD, 16));

        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setPreferredSize(new Dimension(375, 100));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setVerticalAlignment(SwingConstants.CENTER);
        ImageIcon pathHotelPicture = new ImageIcon(mongoDBFunction.pathHotelPicture(hotel[0]));
        Image hotelPicture = pathHotelPicture.getImage();
        Image resizedHotelPicture = hotelPicture.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(resizedHotelPicture));

        button.addActionListener(e -> {
            List<String[]> roomDetails = new ArrayList<>();
            frame.dispose();
            roomDetails = mongoDBFunction.fetchRoomDetails(hotel[0]);
            RoomMenu roomMenu = new RoomMenu(roomDetails);
            roomMenu.showFrame();

        });

        return button;
    }

    private static JButton createRoundedButton(String text) {
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

        return button;
    }
}
