package com.techelevator;

import com.techelevator.view.Menu;
import com.techelevator.view.Inventory;
import com.techelevator.view.Snack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI extends Inventory {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };
	private static final String FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_FINISH_TRANSACTION = "Finish Transaction";
	private final String[] PURCHASE_MENU_OPTIONS = {FEED_MONEY, PURCHASE_MENU_SELECT_PRODUCT, PURCHASE_MENU_FINISH_TRANSACTION};
	private  String choice = new String();
	private String purchaseChoice = new String();
	Scanner usrInput = new Scanner(System.in);

	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() throws IOException {
		this.snackList = fillInventory();
		do {
			choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			//used switch statement to handle the menu options
			switch(choice){
				case MAIN_MENU_OPTION_DISPLAY_ITEMS:
					for(Snack snack: snackList) {
						System.out.println(snack.code + " | " + snack.name + " | " + snack.price + " | QTY: " + snack.stock);
					}
					break;
				case MAIN_MENU_OPTION_PURCHASE:
					while(choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
						purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
						if (purchaseChoice.equals(FEED_MONEY)) {
							System.out.println(System.lineSeparator() + "How much money would you like to add? Please enter a whole dollar amount");
							menu.addedMoney = addMoney(usrInput.nextLine());
							choice = MAIN_MENU_OPTION_PURCHASE;
						} else if (purchaseChoice.equals(PURCHASE_MENU_SELECT_PRODUCT)) {
							for(Snack snack: snackList) {
								System.out.println(snack.code + " | " + snack.name + " | " + snack.price + " | QTY: " + snack.stock);
							}

							System.out.println(System.lineSeparator() + "Please make a selection: ");
							String userSelection = usrInput.nextLine();
							int snackIndex = 0;
							boolean validSelection = false;
							Snack updatedSnack = new Snack();
							for (Snack snack: snackList){
								snackIndex++;
								if(snack.code.equals(userSelection)){
									validSelection = true;
									System.out.println(snack.name + " " + snack.price);
									 break;
								}
							}
							if(!validSelection){
								System.out.println("Please make a valid selection.");
							}
							else {
								if(snackList.get(snackIndex-1).stock > 0 && menu.addedMoney > snackList.get(snackIndex-1).price) {
									updatedSnack = purchaseMethod(snackList.get(snackIndex - 1), menu.addedMoney);
									snackList.set(snackIndex - 1, updatedSnack);
									menu.addedMoney -= updatedSnack.price;
								}
							}


						} else if (purchaseChoice.equals(PURCHASE_MENU_FINISH_TRANSACTION)) {
							int quarters = 0;
							int dimes = 0;
							int nickles = 0;
							double moneyBefore = menu.addedMoney;
							while (menu.addedMoney % 0.25 == 0 && menu.addedMoney > 0){
								menu.addedMoney -= 0.25;
								quarters ++;

							}
							while (menu.addedMoney % 0.10 == 0 && menu.addedMoney > 0){
								menu.addedMoney -= 0.10;
								dimes ++;
							}
							while (menu.addedMoney % 0.05 == 0 && menu.addedMoney > 0){
								menu.addedMoney -= 0.05;
								nickles ++;
							}
							System.out.println ("Change Back:");
							System.out.println ("Number of quarters: " + quarters);
							System.out.println ("Number of dimes: " + dimes);
							System.out.println ("Number of nickles: " + nickles);
							writeToLog("GIVE CHANGE: " + " $" + moneyBefore + " $" + menu.addedMoney );
							choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

						}

					}
					break;
				case MAIN_MENU_OPTION_EXIT:
					System.out.println("See ya later!");
					break;
				default:
					//Should never get here... Other error handling exists in other methods
					System.out.println("Please enter a valid choice.");
					break;
			}


		}while(!choice.equals(MAIN_MENU_OPTION_EXIT));
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		try {
			cli.run();
		}catch(IOException e){
			System.err.println("Unexpected error, please check your system, and try again!");
		}

	}
}
