package monuSirTasks;

import java.util.ArrayList;
import java.util.Scanner;

public class BudgetPlanner {
    static Scanner sc = new Scanner(System.in);
    static ArrayList income = new ArrayList();
    static ArrayList expenses = new ArrayList();
    static ArrayList incomeSource = new ArrayList();
    static ArrayList expenseSource = new ArrayList();
    static double balance = 0;

    static void manager() {

        System.out.println("1 - Add Income\n2 - Add Expense\n3 - View Transactions\n4 - View Balance\n5 - Exit");
        System.out.println("Enter between 1 - 5");
        int i = sc.nextInt();
        if (i < 1 || i > 5) {
            System.err.println("Invalid input Re-enter");
            i = sc.nextInt();
        }
        switch (i) {
            case 1: {
                addIncome();
                break;
            }
            case 2: {
                addExpenses();
                break;
            }
            case 3: {
                viewTransactions();
                break;
            }
            case 4: {
                viewBalance();
                break;
            }
            case 5: {
                System.err.println("PROGRAM CLOSED");
                sc.close();
                break;
            }
        }
    }

    static void addIncome() {
        System.out.println("Add Income");
        int ans;
        do {
            double in = sc.nextDouble();
            sc.nextLine();
            balance += in;
            income.add(in);
            System.out.println("Add the Source of Income");
            String sou = sc.nextLine();
            incomeSource.add(sou);
            System.out.println("Enter :\n1 - Add another entry\n2 - Main Menu");
            ans = sc.nextInt();
            if (ans == 2)
                continue;
            System.out.println("Add another Income");
        } while (ans == 1);
        manager();

    }

    static void addExpenses() {
        System.out.println("Add Expense");
        int ans;
        do {
            double ex = sc.nextDouble();
            balance -= ex;
            sc.nextLine();
            expenses.add(ex);
            System.out.println("Enter reason for Expense");
            String exSou = sc.nextLine();
            expenseSource.add(exSou);
            System.out.println("Enter :\n1 - To Add another expense\n2 - Main Menu ");
            ans = sc.nextInt();
            if (ans == 2)
                continue;
            System.out.println("Add another Expense");
        } while (ans == 1);
        manager();
    }

    static void viewTransactions() {
        System.out.println("Summary of all Transactions :");

        System.out.println("Incomes :");
        for (int i = 0; i < income.size(); i++) {
            System.out.println((i + 1) + ")" + " +" + income.get(i) + "(" + incomeSource.get(i) + ")");
        }
        System.err.println("Expenses :");
        for (int i = 0; i < expenses.size(); i++) {
            System.err.println((i + 1) + ")" + " -" + expenses.get(i) + "(" + expenseSource.get(i) + ")");
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        sc.nextLine();
        System.out.println();
        manager();
    }

    static void viewBalance() {
        if (balance >= 0) {
            System.out.println("Balance is : + " + balance);
        } else {
            System.err.println("Balance is : - " + balance);
        }

        manager();
    }


    public static void main(String[] args) {
        System.out.println("Income Tracker");
        manager();
    }
}
