/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package repodepot;

import java.util.Scanner;
import java.util.Arrays;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.InsertOneResult;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

import org.bson.conversions.Bson;

public class App {

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        System.out.println("________________________________________________________________");
        System.out.println("    /'\\      /'''''\\   /'''''\\   /'''''\\   |'''''''\\   |'''''\\  ");
        System.out.println("   /   \\    |  ____/  |  ____/  |  |'|  |  |  | |   |  | |'|  \\ ");
        System.out.println("  /=====\\   |  |      |  |      |  | |  |  |   _   /   | | |   |");
        System.out.println(" /       \\  |  ''''\\  |  ''''\\  |  |_|  |  |  | \\  \\   | |_|  / ");
        System.out.println("/         \\  \\_____/   \\_____/   \\_____/   |__|  \\__\\  |_____/  ");
        System.out.println("________________________________________________________________");
        System.out.println("");
        System.out.println("[1] Login");
        System.out.println("[2] Sign up");
        String log_or_sign = scnr.nextLine();
        if (log_or_sign.equals("1")){
            System.out.println("Welcome!! Lets get you logged in!!");
            System.out.println("Enter your username: ");
            String userName = scnr.nextLine();  // Read user input
            System.out.println("Password: ");
            String pass = scnr.nextLine();  // Read user input


            System.out.println("Your account has still not been found, would you like to create a new one?[Y or N]: ");
            String newOne = scnr.nextLine();  // Read user input
            if(newOne.equals("Y")){
                if(create()){
                    home();
                }
            }


            //log in
        } else if (log_or_sign.equals("2")){

            if(create()){
                System.out.println("Your account was successfully created!!");
                home();
            }
        }
        scnr.close();
    }







    static Boolean create(){
        Scanner scnr = new Scanner(System.in);
        System.out.println("Welcome new user!! Lets get you an account");
            System.out.println("Please enter a username(must not contain spaces): ");
            String userName = scnr.nextLine();  // Read user input
            while(userName.contains(" ") || alreadyUsed(userName)){
                if(userName.contains(" ")){
                    System.out.println("Please enter a username(must not contain spaces): ");
                    userName = scnr.nextLine();  // Read user input
                }else{
                    System.out.println("That username is already taken please select a new one: ");
                    userName = scnr.nextLine();  // Read user input
                }
                
            }


            System.out.println("Please enter a password(must be at least 8 characters): ");
            String pass = scnr.nextLine();  // Read user input
            while(pass.length()< 8){
                System.out.println("Password must be at least 8 characters: ");
                pass = scnr.nextLine();  // Read user input
            }
            System.out.println("Please re-enter your password: ");
            String checkpass = scnr.nextLine();  // Read user input
            while(!(pass.equals(checkpass))){
                System.out.println("Your password does not match, please re-enter the correct password: ");
                checkpass = scnr.nextLine();  // Read user input
            }



            System.out.println("Please enter a security question to verify your account: ");
            String securityQuestion = scnr.nextLine();  // Read user input

            System.out.println("Please enter your bio: ");
            String bio = scnr.nextLine();  // Read user input

            System.out.println("Please enter youre name: ");
            String name = scnr.nextLine();  // Read user input
        scnr.close();
        String uri = "mongodb+srv://emCorey:test1234@cluster0.cwb4w.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("DolphinMangoCore");
            MongoCollection<Document> collection = database.getCollection("users");
            try {
                // Inserts a sample document describing a movie into the collection
                InsertOneResult result = collection.insertOne(new Document()
                        .append("_id", new ObjectId())
                        .append("UserName", userName)
                        .append("Password", pass)
                        .append("Question", securityQuestion)
                        .append("Bio", bio)
                        .append("Name", name));
                // Prints the ID of the inserted document
                //redirect to home
                return true;
            
            // Prints a message if any exceptions occur during the operation
            } catch (MongoException me) {
                return false;
            }
        }
        
    }

    static String[] getUser(String name){
        String uri = "mongodb+srv://emCorey:test1234@cluster0.cwb4w.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        try (MongoClient mongoClint = MongoClients.create(uri)){
            MongoDatabase database = mongoClient.getDatabase("DolphinMangoCore");
            MongoCollection<Document> collection = databse.getCollection("users");
            Document doc = collection.find(eq("username", name)).first();
            if (doc == null){
                return;
            }
            else{
                user = new String[2];
                user[0] = doc.get("username");
                user[1] = doc.get("password");
                return user;
            }
        }
    }


    static Boolean alreadyUsed(String username){
        String uri = "mongodb+srv://emCorey:test1234@cluster0.cwb4w.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("DolphinMangoCore");
            MongoCollection<Document> collection = database.getCollection("users");
            Bson projectionFields = Projections.fields(
                    Projections.include("UserName"),
                    Projections.excludeId());
                    Document doc = collection.find(eq("UserName", username)).projection(projectionFields).first();
                    if(doc == null){
                        return false;
                    }
                    return true;
        }
    }

    public static void home(){
        Scanner scnr = new Scanner(System.in);

        System.out.println("Welcome [USERNAME]");
        System.out.println("[1] Send Message");
        System.out.println("[2] See Users");
        System.out.println("[3] Rooms");
        System.out.println("[4] Check Messages");
        System.out.println("[5] Update Profile");
        String opt = scnr.nextLine();

        switch(opt){
            case "1":
                //send message
            break;
            case "2":
                //see users
            break;
            case "3":
                //rooms
            break;
            case "4":
                //check messages
            break;
            case "5":
                //update profile
            break;
        }
    }

    public static void createRoom(String roomName, String decription){
        String uri = "mongodb+srv://emCorey:test1234@cluster0.cwb4w.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("DolphinMangoCore");
            MongoCollection<Document> collection = database.getCollection("rooms");
            try {
                // Inserts a sample document describing a movie into the collection
                InsertOneResult result = collection.insertOne(new Document()
                        .append("name: ", roomname)
                        .append("decription: ", decription));
                // Prints the name of the inserted document
                System.out.println("Success! you created the " + roomname + " room." );
            
            // Prints a message if any exceptions occur during the operation
            } catch (MongoException me) {
                System.err.println("Unable to insert due to an error: " + me);
            }
        }
        if(createRoom(roomName, description)){
            home();
        }
    }
}




/*String connectionString = "mongodb+srv://emCorey:test1234@cluster0.cwb4w.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("DolphinMangoCore");
            MongoCollection<Document> collection = database.getCollection("users");
            
            
        }*/
