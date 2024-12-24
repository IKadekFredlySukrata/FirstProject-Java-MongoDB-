package FinalManagement.Main;

import javax.swing.JOptionPane;

import FinalManagement.View.LogInPage;

import static FinalManagement.View.LogInPage.frame;

public class Main {
    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Kelompok 3 \n" + 
                            "I Kadek Fredly Sukrata \t\t(23051204107)\r\n" + //
                            "Imaniya Zulfa M \t\t\t(23051204113)\r\n" + //
                            "Max Pieter Henry Lelepory \t(23051204121)\r\n" + //
                            "Dhimas Ananda R P \t\t(23051204129)\r\n" + //
                            "Yobby Novialdi \t\t\t(23051204143)");

        LogInPage logInPage = new LogInPage();
        logInPage.showFrame();
    }
}
