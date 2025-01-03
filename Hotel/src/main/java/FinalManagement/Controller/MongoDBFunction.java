package FinalManagement.Controller;

import FinalManagement.View.Menu;
import org.bson.Document;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static FinalManagement.View.LogInPage.frame;

public class MongoDBFunction extends MongoDBBase {
    private static String loggedEmail;
    private static String chosenHotel;
    private static String chosenRoom;
    private static String numberRoomBooked;

    public static void setLoggedEmail(String loggedEmail){
        MongoDBFunction.loggedEmail = loggedEmail;
    }

    public static void setChosenHotel(String chosenHotel) {
        MongoDBFunction.chosenHotel = chosenHotel;
    }

    public static void setChosenRoom(String chosenRoom) {
        MongoDBFunction.chosenRoom = chosenRoom;
    }

    public static void setNumberRoomBooked(String numberRoomBooked){
        MongoDBFunction.numberRoomBooked = numberRoomBooked;
    }

    public void userSignUp(String fullname, String gender, String id, String email, String password) {
        Document newUser = new Document("Name", fullname)
                .append("Gender", gender)
                .append("ID", id)
                .append("Email", email)
                .append("Password", password);
        insertDocument("User", newUser);
    }

    public void userLogIn(String email, String password) {
        Document filter = new Document("Email", email).append("Password", password);
        Document userFind = findFirstDocument("User", filter);
        setLoggedEmail(email);

        if (userFind != null) {
            JOptionPane.showMessageDialog(null, "Welcome, " + userFind.getString("Name") + "!");
            frame.dispose();
            Menu menu = new Menu();
            menu.showFrame();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid email or password");
        }
    }

    public List<String[]> hotelSearch(String locationTarget) {
        Document filter = new Document("location", locationTarget);
        List<Document> documents = findDocuments("Hotel", filter);
        List<String[]> hotels = new ArrayList<>();

        for (Document doc : documents) {
            String name = doc.getString("name");
            String rating = doc.get("rating") != null ? doc.get("rating").toString() : "N/A";
            hotels.add(new String[]{name, rating});
        }
        return hotels;
    }

    public String pathHotelPicture(String hotelName) {
        Document filter = new Document("name", hotelName);
        Document document = findFirstDocument("Hotel", filter);

        if (document != null) {
            return document.getString("hotelpicturelocation");
        } else {
            System.out.println("Path can't be found");
        }
        return null;
    }

    public List<String[]> fetchRoomDetails(String hotelName) {
        Document filter = new Document("hotel_name", hotelName);
        List<Document> documents = findDocuments("Room", filter);
        List<String[]> rooms = new ArrayList<>();

        for (Document doc : documents) {
            String chosenHotelName = doc.getString("hotel_name");
            String roomType = doc.getString("room_type");
            String description = doc.getString("description") != null ? doc.getString("description") : "N/A";
            String price = doc.get("price_per_night") != null ? doc.get("price_per_night").toString() : "N/A";
            String roomAvailable = doc.get("rooms_available") != null ? doc.get("rooms_available").toString() : "N/A";
            String imagePath = doc.getString("roompicturelocation") != null ? doc.getString("roompicturelocation") : "";
            rooms.add(new String[]{chosenHotelName, roomType, description, price, roomAvailable, imagePath});
        }
        return rooms;
    }

    public List<String[]> fetchRoomDetails2(String roomType) {
        Document filter = new Document("room_type", roomType);
        List<Document> documents = findDocuments("Room", filter);
        List<String[]> rooms = new ArrayList<>();

        for (Document doc : documents) {
            String chosenHotelName = doc.getString("hotel_name");
            String description = doc.getString("description") != null ? doc.getString("description") : "N/A";
            String price = doc.get("price_per_night") != null ? doc.get("price_per_night").toString() : "N/A";
            String roomAvailable = doc.get("rooms_available") != null ? doc.get("rooms_available").toString() : "N/A";
            String imagePath = doc.getString("roompicturelocation") != null ? doc.getString("roompicturelocation") : "";
            rooms.add(new String[]{chosenHotelName, roomType, description, price, roomAvailable, imagePath});
        }
        return rooms;
    }

