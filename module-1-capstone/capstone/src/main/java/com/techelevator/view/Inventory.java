package com.techelevator.view;

import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class Inventory {
    private String code;
    private String name;
    private String price;
    private String typeOfSnack;
    public List<String> snacks = new ArrayList<>();
    public ArrayList<Snack> snackList = new ArrayList<>();
    public NumberFormat moneyFormat = NumberFormat.getCurrencyInstance();
    public String newMoney = "";
    public double addedMoney;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTypeOfSnack() {
        return typeOfSnack;
    }

    public void setTypeOfSnack(String typeOfSnack) {
        this.typeOfSnack = typeOfSnack;
    }

    public Inventory(String code, String name, String price, String typeOfSnack) throws FileNotFoundException {
        this.code = code;
        this.name = name;
        this.price = price;
        this.typeOfSnack = typeOfSnack;
    }

    public Inventory() {

    }

    public ArrayList<Snack> fillInventory() throws IOException {
        File originalInventory = new File("C:\\Users\\Alex\\Desktop\\Projects\\repos\\module-1-capstone\\module-1-capstone\\capstone\\vendingmachine.csv");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(originalInventory));
            String line = null;
            //The section of code below parses each line in the BufferedReader and separates out each snacks' attributes
            while ((line = reader.readLine()) != null) {
                Snack addedSnack = new Snack();
                addedSnack.code = line.split("\\|")[0];
                addedSnack.name = line.split("\\|")[1];
                addedSnack.price = Double.parseDouble(line.split("\\|")[2]);
                addedSnack.type = line.split("\\|")[3];
                addedSnack.stock = 5;
                snackList.add(addedSnack);
            }


        } catch (IOException e) {
            System.out.println("File not found.");
        }
        //makeSnackList();

        return snackList;
    }

    public double addMoney(String moneyString) {
        if (moneyString == "") {
            return 0.0;
        } else {
            double money = Double.parseDouble(moneyString);
            writeToLog("FEED MONEY: " + moneyString);
            return money;
        }
    }

    // pass the "menu" object to this method to keep
//track of how much money you have because it's stored in a different
    //object than this class.
    public Snack purchaseMethod(Snack snack, double addedMoneyIn) {
        if(addedMoneyIn >= snack.price) {
            if (snack.stock > 0) {
                switch (snack.type) {
                    case "Chip":
                        System.out.println("Crunch Crunch, Yum!");
                        break;
                    case "Candy":
                        System.out.println("Munch Munch, Yum!");
                        break;
                    case "Drink":
                        System.out.println("Glug Glug, Yum!");
                        break;
                    case "Gum":
                        System.out.println("Chew Chew, Yum!");
                    default:
                        //Code should never reach here
                        break;
                }
                writeToLog(snack.name + " " + snack.code + " $" + addedMoney + " $" + (addedMoney - snack.price));
                snack.stock--;
            } else {
                System.out.println("Sold Out, Sorry!");
            }
        }else{
            System.out.println("Not enough money!");
        }

        return snack;

    }

    public void writeToLog(String logStr) {
        //write text to the end of the log file
        File file = new File("log.txt");

        try(FileWriter audit = new FileWriter(file, true)) {
            if(!file.exists()){
                file.createNewFile();
            }
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            logStr = formatter.format(date) + " " + logStr;
            audit.write(logStr+"\n");

        } catch (IOException e) {
            System.err.println("Unexpected Error: " + e);
        }
    }
}
