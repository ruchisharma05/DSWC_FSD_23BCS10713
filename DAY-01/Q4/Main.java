// Custom checked exception
class HardwareLockException extends Exception{
    HardwareLockException(String message){
        super(message);
    }
}

// Custom unchecked exception
class SensorCorruptionException extends RuntimeException{
    SensorCorruptionException(String message){
        super(message);
    }
}

// Dummy telemetry stream class
class TelemetryStream implements AutoCloseable{

    // Simulating telemetry reading
    public void readData() throws HardwareLockException{

        // Simulating hardware lock error
        throw new HardwareLockException("Telemetry file is locked");
    }

    // Automatically called when resource is closed
    public void close(){
        System.out.println("Telemetry stream closed");
    }
}

public class Main{
    public static void main(String[] args){

        // Using try-with-resources
        try(TelemetryStream telemetryStream=new TelemetryStream()){

            telemetryStream.readData();

        }
        catch(HardwareLockException e){
            System.out.println("Checked Exception: "+e.getMessage());
        }
        catch(SensorCorruptionException e){
            System.out.println("Unchecked Exception: "+e.getMessage());
        }
    }
}