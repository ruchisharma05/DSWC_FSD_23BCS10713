import java.util.concurrent.atomic.AtomicInteger;

// Stores drone return count and emergency status
class DroneHive{

    // Safe counter for multiple threads
    AtomicInteger totalDronesReturned=new AtomicInteger(0);

    // Shared emergency flag
    volatile boolean emergencyAbort=false;

    // Called when a drone returns
    public void droneReturned(){
        totalDronesReturned.incrementAndGet();
    }

    // Called when a storm is detected
    public void detectStorm(){
        emergencyAbort=true;
    }
}

public class Main{
    public static void main(String[] args){

        // Creating hive object
        DroneHive droneHive=new DroneHive();

        // Simulating drone returns
        droneHive.droneReturned();
        droneHive.droneReturned();
        droneHive.droneReturned();

        // Storm detected
        droneHive.detectStorm();

        // Printing results
        System.out.println("Returned Drones: "
                +droneHive.totalDronesReturned.get());

        System.out.println("Emergency Abort: "
                +droneHive.emergencyAbort);
    }
}