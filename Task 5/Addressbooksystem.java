import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Contact {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone Number: " + phoneNumber + ", Email Address: " + emailAddress;
    }
}

class AddressBook {
    private List<Contact> contacts;

    public AddressBook() {
        contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(String name) {
        contacts.removeIf(contact -> contact.getName().equalsIgnoreCase(name));
    }

    public Contact findContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    public void saveDataToFile(String filename) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Contact contact : contacts) {
                writer.write(contact.getName() + "," + contact.getPhoneNumber() + "," + contact.getEmailAddress() + "\n");
            }
        }
    }

    public void loadDataFromFile(String filename) throws IOException {
        contacts.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    String phoneNumber = parts[1];
                    String emailAddress = parts[2];
                    contacts.add(new Contact(name, phoneNumber, emailAddress));
                }
            }
        }
    }
}

public class AddressBookSystem {
    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        Scanner scanner = new Scanner(System.in);

        try {
            addressBook.loadDataFromFile("contacts.txt");
            System.out.println("Data loaded from file.");
        } catch (IOException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }

        while (true) {
            System.out.println("\nAddress Book System");
            System.out.println("1. Add Contact");
            System.out.println("2. Remove Contact");
            System.out.println("3. Search for Contact");
            System.out.println("4. Display All Contacts");
            System.out.println("5. Save Data to File");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Contact Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Phone Number: ");
                    String phoneNumber = scanner.nextLine();
                    System.out.print("Enter Email Address: ");
                    String emailAddress = scanner.nextLine();

                    if (!name.isEmpty() && !phoneNumber.isEmpty() && !emailAddress.isEmpty()) {
                        addressBook.addContact(new Contact(name, phoneNumber, emailAddress));
                        System.out.println("Contact added successfully.");
                    } else {
                        System.out.println("Name, Phone Number, and Email Address fields cannot be empty.");
                    }
                    break;
                case 2:
                    System.out.print("Enter Contact Name to Remove: ");
                    String removeName = scanner.nextLine();
                    addressBook.removeContact(removeName);
                    System.out.println("Contact removed successfully.");
                    break;
                case 3:
                    System.out.print("Enter Contact Name to Search: ");
                    String searchName = scanner.nextLine();
                    Contact foundContact = addressBook.findContact(searchName);
                    if (foundContact != null) {
                        System.out.println("Contact found:\n" + foundContact);
                    } else {
                        System.out.println("Contact not found.");
                    }
                    break;
                case 4:
                    List<Contact> allContacts = addressBook.getAllContacts();
                    if (allContacts.isEmpty()) {
                        System.out.println("No contacts in the address book.");
                    } else {
                        System.out.println("All Contacts:");
                        for (Contact contact : allContacts) {
                            System.out.println(contact);
                        }
                    }
                    break;
                case 5:
                    try {
                        addressBook.saveDataToFile("contacts.txt");
                        System.out.println("Data saved to file.");
                    } catch (IOException e) {
                        System.err.println("Error saving data to file: " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("Exiting Address Book System. Thank you!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }
}
