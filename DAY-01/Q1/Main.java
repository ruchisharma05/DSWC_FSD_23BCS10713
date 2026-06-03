// Base class for all space vessels
abstract class SpaceVessel{
    short shipId;
    boolean operationalStatus;
    char fleetClassification;

    // Constructor to initialize vessel details
    SpaceVessel(short shipId,boolean operationalStatus,char fleetClassification){
        this.shipId=shipId;
        this.operationalStatus=operationalStatus;
        this.fleetClassification=fleetClassification;
    }
}

// MiningShip inherits from SpaceVessel
class MiningShip extends SpaceVessel{
    byte bayNumber;
    float[][] oreWeights;
    long totalFleetValue;

    // Constructor to initialize mining ship data
    MiningShip(short shipId,boolean operationalStatus,char fleetClassification,
               byte bayNumber,float[][] oreWeights,long totalFleetValue){
        super(shipId,operationalStatus,fleetClassification);
        this.bayNumber=bayNumber;
        this.oreWeights=oreWeights;
        this.totalFleetValue=totalFleetValue;
    }

    // Calculates total weight of all ore containers
    public float calculateTotalOreWeight(){
        float totalOreWeight=0;

        for(int i=0;i<oreWeights.length;i++){
            for(int j=0;j<oreWeights[i].length;j++){
                totalOreWeight+=oreWeights[i][j];
            }
        }

        return totalOreWeight;
    }

    // Finds the heaviest ore container
    public float findHeaviestContainer(){
        float heaviestContainer=oreWeights[0][0];

        for(int i=0;i<oreWeights.length;i++){
            for(int j=0;j<oreWeights[i].length;j++){
                if(oreWeights[i][j]>heaviestContainer){
                    heaviestContainer=oreWeights[i][j];
                }
            }
        }

        return heaviestContainer;
    }
}

public class Main{
    public static void main(String[] args){

        // 2D array representing ore containers in bays
        float[][] oreWeights={
                {1200.5f,2300.8f,1800.2f},
                {4000.0f,2500.4f,3200.1f},
                {1500.6f,5000.0f,2800.9f}
        };

        // Fleet stored as a 1D array of SpaceVessel objects
        SpaceVessel[] fleetShips=new SpaceVessel[1];

        // Adding a mining ship to the fleet
        fleetShips[0]=new MiningShip(
                (short)1200,
                true,
                'A',
                (byte)3,
                oreWeights,
                45000000000L
        );

        // Typecasting to access MiningShip methods
        MiningShip miningShip=(MiningShip)fleetShips[0];

        // Displaying results
        System.out.println("Total Ore Weight: "+miningShip.calculateTotalOreWeight());
        System.out.println("Heaviest Container: "+miningShip.findHeaviestContainer());
    }
}