    public boolean updateAvailability(List<String[]> room, String getBooked) {
        String[] roomDetails = room.getFirst();

        try {
            setChosenHotel(roomDetails[0]);
            setChosenRoom(roomDetails[1]);
            setNumberRoomBooked(getBooked);
            int bookedQuantity = Integer.parseInt(getBooked);
            int remaining = Integer.parseInt(roomDetails[4]) - bookedQuantity;

            if (remaining < 0) {
                System.err.println("Error: Booking quantity exceeds available rooms.");
                return false;
            }

            Document filter = new Document("room_type", roomDetails[1]);
            Document updateOperation = new Document("$set", new Document("rooms_available", remaining));
            updateDocument("Room", filter, updateOperation);

            System.out.println("Database updated successfully.");
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid input for booking quantity.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: Database update failed.");
            e.printStackTrace();
        }
        return false;
    }

    public void addBookingToUser() {
        @SuppressWarnings("deprecation")
        String timestamp = BookingTimestampHandler.getTimestamp();
        try {
            Document userFilter = new Document("Email", loggedEmail);
            Document user = findFirstDocument("User", userFilter);
    
            if (user == null) {
                System.err.println("User not found.");
                return;
            }
    
            Document roomFilter = new Document("hotel_name", chosenHotel).append("room_type", chosenRoom);
            Document room = findFirstDocument("Room", roomFilter);
    
            if (room == null) {
                System.err.println("Room not found.");
                return;
            }
    
            int newRoomBooking = Integer.parseInt(numberRoomBooked);
            List<Document> bookings = user.getList("bookings", Document.class, new ArrayList<>());
    
            boolean bookingExists = false;
    
            for (Document booking : bookings) {
                if (booking.getString("hotel_name").equals(chosenHotel) &&
                        booking.getString("room_type").equals(chosenRoom)) {
                    int existingRoomsBooked = booking.getInteger("rooms_booked", 0);
                    int updatedRoomsBooked = existingRoomsBooked + newRoomBooking;
    
                    Document updateFilter = new Document("Email", loggedEmail)
                            .append("bookings.hotel_name", chosenHotel)
                            .append("bookings.room_type", chosenRoom);
    
                    Document updateOperation = new Document("$set",
                            new Document("bookings.$.rooms_booked", updatedRoomsBooked)
                                    .append("bookings.$.booking_time", timestamp));
    
                    updateDocument("User", updateFilter, updateOperation);
                    bookingExists = true;
                    System.out.println("Updated existing booking with additional rooms.");
                    break;
                }
            }
    
            if (!bookingExists) {
                Document newBooking = new Document()
                        .append("hotel_name", chosenHotel)
                        .append("room_type", chosenRoom)
                        .append("description", room.getString("description"))
                        .append("price_per_night", room.get("price_per_night"))
                        .append("rooms_booked", newRoomBooking)
                        .append("booking_time", timestamp);
    
                Document updateOperation = new Document("$push", new Document("bookings", newBooking));
                updateDocument("User", userFilter, updateOperation);
                System.out.println("Added a new booking with timestamp: " + timestamp);
            }
    
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format for the booking quantity.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to add booking to user.");
        }
    }
    

