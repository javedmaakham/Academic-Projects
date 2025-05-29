import java.io.*;
import java.util.*;

class Phonebook {
    private PhonebookEntry[] phonebook;
    private int SIZE;
    private final static int DEFAULT_CAPACITY = 100;
    private String filename;

    public Phonebook(String filename) throws FileNotFoundException {
        phonebook = new PhonebookEntry[DEFAULT_CAPACITY];
        SIZE = 0;
        this.filename = filename;

        File num = new File(filename);
        Scanner scan = new Scanner(num);
        try {
            while (scan.hasNext() && SIZE < DEFAULT_CAPACITY) {
                phonebook[SIZE++] = PhonebookEntry.read(scan);
            }
            if (SIZE >= DEFAULT_CAPACITY) {
                System.out.println("*** Exception *** Phonebook capacity exceeded - increase size of underlying array");
                System.exit(0);
            }
        } catch (Exception e) {
            System.out.println("*** Exception *** Error reading file: " + e.getMessage());
        }
        scan.close();
    }

    public void executeLookup(Scanner input) {
        String option;
        System.out.print("lookup, reverse-lookup, quit (l/r/q)? ");
        option = input.next();
        int lookCount = 0;
        int reverseCount = 0;

        while (!option.equals("q")) {
            if (option.equals("l")) {
                System.out.print("last name? ");
                String lastname = input.next();
                System.out.print("first name? ");
                String firstname = input.next();
                lookCount++;

                lookup(firstname, lastname);

            } else if (option.equals("r")) {
                System.out.print("phone number (nnn-nnn-nnnn)? ");
                String number = input.next();
                reverseCount++;

                reverseLookup(number);
            }
            System.out.println();
            System.out.print("lookup, reverse-lookup, quit (l/r/q)? ");
            option = input.next();
        }
        System.out.println();
        System.out.println(lookCount + " lookups performed");
        System.out.println(reverseCount + " reverse lookups performed");
    }

    public void lookup(String firstName, String lastName) {
        boolean found = false;
        for (int i = 0; i < SIZE; i++) {
            if (phonebook[i].getName().getLast().equals(lastName) &&
                    phonebook[i].getName().getFirst().equals(firstName)) {
                System.out.println(phonebook[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("-- Name not found");
        }
    }

    public void reverseLookup(String number) {
        boolean found = false;
        for (int i = 0; i < SIZE; i++) {
            if (phonebook[i].getPhoneNumber().getNumber().equals(number)) {
                System.out.println(number + " belongs to " + phonebook[i].getName().getFirst() + " " + phonebook[i].getName().getLast());
                found = true;
            }
        }
        if (!found) {
            System.out.println("-- Phone number not found");
        }
    }
}

class PhonebookEntry {
    private Name name;
    private PhoneNumber phoneNumber;

    public PhonebookEntry(Name name, PhoneNumber phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static PhonebookEntry read(Scanner input) {
        String lastName = input.next();
        String firstName = input.next();
        String phoneNumber = input.next();
        return new PhonebookEntry(new Name(firstName, lastName), new PhoneNumber(phoneNumber));
    }

    public Name getName() {
        return name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public String toString() {
        return name.getFormal() + "'s phone number is " + phoneNumber.toString();
    }
}

class Name {
    private String first;
    private String last;

    public Name(String first, String last) {
        this.first = first;
        this.last = last;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getFormal() {
        return first + " " + last;
    }
}

class PhoneNumber {
    private String number;

    public PhoneNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public String toString() {
        return number;
    }
}

class PhonebookApp {
    public static void main(String[] args) {
        try {
            Phonebook phoneBook = new Phonebook("phonebook.text");
            Scanner input = new Scanner(System.in);
            phoneBook.executeLookup(input);
            input.close();
        } catch (FileNotFoundException e) {
            System.out.println("*** Exception *** File not found: " + e.getMessage());
        }
    }
}
