import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Class to represent a seat
class Seat {
    private int seatNumber;
    private boolean isBooked;

    public Seat(int seatNumber) {
        this.seatNumber = seatNumber;
        this.isBooked = false;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void book() {
        this.isBooked = true;
    }

    @Override
    public String toString() {
        return "Seat " + seatNumber + (isBooked ? " (Booked)" : " (Available)");
    }
}

// Class to manage the booking system
class BookingSystem {
    private List<Seat> seats;
    private Lock lock = new ReentrantLock(); // Use ReentrantLock for thread safety

    public BookingSystem(int numSeats) {
        this.seats = new ArrayList<>();
        for (int i = 1; i <= numSeats; i++) {
            seats.add(new Seat(i));
        }
    }

    // Method to display available seats
    public void displayAvailableSeats() {
        System.out.println("\nAvailable Seats:");
        for (Seat seat : seats) {
            if (!seat.isBooked()) {
                System.out.print(seat.getSeatNumber() + " ");
            }
        }
        System.out.println();
    }

    // Method to book a seat
    public boolean bookSeat(int seatNumber, String userName) {
        lock.lock(); // Acquire the lock before accessing shared data
        try {
            if (seatNumber <= 0 || seatNumber > seats.size()) {
                System.out.println(userName + ": Invalid seat number.");
                return false;
            }

            Seat seat = seats.get(seatNumber - 1);
            if (!seat.isBooked()) {
                seat.book();
                System.out.println(userName + ": Seat " + seatNumber + " booked successfully.");
                return true;
            } else {
                System.out.println(userName + ": Seat " + seatNumber + " is already booked.");
                return false;
            }
        } finally {
            lock.unlock(); // Release the lock in a finally block to ensure it's always released
        }
    }
    //Method to get the total number of seats
    public int getTotalNumberOfSeats(){
        return seats.size();
    }
}

// Class representing a user thread trying to book a seat
class UserThread extends Thread {
    private BookingSystem bookingSystem;
    private int seatNumber;
    private String userName;

    public UserThread(BookingSystem bookingSystem, int seatNumber, String userName) {
        this.bookingSystem = bookingSystem;
        this.seatNumber = seatNumber;
        this.userName = userName;
    }

    @Override
    public void run() {
        bookingSystem.bookSeat(seatNumber, userName);
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of seats: ");
        int numSeats = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        BookingSystem bookingSystem = new BookingSystem(numSeats);
        bookingSystem.displayAvailableSeats();

        // Create multiple user threads trying to book seats
        int numUsers = 5; // You can change this number
        UserThread[] users = new UserThread[numUsers];
        for (int i = 0; i < numUsers; i++) {
            System.out.print("Enter the seat number for user " + (i + 1) + ": ");
            int seatNumber = scanner.nextInt();
            scanner.nextLine();
            users[i] = new UserThread(bookingSystem, seatNumber, "User" + (i + 1));
            users[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < numUsers; i++) {
            users[i].join();
        }
        bookingSystem.displayAvailableSeats();
        scanner.close();
        System.out.println("Total Number of seats: " + bookingSystem.getTotalNumberOfSeats());

    }
}
