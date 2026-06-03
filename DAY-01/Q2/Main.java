// Stores the status of all 8 sectors
class PowerManager{
    byte sectorStates;

    // Turn a sector ON
    public void turnOnSector(int sectorIndex){
        sectorStates=(byte)(sectorStates|(1<<sectorIndex));
    }

    // Turn a sector OFF
    public void turnOffSector(int sectorIndex){
        sectorStates=(byte)(sectorStates&~(1<<sectorIndex));
    }

    // Check if a sector is ON
    public boolean isSectorOn(int sectorIndex){
        return (sectorStates&(1<<sectorIndex))!=0;
    }
}

public class Main{
    public static void main(String[] args){

        // Creating object
        PowerManager powerManager=new PowerManager();

        // Turning ON some sectors
        powerManager.turnOnSector(0);
        powerManager.turnOnSector(3);
        powerManager.turnOnSector(7);

        // Printing current status
        System.out.println("Sector 0: "+powerManager.isSectorOn(0));
        System.out.println("Sector 1: "+powerManager.isSectorOn(1));
        System.out.println("Sector 3: "+powerManager.isSectorOn(3));
        System.out.println("Sector 7: "+powerManager.isSectorOn(7));

        // Turning OFF sector 3
        powerManager.turnOffSector(3);

        // Checking status again
        System.out.println("After turning off Sector 3");
        System.out.println("Sector 3: "+powerManager.isSectorOn(3));
    }
}