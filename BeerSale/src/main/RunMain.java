package main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import common.SystemConstant;
import dao.UserAccountDAO;
import dao.UserDAO;
import dao.interfaces.IUserAccountDAO;
import dao.interfaces.IUserDAO;
import dto.ReceiptDetailDTO;
import dto.UserAccountDTO;
import dto.UserDTO;

public class RunMain {
	private Scanner sc = new Scanner(System.in);

	public static boolean checkExit = false;
	public static boolean checkLogout = false;

	public static List<ReceiptDetailDTO> lstReceiptDetail = new ArrayList<>();

	public static UserAccountDTO userAccountDTO;
	public static UserDTO userDTO;

	private IUserDAO userDAO;
	private IUserAccountDAO userAccountDAO;

	public RunMain() {
		userDAO = new UserDAO();
		userAccountDAO = new UserAccountDAO();
	}

	public void run() {
		System.out.println("================== Beer Store ================");
		while (true) {
			System.out.println("Do you have an account( y or n )?");
			while (true) {
				try {
					System.out.print(SystemConstant.ANSWER);
					String answer = sc.nextLine();
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
				Admin admin = new Admin();
				admin.adminInterface();
			} else {
				Client client = new Client();
				client.clientInterface();
			}
			if (checkLogout) {
				checkLogout = false;
			}
			if (checkExit){ 
				break;
			}
		}
		if (!lstReceiptDetail.isEmpty()) {
			System.out.println("Your order exported in Receipt.txt file");
		}
		System.out.println("Thank you !");
	}

	private void register() {
		while (true) {
			try {
				System.out.println("================== Register ================");
				System.out.print("Username : ");
				String userName = sc.nextLine();
				if (userAccountDAO.findOneByUserName(userName) != null) {
					throw new Exception("Username is exist!");
				}
				UserAccountDTO accountDTO = new UserAccountDTO();
				System.out.print("Password : ");
				String password = sc.nextLine();
				accountDTO.setUserName(userName);
				accountDTO.setPassword(password);
				System.out.print("Full name : ");
				String name = sc.nextLine();
				System.out.print("Email : ");
				String email = sc.nextLine();
				int phoneNumber = 0;
				phoneNumber = handleInputNumberException(phoneNumber, "Phone number: ");
				userAccountDAO.insert(accountDTO);
				accountDTO = userAccountDAO.findOneByUserNameAndPassword(userName, password);
				UserDTO userDTO = new UserDTO(accountDTO.getId(), name, email, phoneNumber);
				userDAO.insert(userDTO);
				userDTO = userDAO.findOneByUserAccountId(accountDTO.getId());

				System.out.println("Register successfully");
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

	private void logIn() {
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
				String userName = sc.nextLine();
				System.out.print("Password : ");
				String password = sc.nextLine();
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
					String answer = sc.nextLine();
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

	public void checkSuccess(int result, String announceSuccess, String announceFail) {
		if (result != 0) {
			System.out.println(announceSuccess);
		} else {
			System.out.println(announceFail);
		}
	}

	public int handleInputNumberException(int number, String text) {
		boolean checkInputNumber = false;

		while (!checkInputNumber) {
			try {
				Scanner scInt = new Scanner(System.in);
				System.out.print(text);
				number = scInt.nextInt();
				if (number < 0) {
					throw new Exception("Your number can't be negative");
				}
				checkInputNumber = true;
			} catch (InputMismatchException e) {
				System.out.println("You have to input number");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return number;
	}

//	public String handleInputStringException(String inputString,String text) {
//		boolean checkInputNumber = false;
//		
//		while (!checkInputNumber) {
//			try {
//				Scanner scString = new Scanner(System.in);
//				System.out.print(text);
//				inputString = scString.nextLine();
//				if(!Pattern.matches("^[a-zA-Z0-9]", inputString)) {
//					throw new InputMismatchException("Your answer contain special symbol");
//				}
//				checkInputNumber = true;
//			} catch (InputMismatchException e) {
//				System.out.println(e.getMessage());
//			}
//		}
//		return inputString;
//	}
}
