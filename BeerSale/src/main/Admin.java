package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import common.SystemConstant;
import dao.BeerDAO;
import dao.ReceiptDAO;
import dao.ReceiptDetailDAO;
import dao.UserAccountDAO;
import dao.interfaces.IBeerDAO;
import dao.interfaces.IReceiptDAO;
import dao.interfaces.IReceiptDetailDAO;
import dao.interfaces.IUserAccountDAO;
import dto.BeerDTO;
import dto.ReceiptDTO;
import dto.ReceiptDetailDTO;
import dto.UserAccountDTO;

public class Admin {

	private Scanner sc = new Scanner(System.in);

	private IUserAccountDAO userAccountDAO;
	private IBeerDAO beerDAO;
	private IReceiptDAO receiptDAO;
	private IReceiptDetailDAO receiptDetailDAO;
	private RunMain runMain;

	public Admin() {
		userAccountDAO = new UserAccountDAO();
		beerDAO = new BeerDAO();
		receiptDAO = new ReceiptDAO();
		receiptDetailDAO = new ReceiptDetailDAO();
		runMain = new RunMain();
	}

	public void adminInterface() {
		while (true) {
			try {
				System.out.println("================== Administer ================");
				System.out.println("Select place want to come");
				System.out.println("1. Beer");
				System.out.println("2. Receipt");
				System.out.println("3. Account");
				System.out.println("4. Log out");
				System.out.println("5. Exit");
				int answer = 0;
				answer = runMain.handleInputNumberException(answer, SystemConstant.ANSWER);
				switch (answer) {
				case 1:
					beerInterface();
					break;
				case 2:
					receiptInterface();
					break;
				case 3:
					accountInterface();
					break;
				case 4:
					RunMain.userAccountDTO = null;
					RunMain.userDTO = null;
					RunMain.checkLogout = true;
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

	private void accountInterface() {
		while (true) {
			try {
				System.out.println("================== Account ================");
				System.out.println("1. Show accounts");
				System.out.println("2. Search Account");
				System.out.println("3. Change password");
				System.out.println("4. Lock account");
				System.out.println("5. Unlock account");
				System.out.println("6. Back admin");
				System.out.println("7. Exit");
				int answer = 0;
				answer = runMain.handleInputNumberException(answer, SystemConstant.ANSWER);
				switch (answer) {
				case 1:
					showAccounts();
					break;
				case 2:
					searchAccount();
					break;
				case 3:
					changePassword();
					break;
				case 4:
					lockAccount();
					break;
				case 5:
					unlockAccount();
					break;
				case 6:
					return;
				case 7:
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

	private void showAccounts() {
		List<UserAccountDTO> lstAccount = new ArrayList<>();
		lstAccount = userAccountDAO.findAll();
		System.out.printf("%-5s %-20s %-20s %-20s %-20s %-20s \n", "", "User name", "Password", "Role id", "Status",
				"Money");
		for (int i = 0; i < lstAccount.size(); i++) {
			System.out.printf("%-5s %-20s %-20s %-20s %-20s %-20s \n", i + 1, lstAccount.get(i).getUserName(),
					lstAccount.get(i).getPassword(), lstAccount.get(i).getRoleId(), lstAccount.get(i).getStatus(),
					lstAccount.get(i).getMoney());
		}
	}

	private void searchAccount() {
		System.out.print("Input username : ");
		String inputUsername = sc.nextLine();
		UserAccountDTO searchAccount = new UserAccountDTO();
		searchAccount = userAccountDAO.findOneByUserName(inputUsername);
		if (searchAccount != null) {
			System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s \n", "Id", "User name", "Password", "Role id",
					"Status", "Money");
			System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s \n", searchAccount.getId(),
					searchAccount.getUserName(), searchAccount.getPassword(), searchAccount.getRoleId(),
					searchAccount.getStatus(), searchAccount.getMoney());
		} else {
			System.out.println("This account don't exist!");
		}
	}

	private void changePassword() {
		searchCheck(SystemConstant.SEARCH_ACCOUNT);
		int inputUpdate = 0;
		inputUpdate = runMain.handleInputNumberException(inputUpdate, "Input id want to update: ");
		UserAccountDTO updateAccount = userAccountDAO.findOneById(inputUpdate);
		if (updateAccount == null) {
			System.out.println("This account don't exist!");
		} else {
			System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s \n", "Id", "User name", "Password", "Role id",
					"Status", "Money");
			System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s \n", updateAccount.getId(),
					updateAccount.getUserName(), updateAccount.getPassword(), updateAccount.getRoleId(),
					updateAccount.getStatus(), updateAccount.getMoney());
			System.out.print("New password: ");
			String newPassword = sc.nextLine();
			updateAccount.setPassword(newPassword);
			updateAccount.setUpdtId(RunMain.userDTO.getId());
			userAccountDAO.update(updateAccount);
			updateAccount = userAccountDAO.findOneById(updateAccount.getId());
			System.out.println("Change password successfully");
			System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s \n", "Id", "User name", "Password", "Role id",
					"Status", "Money");
			System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s \n", updateAccount.getId(),
					updateAccount.getUserName(), updateAccount.getPassword(), updateAccount.getRoleId(),
					updateAccount.getStatus(), updateAccount.getMoney());
		}

	}

	private void lockAccount() {
		searchCheck(SystemConstant.SEARCH_ACCOUNT);
		int inputUpdate = 0;
		inputUpdate = runMain.handleInputNumberException(inputUpdate, "Input id want to lock: ");
		UserAccountDTO lockAccount = userAccountDAO.findOneById(inputUpdate);
		if (lockAccount == null) {
			System.out.println("This account don't exist!");
		} else {
			if (lockAccount.getStatus() == 1) {
				lockAccount.setStatus(0);
				userAccountDAO.update(lockAccount);
				System.out.println("Lock account successfully");
			} else {
				System.out.println("This account is locked");
			}
		}
	}

	private void unlockAccount() {
		searchCheck(SystemConstant.SEARCH_ACCOUNT);
		int inputUpdate = 0;
		inputUpdate = runMain.handleInputNumberException(inputUpdate, "Input id want to unlock: ");
		UserAccountDTO unlockAccount = userAccountDAO.findOneById(inputUpdate);
		if (unlockAccount == null) {
			System.out.println("This account don't exist!");
		} else {
			if (unlockAccount.getStatus() == 0) {
				unlockAccount.setStatus(1);
				userAccountDAO.update(unlockAccount);
				System.out.println("Unlock account successfully");
			} else {
				System.out.println("This account don't be locked");
			}
		}
	}

	private void searchCheck(String check) {
		while (true) {
			try {
				System.out.println("Do you want to search( y or n )");
				System.out.print(SystemConstant.ANSWER);
				String answer = sc.nextLine();
				if (answer.equals("y")) {
					if (check.equals(SystemConstant.SEARCH_ACCOUNT)) {
						searchAccount();
					} else if (check.equals(SystemConstant.SEARCH_BEER)) {
						searchBeer();
					} else if (check.equals(SystemConstant.SEARCH_RECEIPT)) {
						searchReceipt();
					}
				} else if (answer.equals("n")) {
					break;
				} else {
					throw new Exception(SystemConstant.WRONG_ANSWER);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private void beerInterface() {
		while (true) {
			try {
				System.out.println("================== Beer ================");
				System.out.println("1. Show menu");
				System.out.println("2. Search beer");
				System.out.println("3. Insert beer");
				System.out.println("4. Edit or Add beer");
				System.out.println("5. Delete beer");
				System.out.println("6. Back admin");
				System.out.println("7. Exit");
				int answer = 0;
				answer = runMain.handleInputNumberException(answer, SystemConstant.ANSWER);
				switch (answer) {
				case 1:
					showMenu();
					break;
				case 2:
					searchBeer();
					break;
				case 3:
					insertBeer();
					break;
				case 4:
					editOrAddBeer();
					break;
				case 5:
					deleteBeer();
					break;
				case 6:
					return;
				case 7:
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

	private void showMenu() {
		System.out.println("==================Menu===================");
		List<BeerDTO> lstBeer = new ArrayList<>();
		lstBeer = beerDAO.findAll();
		System.out.printf("%-5s %-25s %-25s %-20s %-20s %-25s %-20s %-10s %-10s %-15s %-25s %-25s \n", "", "Name",
				"Brand", "Origin brand", "Origin", "Ingredients", "Capacity", "Count", "Cost", "Register id",
				"Register date", "Update id", "Update date");
		for (int i = 0; i < lstBeer.size(); i++) {
			String ingredient = handleIngredientString(lstBeer.get(i).getIngredient());
			System.out.printf("%-5s %-25s %-25s %-20s %-20s %-25s %-20s %-10s %-10s %-15s %-25s %-25s \n", i + 1,
					lstBeer.get(i).getName(), lstBeer.get(i).getBrand(), lstBeer.get(i).getOriginBrand(),
					lstBeer.get(i).getOrigin(), ingredient, lstBeer.get(i).getCapacity(), lstBeer.get(i).getCount(),
					lstBeer.get(i).getCost(), lstBeer.get(i).getRegId(), lstBeer.get(i).getRegDate(),
					lstBeer.get(i).getUpdtId(), lstBeer.get(i).getUpdtDate());
		}
	}

	private String handleIngredientString(String ingredient) {
		if (ingredient.length() >= 20) {
			ingredient = ingredient.substring(0, 20) + "...";
		}
		return ingredient;
	}

	private void searchBeer() {
		System.out.print("Input brand : ");
		String inputBrand = sc.nextLine();
		List<BeerDTO> searchBeer = beerDAO.findAllByBrand(inputBrand);
		if (searchBeer.size() != 0) {
			System.out.printf("%-5s %-10s %-25s %-25s %-20s %-20s %-25s %-20s %-10s %-10s %-15s %-25s %-25s \n", "",
					"Id", "Name", "Brand", "Origin brand", "Origin", "Ingredients", "Capacity", "Count", "Cost",
					"Register id", "Register date", "Update id", "Update date");
			for (int i = 0; i < searchBeer.size(); i++) {
				String ingredient = handleIngredientString(searchBeer.get(i).getIngredient());
				System.out.printf("%-5s %-10s %-25s %-25s %-20s %-20s %-25s %-20s %-10s %-10s %-15s %-25s %-25s \n",
						i + 1, searchBeer.get(i).getId(), searchBeer.get(i).getName(), searchBeer.get(i).getBrand(),
						searchBeer.get(i).getOriginBrand(), searchBeer.get(i).getOrigin(), ingredient,
						searchBeer.get(i).getCapacity(), searchBeer.get(i).getCount(), searchBeer.get(i).getCost(),
						searchBeer.get(i).getRegId(), searchBeer.get(i).getRegDate(), searchBeer.get(i).getUpdtId(),
						searchBeer.get(i).getUpdtDate());
			}
		} else {
			System.out.println("Nothing!!");
		}
	}

	private void insertBeer() {
		String name;
		while (true) {
			System.out.print("Beer name: ");
			name = sc.nextLine();
			if (beerDAO.findOneByName(name) != null) {
				System.out.println("Product is exist !");
			} else {
				break;
			}
		}
		System.out.print("Brand: ");
		String brand = sc.nextLine();
		System.out.print("origin_brand: ");
		String originBrand = sc.nextLine();
		System.out.print("origin: ");
		String origin = sc.nextLine();
		System.out.print("Ingredient: ");
		String ingredient = sc.nextLine();
		System.out.print("Capacity: ");
		String capacity = sc.nextLine();
		int count = 0;
		count = runMain.handleInputNumberException(count, "Count: ");
		int cost = 0;
		cost = runMain.handleInputNumberException(cost, "Cost: ");
		BeerDTO beer = new BeerDTO(name, brand, originBrand, origin, ingredient, capacity, count, cost);
		int id = RunMain.userDTO.getId();
		beer.setRegId(id);
		beer.setUpdtId(id);
		beerDAO.insert(beer);
		System.out.println("Insert beer successfully");
		showMenu();
	}

	private void editOrAddBeer() {
		searchCheck(SystemConstant.SEARCH_BEER);
		while (true) {
			try {
				System.out.println("Select case you want");
				System.out.println("1. Edit a beer");
				System.out.println("2. Add count beer");
				System.out.println("3. Back");
				int answer = 0;
				answer = runMain.handleInputNumberException(answer, SystemConstant.ANSWER);
				if (answer == 1) {
					editBeer();
				} else if (answer == 2) {
					addCountBeer();
				} else if (answer == 3) {
					return;
				} else {
					throw new Exception(SystemConstant.WRONG_ANSWER);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}

	}

	private void editBeer() {
		int id = checkBeerExist();
		if (id == 0)
			return;

		BeerDTO editBeer = beerDAO.findOne(id);
		System.out.print("Name: " + editBeer.getName() + " -> ");
		String name = sc.nextLine();
		System.out.print("Brand: " + editBeer.getBrand() + " -> ");
		String brand = sc.nextLine();
		System.out.print("Origin brand: " + editBeer.getOriginBrand() + " -> ");
		String originBrand = sc.nextLine();
		System.out.print("Origin: " + editBeer.getOrigin() + " -> ");
		String origin = sc.nextLine();
		System.out.print("ingredient: " + editBeer.getIngredient() + " -> ");
		String ingredient = sc.nextLine();
		System.out.print("Capacity: " + editBeer.getCapacity() + " -> ");
		String capacity = sc.nextLine();
		int cost = 0;
		cost = runMain.handleInputNumberException(cost, "Cost: " + editBeer.getCost() + " -> ");

		editBeer = new BeerDTO(editBeer.getId(), name, brand, originBrand, origin, ingredient, capacity,
				editBeer.getCount(), cost);
		editBeer.setUpdtId(RunMain.userDTO.getId());
		int result = beerDAO.update(editBeer);

		checkSuccess(result, "Update successfully", "Update unsuccessfully!");
	}

	private void addCountBeer() {
		int id = checkBeerExist();
		if (id == 0)
			return;

		BeerDTO addCountBeer = beerDAO.findOne(id);
		int count = 0;
		count = runMain.handleInputNumberException(count, "Input number to add: ");
		addCountBeer.setCount(addCountBeer.getCount() + count);
		addCountBeer.setUpdtId(RunMain.userDTO.getId());
		int result = beerDAO.update(addCountBeer);

		checkSuccess(result, "Add successfully!", "Add unsuccessfully!");
	}

	private void deleteBeer() {
		searchCheck(SystemConstant.SEARCH_BEER);
		int id = checkBeerExist();
		if (id == 0)
			return;

		while (true) {
			System.out.println("Are you sure to delete it? (y or n)");
			String answer = sc.nextLine();
			if (answer.equals("y")) {
				int result = beerDAO.delete(id);
				checkSuccess(result, "Delete successfully!", "Delete unsuccessfully!");
				break;
			} else if (answer.equals("n")) {
				return;
			} else {
				System.out.println(SystemConstant.WRONG_ANSWER);
			}
		}

	}

	private int checkBeerExist() {
		int id = 0;
		while (true) {
			id = runMain.handleInputNumberException(id, "Input id: ");
			if (beerDAO.findOne(id) != null) {
				break;
			} else {
				System.out.println("Beer do not exist");
				id = 0;
			}
			System.out.println("Do you want to back previous interface? ( y or n )");
			String answer = sc.nextLine();
			if (answer.equals("y")) {
				return 0;
			} else if (answer.equals("n")) {

			}
		}
		return id;
	}

	private void checkSuccess(int result, String announceSuccess, String announceFail) {
		if (result != 0) {
			System.out.println(announceSuccess);
		} else {
			System.out.println(announceFail);
		}
	}

	private void receiptInterface() {
		while (true) {
			try {
				System.out.println("================== Receipt ================");
				System.out.println("1. Show receipt");
				System.out.println("2. Search receipt");
				System.out.println("3. Check receipt");
				System.out.println("4. All receipt detail");
				System.out.println("5. Show receipt detail by receipt id");
				System.out.println("6. Back admin");
				System.out.println("7. Exit");
				int answer = 0;
				answer = runMain.handleInputNumberException(answer, SystemConstant.ANSWER);
				switch (answer) {
				case 1:
					showReceipt();
					break;
				case 2:
					searchReceipt();
					break;
				case 3:
					checkReceipt();
					break;
				case 4:
					showReceiptDetail();
					break;
				case 5:
					showReceiptDetailByReceiptId();
					break;
				case 6:
					return;
				case 7:
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

	private void showReceipt() {
		List<ReceiptDTO> lstReceipt = new ArrayList<>();
		lstReceipt = receiptDAO.findAll();
		System.out.printf("%-5s %-15s %-15s %-15s %-15s %-25s %-15s %-10s \n", "", "User id", "Total", "Status",
				"Register id", "Register date", "Update id", "Update date");
		for (int i = 0; i < lstReceipt.size(); i++) {
			System.out.printf("%-5s %-15s %-15s %-15s %-15s %-25s %-15s %-10s \n", i + 1, lstReceipt.get(i).getUserId(),
					lstReceipt.get(i).getTotal(), lstReceipt.get(i).getStatus(), lstReceipt.get(i).getRegId(),
					lstReceipt.get(i).getRegDate(), lstReceipt.get(i).getUpdtId(), lstReceipt.get(i).getUpdtDate());
		}
	}

	private void searchReceipt() {
		int inputId = 0;
		inputId = runMain.handleInputNumberException(inputId, "Input id : ");
		ReceiptDTO searchReceipt = receiptDAO.findOne(inputId);
		if (searchReceipt != null) {
			System.out.printf("%-10s %-15s %-15s %-15s %-25s %-15s %-10s \n", "User id", "Total", "Status",
					"Register id", "Register date", "Update id", "Update date");
			System.out.printf("%-10s %-15s %-15s %-15s %-25s %-15s %-10s \n", searchReceipt.getUserId(),
					searchReceipt.getTotal(), searchReceipt.getStatus(), searchReceipt.getRegId(),
					searchReceipt.getRegDate(), searchReceipt.getUpdtId(), searchReceipt.getUpdtDate());
		} else {
			System.out.println("Nothing!!");
		}
	}

	private void checkReceipt() {
		searchCheck(SystemConstant.SEARCH_RECEIPT);
		int inputId = 0;
		inputId = runMain.handleInputNumberException(inputId, "Input id : ");
		ReceiptDTO checkReceipt = receiptDAO.findOne(inputId);
		if (checkReceipt != null) {
			if (checkReceipt.getStatus() == 0) {
				checkReceipt.setStatus(1);
				checkReceipt.setUpdtId(RunMain.userDTO.getId());
				int result = receiptDAO.update(checkReceipt);
				checkSuccess(result, "Check successfully", "Check unsuccessfully");
			} else {
				System.out.println("This receipt checked");
			}
		} else {
			System.out.println("Receipt is not exist!!");
		}
	}

	private void showReceiptDetail() {
		List<ReceiptDetailDTO> lstReceiptDetail = new ArrayList<>();
		lstReceiptDetail = receiptDetailDAO.findAll();
		System.out.printf("%-5s %-25s %-25s %-20s %-20s %-25s %-25s %-25s %-25s %-15s \n", "", "Beer id", "User id",
				"Receipt id", "Count", "Cost", "Register id", "Register date", "Update id", "Update date");
		for (int i = 0; i < lstReceiptDetail.size(); i++) {
			System.out.printf("%-5s %-25s %-25s %-20s %-20s %-25s %-25s %-25s %-25s %-15s \n", i + 1,
					lstReceiptDetail.get(i).getBeerId(), lstReceiptDetail.get(i).getUserId(),
					lstReceiptDetail.get(i).getReceiptId(), lstReceiptDetail.get(i).getCount(),
					lstReceiptDetail.get(i).getCost(), lstReceiptDetail.get(i).getRegId(),
					lstReceiptDetail.get(i).getRegDate(), lstReceiptDetail.get(i).getUpdtId(),
					lstReceiptDetail.get(i).getUpdtDate());
		}
	}

	private void showReceiptDetailByReceiptId() {
		List<ReceiptDetailDTO> lstReceiptDetail = new ArrayList<>();
		int id = 0;
		id = runMain.handleInputNumberException(id, "Input receipt id: ");
		lstReceiptDetail = receiptDetailDAO.findByReceiptId(id);
		if (lstReceiptDetail != null) {
			System.out.printf("%-5s %-10s %-25s %-25s %-20s %-20s %-25s %-25s %-25s %-25s %-15s \n", "", "Id",
					"Beer id", "User id", "Receipt id", "Count", "Cost", "Register id", "Register date", "Update id",
					"Update date");
			for (int i = 0; i < lstReceiptDetail.size(); i++) {
				System.out.printf("%-5s %-10s %-25s %-25s %-20s %-20s %-25s %-25s %-25s %-25s %-15s \n", i + 1,
						lstReceiptDetail.get(i).getId(), lstReceiptDetail.get(i).getBeerId(),
						lstReceiptDetail.get(i).getUserId(), lstReceiptDetail.get(i).getReceiptId(),
						lstReceiptDetail.get(i).getCount(), lstReceiptDetail.get(i).getCost(),
						lstReceiptDetail.get(i).getRegId(), lstReceiptDetail.get(i).getRegDate(),
						lstReceiptDetail.get(i).getUpdtId(), lstReceiptDetail.get(i).getUpdtDate());
			}
		} else {
			System.out.println("Nothing!!!");
		}
	}
}
