/**
 * This class reads a file and generates output based on the DNA strand provided and the permutation restrictions.
 *
 * IDE used: IntelliJ
 *
 * @Author Justin Verhoog
 * ST# 7756034
 * @Version 1.0
 * @Since 2024-09-27
 */

import BasicIO.*;

public class Assignement1 {

    public static void main(String[] args) {

        DNAList list1 = null;
        DNAList list2 = new DNAList('A', null);
        System.out.println(compare(list1,list2));



        //Create file to read from
        ASCIIDataFile file = new ASCIIDataFile();

        //Gets the number of lists from the file
        int numLists = file.readInt();

        //Performs the proper operations on each list from the file
        for (int i = 0; i < numLists; i++) {
            //Reads in the list
            int length = file.readInt();
            DNAList list = null;
            DNAList listHeader = null;
            for (int j = 0; j < length; j++) {
                if (list == null) {
                    list = new DNAList(file.readChar(), null);
                    listHeader = list;
                } else {
                    list.next = new DNAList(file.readChar(), null);
                    list = list.next;
                }
            }
            //Prints out the list information
            System.out.print(listHeader + "\t");
            //Creates an extra tab if the list of DNA is small to help with formatting
            if (length < 4) {
                System.out.print("\t");
            }
            //Prints the GC content
            System.out.print("GC content: " + gcContent(listHeader, 0) + "\t");
            //Prints the reverse complement
            System.out.print("Reverse complement: " + reverseComplement(listHeader, null) + "\t");
            //Creates an extra tab if the list of DNA is small to help with formatting
            if (length < 4) {
                System.out.print("\t");
            }
            //Prints out if the strand is self complementary
            System.out.print("Self-complementary: " + compare(listHeader, reverseComplement(listHeader, null)));
            System.out.println();
        }
        //Generates the strands based off the values in the file
        generateStrands(file.readInt(), 0, file.readInt(), null);

        file.close();
    }

    /**
     * Generates DNA strands based off the number of bases and the GC rate. Only creates strands that are not self complementary.
     *
     * @param n    the number of bases in the strand
     * @param k    the position to be filled
     * @param rate the GC content of the strands
     * @param dna  the current list of the DNA strand
     */
    public static void generateStrands(int n, int k, int rate, DNAList dna) {
        //Continue generating new bases until the strand reaches the required length
        if (k < n) {
            DNAList temp = dna;
            //Traverses the current strand to get to the correct position to be filled
            for (int i = 0; i < k - 1; i++) {
                temp = temp.next;
            }
            //If the list is null create a new temporary DNA base otherwise add a new temporary base to the next Base
            if (temp == null) {
                temp = new DNAList('A', null);
                dna = temp;
            } else {
                temp.next = new DNAList('A', null);
                temp = temp.next;
            }
            //Set the base to the correct letter and then recursively call the next base to be generated (I don't need to initially set it to 'A' since it does by default previously)
            generateStrands(n, k + 1, rate, dna);
            temp.setBase('C');
            generateStrands(n, k + 1, rate, dna);
            temp.setBase('G');
            generateStrands(n, k + 1, rate, dna);
            temp.setBase('T');
            generateStrands(n, k + 1, rate, dna);
        }
        //If the strand is the correct size check if it should be printed and if it should then print it
        else if (!compare(dna, reverseComplement(dna, null)) && gcContent(dna, 0) == rate) {
            System.out.println(dna);
        }
    }

    /**
     * Compares two lists to see if they are the same by recursion
     *
     * @param   list1 the first list to be compared
     * @param   list2 the second list to be compared
     * @return  true if the lists are the same or false otherwise
     */
    public static boolean compare(DNAList list1, DNAList list2) {
        //if either list is null that means that either both are null meaning they are the same or only one is null meaning they are not the same so return that value
        if (list1 == null || list2 == null) {
            return list1 == null && list2 == null;
        }
        //Check if the current base is the same. If they are the same check the next base otherwise return false
        else {
            if (list1.getBase() == list2.getBase()) {
                return compare(list1.next, list2.next);
            } else {
                return false;
            }
        }
    }

    /**
     * Returns the reverse complement of the DNA strand.
     *
     * @param   original the strand that the reverse complement should be based on.
     * @param   revComp  the reverse complement of the original that is being constructed
     * @return  the reverse complement of the original strand
     */
    public static DNAList reverseComplement(DNAList original, DNAList revComp) {
        if (original == null) {
            return revComp;
        }
        return reverseComplement(original.next, new DNAList(getComplement(original.getBase()), revComp));
    }

    /**
     * Returns the complement of the passed base
     *
     * @param   base the base of DNA to find the complement of
     * @return  the complement of the base
     */
    public static char getComplement(char base) {
        if (base == 'A') {
            return 'T';
        } else if (base == 'T') {
            return 'A';
        } else if (base == 'C') {
            return 'G';
        } else if (base == 'G') {
            return 'C';
        } else {
            throw new RuntimeException("improper base type");
        }
    }

    /**
     * Gets the GC content of a strand of DNA
     *
     * @param   strand the strand to calculate GC content
     * @param   count  the current GC count
     * @return  the GC content of the strand
     */
    public static int gcContent(DNAList strand, int count) {
        if (strand == null) {
            return count;
        } else {
            if (strand.getBase() == 'G' || strand.getBase() == 'C') {
                count++;
            }
            return gcContent(strand.next, count);
        }
    }

}
