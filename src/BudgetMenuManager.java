import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BudgetMenuManager extends JFrame implements ActionListener {

    private JFrame mainFrame = new JFrame();
    private JPanel mainPanel = new JPanel(new FlowLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("Images/Lots-Money.jpg");
            Image image = img.getImage();

            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    };

    /**
     * Local variables for the main budget window
     */
    private JLabel budgetTitle = new JLabel("Budget Menu");
    private JLabel budgetPrompt = new JLabel("Please enter your budget:");
    public static JTextField budgetInput = new JTextField(10);
    private JButton submitBudget = new JButton("Submit your budget");
    private JLabel currentBalancePrompt = new JLabel("Please enter your current balance:");
    public static JTextField currentBalanceInput = new JTextField(10);
    private JButton submitCurrentBalance = new JButton("Submit your current balance");
    private ArrayList<Integer> days = new ArrayList<Integer>();
    public static JComboBox<Integer> daysSelection;
    private JButton submitDaysSelection = new JButton("Submit your days");
    private JButton returnMainMenu = new JButton("Return");

    /**
     * The budget window
     */
    public void init() {
        hideWindow();

        budgetTitle.setFont(new Font("Times New Roman", Font.BOLD, 40));
        budgetTitle.setForeground(Color.WHITE);

        budgetPrompt.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        budgetPrompt.setForeground(Color.WHITE);
        submitBudget.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        submitBudget.setPreferredSize(new Dimension(150, 30));

        currentBalancePrompt.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        currentBalancePrompt.setForeground(Color.WHITE);
        submitCurrentBalance.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        submitCurrentBalance.setPreferredSize(new Dimension(250, 30));

        JLabel daysPrompt = new JLabel("Please enter the number of days you want this budget for:");
        daysPrompt.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        daysPrompt.setForeground(Color.WHITE);

        for(int i = 1; i <= 31; i++) {
            days.add(i);
        }

        daysSelection = new JComboBox<Integer>(days.toArray(new Integer[0]));

        submitDaysSelection.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        submitDaysSelection.setPreferredSize(new Dimension(150,30));

        returnMainMenu.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        returnMainMenu.setPreferredSize(new Dimension(600, 200));

        mainPanel.add(budgetTitle);
        mainPanel.add(Box.createHorizontalStrut(2000));
        mainPanel.add(budgetPrompt);
        mainPanel.add(budgetInput);
        mainPanel.add(submitBudget);
        mainPanel.add(Box.createHorizontalStrut(2000));
        mainPanel.add(currentBalancePrompt);
        mainPanel.add(currentBalanceInput);
        mainPanel.add(submitCurrentBalance);
        mainPanel.add(Box.createHorizontalStrut(2000));
        mainPanel.add(daysPrompt);
        mainPanel.add(daysSelection);
        mainPanel.add(submitDaysSelection);
        mainPanel.add(Box.createHorizontalStrut(1000));
        mainPanel.add(returnMainMenu);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setTitle("Budgeting Manager Menu");
        mainFrame.setResizable(false);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setUndecorated(false);
        mainFrame.setLocationRelativeTo(null);

        submitBudget.addActionListener(this);
        submitCurrentBalance.addActionListener(this);
        submitDaysSelection.addActionListener(this);
        returnMainMenu.addActionListener(this);
    }

    public void showWindow() {
        mainFrame.setVisible(true);
    }

    public void hideWindow() {
        mainFrame.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object choice = e.getSource();
         if (choice == submitBudget) {
            if(budgetInput.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a budget amount", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String budgetAmount = budgetInput.getText();
                budgetInput.setText("");
                BudgetProgramDatabase.saveToUserInfo(budgetAmount);  
            }
        }
        else if (choice == submitCurrentBalance) {
            if(currentBalanceInput.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter your balance", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String balanceAmount = currentBalanceInput.getText();
                currentBalanceInput.setText("");
                BudgetProgramDatabase.saveToUserInfo(balanceAmount);
            }
        }
        else if (choice == submitDaysSelection){
            String daysAmount = daysSelection.getSelectedItem().toString();
            daysSelection.setSelectedIndex(0);
            BudgetProgramDatabase.saveToUserInfo(daysAmount);
        }
        else {
            hideWindow();
            GUIManager.getInstance().mainMenu.showWindow();
        }
    }
}
