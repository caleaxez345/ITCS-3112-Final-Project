import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class SpendingMenuManager extends JFrame implements ActionListener {
    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel(new FlowLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("Images/Lots-Money.jpg");
            Image image = img.getImage();

            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    };

    private JFrame inputSpendingDetailsFrame = new JFrame();
    private JPanel inputSpendingDetailsPanel = new JPanel(new FlowLayout()) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon img = new ImageIcon("Images/Lots-Money.jpg");
            Image image = img.getImage();

            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    };

    private JFrame editSpendingDetailsFrame = new JFrame();
    private JPanel editSpendingDetailsPanel = new JPanel(new FlowLayout());

    private JFrame removeSpendingDetailsFrame = new JFrame();
    private JPanel removeSpendingDetailsPanel = new JPanel(new FlowLayout());

    /**
     * Local variables for the spending main window
     */
    private JLabel spendingMenu = new JLabel("Spending Menu", SwingConstants.CENTER);
    private JButton inputSpendingDetails = new JButton("Enter items bought");
    private JButton editSpendingDetails = new JButton("Edit items bought");
    private JButton removeSpendingDetails = new JButton("Remove items bought");
    private JButton returnMainMenu = new JButton("Return");
    private ArrayList<String> itemCategory = new ArrayList<String>(Arrays.asList("Home & Utilities", "Transportation",
            "Groceries", "Health", "Restaurants", "Shopping & Entertainment", "Education", "Finance", "Other"));

    /**
     * Local variables for the input window
     */
    private JTextField inputMoneySpent = new JTextField(5);
    private JButton submitMoneySpent = new JButton("Submit");
    private JTextField inputItemName = new JTextField(5);
    private JButton submitItemName = new JButton("Submit");
    private JComboBox<String> itemCategorySelection;
    private JButton submitItemCategory = new JButton("Submit");
    private JButton returnSpendingMenu = new JButton("Return");

    /**
     * Local variables for the edit window
     */
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel editTableModel;
    private JButton returnSpendingMenuFromEdit = new JButton("Return");
    private ArrayList<String> newData;
    private JButton saveEdits = new JButton("Save");

    /**
     * Local variables for the remove window
     */
    private JTable tableTwo;
    private JScrollPane scrollPaneTwo;
    private DefaultTableModel removeTableModel;
    private JButton returnSpendingMenuFromRemove = new JButton("Return");
    private ArrayList<String> newDataTwo;
    private JButton remove = new JButton("Remove");

    /**
     * The spending main menu window
     */
    public void init() {
        hideWindow();

        inputSpendingDetailsWindow();
        hideInputSpendingDetailsWindow();

        editSpendingDetailsWindow();
        hideEditSpendingDetailsWindow();

        removeSpendingDetailsWindow();
        hideRemoveSpendingDetailsWindow();

        spendingMenu.setFont(new Font("Times New Roman", Font.BOLD, 40));
        spendingMenu.setForeground(Color.WHITE);

        inputSpendingDetails.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        inputSpendingDetails.setPreferredSize(new Dimension(600, 200));

        editSpendingDetails.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        editSpendingDetails.setPreferredSize(new Dimension(600, 200));

        removeSpendingDetails.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        removeSpendingDetails.setPreferredSize(new Dimension(600, 200));

        returnMainMenu.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        returnMainMenu.setPreferredSize(new Dimension(600, 200));

        panel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));
        panel.setLayout(new GridLayout(5, 0));
        panel.add(spendingMenu);
        panel.add(inputSpendingDetails);
        panel.add(editSpendingDetails);
        panel.add(removeSpendingDetails);
        panel.add(returnMainMenu);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setTitle("Spending Manager Menu");
        frame.setResizable(false);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        inputSpendingDetails.addActionListener(this);
        editSpendingDetails.addActionListener(this);
        removeSpendingDetails.addActionListener(this);
        returnMainMenu.addActionListener(this);
    }

    public void showWindow() {
        frame.setVisible(true);
    }

    public void hideWindow() {
        frame.setVisible(false);
    }

    public void showInputSpendingDetailsWindow() {
        inputSpendingDetailsFrame.setVisible(true);
    }

    public void hideInputSpendingDetailsWindow() {
        inputSpendingDetailsFrame.setVisible(false);
    }

    public void showEditSpendingDetailsWindow() {
        editSpendingDetailsFrame.setVisible(true);
    }

    public void hideEditSpendingDetailsWindow() {
        editSpendingDetailsFrame.setVisible(false);
    }

    public void showRemoveSpendingDetailsWindow() {
        removeSpendingDetailsFrame.setVisible(true);
    }

    public void hideRemoveSpendingDetailsWindow() {
        removeSpendingDetailsFrame.setVisible(false);
    }

    /**
     * Using this method to populate the DefaultTableModel
     * @return 
     */
    public static DefaultTableModel createTableData() {
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        ArrayList<String> dataFromDatabase = BudgetProgramDatabase.readData();

        defaultTableModel.addColumn("Budget amount");
        defaultTableModel.addColumn("Current balance");
        defaultTableModel.addColumn("Days for budget");
        defaultTableModel.addColumn("Money spent");
        defaultTableModel.addColumn("Item name");
        defaultTableModel.addColumn("Item type");

        for (int i = 0; i < dataFromDatabase.size(); i += 6) {
            String[] rowData = new String[6];
            for (int j = 0; j < 6; j++) {
                if (i + j < dataFromDatabase.size()) {
                    rowData[j] = dataFromDatabase.get(i + j);
                } else {
                    rowData[j] = "";
                }
            }
            defaultTableModel.addRow(rowData);
        }
        return defaultTableModel;
    }

    /**
     * Window to input spending details
     */
    public void inputSpendingDetailsWindow() {
        JLabel spendingDetailsTitle = new JLabel("Spending Details", SwingConstants.CENTER);
        spendingDetailsTitle.setFont(new Font("Times New Roman", Font.BOLD, 50));
        spendingDetailsTitle.setForeground(Color.WHITE);

        JLabel spentMoney = new JLabel("Please enter how much money you've spent so far:");
        spentMoney.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        spentMoney.setForeground(Color.WHITE);

        submitMoneySpent.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        submitMoneySpent.setPreferredSize(new Dimension(100, 20));

        JLabel itemName = new JLabel("Please enter the name of the item that you bought");
        itemName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        itemName.setForeground(Color.WHITE);

        submitItemName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        submitItemName.setPreferredSize(new Dimension(100, 20));

        JLabel selectItemPrompt = new JLabel("Please select the type of item purchased:");
        selectItemPrompt.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        selectItemPrompt.setForeground((Color.WHITE));
        itemCategorySelection = new JComboBox<String>(itemCategory.toArray(new String[0]));
        submitItemCategory.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        submitItemCategory.setForeground(Color.BLACK);

        returnSpendingMenu.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        returnSpendingMenu.setPreferredSize(new Dimension(200, 100));
        returnSpendingMenu.setForeground(Color.BLACK);

        inputSpendingDetailsPanel.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));
        inputSpendingDetailsPanel.add(spendingDetailsTitle);
        inputSpendingDetailsPanel.add(Box.createRigidArea(new Dimension(2000, 30)));
        inputSpendingDetailsPanel.add(spentMoney);
        inputSpendingDetailsPanel.add(inputMoneySpent);
        inputSpendingDetailsPanel.add(submitMoneySpent);
        inputSpendingDetailsPanel.add(Box.createRigidArea(new Dimension(1000, 30)));
        inputSpendingDetailsPanel.add(itemName);
        inputSpendingDetailsPanel.add(inputItemName);
        inputSpendingDetailsPanel.add(submitItemName);
        inputSpendingDetailsPanel.add(Box.createRigidArea(new Dimension(1500, 30)));
        inputSpendingDetailsPanel.add(selectItemPrompt);
        inputSpendingDetailsPanel.add(itemCategorySelection);
        inputSpendingDetailsPanel.add(submitItemCategory);
        inputSpendingDetailsPanel.add(Box.createRigidArea(new Dimension(1500, 150)));
        inputSpendingDetailsPanel.add(returnSpendingMenu);

        inputSpendingDetailsFrame.add(inputSpendingDetailsPanel, BorderLayout.CENTER);
        inputSpendingDetailsFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        inputSpendingDetailsFrame.setTitle("Spending Details");
        inputSpendingDetailsFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        inputSpendingDetailsFrame.setResizable(false);
        inputSpendingDetailsFrame.setLocationRelativeTo(null);

        submitMoneySpent.addActionListener(this);
        submitItemName.addActionListener(this);
        submitItemCategory.addActionListener(this);
        returnSpendingMenu.addActionListener(this);
    }


    /**
     * Allow the user to edit their information entered
     */
    public void editSpendingDetailsWindow() {
        editTableModel = createTableData();
        table = new JTable(editTableModel) {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int row = rowAtPoint(p);
                int col = columnAtPoint(p);

                try {
                    tip = getValueAt(row, col).toString();
                } catch (RuntimeException e1) {
                    e1.printStackTrace();
                }

                return tip;
            }
        };

        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        JTableHeader header = table.getTableHeader();
        ToolTipManager.sharedInstance().registerComponent(header);
        header.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int column = header.columnAtPoint(e.getPoint());
                header.setToolTipText(table.getColumnName(column));
            }
        });

        scrollPane = new JScrollPane(table);
        scrollPane.revalidate();
        scrollPane.repaint();

        returnSpendingMenuFromEdit.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        returnSpendingMenuFromEdit.setPreferredSize(new Dimension(300, 100));
        returnSpendingMenuFromEdit.setForeground(Color.BLACK);

        saveEdits.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        saveEdits.setPreferredSize(new Dimension(300, 100));
        saveEdits.setForeground(Color.BLACK);

        editSpendingDetailsPanel.add(returnSpendingMenuFromEdit);
        editSpendingDetailsPanel.add(saveEdits);

        editSpendingDetailsFrame.add(scrollPane, BorderLayout.CENTER);
        editSpendingDetailsFrame.add(editSpendingDetailsPanel, BorderLayout.SOUTH);
        editSpendingDetailsFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        editSpendingDetailsFrame.setTitle("Edit Spending Details");
        editSpendingDetailsFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        editSpendingDetailsFrame.setResizable(false);
        editSpendingDetailsFrame.setLocationRelativeTo(null);

        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent event) {
                if (event.getType() == TableModelEvent.UPDATE) {
                    int row = editTableModel.getRowCount();
                    int col = editTableModel.getColumnCount();
                    newData = new ArrayList<String>();
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < col; j++) {
                            Object info = editTableModel.getValueAt(i, j);
                            newData.add(info.toString());
                        }
                    }
                }
            }
        });

        returnSpendingMenuFromEdit.addActionListener(this);
        saveEdits.addActionListener(this);
    }

    /**
     * Allows the user to delete any desired row
     */
    public void removeSpendingDetailsWindow() {
        removeTableModel = createTableData();
        tableTwo = new JTable(removeTableModel) {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int row = rowAtPoint(p);
                int col = columnAtPoint(p);

                try {
                    tip = getValueAt(row, col).toString();
                } catch (RuntimeException e1) {
                    e1.printStackTrace();
                }
                return tip;
            }
        };

        tableTwo.getTableHeader().setReorderingAllowed(false);
        tableTwo.getTableHeader().setResizingAllowed(false);
        tableTwo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        JTableHeader header = tableTwo.getTableHeader();
        ToolTipManager.sharedInstance().registerComponent(header);
        header.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int column = header.columnAtPoint(e.getPoint());
                header.setToolTipText(tableTwo.getColumnName(column));
            }
        });

        scrollPaneTwo = new JScrollPane(tableTwo);
        scrollPaneTwo.revalidate();
        scrollPaneTwo.repaint();

        returnSpendingMenuFromRemove.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        returnSpendingMenuFromRemove.setPreferredSize(new Dimension(300, 100));
        returnSpendingMenuFromRemove.setForeground(Color.BLACK);

        remove.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        remove.setPreferredSize(new Dimension(300, 100));
        remove.setForeground(Color.BLACK);

        removeSpendingDetailsPanel.add(returnSpendingMenuFromRemove);
        removeSpendingDetailsPanel.add(remove);

        removeSpendingDetailsFrame.add(scrollPaneTwo, BorderLayout.CENTER);
        removeSpendingDetailsFrame.add(removeSpendingDetailsPanel, BorderLayout.SOUTH);
        removeSpendingDetailsFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        removeSpendingDetailsFrame.setTitle("Remove Spending Details");
        removeSpendingDetailsFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        removeSpendingDetailsFrame.setResizable(false);
        removeSpendingDetailsFrame.setLocationRelativeTo(null);

        returnSpendingMenuFromRemove.addActionListener(this);
        remove.addActionListener(this);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tableTwo.getSelectedRow() != -1) {
                    removeTableModel.removeRow(tableTwo.getSelectedRow());
                    JOptionPane.showMessageDialog(null, "This row of data has been deleted successfully!", "Deletion",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                int row = removeTableModel.getRowCount();
                int col = removeTableModel.getColumnCount();
                newDataTwo = new ArrayList<String>();

                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < col; j++) {
                        Object info = removeTableModel.getValueAt(i, j);
                        newDataTwo.add(info.toString());
                    }
                }
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object choice = e.getSource();
        if (choice == inputSpendingDetails) {
            hideWindow();
            showInputSpendingDetailsWindow();
        } else if (choice == submitMoneySpent) {
            if (inputMoneySpent.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter the amount spent", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String moneySpent = inputMoneySpent.getText();
                inputMoneySpent.setText("");
                BudgetProgramDatabase.saveToUserInfo(moneySpent);
            }
        } else if (choice == submitItemName) {
            if (inputItemName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter item name", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String itemName = inputItemName.getText();
                inputItemName.setText("");
                BudgetProgramDatabase.saveToUserInfo(itemName);
            }
        } else if (choice == submitItemCategory) {
            String itemCategory = itemCategorySelection.getSelectedItem().toString();
            itemCategorySelection.setSelectedIndex(0);
            BudgetProgramDatabase.saveToUserInfo(itemCategory);
        } else if (choice == returnSpendingMenu) {
            hideInputSpendingDetailsWindow();
            showWindow();
        } else if (choice == editSpendingDetails) {
            hideWindow();
            editSpendingDetailsWindow();
            showEditSpendingDetailsWindow();
        } else if (choice == saveEdits) {
            if (newData == null) {
                JOptionPane.showMessageDialog(null, "Error", "Please enter data or select a cell", JOptionPane.ERROR_MESSAGE);
            } else {
                BudgetProgramDatabase.saveToFileFromTable(newData);
            }
        } else if (choice == returnSpendingMenuFromEdit) {
            hideEditSpendingDetailsWindow();
            showWindow();
        } else if (choice == removeSpendingDetails) {
            hideWindow();
            removeSpendingDetailsWindow();
            showRemoveSpendingDetailsWindow();
        } else if (choice == remove) {
            BudgetProgramDatabase.saveToFileFromTable(newDataTwo);
        } else if (choice == returnSpendingMenuFromRemove) {
            hideRemoveSpendingDetailsWindow();
            showWindow();
        } else {
            hideWindow();
            GUIManager.getInstance().mainMenu.showWindow();
        }
    }
}