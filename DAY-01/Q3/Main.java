// This class is used to store and modify DNA sequence
class DNASequencer{
    StringBuilder sequenceBuilder;

    // Creating StringBuilder with large capacity
    DNASequencer(){
        sequenceBuilder=new StringBuilder(100000);
    }

    // Adding DNA characters one by one
    public void ingestSequence(char[] sensorData){
        for(int i=0;i<sensorData.length;i++){
            sequenceBuilder.append(sensorData[i]);
        }
    }

    // Replacing a part of DNA with a new sequence
    public void mutateDNA(String target,String replacement){
        int position=sequenceBuilder.indexOf(target);

        // If the target sequence is found
        if(position!=-1){
            sequenceBuilder.replace(
                    position,
                    position+target.length(),
                    replacement
            );
        }
    }

    // Returning final DNA sequence
    public String getSequence(){
        return sequenceBuilder.toString();
    }
}

public class Main{
    public static void main(String[] args){

        // Creating DNA object
        DNASequencer dnaSequencer=new DNASequencer();

        // Sample DNA received from sensor
        char[] sensorData={'A','C','T','G','A','C','T','G'};

        // Storing DNA sequence
        dnaSequencer.ingestSequence(sensorData);

        // Printing original DNA
        System.out.println("Original DNA: "
                +dnaSequencer.getSequence());

        // Mutating ACT into GGG
        dnaSequencer.mutateDNA("ACT","GGG");

        // Printing DNA after mutation
        System.out.println("After Mutation: "
                +dnaSequencer.getSequence());
    }
}