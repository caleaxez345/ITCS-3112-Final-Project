import java.io.*;
import java.util.*;

public class BudgetProgramDatabase {

    /**
     * Reading user input to the txt file
     * @param userInfo
     */
    public static void saveToUserInfo(String userInfo) {
        File pathToUserInfo = new File("TextFiles/userInfo.txt");
        try (BufferedWriter info = new BufferedWriter(new FileWriter(pathToUserInfo, true))) {
            if(pathToUserInfo.length() > 0) {
                info.newLine();
            }
            info.write(userInfo);
            info.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reading the txt file, mainly used to populate tables
     * @return
     */
    public static ArrayList<String> readData() {
        ArrayList<String> list = new ArrayList<String>();
        try {
            BufferedReader readUserInfoFile = new BufferedReader(new FileReader("TextFiles/userInfo.txt"));
            String line = "";
            while ((line = readUserInfoFile.readLine()) != null) {
                list.add(line);
            }
            readUserInfoFile.close();
            return list;
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * If the user decides to make any edits or remove data from the table this method writes the new data to the txt file
     * @param data
     */
    public static void saveToFileFromTable(ArrayList<String> data) {
        File path = new File("TextFiles/userInfo.txt");
        try {
            if(path.exists()) {
                path.delete();
            }
            path.createNewFile();
            
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                for(String newData : data) {
                    writer.write(newData);
                    writer.newLine();
                }
                writer.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}