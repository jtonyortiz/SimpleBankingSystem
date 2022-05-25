package banking;

import java.util.*;

public class Main {
    private int BIN = 400000;
    private long accout_num;
    public String pin_number;
    public String card_number;
    private int balance = 0;


    //Constructor:
    public Main(){
        this.pin_number = this.generatePinNumber();
        this.card_number = this.generateVerifiedCardNumber();
        this.balance = 0;
    }

    //Method:
    //generateAccountNumber()
    //
    //
    public long generateAccountNumber(){
        //Create a random 10-digit number from a seed:
        return (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
    }

    //Method:
    //generatePinNumber()
    //Description: generates pin-number for user
    //Returns String type
    public String generatePinNumber(){
        Random r = new Random();
        return String.format("%04d", r.nextInt(1001));
    }

    //Method:
    //getCardNumber()
    //Description: Returns complete card number
    //Returns: String type
    public String getCardNumber(){
        return Integer.toString(this.BIN) + Long.toString(this.accout_num);
    }

    public String getPinNumber(){
        return this.pin_number;
    }

    //Method: getBalance()
    //Description: Returns user's balance from credit card
    //Returns: int
    public int getBalance(){
        return this.balance;
    }


    //Method: printCardInfo
    //Description: Displays Credit-card Information to User
    //Returns: void
    public void printCardInfo(){
        System.out.println("You card has been created");
        System.out.println("Your card number: ");
        System.out.println(getCardNumber());
        System.out.println("Your card PIN: ");
        System.out.println(getPinNumber());
    }

    //Method: generateVerifiedCardNumber()
    //Description: Checks the create card number
    //to see if the card number passed Luhn verification
    //Returns String type
    public String generateVerifiedCardNumber(){
        String verifyCardNum = "";
        boolean passed = false;
        verifyCardNum = this.getCardNumber();

        //Do-while Loop: Repeat process till
        //verified card number is obtained
        do {
            passed = luhnTest(verifyCardNum);
            if(passed == false){
                this.accout_num = this.generateAccountNumber();
                verifyCardNum = this.BIN + Long.toString(this.accout_num);
            }else{
                passed = true;
            }


        }while(passed == false);

        //Return verified card number:
        return verifyCardNum;
    }

    //Method: luhnTest(String number):
    //Description: Used to check if the card itself is valid
    //via Luhn Algorithm
    //Returns: boolean type
    public static boolean luhnTest(String number){
        int s1 = 0, s2 = 0;
        String reverse = new StringBuffer(number).reverse().toString();
        for(int i = 0 ;i < reverse.length();i++){
            int digit = Character.digit(reverse.charAt(i), 10);
            if(i % 2 == 0){//this is for odd digits, they are 1-indexed in the algorithm
                s1 += digit;
            }else{//add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
                s2 += 2 * digit;
                if(digit >= 5){
                    s2 -= 9;
                }
            }
        }
        return (s1 + s2) % 10 == 0;
    }


    //Main Driver:
    // ==================== ======= ================
    public static void main(String[] args) throws CloneNotSupportedException {
        Scanner scan = new Scanner(System.in);
        boolean isExit = false;
        int menuSelection;
        int subMenuSelection;

        //Account List for Accounts:
        List<Main> acctList = new ArrayList<>();


        //Do-while logic loop to control main-menu:
        do {
            //Main Menu Selection:
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            menuSelection = scan.nextInt();

            switch (menuSelection)
            {
                //Create your acct.
                case 1:
                    Main card = new Main(); //Create Instance
                    card.printCardInfo(); //Display Information about Card
                    acctList.add(card); //Adds card to ArrayList
                    break;

                case 2: //Log into account:
                    boolean exists = false;
                    do {
                        System.out.println("Enter your card number: ");
                        String cardNo = scan.nextLine();
                        cardNo = scan.nextLine();
                        System.out.println("Enter your PIN: ");
                        String PIN = scan.nextLine();

                        System.out.println("TEST: Entered Card Number " + cardNo);
                        System.out.println("TEST: Entered Pin Number: " + PIN);

                        Main retAcct = null;

                        for (int i = 0; i < acctList.size(); i++) {
                            if(acctList.get(i).card_number.equalsIgnoreCase(cardNo)){
                                if(acctList.get(i).pin_number.equals(PIN)){
                                    exists = true;
                                    retAcct = acctList.get(i);
                                }
                            }

                        }

                        if (exists == false) {
                            System.out.println("Wrong card number or PIN!");
                        } else {
                            System.out.println("You have successfully logged in!");
                            boolean doExit = false;
                            do {

                                System.out.println("1. Balance");
                                System.out.println("2. Log out");
                                System.out.println("0. Exit");
                                int select = scan.nextInt();

                                switch(select){
                                    //Get Balance:
                                    case 1:
                                        System.out.println("Balance: " + retAcct.getBalance());
                                        break;
                                        //Log-Out:
                                    case 2:
                                        doExit = true;
                                        break;
                                        //Exit:
                                    case 3:
                                        break;
                                    default:
                                        break;
                                }
                            } while (!doExit);
                        }

                    }while(!exists);

                    break;
                case 0: //Exit program:
                    isExit = true;
                    System.out.println("Bye!");
                    break;
                default:
                    break;
            }



        } while(!isExit);

        scan.close();

    }
}