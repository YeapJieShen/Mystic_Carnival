/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Package1;

import java.util.ArrayList;

/**
 *
 * @author Jie Shen
 */

public class Account {

    private String username;
    private String password;

//    Constructors
    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

//    Setters
    public void setPassword(String password) {
        this.password = password;
    }

//    Getters
    public String getUsername() {
        return this.username;
    }

// Static Functionalities
    public static boolean verifyAccount(Account account, String password) {
        return account.password.equals(password);
    }

    public static boolean checkDuplicateUsername(ArrayList<Account> accounts, String newUsername) {
        for (Account account : accounts) {
            if (account.username.equals(newUsername)) {
                return true;
            }
        }
        return false;
    }

    public static boolean validPasswordFormat(String password) {
        return password.matches(
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        // ^ start of string, (?=.) are positive lookaheads : >=1(lowercase uppercase digit sp char), allowed characters, >=8 characters, $ end of string 
    }

    @Override
    public String toString() {
        return "Username: " + this.username
                + "\nPassword: " + this.password;
    }
}
