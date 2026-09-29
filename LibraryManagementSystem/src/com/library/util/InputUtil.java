package com.library.util;

import java.util.Scanner;

public final class InputUtil {
    private InputUtil(){}
    public static int readInt(Scanner s,String msg){
        while(true){System.out.print(msg);try{return Integer.parseInt(s.nextLine().trim());}catch(NumberFormatException e){System.out.println("Enter a valid number.");}}
    }
    public static String readString(Scanner s,String msg){System.out.print(msg);return s.nextLine().trim();}
}
