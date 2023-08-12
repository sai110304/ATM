import java.util.*;

interface execute {// an interface that executes all methods or functionalities of the ATM program

    public boolean check(int a);

    double view_balance();

    public double withdraw(double amount);

    public double deposit(double amount);

    void set_pin(int x);

    public boolean check(int account_num, int num);

    public boolean amtavailable(int amt);

    public int getPin();

    public void set_account(int account_number, int pin, double balance, int sc);

    public int getAccount_number();

    public int getSecurity_code();
}

abstract class Account implements execute {//this class contains methods related to users login credentials and account details(balance).
    private int account_number;
    private int pin;
    double balance;
    private int security_code;

    public int getSecurity_code() {
        return security_code;
    }

    public void set_account(int account_number, int p, double balance, int sc) {//this method is used to set the user account.
        this.account_number = account_number;
        this.pin = p;
        this.balance = balance;
        this.security_code = sc;
    }

    public int getAccount_number() {
        return account_number;
    }

    public void set_pin(int x) {
        this.pin = x;
    }

    public int getPin() {
        return pin;
    }

    public boolean check(int account_num, int num) {//this method checks whether the entered account number is correct or not.
        if (this.account_number == account_num) {
            return true;
        } else {
            return false;
        }
    }


    public boolean check(int pin) {// this method checks whether the entered pin-number is correct or not.
        if (this.pin == pin) {
            return true;
        } else {
            return false;
        }
    }

    public boolean amtavailable(int amt) {// this method checks whether the money is available in users bank account
        if (amt > balance) {
            return false;
        } else {
            return true;
        }
    }
}

class menu extends Account {//this class is defined for carrying out various operations on users bank balance

    public double view_balance() {
        return balance;
    }

    public double deposit(double amount) {//this method adds money to users account when amount is deposited
        balance = balance + amount;
        return balance;
    }

    public double withdraw(double amount) {// the money will be deducted from users account when user withdraws money
        balance = balance - amount;
        return balance;
    }
}

class CashDispenser {//this class is specified for changes in amount of money  in ATM system.
    int amtd = 100000;

    public void dispenseCash(int amt) {// this method deducts the money from the ATM machine when user withdraws money
        amtd = amtd - amt;
        System.out.printf("\ncollect amount  %s", amt);
    }

    public void depositslot(int amt) {// this method adds money to the ATM machine when user deposits the money
        amtd = amtd + amt;
    }

    public boolean CashAvailable(int amt) {//it checks the withdraw money is available in ATM machine or not
        if (amt > amtd) {
            return false;
        } else {
            return true;
        }
    }
}

class Bank_users {//this class is specified for creating data base for bank and also to check the account number of user in in banks data base using methods
    execute[] e = new menu[10];

    void b_user() {//in this method data base is created using arrays (of size 10)
        for (int i = 0; i < 10; i++) {
            e[i] = new menu();
        }
        e[0].set_account(12340, 10020, 10000, 1240);
        e[1].set_account(12341, 10021, 20000, 1241);
        e[2].set_account(12342, 10022, 50000, 1242);
        e[3].set_account(12343, 10023, 15000, 1243);
        e[4].set_account(12344, 10024, 25000, 1244);
        e[5].set_account(12345, 10025, 16000, 1245);
        e[6].set_account(12346, 10026, 45000, 1246);
        e[7].set_account(12347, 10027, 35000, 1247);
        e[8].set_account(12348, 10028, 13000, 1248);
        e[9].set_account(12349, 10029, 55000, 1249);
    }

    int check_bu(int ac)//it checks the bank user account number in data base and returns respective index of the user in the array
    {
        for (int i = 0; i < 10; i++) {
            if (e[i].getAccount_number() == ac) {
                return i;
            }
        }
        return -1;
    }
}

public class ATM {//It has objects for the classes further it contains a menu driven  program where the methods in each class are invoked

