import javax.swing.SwingUtilities;

public class GUIManager{
    private static GUIManager instance = new GUIManager();
    public MainMenuManager mainMenu;
    public BudgetMenuManager budgetMenu;
    public SpendingMenuManager spendingMenu;
    public ProgressMenuManager progressMenu;

    private GUIManager(){
        mainMenu = new MainMenuManager();
        budgetMenu = new BudgetMenuManager();
        spendingMenu = new SpendingMenuManager();
        progressMenu = new ProgressMenuManager();
    }

    public static GUIManager getInstance() {
        if(instance == null) {
            instance = new GUIManager();
        }
        return instance;
    }

    public void init() {
        mainMenu.init();
        budgetMenu.init();
        spendingMenu.init();
        progressMenu.init();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> { 
            GUIManager guiManager = GUIManager.getInstance();
            guiManager.init();
        });
    }
}