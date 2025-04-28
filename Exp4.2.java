import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

// Class to represent a playing card
class Card {
    private String suit; // e.g., "Hearts", "Diamonds", "Clubs", "Spades"
    private String rank; // e.g., "2", "3", "4", ..., "10", "Jack", "Queen", "King", "Ace"

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return suit.equals(card.suit) && rank.equals(card.rank);
    }

    @Override
    public int hashCode() {
        int result = suit.hashCode();
        result = 31 * result + rank.hashCode();
        return result;
    }
}

// Class to manage a collection of playing cards
public class CardCollection {
    private List<Card> cards;
    private Scanner scanner;

    public CardCollection() {
        this.cards = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    // Method to add a card to the collection
    public void addCard() {
        System.out.println("Enter card details:");
        System.out.print("Suit (Hearts, Diamonds, Clubs, Spades): ");
        String suit = scanner.nextLine();
        if (!isValidSuit(suit)) {
            System.out.println("Invalid suit. Card not added.");
            return;
        }

        System.out.print("Rank (2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King, Ace): ");
        String rank = scanner.nextLine();
        if (!isValidRank(rank)) {
            System.out.println("Invalid rank. Card not added.");
            return;
        }

        Card newCard = new Card(suit, rank);
        if (cards.contains(newCard)) {
            System.out.println("Card already exists in the collection.");
            return;
        }

        cards.add(newCard);
        System.out.println("Card added successfully.");
    }

    // Helper method to validate suit
    private boolean isValidSuit(String suit) {
        return suit.equals("Hearts") || suit.equals("Diamonds") || suit.equals("Clubs") || suit.equals("Spades");
    }

    // Helper method to validate rank
    private boolean isValidRank(String rank) {
        return rank.matches("2|3|4|5|6|7|8|9|10|Jack|Queen|King|Ace");
    }

    // Method to find cards by suit
    public void findCardsBySuit() {
        if (cards.isEmpty()) {
            System.out.println("No cards in the collection.");
            return;
        }
        System.out.print("Enter the suit to search for (Hearts, Diamonds, Clubs, Spades): ");
        String searchSuit = scanner.nextLine();
        if (!isValidSuit(searchSuit)) {
            System.out.println("Invalid suit.");
            return;
        }

        List<Card> matchingCards = new ArrayList<>();
        for (Card card : cards) {
            if (card.getSuit().equals(searchSuit)) {
                matchingCards.add(card);
            }
        }

        if (matchingCards.isEmpty()) {
            System.out.println("No cards found with suit " + searchSuit);
        } else {
            System.out.println("Cards with suit " + searchSuit + ":");
            for (Card card : matchingCards) {
                System.out.println(card);
            }
        }
    }

    // Method to display all cards in the collection
    public void displayAllCards() {
        if (cards.isEmpty()) {
            System.out.println("No cards in the collection.");
            return;
        }
        System.out.println("All cards in the collection:");
        for (Card card : cards) {
            System.out.println(card);
        }
    }

    // Method to remove a specific card
    public void removeCard() {
        if (cards.isEmpty()) {
            System.out.println("No cards in the collection to remove.");
            return;
        }

        System.out.print("Enter the suit of the card to remove: ");
        String suitToRemove = scanner.nextLine();
        if (!isValidSuit(suitToRemove)) {
            System.out.println("Invalid suit.");
            return;
        }

        System.out.print("Enter the rank of the card to remove: ");
        String rankToRemove = scanner.nextLine();
        if (!isValidRank(rankToRemove)) {
            System.out.println("Invalid rank.");
            return;
        }

        Card cardToRemove = new Card(suitToRemove, rankToRemove);
        if (cards.contains(cardToRemove)) {
            cards.remove(cardToRemove);
            System.out.println("Card removed successfully.");
        } else {
            System.out.println("Card not found in the collection.");
        }
    }

    public static void main(String[] args) {
        CardCollection cardCollection = new CardCollection();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nCard Collection Menu:");
            System.out.println("1. Add Card");
            System.out.println("2. Find Cards by Suit");
            System.out.println("3. Display All Cards");
            System.out.println("4. Remove Card");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                choice = -1; // Set to -1 to continue the loop
                scanner.nextLine(); // Consume the invalid input
            }

            switch (choice) {
                case 1:
                    cardCollection.addCard();
                    break;
                case 2:
                    cardCollection.findCardsBySuit();
                    break;
                case 3:
                    cardCollection.displayAllCards();
                    break;
                case 4:
                    cardCollection.removeCard();
                    break;
                case 0:
                    System.out.println("Exiting program.");
                    break;
                case -1:
                    // Do nothing, loop continues
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}