    public static void main(String[] args) {

        Scanner sk = new Scanner(System.in);
        System.out.println("Welcome");
        CashDispenser CD = new CashDispenser();
        Bank_users bu = new Bank_users();
        bu.b_user();
        while (true) {
            System.out.println("Enter account number");
            int ac = sk.nextInt();
            int b = bu.check_bu(ac);
            while (b == -1) {
                System.out.println("enter correct account number");
                ac = sk.nextInt();
                b = bu.check_bu(ac);
            }
            System.out.println("Enter pin");
            int p = sk.nextInt();
            int count = 3;
            while (count != 1) {
                if (bu.e[b].check(p)) {
                    while (true) {
                        System.out.println("\n1) view balance \n2) withdraw \n3) deposit \n4)change password \n5)exit");
                        int c = sk.nextInt();
                        if (c == 1) {
                            System.out.println(bu.e[b].view_balance());
                        } else if (c == 2) {
                            System.out.println("enter the amount to withdraw");
                            int at = sk.nextInt();
                            if (CD.CashAvailable(at) && bu.e[b].amtavailable(at)) {
                                bu.e[b].withdraw(at);
                                System.out.printf("\n your balance amount %s", bu.e[b].view_balance());
                                CD.dispenseCash(at);
                            } else {
                                if (!CD.CashAvailable(at)) {
                                    System.out.println("ATM is out of money");
                                } else if (!bu.e[b].amtavailable(at)) {
                                    System.out.println("insufficient balance");
                                }
                            }
                        } else if (c == 3) {
                            System.out.println("enter the amount to deposit");
                            int at = sk.nextInt();
                            bu.e[b].deposit(at);
                            CD.depositslot(at);
                            System.out.printf("\n your balance amount %s", bu.e[b].view_balance());
                        } else if (c == 4) {
                            System.out.println("enter security pin to change pass word");
                            int sp = sk.nextInt();
                            while(true) {
                                if (sp == bu.e[b].getSecurity_code()) {
                                    System.out.println("enter your new pin");
                                    int pn = sk.nextInt();
                                    bu.e[b].set_pin(pn);
                                    System.out.println("your new pin");
                                    System.out.println(bu.e[b].getPin());
                                    break;
                                } else {
                                    System.out.println("Enter correct security code");
                                    sp = sk.nextInt();
                                }
                            }
                        } else if (c == 5) {
                            count = -1;
                            System.out.println("Thank You!");
                            System.out.println();
                            break;
                        }
                    }
                    break;
                } else {
                    System.out.println("Invalid password ");
                    System.out.println("Enter account number");
                    ac = sk.nextInt();
                    b = bu.check_bu(ac);
                    while (b == -1) {
                        System.out.println("enter correct account number");
                        ac = sk.nextInt();
                        b = bu.check_bu(ac);
                    }
                    System.out.println("Enter pin");
                    p = sk.nextInt();
                    count--;
                }
            }
            if (count == 1) {

                System.out.println("Choose the option");
                System.out.println("1)would you like to reset your password \n2)exit");
                int ch = sk.nextInt();
                if (ch == 1) {
                    System.out.println("enter security pin to change pass word");
                    int sp = sk.nextInt();
                    while (true) {
                        if (sp == bu.e[b].getSecurity_code()) {
                            System.out.println("enter your new pin");
                            int pin1 = sk.nextInt();
                            bu.e[b].set_pin(pin1);
                            System.out.println("your new pin");
                            System.out.println(bu.e[b].getPin());
                            break;
                        } else {
                            System.out.println("Enter correct security code");
                            sp = sk.nextInt();
                        }
                    }
                    System.out.println("Enter account number");
                    ac = sk.nextInt();
                    b = bu.check_bu(ac);
                    while (b == -1) {
                        System.out.println("enter correct account number");
                        ac = sk.nextInt();
                        b = bu.check_bu(ac);
                    }
                    System.out.println("Enter pin");
                    p = sk.nextInt();
                    if (bu.e[b].check(p)) {
                        while (true) {
                            System.out.println("\n1) view balance \n2) withdraw \n3) deposit \n4)exit");
                            int c = sk.nextInt();
                            if (c == 1) {
                                System.out.println(bu.e[b].view_balance());
                            } else if (c == 2) {
                                System.out.println("enter the amount to withdraw");
                                int at = sk.nextInt();
                                if (CD.CashAvailable(at) && bu.e[b].amtavailable(at)) {
                                    bu.e[b].withdraw(at);
                                    System.out.printf("\n your balance amount %s", bu.e[b].view_balance());
                                    CD.dispenseCash(at);
                                } else {
                                    if (!CD.CashAvailable(at)) {
                                        System.out.println("ATM is out of money");
                                    } else if (!bu.e[b].amtavailable(at)) {
                                        System.out.println("insufficient balance");
                                    }
                                }
                            } else if (c == 3) {
                                System.out.println("enter the amount to deposit");
                                int at = sk.nextInt();
                                bu.e[b].deposit(at);
                                CD.depositslot(at);
                                System.out.printf("\n your balance amount %s", bu.e[b].view_balance());
                            } else if (c == 4) {
                                System.out.println("Thank You!");
                                System.out.println();
                                break;
                            }
                        }
                    } else {
                        continue;
                    }
                } else if (ch == 2) {
                    System.out.println("Thank You");
                    System.out.println();
                } else {
                    continue;
                }
            }
        }
    }
}

