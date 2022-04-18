package com.techelevator.view;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class InventoryTest extends TestCase {
    private Inventory invTest = new Inventory();

@Test
    public void testFillInventory() {
    File invFile = new File("C:\\Users\\Alex\\Desktop\\Projects\\repos\\module-1-capstone\\module-1-capstone\\capstone\\vendingmachine.csv");
    try {
        ArrayList<Snack> snacksTest = invTest.fillInventory();
        BufferedReader reader = new BufferedReader(new FileReader(invFile));
        String line = null;
        int index = 0;
       while ((line = reader.readLine()) != null){
           Snack snackTest = snacksTest.get(index);
           assertEquals(snackTest.code,line.split("\\|")[0]);
           assertEquals(snackTest.name,line.split("\\|")[1]);
           assertEquals(snackTest.price,Double.parseDouble(line.split("\\|")[2]));
           assertEquals(snackTest.type,line.split("\\|")[3]);
           assertEquals(snackTest.stock, 5);
           index++;
        }
    }
    catch(IOException e){
        System.out.println("Test failed, inventory file not found");
        assert false;
    }
    }

    public void testAddMoney() {
    assertEquals(invTest.addMoney("5"), 5.0);
    }

    public void testPurchaseMethod() {
    Snack snack = new Snack();
    snack.price = 1.50;
    snack.stock = 5;
    snack.name = "test snack";
    snack.type = "Chip";
    assertEquals(snack.stock-1, invTest.purchaseMethod(snack, 1.50).stock);
    assertEquals(snack.stock, invTest.purchaseMethod(snack, 0).stock);
    assertEquals(snack.stock-1, invTest.purchaseMethod(snack, 5.00).stock);
    }

    public void testWriteToLog() {
    invTest.writeToLog("Test String");
    }

    // test each method to make sure what it's returning is correct



}