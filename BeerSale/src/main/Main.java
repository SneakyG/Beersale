package main;

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
import dto.UserAccountDTO;
import dto.UserDTO;

public class Main {

	private static Scanner sc = new Scanner(System.in);
	private static boolean checkExit = false;
	private static boolean checkLogout = false;

	private static UserAccountDTO userAccountDTO;
	private static UserDTO userDTO;
	
	private static IUserDAO userDAO = new UserDAO();
	private static IUserAccountDAO userAccountDAO = new UserAccountDAO();
	private static IBeerDAO beerDAO = new BeerDAO();
	private static IReceiptDAO receiptDAO = new ReceiptDAO();
	private static IReceiptDetailDAO receiptDetailDAO = new ReceiptDetailDAO();

	public static void main(String[] args) {
		System.out.println("================== Beer Store ================");
		while (true) {
			System.out.println("Do you have an account( y or n )?");
			while (true) {
				try {
					System.out.print(SystemConstant.ANSWER);
					String answer = sc.next();
					if (answer.equals("n")) {
						register();
						break;
					} else if (answer.equals("y")) {
						break;
					} else {
						throw new Exception(SystemConstant.WRONG_ANSWER);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			}

			logIn();

			if (userAccountDTO.getRoleId() == 1) {
				adminInterface();
			} else {
				clientInterface();
			}
			if (checkLogout) {
				checkLogout = false;
				continue;
			}
			if (checkExit)
				break;
		}
		System.out.println("Thank you !");
	}

	private static void register() {
		IUserDAO userDAO = new UserDAO();
		while (true) {
			try {
				System.out.println("================== Register ================");
				System.out.print("Username : ");
				String userName = sc.next();
				if (userAccountDAO.findOneByUserName(userName) != null) {
					throw new Exception("Username is exist!");
				}
				UserAccountDTO accountDTO = new UserAccountDTO();

				System.out.print("Password : ");
				String password = sc.next();
				accountDTO.setUserName(userName);
				accountDTO.setPassword(password);
				sc.nextLine();
				System.out.print("Full name : ");
				String name = sc.nextLine();
				System.out.print("Email : ");
				String email = sc.next();
				System.out.print("Phone number : ");
				int phoneNumber = sc.nextInt();
				userAccountDAO.insert(accountDTO);
				accountDTO = userAccountDAO.findOneByUserNameAndPassword(userName, password);
				int id = accountDTO.getId();
				UserDTO userDTO = new UserDTO(id, name, email, phoneNumber, id, id, 0);
				userDAO.insert(userDTO);
				System.out.println("Register successfully");
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	private static void logIn() {
		userAccountDTO = new UserAccountDTO();
		userDTO = new UserDTO();
		boolean checkNoAccount = true;
		while (true) {
			try {
				if (checkNoAccount == false) {
					register();
				}
				System.out.println("================== Log in ================");
				System.out.print("Username : ");
				String userName = sc.next();
				System.out.print("Password : ");
				String password = sc.next();
				userAccountDTO = userAccountDAO.findOneByUserNameAndPassword(userName, password);
				if (userAccountDTO == null) {
					throw new Exception("Username or password is wrong");
				} else {
					if (userAccountDTO.getStatus() != 1) {
						throw new Exception("Your account is locked");
					}
					userDTO = userDAO.findOneByUserAccountId(userAccountDTO.getId());
					System.out.println("Login successfully");
					break;
				}

			} catch (Exception e) {

				System.out.println(e.getMessage());
				while (true) {
					System.out.println("Do you want to create a new account ( y or n )?");
					System.out.print(SystemConstant.ANSWER);
					String answer = sc.next();
					if (answer.equals("y")) {
						checkNoAccount = false;
						break;
					} else if (answer.equals("n")) {
						break;
					} else {
						System.out.println(SystemConstant.WRONG_ANSWER);
					}
				}
			}
		}
	}

// ===================== Administer ======================
	private static void adminInterface() {
		while (true) {
			try {
				System.out.println("================== Administer ================");
				System.out.println("Select place want to come");
				System.out.println("1. Beer");
				System.out.println("2. Receipt");
				System.out.println("3. Account");
				System.out.println("4. Log out");
				System.out.println("5. Exit");
				System.out.print(SystemConstant.ANSWER);
				int answer = sc.nextInt();
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
					userAccountDTO = null;
					userDTO = null;
					checkLogout = true;
					return;
				case 5:
					checkExit = true;
					break;
				default:
					throw new Exception(SystemConstant.WRONG_ANSWER);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if (checkExit)
				break;
		}
	}

	private static void accountInterface() {
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
				System.out.print(SystemConstant.ANSWER);
				int answer = sc.nextInt();
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
					checkExit = true;
					break;
				default:
					throw new Exception(SystemConstant.WRONG_ANSWER);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if (checkExit)
				break;
		}
	}

	private static void showAccounts() {
		List<UserAccountDTO> lstAccount = new ArrayList<>();
		lstAccount = userAccountDAO.findAll();
		for (int i = 0; i < lstAccount.size(); i++) {
			System.out.println(i + 1 + " " + lstAccount.get(i).toString());
		}
	}

	private static void searchAccount() {
		System.out.print("Input username : ");
		String inputUsername = sc.next();
		UserAccountDTO searchAccount = new UserAccountDTO();
		searchAccount = userAccountDAO.findOneByUserName(inputUsername);
		if (searchAccount != null) {
			System.out.println(searchAccount.getId() + "-" + searchAccount.toString());
		} else {
			System.out.println("This account don't exist!");
		}
	}

	private static void changePassword() {
		searchCheck(SystemConstant.SEARCH_ACCOUNT);
		System.out.print("Input id want to update: ");
		int inputUpdate = sc.nextInt();
		UserAccountDTO updateAccount = userAccountDAO.findOneById(inputUpdate);
		if (updateAccount == null) {
			System.out.println("This account don't exist!");
		} else {
			System.out.println(updateAccount.toString());
			System.out.print("New password: ");
			String newPassword = sc.next();
			updateAccount.setPassword(newPassword);
			updateAccount.setUpdtId(userDTO.getId());
			userAccountDAO.update(updateAccount);
			updateAccount = userAccountDAO.findOneById(updateAccount.getId());
			System.out.println("Change password successfully");
			System.out.println(updateAccount.toString());
		}

	}

	private static void lockAccount() {
		searchCheck(SystemConstant.SEARCH_ACCOUNT);
		System.out.print("Input id want to update: ");
		int inputUpdate = sc.nextInt();
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

	private static void unlockAccount() {
		searchCheck(SystemConstant.SEARCH_ACCOUNT);
		System.out.print("Input id want to update: ");
		int inputUpdate = sc.nextInt();
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

	private static void searchCheck(String check) {
		while (true) {
			try {
				System.out.println("Do you want to search( y or n )");
				System.out.print(SystemConstant.ANSWER);
				String answer = sc.next();
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

	private static void beerInterface() {
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
				System.out.print(SystemConstant.ANSWER);
				int answer = sc.nextInt();
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
					checkExit = true;
					break;
				default:
					throw new Exception(SystemConstant.WRONG_ANSWER);
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if (checkExit)
				break;
		}
	}

	private static void showMenu() {
		List<BeerDTO> lstBeer = new ArrayList<>();
		lstBeer = beerDAO.findAll();
		for (int i = 0; i < lstBeer.size(); i++) {
			System.out.println(i + 1 + " " + lstBeer.get(i).toString());
		}
	}

	private static void searchBeer() {
		System.out.print("Input brand : ");
		String inputBrand = sc.next();
		List<BeerDTO> searchBeer = beerDAO.findAllByBrand(inputBrand);
		if (searchBeer.size() != 0) {
			for (int i = 0; i < searchBeer.size(); i++) {
				System.out.println(searchBeer.get(i).getId() + "-" + searchBeer.get(i).toString());
			}
		} else {
			System.out.println("Nothing!!");
		}
	}

	private static void insertBeer() {
		String name;
		sc.nextLine();
		while (true) {
			System.out.print("Beer name: ");
			name = sc.nextLine();
			if (beerDAO.findOneByName(name)) {
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
		System.out.print("Count: ");
		int count = sc.nextInt();
		System.out.print("Cost: ");
		int cost = sc.nextInt();
		BeerDTO beer = new BeerDTO(name, brand, originBrand, origin, ingredient, capacity, count, cost);
		int id = userDTO.getId();
		beer.setRegId(id);
		beer.setUpdtId(id);
		beerDAO.insert(beer);
		System.out.println("Insert beer successfully");
		showMenu();
	}

	private static void editOrAddBeer() {
		searchCheck(SystemConstant.SEARCH_BEER);
		while (true) {
			try {
				System.out.println("Select case you want");
				System.out.println("1. Edit a beer");
				System.out.println("2. Add count beer");
				System.out.println("3. Back");
				System.out.print(SystemConstant.ANSWER);
				int answer = sc.nextInt();
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

	private static void editBeer() {
		int id = checkBeerExist();
		if (id == 0)
			return;

		BeerDTO editBeer = beerDAO.findOne(id);
		sc.nextLine();
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
		System.out.print("Cost: " + editBeer.getCost() + " -> ");
		int cost = sc.nextInt();

		editBeer = new BeerDTO(editBeer.getId(), name, brand, originBrand, origin, ingredient, capacity,
				editBeer.getCount(), cost);
		editBeer.setUpdtId(userDTO.getId());
		int result = beerDAO.update(editBeer);
		
		checkSuccess(result, "Update successfully", "Update failed!");
	}

	private static void addCountBeer() {
		int id = checkBeerExist();
		if (id == 0)
			return;

		BeerDTO addCountBeer = beerDAO.findOne(id);
		System.out.print("Input number to add: ");
		int count = sc.nextInt();
		addCountBeer.setCount(addCountBeer.getCount() + count);
		addCountBeer.setUpdtId(userDTO.getId());
		int result = beerDAO.update(addCountBeer);
		
		checkSuccess(result, "Add successfully!", "Add failed!");
	}

	private static void deleteBeer() {
		searchCheck(SystemConstant.SEARCH_BEER);
		int id = checkBeerExist();
		if(id == 0) return;
		
		while(true) {
			System.out.println("Are you sure to delete it? (y or n)");
			String answer = sc.next();
			if(answer.equals("y")) {
				int result = beerDAO.delete(id);
				checkSuccess(result,  "Delete successfully!","Delete failed!");
				break;
			}else if(answer.equals("n")) {
				return;
			}else {
				System.out.println(SystemConstant.WRONG_ANSWER);
			}
		}
		
	}
	
	private static int checkBeerExist() {
		int id;
		while (true) {
			System.out.print("Input id: ");
			id = sc.nextInt();
			if (beerDAO.findOne(id) != null) {
				break;
			} else {
				System.out.println("Beer do not exist");
				id = 0;
			}
			System.out.println("Do you want to back previous interface? ( y or n )");
			String answer = sc.next();
			if (answer.equals("y")) {
				return 0;
			} else if (answer.equals("n")) {

			}
		}
		return id;
	}

	private static void checkSuccess(int result, String announceSuccess,String announceFail) {
		if(result != 0) {
			System.out.println(announceSuccess);
		}else {
			System.out.println(announceFail);
		}
	}

	private static void receiptInterface() {
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
				System.out.print(SystemConstant.ANSWER);
				int answer = sc.nextInt();
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
					checkExit = true;
					break;
				default:
					throw new Exception(SystemConstant.WRONG_ANSWER);
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if (checkExit)
				break;
		}
	}

	private static void showReceipt() {
		List<ReceiptDTO> lstReceipt = new ArrayList<>();
		lstReceipt = receiptDAO.findAll();
		for (int i = 0; i < lstReceipt.size(); i++) {
			System.out.println(i + 1 + " " + lstReceipt.get(i).toString());
		}
	}

	private static void searchReceipt() {
		System.out.print("Input id : ");
		int inputId = sc.nextInt();
		ReceiptDTO searchReceipt = receiptDAO.findOne(inputId);
		if (searchReceipt != null) {
			System.out.println(searchReceipt.toString());
		} else {
			System.out.println("Nothing!!");
		}
	}

	private static void checkReceipt() {
		searchCheck(SystemConstant.SEARCH_RECEIPT);
		System.out.print("Input id: ");
		int inputId = sc.nextInt();
		ReceiptDTO checkReceipt = receiptDAO.findOne(inputId);
		if(checkReceipt != null) {
			if(checkReceipt.getStatus() == 0) {
				checkReceipt.setStatus(1);
				checkReceipt.setUpdtId(userDTO.getId());
				int result = receiptDAO.update(checkReceipt);
				checkSuccess(result, "Check successfully", "Check fail");
			}else {
				System.out.println("This receipt checked");
			}
		}else {
			System.out.println("Receipt is not exist!!");
		}
	}

	private static void showReceiptDetail() {
		List<ReceiptDetailDTO> lstReceiptDetail = new ArrayList<>();
		lstReceiptDetail = receiptDetailDAO.findAll();
		for (int i = 0; i < lstReceiptDetail.size(); i++) {
			System.out.println(i + 1 + " " + lstReceiptDetail.get(i).toString());
		}
	}

	private static void showReceiptDetailByReceiptId() {
		List<ReceiptDetailDTO> lstReceiptDetail = new ArrayList<>();
		System.out.print("Input receipt id: ");
		int id = sc.nextInt();
		lstReceiptDetail = receiptDetailDAO.findByReceiptId(id);
		if(lstReceiptDetail != null) {
			for (int i = 0; i < lstReceiptDetail.size(); i++) {
				System.out.println(i + 1 + " " + lstReceiptDetail.get(i).toString());
			}
		}else {
			System.out.println("Nothing!!!");
		}
	}

	// ===================== Client ======================
	private static void clientInterface() {
		System.out.println("Customer here");
	}
}