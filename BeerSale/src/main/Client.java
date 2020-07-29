package main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import common.SystemConstant;
import dao.BeerDAO;
import dao.ReceiptDAO;
import dao.ReceiptDetailDAO;
import dao.UserAccountDAO;
import dao.UserDAO;
import dao.interfaces.IBeerDAO;
import dao.interfaces.IReceiptDAO;
import dao.interfaces.IReceiptDetailDAO;
import dao.interfaces.IUserAccountDAO;
import dao.interfaces.IUserDAO;
import dto.BeerDTO;
import dto.ReceiptDTO;
import dto.ReceiptDetailDTO;

public class Client {

	private Scanner sc = new Scanner(System.in);
	
	private boolean checkSelectOther = false;
	private boolean checkBuyMore = false;
	
	private ReceiptDTO receipt;
	
	private IUserAccountDAO userAccountDAO;
	private IBeerDAO beerDAO;
	private IReceiptDAO receiptDAO;
	private IReceiptDetailDAO receiptDetailDAO;
	private IUserDAO userDAO;
	private RunMain runMain;
	
	public Client() {
		userAccountDAO = new UserAccountDAO();
		beerDAO = new BeerDAO();
		receiptDAO = new ReceiptDAO();
		receiptDetailDAO = new ReceiptDetailDAO();
		userDAO = new UserDAO();
		runMain = new RunMain();
	}