    public List<String[]> getOrderedRoomsByCustomer() {
        List<String[]> orderedRooms = new ArrayList<>();

        try {
            Document userFilter = new Document("Email", loggedEmail);
            Document user = findFirstDocument("User", userFilter);

            if (user == null) {
                System.err.println("User not found.");
                return orderedRooms;
            }

            List<Document> bookings = user.getList("bookings", Document.class, new ArrayList<>());

            for (Document booking : bookings) {
                String hotelName = booking.getString("hotel_name");
                String roomType = booking.getString("room_type");
                String roomQuantity = booking.get("price_per_night") != null ? booking.get("price_per_night").toString() : "N/A";
                String roomsBooked = booking.get("rooms_booked") != null ? booking.get("rooms_booked").toString() : "N/A";
                String bookingTime = booking.getString("booking_time") != null ? booking.getString("booking_time") : "N/A";

                orderedRooms.add(new String[]{hotelName, roomType, roomQuantity, roomsBooked, bookingTime});
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to fetch ordered rooms.");
        }

        return orderedRooms;
    }

    public boolean updateAvailabilityCancel(List<String[]> rooms, String getBooked) {
        String[] roomsDetails = rooms.getFirst();
        
        try {
            setChosenRoom(roomsDetails[1]);
            setNumberRoomBooked(getBooked);
            int bookedQuantity = Integer.parseInt(getBooked);
            int remaining = Integer.parseInt(roomsDetails[4]) + bookedQuantity;

            if (remaining < 0) {
                System.err.println("Error: Booking quantity exceeds available rooms.");
                return false;
            }

            Document filter = new Document("room_type", roomsDetails[1]);
            Document updateOperation = new Document("$set", new Document("rooms_available", remaining));
            updateDocument("Room", filter, updateOperation);

            System.out.println("Database updated successfully.");
            return true;
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid input for booking quantity.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: Database update failed.");
            e.printStackTrace();
        }
        return false;
    }

    public void cancelBookingFromUser(String numberHotelBookedCancel) {
        String timestamp = BookingTimestampHandler.getTimestamp();
    
        try {
            Document userFilter = new Document("Email", loggedEmail);
            Document user = findFirstDocument("User", userFilter);
    
            if (user == null) {
                System.err.println("User not found.");
                return;
            }
    
            int cancelQuantity = Integer.parseInt(numberHotelBookedCancel);
            List<Document> bookings = user.getList("bookings", Document.class, new ArrayList<>());
    
            boolean bookingExists = false;
    
            for (Document booking : bookings) {
                if (booking.getString("room_type").equals(chosenRoom)) {
                    int existingRoomsBooked = booking.getInteger("rooms_booked");
                    int updatedRoomsBooked = existingRoomsBooked - cancelQuantity;
    
                    if (updatedRoomsBooked < 0) {
                        System.err.println("Error: Cancel quantity exceeds the booked quantity.");
                        return;
                    }
    
                    if (updatedRoomsBooked == 0) {
                        Document pullOperation = new Document("$pull", new Document("bookings", new Document("room_type", chosenRoom)));
                        updateDocument("User", userFilter, pullOperation);
                    } else {
                        Document updateFilter = new Document("Email", loggedEmail)
                                .append("bookings.room_type", chosenRoom);
    
                        Document updateOperation = new Document("$set", new Document("bookings.$.rooms_booked", updatedRoomsBooked));
                        updateDocument("User", updateFilter, updateOperation);
                    }
    
                    Document cancelFilter = new Document("Email", loggedEmail)
                            .append("cancelbooking.room_type", chosenRoom);
    
                    Document existingCancelBooking = findFirstDocument("User", cancelFilter);
    
                    if (existingCancelBooking != null) {
                        Document updateCancelOperation = new Document("$inc", new Document("cancelbooking.$.quantity", cancelQuantity));
                        updateDocument("User", cancelFilter, updateCancelOperation);
                    } else {
                        Document canceledBooking = new Document()
                                .append("hotel_name", chosenHotel)
                                .append("room_type", chosenRoom)
                                .append("quantity", cancelQuantity)
                                .append("cancel_time", timestamp);
    
                        Document pushCancelOperation = new Document("$push", new Document("cancelbooking", canceledBooking));
                        updateDocument("User", userFilter, pushCancelOperation);
                    }
    
                    System.out.println("Booking cancellation processed successfully.");
                    bookingExists = true;
                    break;
                }
            }
    
            if (!bookingExists) {
                System.err.println("Booking not found for the specified room.");
            }
    
        } catch (NumberFormatException e) {
            System.err.println("Invalid number format for the booking quantity.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Failed to process booking cancellation.");
            e.printStackTrace();
        }
    }
    
}
