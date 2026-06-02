abstract class SpaceVessel{
    short shipId;
    boolean operationalStatus;
    char fleetClassification;

    SpaceVessel(short shipId,boolean operationalStatus,char fleetClassification){
        this.shipId=shipId;
        this.operationalStatus=operationalStatus;
        this.fleetClassification=fleetClassification;
    }
}

class MiningShip extends SpaceVessel{
    byte bayNumber;
    float[][] oreWeights;
    long totalFleetValue;

    MiningShip(short shipId,boolean operationalStatus,char fleetClassification,
               byte bayNumber,float[][] oreWeights,long totalFleetValue){
        super(shipId,operationalStatus,fleetClassification);
        this.bayNumber=bayNumber;
        this.oreWeights=oreWeights;
        this.totalFleetValue=totalFleetValue;
    }

    public float calculateTotalOreWeight(){
        float totalOreWeight=0;

        for(int i=0;i<oreWeights.length;i++){
            for(int j=0;j<oreWeights[i].length;j++){
                totalOreWeight+=oreWeights[i][j];
            }
        }

        return totalOreWeight;
    }

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

        float[][] oreWeights={
                {1200.5f,2300.8f,1800.2f},
                {4000.0f,2500.4f,3200.1f},
                {1500.6f,5000.0f,2800.9f}
        };

        SpaceVessel[] fleetShips=new SpaceVessel[1];

        fleetShips[0]=new MiningShip(
                (short)1200,
                true,
                'A',
                (byte)3,
                oreWeights,
                45000000000L
        );

        MiningShip miningShip=(MiningShip)fleetShips[0];

        System.out.println("Total Ore Weight: "+miningShip.calculateTotalOreWeight());
        System.out.println("Heaviest Container: "+miningShip.findHeaviestContainer());
    }
}