package com.ChakreAditya;


import java.util.Scanner;

public class CheckPassword {

    Scanner sc =  new Scanner(System.in);

    public boolean EnterPass(){
        System.out.println();
        System.out.println("Enter Admin Password: ");

        String pass = sc.nextLine();
        return pass.equals("");
    }

}


