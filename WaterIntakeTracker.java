import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WaterIntakeTracker
{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get user age input
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();

        // Calculate daily water intake goal based on age
        int dailyIntakeGoal = getWaterIntakeByAge(age);
        System.out.println("Your recommended daily water intake goal: " + dailyIntakeGoal + " mL");

        // Get reminder interval input
        System.out.print("Enter reminder interval in seconds: ");
        int reminderInterval = scanner.nextInt();

        // Start water intake reminders
        WaterReminder reminder = new WaterReminder(reminderInterval);
        reminder.startReminder();

        // Keep program running for reminders
        System.out.println("Reminders have started. Press Ctrl+C to stop.");
        scanner.close();
    }

    // Determine water intake based on age group
    public static int getWaterIntakeByAge(int age) {
        if (age <= 12) {
            return 1000; // For children
        } else if (age <= 18) {
            return 2000; // For teenagers
        } else if (age <= 60) {
            return 2500; // For adults
        } else {
            return 2000; // For seniors
        }
    }
}


       // Water reminder class for scheduling reminders
        class WaterReminder {
       private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
       private int interval;

       public WaterReminder(int interval){
           this.interval = interval;
       }
       public void startReminder() {
             Runnable reminderTask = new Runnable() {
                 @Override
                 public void run() {

                     // Get current date and time
                     String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd hh:mm a"));
                     System.out.println("Time to drink water!");

                     showReminderGUI("It's time to drink water!", currentTime);
                     /*Display alert message with current time
                     System.out.println("Reminder: It's time to drink water! [" + currentTime + "]");
                      */

                     //Play beep sound
                     Toolkit.getDefaultToolkit().beep();
                 }
             };

               /* Schedule the task based on the user-defined interval */
               scheduler.scheduleAtFixedRate(reminderTask, 0 , interval, TimeUnit.SECONDS);
           }
           public void stopReminder(){
               scheduler.shutdown();
           }
           // Function to create and display a GUI reminder
           private void showReminderGUI(String message, String dateTime) {
               JFrame frame = new JFrame("Water Intake Reminder");
               frame.setSize(300, 200);
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setLayout(new BorderLayout());

               // Set label with message and datetime
               JLabel messageLabel = new JLabel("<html><h2>" + message + "</h2><p>" + dateTime + "</p></html>", SwingConstants.CENTER);
               messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));

               // Customize panel with color
               JPanel panel = new JPanel();
               panel.setBackground(new Color(173, 216, 230)); // Light blue color for a water theme
               panel.add(messageLabel);

               // Add panel to frame
               frame.add(panel, BorderLayout.CENTER);

               // Show frame and set to close after reminder time
               frame.setVisible(true);
               frame.setLocationRelativeTo(null); // Center on screen

               // Close the frame automatically after 10 seconds
               Timer timer = new Timer(10000, e -> frame.dispose());
               timer.setRepeats(false);
               timer.start();
           }
       }

