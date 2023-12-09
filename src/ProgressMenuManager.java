import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class ProgressMenuManager extends JFrame implements ActionListener {

    private JFrame frame = new JFrame();
    private JPanel panel = new JPanel(new FlowLayout());

    private static ArrayList<String> dataFromDatabase;
    private static DefaultTableModel tableModel;

    private JTable table;
    private JScrollPane scrollPane;
    private JButton returnMainMenu = new JButton("Return");

    /**
     * Populating the DefaultTableModel
     * @return 
     */
    public static DefaultTableModel progressTableData() {
        dataFromDatabase = BudgetProgramDatabase.readData();
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableModel.addColumn("Budget amount");
        tableModel.addColumn("Current balance");
        tableModel.addColumn("Days for budget");
        tableModel.addColumn("Money spent");
        tableModel.addColumn("Item name");
        tableModel.addColumn("Item type");

        for (int i = 0; i < dataFromDatabase.size(); i += 6) {
            String[] rowData = new String[6];
            for (int j = 0; j < 6; j++) {
                if (i + j < dataFromDatabase.size()) {
                    rowData[j] = dataFromDatabase.get(i + j);
                } else {
                    rowData[j] = "";
                }
            }
            tableModel.addRow(rowData);
        }
        return tableModel;
    }

    /**
     * Progress menu window
     */
    public void init() {
        DefaultTableModel tableModel = progressTableData();

        table = new JTable(tableModel) {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                java.awt.Point p = e.getPoint();
                int row = rowAtPoint(p);
                int col = columnAtPoint(p);

                try {
                    tip = getValueAt(row, col).toString();
                } catch(RuntimeException e1) {
                    e1.printStackTrace();
                }
                return tip;
            }
        };

        JTableHeader header = table.getTableHeader();
        ToolTipManager.sharedInstance().registerComponent(header);
        header.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int column = header.columnAtPoint(e.getPoint());
                header.setToolTipText(table.getColumnName(column));
            }
        });

        table.setCellSelectionEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        scrollPane = new JScrollPane(table);

        tableModel.addColumn("Amount left to hit the budget");
        for (int i = 0; i < tableModel.getRowCount(); i++) {

            String currentBalance = table.getModel().getValueAt(i, 1).toString();
            String moneySpent = table.getModel().getValueAt(i, 3).toString();
            String budgetAmount = table.getModel().getValueAt(i, 0).toString();

            if (currentBalance == null || moneySpent == null || budgetAmount == null || currentBalance.isEmpty() || moneySpent.isEmpty() || budgetAmount.isEmpty()) {
                tableModel.setValueAt(0, i, tableModel.getColumnCount() - 1);
            } else {
                double result = Double.parseDouble(currentBalance) - Double.parseDouble(moneySpent);

                double amountLeftForBudget = result - Double.parseDouble(budgetAmount);

                tableModel.setValueAt(String.format("%.2f", amountLeftForBudget), i, tableModel.getColumnCount() - 1);
            }
        }

        returnMainMenu.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        returnMainMenu.setPreferredSize(new Dimension(600, 200));

        panel.add(returnMainMenu);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setTitle("Progress Manager Menu");
        frame.setResizable(false);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);

        returnMainMenu.addActionListener(this);
    }

    public void showWindow() {
        frame.setVisible(true);
    }

    public void hideWindow() {
        frame.setVisible(false);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        Object choice = e.getSource();
        if (choice == returnMainMenu) {
            hideWindow();
            GUIManager.getInstance().mainMenu.showWindow();
        }
    }
}
