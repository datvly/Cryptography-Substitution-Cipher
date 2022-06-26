import java.util.Locale;
import java.util.Scanner;

/**
 * CS312 Assignment 9.
 *
 * On my honor, Dat Ly, this programming assignment is my own work and I have
 * not shared my solution with any other student in the class.
 *
 *  email address: dat.ly@utexas.edu
 *  UTEID: dl36287
 *  TA name: Pavan
 *  Number of slip days used on this assignment: 0
 *  
 * Program to decrypt a message that has been
 * encrypted with a substitution cipher.
 * We assume only characters with ASCII codes
 * from 32 to 126 inclusive have been encrypted.
 */

//Decrypts a file that has been encrypted with a substitution cipher
public class Decrypt {

    public final static int MAX_ASCII_VALUE = 128;
    public final static int MIN_PRINTABLE_ASCII_VALUE = 32;
    public final static int MAX_PRINTABLE_ASCII_VALUE = 127;

    //Decrypts a file with substitution cipher and the key
    //can be change by the users.
    public static void main(String[] arg) {
        Scanner keyboard = new Scanner(System.in);
        String fileName = getFileName(keyboard);
        String encryptedText = DecryptUtilities.convertFileToString(fileName);
        entireDecryptingProcess(keyboard, encryptedText);
        keyboard.close();
    }


    //The entire decrypting process that allows the user to change the key
    //and will continue unless the user says not to
    public static void entireDecryptingProcess (Scanner keyboard, String encryptedText) {
        boolean changeOfKey = true;
        char[] key = decryptingProcess (encryptedText);
        while (changeOfKey) {
            System.out.println("Do you want to make a change to the key?");
            System.out.print("Enter 'Y' or 'y' to make change: ");
            String userInput = keyboard.nextLine().toLowerCase();
            changeOfKey = userInput.equals("y");
            if (changeOfKey) {
                System.out.print("Enter the decrypt character you want to change: ");
                String decryptChange = keyboard.nextLine();
                System.out.print("Enter what the character " + decryptChange + " should decrypt to instead: ");
                String decryptInstead = keyboard.nextLine();
                System.out.println(decryptChange + "'s will now decrypt to " + decryptInstead + "'s and vice versa.");
                System.out.println();
                changingTheKey(decryptChange, decryptInstead, key);
                System.out.println("The current version of the decrypted text is: ");
                System.out.println();
                decryptingVersionText(encryptedText, key);
            }
        }
        displayingFinalDecryptedText (encryptedText, key);
    }


    //The decrypting process that displays everything
    //and returns the key back
    public static char[] decryptingProcess (String encryptedText) {
        encryptedVersionText (encryptedText);
        int[] frequency = calculatingFrequency (encryptedText);
        displayingFrequency (frequency);
        char[] key = DecryptUtilities.getDecryptionKey (frequency);
        displayingASCIIKey (key);
        System.out.println("The current version of the decrypted text is: ");
        System.out.println();
        decryptingVersionText (encryptedText, key);
        return key;
    }


    //Changing the key by swapping what user want to change and the character they want
    //to swap it with
    public static void changingTheKey (String decryptChange, String decryptInstead, char[] key) {
        int decryptChangeLocation = 0;
        int decryptInsteadLocation = 0;
        for (int i = 0; i < key.length; i++) {
            String keyString = "";
            keyString += key[i];
            if (keyString.equals(decryptChange)) {
                decryptChangeLocation = i;
            }
            if (keyString.equals(decryptInstead)) {
                decryptInsteadLocation = i;
            }
        }
        char swap = key[decryptChangeLocation];
        key[decryptChangeLocation] = key[decryptInsteadLocation];
        key[decryptInsteadLocation] = swap;
    }


    //Displaying the final decrypted text based on
    //if the user no longer want to change the key
    public static void displayingFinalDecryptedText (String encryptedText,  char[] key) {
        displayingASCIIKey (key);
        System.out.println("The final version of the decrypted text is: ");
        System.out.println();
        decryptingVersionText (encryptedText, key);
    }


    //Prints out the encryptedText
    public static void encryptedVersionText(String encryptedText) {
        System.out.println("The encrypted text is:");
        System.out.println(encryptedText);
    }


    //Calculate the frequencies of the characters within the file
    //Assigning it to an index of the frequency array.
    public static int[] calculatingFrequency (String encryptedText) {
        int[] frequency = new int[MAX_ASCII_VALUE];
        for (int i = 0; i < encryptedText.length(); i++) {
            char character = encryptedText.charAt(i);
            frequency[character]++;
        }
        return frequency;
    }


    //Display the frequencies of characters from ASCII value of 32 to 127
    public static void displayingFrequency (int[] frequency) {
        System.out.println("Frequencies of characters.");
        System.out.println("Character - Frequency");
        for (int i = MIN_PRINTABLE_ASCII_VALUE; i < MAX_PRINTABLE_ASCII_VALUE; i++) {
            System.out.println((char) i + " - " + frequency[i]);
        }
    }


    //Displays the printable ASCII key
    public static void displayingASCIIKey (char[] key) {
        System.out.println();
        System.out.println("The current version of the key for ASCII characters 32 to 126 is: ");
        for (int i = MIN_PRINTABLE_ASCII_VALUE; i < MAX_PRINTABLE_ASCII_VALUE; i++) {
            System.out.println("Encrypt character: " + (char) i + ", decrypt character: " + key[i]);
        }
        System.out.println();
    }


    //Display the decrypted text
    public static void decryptingVersionText (String encryptedText, char[] key) {
        String decryptedText = "";
        for (int i = 0; i < encryptedText.length(); i++) {
            char character = encryptedText.charAt(i);
            decryptedText += key[character];
        }
        System.out.println(decryptedText);
    }


    // Get the name of file to use. For the assignment no error
    // checking is required.
    public static String getFileName(Scanner kbScanner) {
        System.out.print("Enter the name of the encrypted file: ");
        String fileName = kbScanner.nextLine().trim();
        System.out.println();
        return fileName;
    }
}
