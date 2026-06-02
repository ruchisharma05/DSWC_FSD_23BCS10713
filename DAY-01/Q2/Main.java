class PowerManager{
    byte sectorStates;

    public void turnOnSector(int sectorIndex){
        sectorStates=(byte)(sectorStates|(1<<sectorIndex));
    }

    public void turnOffSector(int sectorIndex){
        sectorStates=(byte)(sectorStates&~(1<<sectorIndex));
    }

    public boolean isSectorOn(int sectorIndex){
        return (sectorStates&(1<<sectorIndex))!=0;
    }
}

public class Main{
    public static void main(String[] args){

        PowerManager powerManager=new PowerManager();

        powerManager.turnOnSector(0);
        powerManager.turnOnSector(3);
        powerManager.turnOnSector(7);

        System.out.println("Sector 0: "+powerManager.isSectorOn(0));
        System.out.println("Sector 1: "+powerManager.isSectorOn(1));
        System.out.println("Sector 3: "+powerManager.isSectorOn(3));
        System.out.println("Sector 7: "+powerManager.isSectorOn(7));

        powerManager.turnOffSector(3);

        System.out.println("After turning off Sector 3");
        System.out.println("Sector 3: "+powerManager.isSectorOn(3));
    }
}