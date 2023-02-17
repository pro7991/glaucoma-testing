package com.glucoma.utils;

import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {
    public static void main(String[] args) {
        String fileName = "output.txt";
        String textToWrite = "Hello, world!";
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(textToWrite);
            writer.close();
            System.out.println("Successfully wrote to file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}