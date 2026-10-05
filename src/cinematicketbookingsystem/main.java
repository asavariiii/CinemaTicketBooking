package cinematicketbookingsystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

/**
 * Main
 */
class Main extends JFrame {

    // =========================
    // COLORS
    // =========================

    private final Color BACKGROUND = new Color(18, 18, 24);
    private final Color PANEL = new Color(28, 28, 36);
    private final Color PANEL_LIGHT = new Color(38, 38, 48);
    private final Color PRIMARY = new Color(125, 65, 190);
    private final Color WHITE = Color.WHITE;
    private final Color TEXT_LIGHT = new Color(200, 200, 210);
    private final Color GREEN = new Color(50, 180, 100);
    private final Color RED = new Color(210, 70, 70);

    // =========================
    // MAIN UI
    // =========================

    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Login
    private JTextField loginUsername;
    private JPasswordField loginPassword;

    // Register
    private JTextField registerUsername;
    private JPasswordField registerPassword;
    private JPasswordField registerConfirm;

    // Booking information
    private String currentUser;
    private String selectedMovie;
    private String selectedDate;
    private String selectedTime;

    private final List<String> selectedSeats = new ArrayList<>();
    private final Map<String, JButton> seatButtons = new HashMap<>();

    private JLabel movieTitleLabel;
    private JLabel movieInfoLabel;
    private JLabel totalLabel;
    private JComboBox<String> dateBox;
    private JComboBox<String> timeBox;
    private JTextArea historyArea;
    private JLabel confirmationMessage1;
    private JTextComponent confirmationReceipt1;
    private String lastBookingId;
    private double lastTotal;

    // Files
    private final Path usersFile = Paths.get("users.txt");
    private final Path bookingsFile = Paths.get("bookings.txt");

    // Movies
    private final String[][] movies = {
            {"Interstellar", "Sci-Fi • Adventure", "₹220"},
            {"Inception", "Sci-Fi • Thriller", "₹200"},
            {"The Dark Knight", "Action • Crime", "₹240"},
            {"3 Idiots", "Comedy • Drama", "₹180"},
            {"Zindagi Na Milegi Dobara", "Drama • Adventure", "₹200"},
            {"Avengers: Endgame", "Action • Sci-Fi", "₹250"}
    };
    

    // =========================
    // CONSTRUCTOR
    // =========================

    public Main() {

        setTitle("CinéBook - Movie Ticket Booking");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        createFiles();

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BACKGROUND);