	public void clientInterface() {
		while (true) {
			try {
				System.out.println("================== Client ================");
				System.out.println("Select place want to come");
				System.out.println("1. Profile");
				System.out.println("2. Buy beer");
				System.out.println("3. Log out");
				System.out.println("4. Exit");
				int answer = 0;
				answer = runMain.handleInputNumberException(answer,SystemConstant.ANSWER);
				switch (answer) {
				case 1:
					profile();
					break;
				case 2:
					buyBeer();
					break;
				case 3:
					RunMain.userAccountDTO = null;
					RunMain.userDTO = null;
					RunMain.checkLogout = true;
					return;
				case 4:
					RunMain.checkExit = true;
					break;
				default:
					throw new Exception(SystemConstant.WRONG_ANSWER);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if (RunMain.checkLogout || RunMain.checkExit)
				return;

		}
	}

	private void profile() {
		while (true) {
			try {
				System.out.println("================== Profile ================");
				System.out.println("1. Show profile");
				System.out.println("2. Edit profile");
				System.out.println("3. Charge money");
				System.out.println("4. Back client");
				System.out.println("5. Exit");
				int answer = 0;
				answer = runMain.handleInputNumberException(answer,SystemConstant.ANSWER);
				switch (answer) {
				case 1:
					showProfile();
					break;
				case 2:
					editProfile();
					break;
				case 3:
					chargeMoney();
					break;
				case 4:
					return;
				case 5:
					RunMain.checkExit = true;
					break;
				default:
					throw new Exception(SystemConstant.WRONG_ANSWER);
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if (RunMain.checkExit)
				break;
		}
	}

	private void chargeMoney() {
		while (true) {
			int moneyCharge = 0;
			moneyCharge = runMain.handleInputNumberException(moneyCharge,"Input money charge: ");
			if (moneyCharge < SystemConstant.MIN_ADD_MONEY) {
				System.out.println("Your extra money not equal min (20000)");
				continue;
			} else {
				RunMain.userAccountDTO.setMoney(RunMain.userAccountDTO.getMoney() + moneyCharge);
				int result = userAccountDAO.update(RunMain.userAccountDTO);
				runMain.checkSuccess(result, "Charge successfully", "Charge unsuccessfully");
				return;
			}
		}
	}

	private void showProfile() {
		System.out.println(RunMain.userDTO.toString());
		System.out.println("Money: " + RunMain.userAccountDTO.getMoney());
	}

	private void editProfile() {
		System.out.println("================== Edit profile ======================");
		System.out.println("Name: " + RunMain.userDTO.getName() + " -> ");
		String name = sc.nextLine();
		System.out.println("Email: " + RunMain.userDTO.getEmail() + " -> ");
		String email = sc.nextLine();
		int phoneNumber = 0;
		phoneNumber = runMain.handleInputNumberException(phoneNumber,"Phone number: " + RunMain.userDTO.getPhoneNumber() + " -> ");
		RunMain.userDTO.setName(name);
		RunMain.userDTO.setEmail(email);
		RunMain.userDTO.setPhoneNumber(phoneNumber);
		int result = userDAO.update(RunMain.userDTO);
		runMain.checkSuccess(result, "Update successfully", "Update unsuccessfully");
	}

	private void buyBeer() {
		checkMoneyMin();
		while (true) {
			if (checkSelectOther) {
				checkSelectOther = false;
				System.out.println("Do you want to show menu ? (y or n)");
				String answer = sc.nextLine();
				if (answer.equals("y")) {
					showMenu();
				} else if (answer.equals("n")) {

				} else {
					System.out.println(SystemConstant.WRONG_ANSWER);
				}
			} else {
				showMenu();
			}

			selectBeer();
//			
			if (!checkBuyMore && receipt != null) {
				RunMain.lstReceiptDetail = receiptDetailDAO.findByReceiptId(receipt.getId());
				exportReceipt();
				RunMain.checkExit = true;
				return;
			}
		}
	}

	private void showMenu() {
		System.out.println("==================Menu===================");
		List<BeerDTO> lstBeer = new ArrayList<>();
		lstBeer = beerDAO.findAll();
		System.out.printf("%-5s %-25s %-25s %-25s %-25s %-25s \n","","Name","Origin","Capacity","Count","Cost");
		for (int i = 0; i < lstBeer.size(); i++) {
			System.out.printf("%-5s %-25s %-25s %-25s %-25s %-25s \n",i+1,lstBeer.get(i).getName(),lstBeer.get(i).getOrigin(),lstBeer.get(i).getCapacity(),lstBeer.get(i).getCount(),lstBeer.get(i).getCost());
		}
	}
	
	private void selectBeer() {
		while (true) {
			if (checkSelectOther)
				return;
			System.out.print("\nInput name: ");
			String name = sc.nextLine();
			BeerDTO selectBeer = searchBeers(name);
			if (selectBeer == null) {
				System.out.println("Your select is not exist!!");
//				
				continue;
			}
			if (selectBeer.getCount() == 0) {
				System.out.println("This beer is sold out! Please select other one\n");
				return;
			}
			if (RunMain.userAccountDTO.getMoney() < selectBeer.getCost()) {
				while (true) {
					System.out.println("Your money not enough!");
					System.out.println("Do you want to select other beer? (y or n)");
					String answer = sc.nextLine();
					if (answer.equals("y")) {
						checkSelectOther = true;
						break;
					} else if (answer.equals("n")) {
						addMoneyAndBuy(selectBeer);
						if (!checkSelectOther)
							checkBuyMore();
						if (!checkBuyMore)
							return;
						break;
					} else {
						System.out.println(SystemConstant.WRONG_ANSWER);
					}
				}
			} else {
				addMoneyAndBuy(selectBeer);
				if (!checkSelectOther)
					checkBuyMore();
				break;
			}
		}
	}

	private BeerDTO searchBeers(String name) {
		BeerDTO beer = new BeerDTO();
		List<BeerDTO> lstBeer = beerDAO.findAllByName(name);
		if (lstBeer.isEmpty())
			return null;
		System.out.println("Which beer do you want to select: ");
		System.out.printf("%-5s %-25s %-25s %-25s %-25s %-25s \n","","Name","Origin","Capacity","Count","Cost");
		for (int i = 0; i < lstBeer.size(); i++) {
			System.out.printf("%-5s %-25s %-25s %-25s %-25s %-25s \n",i+1,lstBeer.get(i).getName(),lstBeer.get(i).getOrigin(),lstBeer.get(i).getCapacity(),lstBeer.get(i).getCount(),lstBeer.get(i).getCost());
		}
		while (true) {
			int answer = 0;
			answer = runMain.handleInputNumberException(answer,"Select number: ");
			if (answer < 1 || answer > lstBeer.size() + 1) {
				System.out.println(SystemConstant.WRONG_ANSWER);
			} else {
				beer = lstBeer.get(answer - 1);
				break;
			}
		}
		return beer;
	}

	private void checkBuyMore() {
		while (true) {
			System.out.println("Do you want to buy more? (y or n)");
			String answer = sc.nextLine();
			if (answer.equals("y")) {
				checkBuyMore = true;
				break;
			} else if (answer.equals("n")) {
				checkBuyMore = false;
				break;
			} else {
				System.out.println(SystemConstant.WRONG_ANSWER);
			}
		}
	}

	private void addMoneyAndBuy(BeerDTO selectBeer) {
		int countBuy = 1;

		checkMoneyAndBeerCost(selectBeer, countBuy);
		if (RunMain.userAccountDTO.getMoney() < selectBeer.getCost() * countBuy)
			return;

		while (true) {
			int count = 0;
			count = runMain.handleInputNumberException(count,"How many you want to buy: ");
			if (count > selectBeer.getCount()) {
				System.out.println(selectBeer.getName() + " just have " + selectBeer.getCount());
				System.out.println("Do you want to buy other beer? (y or n)");
				String answer = sc.nextLine();
				if (answer.equals("y")) {
					checkSelectOther = true;
					return;
				} else if (answer.equals("n")) {

				} else {
					System.out.println(SystemConstant.WRONG_ANSWER);
				}
			} else {
				countBuy = count;
				checkMoneyAndBeerCost(selectBeer, countBuy);
				if (RunMain.userAccountDTO.getMoney() < selectBeer.getCost() * countBuy)
					return;
				RunMain.userAccountDTO.setMoney(RunMain.userAccountDTO.getMoney() - selectBeer.getCost() * countBuy);
				int result = userAccountDAO.update(RunMain.userAccountDTO);
				runMain.checkSuccess(result, "Buy successfully", "Buy unsuccessfully");
				break;
			}
		}

		if (receipt == null) {
			receipt = new ReceiptDTO(RunMain.userDTO.getId(), 0, 0);
			receipt.setRegId(RunMain.userDTO.getId());
			receipt.setUpdtId(RunMain.userDTO.getId());
			receiptDAO.insert(receipt);
		}
		List<ReceiptDTO> lst = receiptDAO.findAll();
		receipt = lst.get(lst.size() - 1);

		selectBeer.setCount(selectBeer.getCount() - countBuy);
		beerDAO.update(selectBeer);
		ReceiptDetailDTO receiptDetail = receiptDetailDAO.findByReceiptIdAndBeerId(receipt.getId(), selectBeer.getId());
		if (receiptDetail != null) {
			receiptDetail.setCount(receiptDetail.getCount() + countBuy);
			receiptDetail.setCost(receiptDetail.getCount() * selectBeer.getCost());
			receiptDetailDAO.update(receiptDetail);
		} else {
			receiptDetail = new ReceiptDetailDTO(RunMain.userDTO.getId(), selectBeer.getId(), receipt.getId(), countBuy,
					countBuy * selectBeer.getCost());
			receiptDetailDAO.insert(receiptDetail);
		}
		receipt.setTotal(receiptDetailDAO.totalByReceiptId(receipt.getId()));
		receiptDAO.update(receipt);
	}

	private void checkMoneyMin() {
		while (true) {
			if (RunMain.userAccountDTO.getMoney() < runMain.MIN_COST) {
				System.out.println("Current your money: " + RunMain.userAccountDTO.getMoney());
				System.out.println("Your money not enough! Do you want to add your money (min = 20000) ? (y or n)");
				String answer = sc.nextLine();
				if (answer.equals("y")) {
					System.out.print("You want to add: ");
					int moneyAdd = 0;
					moneyAdd = runMain.handleInputNumberException(moneyAdd,"You want to add: ");
					if (moneyAdd < SystemConstant.MIN_ADD_MONEY) {
						System.out.println("Your extra money not equal min (20000) ");
//						
					} else {
						RunMain.userAccountDTO.setMoney(RunMain.userAccountDTO.getMoney() + moneyAdd);
						int result = userAccountDAO.update(RunMain.userAccountDTO);
						runMain.checkSuccess(result, "Add money successfully", "Add money unsuccessfully");
						if (result != 0) {
							RunMain.userAccountDTO = userAccountDAO.findOneById(RunMain.userAccountDTO.getId());
						}
					}
				} else if (answer.equals("n")) {
					while (true) {
						System.out.println("Do you want to log out? ( y or n )");
						String answer2 = sc.nextLine();
						if (answer2.equals("y")) {
							RunMain.userAccountDTO = null;
							RunMain.userDTO = null;
							RunMain.checkLogout = true;
							return;
						} else if (answer2.equals("n")) {
							RunMain.checkExit = true;
							return;
						} else {
							System.out.println(SystemConstant.WRONG_ANSWER);
						}
					}
				} else {
					System.out.println(SystemConstant.WRONG_ANSWER);
				}
			}
			return;
		}
	}

	private void checkMoneyAndBeerCost(BeerDTO selectBeer, int countBuy) {
		while (RunMain.userAccountDTO.getMoney() < selectBeer.getCost() * countBuy) {
			System.out.println("Total : " + selectBeer.getCost() * countBuy);
			System.out.println("Your current money: " + RunMain.userAccountDTO.getMoney());
			System.out.println("Your money not enough money!!");
			System.out.println("Do you want to add money? (y or n)");
			String answer = sc.nextLine();
			if (answer.equals("y")) {
				int moneyAdd = 0;
				moneyAdd = runMain.handleInputNumberException(moneyAdd,"Please add more money\nYou want to add: ");
				if (moneyAdd < SystemConstant.MIN_ADD_MONEY) {
					System.out.println("Your extra money not equal min (20000) ");
					continue;
				}
				RunMain.userAccountDTO.setMoney(RunMain.userAccountDTO.getMoney() + moneyAdd);
				int result = userAccountDAO.update(RunMain.userAccountDTO);
				runMain.checkSuccess(result, "Add money successfully", "Add money unsuccessfully");
				if (result != 0) {
					RunMain.userAccountDTO = userAccountDAO.findOneById(RunMain.userAccountDTO.getId());
					System.out.println("Current your money : " + RunMain.userAccountDTO.getMoney());
				}
				if (RunMain.userAccountDTO.getMoney() < selectBeer.getCost() * countBuy)
					continue;
			} else if (answer.equals("n")) {
				return;
			} else {
				System.out.println(SystemConstant.WRONG_ANSWER);
			}

		}
	}

	private void exportReceipt() {
		try {
			StringBuffer buffer = new StringBuffer();
			List<String> lstBeerName = receiptDetailDAO.findBeerNameByBeerIdAndReceiptId(receipt.getId());
			String receiptUrl = "D:/Giang/Document/Resource java/git/Beersale/BeerSale/receipt/Receipt.txt";
			FileOutputStream fo = new FileOutputStream(receiptUrl);
			buffer.append("=========================Receipt=========================\n");
			buffer.append(String.format("%-10s %-10s \n", "User name:",RunMain.userDTO.getName()));
			buffer.append(String.format("%-10s %-10s \n", "Receipt id:",receipt.getId()));
			buffer.append(String.format("%-5s %-20s %-10s \n","","Name(x Count)","Cost"));
			for (int i = 0; i < RunMain.lstReceiptDetail.size(); i++) {
				buffer.append(String.format("%-5s %-20s %-10s \n", (i + 1) + ". ",lstBeerName.get(i) + " x" + RunMain.lstReceiptDetail.get(i).getCount(),RunMain.lstReceiptDetail.get(i).getCost()));
			}
			buffer.append("Total money\t:\t" + receipt.getTotal());

			byte[] bytes = buffer.toString().getBytes();

			fo.write(bytes);
			fo.flush();
			fo.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
