class DNASequencer{
    StringBuilder sequenceBuilder;

    DNASequencer(){
        sequenceBuilder=new StringBuilder(100000);
    }

    public void ingestSequence(char[] sensorData){
        for(int i=0;i<sensorData.length;i++){
            sequenceBuilder.append(sensorData[i]);
        }
    }

    public void mutateDNA(String target,String replacement){
        int position=sequenceBuilder.indexOf(target);

        if(position!=-1){
            sequenceBuilder.replace(
                    position,
                    position+target.length(),
                    replacement
            );
        }
    }

    public String getSequence(){
        return sequenceBuilder.toString();
    }
}

public class Main{
    public static void main(String[] args){

        DNASequencer dnaSequencer=new DNASequencer();

        char[] sensorData={'A','C','T','G','A','C','T','G'};

        dnaSequencer.ingestSequence(sensorData);

        System.out.println("Original DNA: "
                +dnaSequencer.getSequence());

        dnaSequencer.mutateDNA("ACT","GGG");

        System.out.println("After Mutation: "
                +dnaSequencer.getSequence());
    }
}