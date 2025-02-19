import java.util.Scanner;
import java.util.ArrayList;
 
interface MediaDevice {
    void activateCamera();
    void activateMicrophone();
}
 
class FakeCallSos implements MediaDevice {
    @Override
    public void activateCamera() {
        System.out.println("Camera activated: Displaying fake call/SOS video feed...");
    }
    @Override
    public void activateMicrophone() {
        System.out.println("Microphone activated: Audio streaming in progress...");
    }
}
 
abstract class UserOperations {
    public abstract void signup();
    public abstract boolean login();
    public abstract void addContact();
    public abstract void addEmergencyContact();
    public abstract void emergencySOS();
    public abstract void fakeCall();
    public abstract void emergencySOSWithMedia();
}
 
class User {
    private String username;
    private String password;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
 
class Contact {
    private String name;
    private String phone;
    private String email;
    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getEmail() {
        return email;
    }
    public String toString() {
        return "Contact: " + name + ", Phone: " + phone + ", Email: " + email;
    }
}
 
class EmergencyContact extends Contact {
    public EmergencyContact(String name, String phone, String email) {
        super(name, phone, email);
    }
    @Override
    public String toString() {
        return "Emergency " + super.toString();
    }
}
 
class App extends UserOperations {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ArrayList<EmergencyContact> emergencyContacts = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private User currentUser = null;
    public void signup() {
        System.out.println("===== Signup =====");
        System.out.print("Enter Username/Email: ");
        String username = scanner.nextLine();
        System.out.print("Create Password: ");
        String password = scanner.nextLine();
        users.add(new User(username, password));
        System.out.println("Signup Successful! You can now login.");
    }
    public boolean login() {
        System.out.println("===== Login =====");
        System.out.print("Enter Username/Email: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Login Successful!");
                currentUser = user;
                return true;
            }
        }
        System.out.println("Invalid Credentials! Please signup if you don't have an account.");
        return false;
    }
    public void addContact() {
        if(currentUser == null) {
            System.out.println("Please login first.");
            return;
        }
        System.out.println("===== Add Contact =====");
        System.out.print("Enter Contact Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Contact Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Contact Email: ");
        String email = scanner.nextLine();
        Contact contact = new Contact(name, phone, email);
        contacts.add(contact);
        System.out.println("Contact added: " + contact);
    }
    public void addEmergencyContact() {
        if(currentUser == null) {
            System.out.println("Please login first.");
            return;
        }
        System.out.println("===== Add Emergency Contact =====");
        System.out.print("Enter Emergency Contact Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Emergency Contact Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Emergency Contact Email: ");
        String email = scanner.nextLine();
        EmergencyContact emergencyContact = new EmergencyContact(name, phone, email);
        emergencyContacts.add(emergencyContact);
        System.out.println("Emergency Contact added: " + emergencyContact);
    }
    public void displayContacts() {
        System.out.println("===== All Contacts =====");
        if (contacts.isEmpty() && emergencyContacts.isEmpty()) {
            System.out.println("No contacts available.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
            for (EmergencyContact emergencyContact : emergencyContacts) {
                System.out.println(emergencyContact);
            }
        }
    }
    public void emergencySOS() {
        if(currentUser == null) {
            System.out.println("Please login first.");
            return;
        }
        System.out.println("===== Emergency SOS =====");
        if(emergencyContacts.isEmpty()) {
            System.out.println("No emergency contacts available to send SOS.");
        } else {
            System.out.println("SOS triggered! Notifying emergency contacts:");
            for (EmergencyContact ec : emergencyContacts) {
                System.out.println("Sending SOS to: " + ec);
            }
        }
    }
    public void fakeCall() {
        if(currentUser == null) {
            System.out.println("Please login first to initiate a fake call.");
            return;
        }
        System.out.println("===== Fake Call =====");
        FakeCallSos media = new FakeCallSos();
        System.out.println("Initiating fake call...");
        media.activateCamera();
        media.activateMicrophone();
        System.out.println("Fake call in progress...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Fake call ended.");
    }
    public void emergencySOSWithMedia() {
        if(currentUser == null) {
            System.out.println("Please login first to trigger SOS.");
            return;
        }
        System.out.println("===== Emergency SOS with Media =====");
        FakeCallSos media = new FakeCallSos();
        media.activateCamera();
        media.activateMicrophone();
        if(emergencyContacts.isEmpty()) {
            System.out.println("No emergency contacts available to send SOS.");
        } else {
            System.out.println("SOS triggered with media activated! Notifying emergency contacts:");
            for (EmergencyContact ec : emergencyContacts) {
                System.out.println("Sending SOS to: " + ec);
            }
        }
    }
}
 
public class MenuDrivenApp {
    public static void main(String[] args) {
        App app = new App();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while(!exit) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Add Contact");
            System.out.println("4. Add Emergency Contact");
            System.out.println("5. Display Contacts");
            System.out.println("6. Emergency SOS (Basic)");
            System.out.println("7. Fake Call");
            System.out.println("8. Emergency SOS with Media (Camera & Microphone)");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch(choice) {
                case 1:
                    app.signup();
                    break;
                case 2:
                    app.login();
                    break;
                case 3:
                    app.addContact();
                    break;
                case 4:
                    app.addEmergencyContact();
                    break;
                case 5:
                    app.displayContacts();
                    break;
                case 6:
                    app.emergencySOS();
                    break;
                case 7:
                    app.fakeCall();
                    break;
                case 8:
                    app.emergencySOSWithMedia();
                    break;
                case 9:
                    exit = true;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}
