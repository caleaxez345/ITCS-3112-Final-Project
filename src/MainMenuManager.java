import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class MainMenuManager extends JFrame implements ActionListener{

    private JLabel mainMenu = new JLabel("Innovative Budgeting", SwingConstants.CENTER);
    private JButton button = new JButton("Budget Manager Menu");
    private JButton button2 = new JButton("Spending Manager Menu");
    private JButton button3 = new JButton("Progress Manager Menu");
    private JButton button4 = new JButton("Exit");

    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel() {
        @Override
            protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("Images/Dollar.jpg");
            Image image = img.getImage();
                
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

    /**
     * The main menu window
     */
    public void init() {
        mainMenu.setFont(new Font("Times New Roman", Font.BOLD, 50));
        mainMenu.setForeground(Color.BLACK);
        button.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        button2.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        button3.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        button4.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        panel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));
        panel.setLayout(new GridLayout(5, 0));
        panel.add(mainMenu);
        panel.add(button);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setTitle("Innovative Budgeting");
        frame.setResizable(false);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.setLocationRelativeTo(null);
        
        button.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        button4.addActionListener(this);   

        showWindow();
    }

    public void showWindow() {
        frame.setVisible(true);
    }

    public void hideWindow() {
        frame.setVisible(false);
    }

    public void openBudgetMenu() {
        hideWindow();
        GUIManager.getInstance().budgetMenu.showWindow();
    }

    public void openSpendingMenu() {
        hideWindow();
        GUIManager.getInstance().spendingMenu.showWindow();
    }

    public void openProgressMenu() {
        hideWindow();
        GUIManager.getInstance().progressMenu.init();
        GUIManager.getInstance().progressMenu.showWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object choice = e.getSource();
        if(choice == button) {
            openBudgetMenu();
        }
        else if(choice == button2) {
            openSpendingMenu();
        }
        else if(choice == button3) {
            openProgressMenu();
        }
        else {
           System.exit(0);
        }
    }
}

