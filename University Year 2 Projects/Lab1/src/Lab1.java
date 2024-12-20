import java.util.Arrays;

/**
 * This class reads uses two different approaches to generate all permutations for a set of numbers,
 * one approach utilizes an array, while the other uses a doubly linked list
 *
 * IDE used:IntelliJ
 *
 * @Author Justin Verhoog
 * ST# 7756034
 * @Version 1.0
 * @Since 2024-09-19
 */


public class Lab1{

    public static void main(String[] args) {
        /*
        System.out.println("Array implementation 3:");
        //Generate the permutations
        ArrayImplementation.generateArrays(3,new int[]{1,2,3});
        System.out.println();

        System.out.println("Array implementation 4:");
        //Generate the permutations
        ArrayImplementation.generateArrays(4,new int[]{1,2,3,4});
        System.out.println();

         */

        //Create an instance of the DoublyLinkedlistClass
        DoublyLinkedList list = new DoublyLinkedList();
        list.numNodes = 3;

        System.out.println("List implementation 3:");
        //Generate the lists
        Node list2 = list.fillList(3,null);

        list.generateDLL(3,list2);

/*
        list.numNodes = 4;

        System.out.println("List implementation 4:");
        //Generate the lists
        list.generateDLL(4,list.fillList(4,null));

 */

    }// Lab1
}// Lab1