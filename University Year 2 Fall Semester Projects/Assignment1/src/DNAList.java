/**
 * This class is a basic list class for representing a strand of DNA
 *
 * IDE used: IntelliJ
 *
 * @Author Justin Verhoog
 * ST# 7756034
 * @Version 1.0
 * @Since 2024-09-27
 */
public class DNAList {
    // char representing the base of the DNA
    private char base;

    //Reference to the next node of DNA
    public DNAList next;

    /**
     * Constructs a new DNAList
     * @param   base the base of the DNA
     * @param   next the next DNA in the strand
     */
    public DNAList(char base, DNAList next){
        if(base != 'A' && base != 'C' && base != 'G' && base != 'T'){
            throw new RuntimeException(base + " is not a proper base type");
        }
        this.base = base;
        this.next = next;
    }

    /**
     * @return the base of the DNA
     */
    public char getBase() {
        return base;
    }

    /**
     * Sets the base of the DNA
     * @param   newBase the new base of the DNA
     */
    public void setBase(char newBase){
        if(newBase != 'A' && newBase != 'C' && newBase != 'G' && newBase != 'T'){
            throw new RuntimeException(newBase + " is not a proper base type");
        }else{
            this.base = newBase;
        }
    }

    /**
     * Reformat the printing of the DNA strand so that it is formatted properly
     * @return  the formatted string
     */
    public String toString(){
        StringBuilder sequence = new StringBuilder();
        DNAList list = this;
        while (list != null){
            sequence.append(list.base);
            list = list.next;
        }
        return sequence.toString();
    }
}