        mainPanel.add(createWelcomePanel(), "WELCOME");
        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createRegisterPanel(), "REGISTER");
        mainPanel.add(createHomePanel(), "HOME");
        mainPanel.add(createShowPanel(), "SHOW");
        mainPanel.add(createSeatPanel(), "SEATS");
        mainPanel.add(createPaymentPanel(), "PAYMENT");
        mainPanel.add(createConfirmationPanel(), "CONFIRMATION");
        mainPanel.add(createHistoryPanel(), "HISTORY");

        add(mainPanel);

        cardLayout.show(mainPanel, "WELCOME");
    }

    // =========================
    // FILE SETUP
    // =========================

    private void createFiles() {

        try {

            if (!Files.exists(usersFile)) {
                Files.createFile(usersFile);
            }

            if (!Files.exists(bookingsFile)) {
                Files.createFile(bookingsFile);
            }

        } catch (IOException e) {
            showError("Could not create project files.");
        }
    }

    // =========================
    // WELCOME SCREEN
    // =========================

    private JPanel createWelcomePanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(13, 13, 18));
        header.setBorder(new EmptyBorder(22, 35, 22, 35));

        JLabel logo = label("🎬  CINÉBOOK", 28, WHITE);
        JLabel subtitle = label("MOVIE TICKET BOOKING", 12, TEXT_LIGHT);

        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setBackground(new Color(13, 13, 18));

        logoPanel.add(logo);
        logoPanel.add(Box.createVerticalStrut(5));
        logoPanel.add(subtitle);

        header.add(logoPanel, BorderLayout.WEST);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(BACKGROUND);
        center.setBorder(new EmptyBorder(110, 50, 50, 50));

        JLabel title = label("BOOK YOUR MOVIE", 46, WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel text = label(
                "Choose your favourite movie and enjoy your show.",
                17,
                TEXT_LIGHT
        );
        text.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton start = button("START BOOKING");

        start.setAlignmentX(Component.CENTER_ALIGNMENT);

        start.addActionListener(e ->
                cardLayout.show(mainPanel, "LOGIN")
        );

        center.add(title);
        center.add(Box.createVerticalStrut(18));
        center.add(text);
        center.add(Box.createVerticalStrut(45));
        center.add(start);

        JPanel footer = new JPanel();
        footer.setBackground(new Color(13, 13, 18));

        footer.add(label(
                "© 2026 CinéBook | Movie Ticket Booking System",
                12,
                Color.GRAY
        ));

        panel.add(header, BorderLayout.NORTH);
        panel.add(center, BorderLayout.CENTER);
        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }

    // =========================
    // LOGIN SCREEN
    // =========================

    private JPanel createLoginPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(55, 260, 40, 260));

        JLabel logo = label("🎬 CINÉBOOK", 30, WHITE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = label("Welcome Back", 27, WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = label(
                "Login to continue booking your movie",
                14,
                TEXT_LIGHT
        );
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginUsername = new JTextField();
        styleField(loginUsername);

        loginPassword = new JPasswordField();
        styleField(loginPassword);

        JLabel userLabel = label("Username", 14, WHITE);
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel passLabel = label("Password", 14, WHITE);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton login = button("LOGIN");
        login.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton register = new JButton("Create New Account");
        register.setForeground(TEXT_LIGHT);
        register.setBackground(BACKGROUND);
        register.setBorderPainted(false);
        register.setFocusPainted(false);
        register.setAlignmentX(Component.CENTER_ALIGNMENT);

        login.addActionListener(e -> performLogin());

        register.addActionListener(e ->
                cardLayout.show(mainPanel, "REGISTER")
        );

        panel.add(logo);
        panel.add(Box.createVerticalStrut(20));
        panel.add(title);
        panel.add(Box.createVerticalStrut(8));
        panel.add(subtitle);

        panel.add(Box.createVerticalStrut(30));

        panel.add(userLabel);
        panel.add(Box.createVerticalStrut(7));
        panel.add(loginUsername);

        panel.add(Box.createVerticalStrut(18));

        panel.add(passLabel);
        panel.add(Box.createVerticalStrut(7));
        panel.add(loginPassword);

        panel.add(Box.createVerticalStrut(28));

        panel.add(login);
        panel.add(Box.createVerticalStrut(10));
        panel.add(register);

        return panel;
    }

    // =========================
    // REGISTER SCREEN
    // =========================

    private JPanel createRegisterPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(45, 260, 40, 260));

        JLabel logo = label("🎬 CINÉBOOK", 30, WHITE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = label("Create Account", 27, WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = label(
                "Create your CinéBook account",
                14,
                TEXT_LIGHT
        );
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        registerUsername = new JTextField();
        styleField(registerUsername);

        registerPassword = new JPasswordField();
        styleField(registerPassword);

        registerConfirm = new JPasswordField();
        styleField(registerConfirm);

        JButton create = button("CREATE ACCOUNT");
        create.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton back = new JButton("Back to Login");
        back.setForeground(TEXT_LIGHT);
        back.setBackground(BACKGROUND);
        back.setBorderPainted(false);
        back.setFocusPainted(false);
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        create.addActionListener(e -> registerUser());

        back.addActionListener(e ->
                cardLayout.show(mainPanel, "LOGIN")
        );

        panel.add(logo);
        panel.add(Box.createVerticalStrut(18));
        panel.add(title);
        panel.add(Box.createVerticalStrut(8));
        panel.add(subtitle);

        panel.add(Box.createVerticalStrut(25));

        panel.add(label("Username", 14, WHITE));
        panel.add(registerUsername);

        panel.add(Box.createVerticalStrut(12));

        panel.add(label("Password", 14, WHITE));
        panel.add(registerPassword);

        panel.add(Box.createVerticalStrut(12));

        panel.add(label("Confirm Password", 14, WHITE));
        panel.add(registerConfirm);

        panel.add(Box.createVerticalStrut(25));

        panel.add(create);
        panel.add(Box.createVerticalStrut(8));
        panel.add(back);

        return panel;
    }

    // =========================
    // HOME / MOVIES
    // =========================

    private JPanel createHomePanel() {

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BACKGROUND);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(13, 13, 18));
        header.setBorder(new EmptyBorder(18, 25, 18, 25));

        JLabel logo = label("🎬 CINÉBOOK", 25, WHITE);

        JButton history = smallButton("MY BOOKINGS");
        JButton logout = smallButton("LOGOUT");

        history.addActionListener(e -> {
            refreshHistory();
            cardLayout.show(mainPanel, "HISTORY");
        });

        logout.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainPanel, "WELCOME");
        });

        JPanel right = new JPanel(new FlowLayout());
        right.setBackground(new Color(13, 13, 18));
        right.add(history);
        right.add(logout);

        header.add(logo, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);

        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(BACKGROUND);
        content.setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel title = label("Now Showing", 30, WHITE);

        JLabel welcome = label(
                "Welcome, " + (currentUser == null ? "" : currentUser),
                14,
                TEXT_LIGHT
        );

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(BACKGROUND);
        titlePanel.add(title, BorderLayout.WEST);
        titlePanel.add(welcome, BorderLayout.EAST);

        JPanel movieGrid = new JPanel(new GridLayout(2, 3, 18, 18));
        movieGrid.setBackground(BACKGROUND);

        for (String[] movie : movies) {

            JPanel card = createMovieCard(
                    movie[0],
                    movie[1],
                    movie[2]
            );

            movieGrid.add(card);
        }

        content.add(titlePanel, BorderLayout.NORTH);
        content.add(movieGrid, BorderLayout.CENTER);

        main.add(header, BorderLayout.NORTH);
        main.add(content, BorderLayout.CENTER);

        return main;
    }

    private JPanel createMovieCard(
            String movie,
            String genre,
            String price
    ) {

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(PANEL);
        card.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel icon = label("🎬", 38, WHITE);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel name = label(movie, 20, WHITE);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel type = label(genre, 12, TEXT_LIGHT);
        type.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel cost = label("From " + price, 13, new Color(180, 130, 230));
        cost.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton select = button("SELECT");
        select.setAlignmentX(Component.CENTER_ALIGNMENT);

        select.addActionListener(e -> {

            selectedMovie = movie;

            movieTitleLabel.setText(movie);
            movieInfoLabel.setText(
                    genre + "  •  Starting price: " + price
            );

            cardLayout.show(mainPanel, "SHOW");
        });

        card.add(icon);
        card.add(Box.createVerticalStrut(8));
        card.add(name);
        card.add(Box.createVerticalStrut(5));
        card.add(type);
        card.add(Box.createVerticalStrut(5));
        card.add(cost);
        card.add(Box.createVerticalStrut(10));
        card.add(select);

        return card;
    }

    // =========================
    // SHOW SELECTION
    // =========================

    private JPanel createShowPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(35, 70, 35, 70));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(BACKGROUND);

        movieTitleLabel = label("Movie", 34, WHITE);
        movieTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        movieInfoLabel = label("", 15, TEXT_LIGHT);
        movieInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        top.add(movieTitleLabel);
        top.add(Box.createVerticalStrut(8));
        top.add(movieInfoLabel);

        JPanel form = new JPanel();
        form.setBackground(PANEL);
        form.setBorder(new EmptyBorder(35, 80, 35, 80));
        form.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel dateLabel = label("SELECT DATE", 15, WHITE);

        dateBox = new JComboBox<>();
        for (int i = 0; i < 5; i++) {

            LocalDate date = LocalDate.now().plusDays(i);

            dateBox.addItem(
                    date.format(
                            DateTimeFormatter.ofPattern("dd MMM yyyy")
                    )
            );
        }

        styleCombo(dateBox);

        JLabel timeLabel = label("SELECT SHOW TIME", 15, WHITE);

        timeBox = new JComboBox<>(
                new String[]{
                        "10:00 AM",
                        "01:30 PM",
                        "04:30 PM",
                        "07:30 PM",
                        "10:30 PM"
                }
        );

        styleCombo(timeBox);

        JPanel options = new JPanel(new GridLayout(2, 2, 20, 15));
        options.setBackground(PANEL);

        options.add(dateLabel);
        options.add(timeLabel);
        options.add(dateBox);
        options.add(timeBox);

        JButton continueButton = button("CONTINUE TO SEATS");
        continueButton.setPreferredSize(new Dimension(250, 48));

        continueButton.addActionListener(e -> {

            selectedDate = (String) dateBox.getSelectedItem();
            selectedTime = (String) timeBox.getSelectedItem();

            selectedSeats.clear();

            resetSeats();

            updateTotal();

            cardLayout.show(mainPanel, "SEATS");
        });

        JButton back = smallButton("BACK");

        back.addActionListener(e ->
                cardLayout.show(mainPanel, "HOME")
        );

        JPanel bottom = new JPanel();
        bottom.setBackground(BACKGROUND);
        bottom.add(back);
        bottom.add(continueButton);

        panel.add(top, BorderLayout.NORTH);
        panel.add(options, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    // =========================
    // SEAT SCREEN
    // =========================

    private JPanel createSeatPanel() {

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BACKGROUND);
        main.setBorder(new EmptyBorder(20, 30, 20, 30));

        JLabel title = label("SELECT YOUR SEATS", 28, WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(BACKGROUND);

        top.add(title, BorderLayout.NORTH);

        JLabel screen = label(
                "━━━━━━━━━━━━━━━━  SCREEN  ━━━━━━━━━━━━━━━━",
                14,
                TEXT_LIGHT
        );
        screen.setHorizontalAlignment(SwingConstants.CENTER);

        top.add(screen, BorderLayout.CENTER);

        JPanel seatGrid = new JPanel(
                new GridLayout(5, 8, 10, 10)
        );

        seatGrid.setBackground(BACKGROUND);
        seatGrid.setBorder(
                new EmptyBorder(25, 80, 25, 80)
        );

        seatButtons.clear();

        for (char row = 'A'; row <= 'E'; row++) {

            for (int number = 1; number <= 8; number++) {

                String seat = row + String.valueOf(number);

                JButton seatButton = new JButton(seat);

                seatButton.setFocusPainted(false);
                seatButton.setForeground(WHITE);
                seatButton.setBackground(PANEL_LIGHT);
                seatButton.setFont(
                        new Font("SansSerif", Font.BOLD, 12)
                );

                seatButton.addActionListener(e ->
                        toggleSeat(seat)
                );

                seatButtons.put(seat, seatButton);
                seatGrid.add(seatButton);
            }
        }

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(BACKGROUND);

        totalLabel = label("Total: ₹0", 20, WHITE);

        JButton continueButton =
                button("CONTINUE TO PAYMENT");

        JButton back = smallButton("BACK");

        continueButton.addActionListener(e -> {

            if (selectedSeats.isEmpty()) {

                showError("Please select at least one seat.");

                return;
            }

            cardLayout.show(mainPanel, "PAYMENT");
        });

        back.addActionListener(e ->
                cardLayout.show(mainPanel, "SHOW")
        );

        bottom.add(totalLabel, BorderLayout.WEST);

        JPanel buttons = new JPanel();
        buttons.setBackground(BACKGROUND);
        buttons.add(back);
        buttons.add(continueButton);

        bottom.add(buttons, BorderLayout.EAST);

        main.add(top, BorderLayout.NORTH);
        main.add(seatGrid, BorderLayout.CENTER);
        main.add(bottom, BorderLayout.SOUTH);

        return main;
    }

    private void toggleSeat(String seat) {

        JButton button = seatButtons.get(seat);

        if (isSeatBooked(seat)) {

            showError("Seat " + seat + " is already booked.");

            return;
        }

        if (selectedSeats.contains(seat)) {

            selectedSeats.remove(seat);

            button.setBackground(PANEL_LIGHT);

        } else {

            selectedSeats.add(seat);

            button.setBackground(PRIMARY);
        }

        updateTotal();
    }

    private void resetSeats() {

        for (Map.Entry<String, JButton> entry :
                seatButtons.entrySet()) {

            String seat = entry.getKey();
            JButton button = entry.getValue();

            if (isSeatBooked(seat)) {

                button.setBackground(RED);
                button.setEnabled(false);

            } else {

                button.setBackground(PANEL_LIGHT);
                button.setEnabled(true);
            }
        }
    }

    // =========================
    // PAYMENT
    // =========================

    private JPanel createPaymentPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(35, 250, 35, 250));

        JLabel title = label("PAYMENT", 32, WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel summary = label("", 15, TEXT_LIGHT);
        summary.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel amount = label("", 28, WHITE);
        amount.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel paymentBox = new JPanel(new GridLayout(3, 1, 10, 10));
        paymentBox.setBackground(PANEL);
        paymentBox.setBorder(new EmptyBorder(20, 30, 20, 30));

        JRadioButton upi = new JRadioButton("UPI");
        JRadioButton card = new JRadioButton("Debit / Credit Card");
        JRadioButton cash = new JRadioButton("Pay at Counter");

        ButtonGroup group = new ButtonGroup();
        group.add(upi);
        group.add(card);
        group.add(cash);

        upi.setSelected(true);

        styleRadio(upi);
        styleRadio(card);
        styleRadio(cash);

        paymentBox.add(upi);
        paymentBox.add(card);
        paymentBox.add(cash);

        JButton pay = button("PAY & BOOK TICKET");
        pay.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton back = smallButton("BACK");
        back.setAlignmentX(Component.CENTER_ALIGNMENT);

        pay.addActionListener(e -> {
            String method;

            if (upi.isSelected()) {
                method = "UPI";
            } else if (card.isSelected()) {
                method = "Card";
            } else {
                method = "Cash";
            }

            bookTicket1(method);
        });

        back.addActionListener(e ->
                cardLayout.show(mainPanel, "SEATS")
        );

        panel.add(title);
        panel.add(Box.createVerticalStrut(20));
        panel.add(summary);
        panel.add(Box.createVerticalStrut(12));
        panel.add(amount);
        panel.add(Box.createVerticalStrut(25));
        panel.add(paymentBox);
        panel.add(Box.createVerticalStrut(25));
        panel.add(pay);
        panel.add(Box.createVerticalStrut(8));
        panel.add(back);

        panel.addHierarchyListener(e -> {
            if (summary != null) {
                String seats = String.join(", ", selectedSeats);

                summary.setText(
                        selectedMovie +
                        "  •  " +
                        selectedDate +
                        "  •  " +
                        selectedTime +
                        "  •  Seats: " +
                        seats
                );

                amount.setText(
                        "Total Amount: ₹" +
                        String.format("%.0f", calculateTotal())
                );
            }
        });

        return panel;
    }

    // =========================
    // CONFIRMATION
    // =========================

    private JPanel createConfirmationPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(55, 180, 40, 180));

        JLabel title = label("BOOKING CONFIRMED", 32, WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        confirmationMessage1 = label(
                "Your ticket has been booked successfully!",
                16,
                TEXT_LIGHT
        );
        confirmationMessage1.setAlignmentX(Component.CENTER_ALIGNMENT);

        confirmationReceipt1 = new JTextArea();
        confirmationReceipt1.setEditable(false);
        ((JTextArea) confirmationReceipt1).setLineWrap(true);
        ((JTextArea) confirmationReceipt1).setWrapStyleWord(true);
        confirmationReceipt1.setBackground(PANEL);
        confirmationReceipt1.setForeground(WHITE);
        confirmationReceipt1.setFont(
                new Font("Monospaced", Font.PLAIN, 15)
        );
        confirmationReceipt1.setBorder(
                new EmptyBorder(20, 25, 20, 25)
        );

        JScrollPane receiptScroll = new JScrollPane((JTextArea) confirmationReceipt1);
        receiptScroll.setBorder(null);

        JButton home = button("BACK TO HOME");
        home.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton history1 = smallButton("VIEW MY BOOKINGS");
        history1.setAlignmentX(Component.CENTER_ALIGNMENT);

        home.addActionListener(e ->
                cardLayout.show(mainPanel, "HOME")
        );

        history1.addActionListener(e -> {
            refreshHistory();
            cardLayout.show(mainPanel, "HISTORY");
        });

        panel.add(title);
        panel.add(Box.createVerticalStrut(12));
        panel.add(confirmationMessage1);
        panel.add(Box.createVerticalStrut(20));
        panel.add(receiptScroll);
        panel.add(Box.createVerticalStrut(20));
        panel.add(home);
        panel.add(Box.createVerticalStrut(8));
        panel.add(history1);

        return panel;
    }

    private void bookTicket1(String paymentMethod) {

        if (selectedSeats.isEmpty()) {
            showError("Please select seats.");
            return;
        }

        lastBookingId = "CB" + (10000 + new Random().nextInt(90000));
        lastTotal = calculateTotal();

        String seats = String.join(", ", selectedSeats);
        String record = currentUser + "|" +
                lastBookingId + "|" +
                selectedMovie + "|" +
                selectedDate + "|" +
                selectedTime + "|" +
                seats + "|" +
                String.format("%.0f", lastTotal);

        try {
            Files.write(
                    bookingsFile,
                    Arrays.asList(record),
                    StandardOpenOption.APPEND
            );

            if (confirmationMessage1 != null) {
                confirmationMessage1.setText(
                        "<html><center>" +
                                "Your ticket has been booked successfully!<br>" +
                                "Booking ID: <b>" +
                                lastBookingId +
                                "</b><br>" +
                                "Payment: " +
                                paymentMethod +
                                "</center></html>"
                );
            }

            String bookedSeats = String.join(", ", selectedSeats);
            String receiptText =
                    "====================================\n" +
                    "          CINÉBOOK BOOKING\n" +
                    "====================================\n\n" +
                    "Booking ID : " + lastBookingId + "\n" +
                    "Movie      : " + selectedMovie + "\n" +
                    "Date       : " + selectedDate + "\n" +
                    "Show Time  : " + selectedTime + "\n" +
                    "Seats      : " + bookedSeats + "\n" +
                    "Amount     : ₹" + String.format("%.0f", lastTotal) + "\n" +
                    "Payment    : " + paymentMethod + "\n\n" +
                    "====================================";

            if (confirmationReceipt1 != null) {
                confirmationReceipt1.setText(receiptText);
                confirmationReceipt1.setCaretPosition(0);
            }

            refreshHistory();
            cardLayout.show(mainPanel, "CONFIRMATION");

        } catch (IOException e) {
            showError("Could not save booking.");
        }
    }

    // =========================
    // HISTORY
    // =========================

    private JPanel createHistoryPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        panel.setBorder(new EmptyBorder(25, 35, 25, 35));

        JLabel title = label("MY BOOKINGS", 30, WHITE);

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setBackground(PANEL);
        historyArea.setForeground(WHITE);
        historyArea.setFont(
                new Font("Monospaced", Font.PLAIN, 14)
        );
        historyArea.setBorder(
                new EmptyBorder(20, 20, 20, 20)
        );

        JScrollPane scroll = new JScrollPane(historyArea);
        scroll.setBorder(null);

        JButton back = smallButton("BACK TO HOME");
        back.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        panel.add(title, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(back, BorderLayout.SOUTH);
        panel.putClientProperty("historyArea", historyArea);

        return panel;
    }

    private void refreshHistory() {
        if (historyArea == null) {
            return;
        }

        StringBuilder text = new StringBuilder();

        try {
            List<String> lines = Files.readAllLines(bookingsFile);

            for (String line : lines) {
                String[] data = line.split("\\|");

                if (data.length >= 7 && data[0].equals(currentUser)) {
                    text.append("====================================\n");
                    text.append("          CINÉBOOK BOOKING\n");
                    text.append("====================================\n\n");

                    text.append("Booking ID : ")
                        .append(data[1]).append("\n");

                    text.append("Movie      : ")
                        .append(data[2]).append("\n");

                    text.append("Date       : ")
                        .append(data[3]).append("\n");

                    text.append("Show Time  : ")
                        .append(data[4]).append("\n");

                    text.append("Seats      : ")
                        .append(data[5]).append("\n");

                    text.append("Amount     : ₹")
                        .append(data[6]).append("\n\n");

                    text.append("====================================\n\n");
                }
            }

            if (text.length() == 0) {
                text.append("\n\nNo bookings found.");
            }

            historyArea.setText(text.toString());
        } catch (Exception e) {
            historyArea.setText("Unable to load booking history.");
        }
    }

    private void performLogin() {

        String username =
                loginUsername.getText().trim();

        String password =
                new String(loginPassword.getPassword());

        if (username.isEmpty() ||
                password.isEmpty()) {

            showError(
                    "Please enter username and password."
            );

            return;
        }

        try {

            List<String> users =
                    Files.readAllLines(usersFile);

            for (String user : users) {

                String[] data =
                        user.split("\\|");

                if (data.length >= 2 &&
                        data[0].equals(username) &&
                        data[1].equals(password)) {

                    currentUser = username;

                    JOptionPane.showMessageDialog(
                            this,
                            "Login successful!",
                            "Welcome to CinéBook",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    mainPanel.remove(
                            getCardIndex("HOME")
                    );

                    mainPanel.add(
                            createHomePanel(),
                            "HOME"
                    );

                    cardLayout.show(
                            mainPanel,
                            "HOME"
                    );

                    return;
                }
            }

            // Demo login
            if (username.equals("admin") &&
                    password.equals("123")) {

                currentUser = username;

                cardLayout.show(
                        mainPanel,
                        "HOME"
                );

                return;
            }

            showError(
                    "Invalid username or password."
            );

        } catch (IOException e) {

            showError(
                    "Unable to read user data."
            );
        }
    }

    // =========================
    // REGISTER
    // =========================

    private void registerUser() {

        String username =
                registerUsername.getText().trim();

        String password =
                new String(registerPassword.getPassword());

        String confirm =
                new String(registerConfirm.getPassword());

        if (username.isEmpty() ||
                password.isEmpty() ||
                confirm.isEmpty()) {

            showError("Please fill all fields.");

            return;
        }

        if (!password.equals(confirm)) {

            showError("Passwords do not match.");

            return;
        }

        try {

            List<String> users =
                    Files.readAllLines(usersFile);

            for (String user : users) {

                String[] data =
                        user.split("\\|");

                if (data.length >= 1 &&
                        data[0].equals(username)) {

                    showError(
                            "Username already exists."
                    );

                    return;
                }
            }

            Files.write(
                    usersFile,
                    Arrays.asList(
                            username + "|" + password
                    ),
                    StandardOpenOption.APPEND
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Account created successfully!",
                    "CinéBook",
                    JOptionPane.INFORMATION_MESSAGE
            );

            registerUsername.setText("");
            registerPassword.setText("");
            registerConfirm.setText("");

            cardLayout.show(mainPanel, "LOGIN");

        } catch (IOException e) {

            showError(
                    "Unable to save account."
            );
        }
    }

    // =========================
    // BOOK TICKET
    // =========================

    

    // =========================
    // PRICING
    // =========================

    private double calculateTotal() {

        double total = 0;

        for (String seat : selectedSeats) {

            char row = seat.charAt(0);

            switch (row) {

                case 'A':
                case 'B':
                    total += 180;
                    break;

                case 'C':
                case 'D':
                    total += 220;
                    break;

                case 'E':
                    total += 260;
                    break;
            }
        }

        return total;
    }

    private void updateTotal() {

        if (totalLabel != null) {

            totalLabel.setText(
                    "Total: ₹" +
                    String.format(
                            "%.0f",
                            calculateTotal()
                    )
            );
        }
    }

    // =========================
    // BOOKED SEATS
    // =========================

    private boolean isSeatBooked(String seat) {

        if (selectedMovie == null ||
                selectedDate == null ||
                selectedTime == null) {

            return false;
        }

        try {

            List<String> lines =
                    Files.readAllLines(bookingsFile);

            for (String line : lines) {

                String[] data =
                        line.split("\\|");

                if (data.length >= 6 &&
                        data[2].equals(selectedMovie) &&
                        data[3].equals(selectedDate) &&
                        data[4].equals(selectedTime)) {

                    String[] bookedSeats =
                            data[5].split(",");

                    for (String booked :
                            bookedSeats) {

                        if (booked.equals(seat)) {
                            return true;
                        }
                    }
                }
            }

        } catch (IOException e) {

            return false;
        }

        return false;
    }

    // =========================
    // UI HELPERS
    // =========================

    private JLabel label(
            String text,
            int size,
            Color color
    ) {

        JLabel label = new JLabel(text);

        label.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        size
                )
        );

        label.setForeground(color);

        return label;
    }

    private JButton button(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        14
                )
        );

        button.setForeground(WHITE);
        button.setBackground(PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setPreferredSize(
                new Dimension(250, 48)
        );

        return button;
    }

    private JButton smallButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );

        button.setForeground(WHITE);
        button.setBackground(PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        return button;
    }

    private void styleField(JTextField field) {

        field.setMaximumSize(
                new Dimension(420, 42)
        );

        field.setPreferredSize(
                new Dimension(420, 42)
        );

        field.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        15
                )
        );
    }

    private void styleCombo(
            JComboBox<String> combo
    ) {

        combo.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        combo.setBackground(WHITE);
        combo.setPreferredSize(
                new Dimension(200, 40)
        );
    }

    private void styleRadio(
            JRadioButton radio
    ) {

        radio.setBackground(PANEL);
        radio.setForeground(WHITE);
        radio.setFocusPainted(false);
        radio.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );
    }

    private void showError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "CinéBook",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private int getCardIndex(String name) {

        if ("HOME".equals(name)) {
            return 3;
        }

        if ("HISTORY".equals(name)) {
            return 8;
        }

        if ("CONFIRMATION".equals(name)) {
            return 7;
        }

        return 0;
    }

    // =========================
    // MAIN
    // =========================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Main window = new Main();

            window.setVisible(true);
        });
    }

    public Color getBACKGROUND() {
        return BACKGROUND;
    }

    public Color getPANEL() {
        return PANEL;
    }

    public Color getPANEL_LIGHT() {
        return PANEL_LIGHT;
    }

    public Color getPRIMARY() {
        return PRIMARY;
    }

    public Color getWHITE() {
        return WHITE;
    }

    public Color getTEXT_LIGHT() {
        return TEXT_LIGHT;
    }

    public Color getGREEN() {
        return GREEN;
    }

    public Color getRED() {
        return RED;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JTextField getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(JTextField loginUsername) {
        this.loginUsername = loginUsername;
    }

    public JPasswordField getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(JPasswordField loginPassword) {
        this.loginPassword = loginPassword;
    }

    public JTextField getRegisterUsername() {
        return registerUsername;
    }

    public void setRegisterUsername(JTextField registerUsername) {
        this.registerUsername = registerUsername;
    }

    public JPasswordField getRegisterPassword() {
        return registerPassword;
    }

    public void setRegisterPassword(JPasswordField registerPassword) {
        this.registerPassword = registerPassword;
    }

    public JPasswordField getRegisterConfirm() {
        return registerConfirm;
    }

    public void setRegisterConfirm(JPasswordField registerConfirm) {
        this.registerConfirm = registerConfirm;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getSelectedMovie() {
        return selectedMovie;
    }

    public void setSelectedMovie(String selectedMovie) {
        this.selectedMovie = selectedMovie;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public JLabel getConfirmationMessage1111() {
        return getConfirmationMessage1111();
    }

    public void setConfirmationMessage1111(JLabel confirmationMessage1111) {
    }

    public JPanel getReceiptPanel1() {
        return getReceiptPanel1();
    }

    public void setReceiptPanel1(JPanel receiptPanel1) {
    }

    public List<String> getSelectedSeats() {
        return selectedSeats;
    }

    public Map<String, JButton> getSeatButtons() {
        return seatButtons;
    }

    public JLabel getMovieTitleLabel() {
        return movieTitleLabel;
    }

    public void setMovieTitleLabel(JLabel movieTitleLabel) {
        this.movieTitleLabel = movieTitleLabel;
    }

    public JLabel getMovieInfoLabel() {
        return movieInfoLabel;
    }

    public void setMovieInfoLabel(JLabel movieInfoLabel) {
        this.movieInfoLabel = movieInfoLabel;
    }

    public JLabel getTotalLabel() {
        return totalLabel;
    }

    public void setTotalLabel(JLabel totalLabel) {
        this.totalLabel = totalLabel;
    }

    public JComboBox<String> getDateBox() {
        return dateBox;
    }

    public void setDateBox(JComboBox<String> dateBox) {
        this.dateBox = dateBox;
    }

    public JComboBox<String> getTimeBox() {
        return timeBox;
    }

    public void setTimeBox(JComboBox<String> timeBox) {
        this.timeBox = timeBox;
    }

    public String getLastBookingId() {
        return lastBookingId;
    }

    public void setLastBookingId(String lastBookingId) {
        this.lastBookingId = lastBookingId;
    }

    public double getLastTotal() {
        return lastTotal;
    }

    public void setLastTotal(double lastTotal) {
        this.lastTotal = lastTotal;
    }

    public Path getUsersFile() {
        return usersFile;
    }

    public Path getBookingsFile() {
        return bookingsFile;
    }

    public String[][] getMovies() {
        return movies;
    }
}