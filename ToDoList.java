package monuSirTasks;


import java.util.Scanner;

public class ToDoList {
    static String[] allTasks = new String[10];
    static String[] tasksCompleted = new String[10];
    static int count = 0;
    static Scanner sc = new Scanner(System.in);

    static void manageToDoList() {
        System.out.println(" \n1 - Add tasks \n2 - View Tasks \n3 - Remove a task\n4 - Mark a task completed\n5 - Exit\nEnter Here");
        int inputMain = sc.nextInt();
        if (inputMain < 1 || inputMain > 5) {
            System.err.println("Invalid input \nSelect between 1 to 5");
            manageToDoList();
        }
        switch (inputMain) {
            case 1: {
                addTasks();
                break;
            }
            case 2: {
                viewTask();
                break;
            }
            case 3: {
                removeATask();
                break;
            }
            case 4: {
                markAsCompleted();
                break;
            }
            case 5: {
                sc.close();
                System.err.println("PROGRAM CLOSED");
                break;
            }
        }
    }

    static void addTasks() {
        System.out.println("You Can Add tasks Here :");
        int ans;
        String task;
        sc.nextLine();
        do {
            System.out.println("Enter a new task :");
            task = sc.nextLine();
            tasksCompleted[count] = "In Progress";
            allTasks[count++] = task;
            System.out.println("Do you want Add another task \n1 - Yes\n2 - No");
            ans = sc.nextInt();
            sc.nextLine();
        } while (ans == 1);
        manageToDoList();
    }

    static void viewTask() {
        System.out.println("Your current tasks are :\nId Name     Status");
        for (int i = 0; i < count; i++) {
            System.out.println((i + 1) + " - " + allTasks[i] + " : " + tasksCompleted[i]);
        }
        System.out.println();
        manageToDoList();
    }

    static void removeATask() {
        System.out.println("Select the task you want to remove");
        int removeTask = sc.nextInt();
        boolean removePerformed = false;
        for (int i = 0; i < count; i++) {
            if (i < removeTask - 1) {
                continue;
            }
            if (i == removeTask - 1) {
                removePerformed = true;
            } else {
                allTasks[i - 1] = allTasks[i];
                tasksCompleted[i - 1] = tasksCompleted[i];
            }
        }
        if (removePerformed == true) {
            count--;
        }
        manageToDoList();
    }

    static void markAsCompleted() {
        System.out.println("Select the task you want to mark as completed");
        int completedTask = sc.nextInt();
        if (completedTask > 0 && completedTask <= count && tasksCompleted[completedTask - 1] != "Completed") {
            tasksCompleted[completedTask - 1] = "Completed";
        } else if (completedTask > 0 && completedTask <= count && tasksCompleted[completedTask - 1] == "Completed") {
            System.err.println("This item is already marked completed");
        }
        manageToDoList();
    }

    public static void main(String[] args) {
        System.out.println("To Do List");
	System.out.println("Md Ali");
        manageToDoList();
    }

}
