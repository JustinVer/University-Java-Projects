import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class DrugHeap {

    Drug[] data;
    Drug[] myHeap;
    int size;

    File iFile;
    FileWriter oFile;
    BufferedWriter writer;

    public DrugHeap(String filePath) throws IOException {
        iFile = new File(filePath);
        oFile = new FileWriter(filePath + "output");
        writer = new BufferedWriter(oFile);
    }

    /**
     *
     */
    public void readData() throws FileNotFoundException {
        Scanner tempScanner = new Scanner(iFile);
        int fileSize = -1;
        while (tempScanner.hasNext()) {
            fileSize++;
            tempScanner.nextLine();
        }
        data = new Drug[fileSize];
        tempScanner = new Scanner(iFile);
        tempScanner.useDelimiter("\t");
        tempScanner.nextLine();
        for (int i = 0; i < data.length; i++) {
            String genericName = tempScanner.next();
            String SMILES = tempScanner.next();
            String drugBankID = tempScanner.next();
            String url = tempScanner.next();
            String drugGroups = tempScanner.next();
            Double score = Double.valueOf(tempScanner.nextLine());
            data[i] = new Drug(drugBankID, genericName, SMILES, drugGroups, url, score);
        }
    }

    /**
     *
     */
    public void trickleDown(Drug[] heap, int i) {
        boolean restored = false;
        while (!restored) {
            int iDrugID = Integer.parseInt(heap[i].drugBankID.substring(2));
            int leftChildID;
            int rightChildID;
            if(2*i < heap.length && heap[2 * i] != null) {
                leftChildID = Integer.parseInt(heap[2 * i].drugBankID.substring(2));
            } else {
                leftChildID = 999999999;
            }
            if((2*i) + 1 < heap.length && heap[(2 * i) + 1]  != null) {
                rightChildID = Integer.parseInt(heap[(2 * i) + 1].drugBankID.substring(2));
            } else{
                rightChildID = 999999999;
            }

            if (iDrugID > leftChildID && leftChildID < rightChildID) {
                swap(heap,i,2 * i);
                i = i * 2;
            } else if (iDrugID > rightChildID && rightChildID < leftChildID) {
                swap(heap,i,(2 * i) + 1);
                i = (i * 2) + 1;
            } else {
                restored = true;
            }
        }
    }

    /**
     *
     */
    public void trickleUp(Drug[] heap, int i) {
        boolean restored = false;
        while (!restored) {
            int iDrugID = Integer.parseInt(heap[i].drugBankID.substring(2));

            if (iDrugID < Integer.parseInt(heap[i / 2].drugBankID.substring(2))) {
                swap(heap,i,i/2);
                i = i/2;
            } else {
                restored = true;
            }
        }
    }

    /**
     *
     */
    public void buildHeap() {
        myHeap = new Drug[data.length + 1];
        myHeap[0] = new Drug("000", null, null, null, null, null);
        size = 0;
        for (Drug drug : data) {
            size+=1;
            insert(myHeap, drug, size);
        }
    }

    /**
     *
     */
    public void swap(Drug[] heap, int i, int j) {
        Drug temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     *
     */
    public void insert(Drug[] heap, Drug myDrug, int size) {
        heap[size] = myDrug;
        trickleUp(heap, size);
    }

    /**
     *
     */
    public Drug removeMin(Drug[] heap,int size) {
        if (size > 0) {
            Drug min = heap[1];
            heap[1] = heap[size];
            heap[size] = null;
            if(heap[1] != null) {
                trickleDown(heap, 1);
            }
            return min;
        } else {
            return null;
        }
    }

    /**
     *
     */
    public void heapSort() throws IOException {
        Drug[] heap = new Drug[data.length + 1];
        heap[0] = new Drug("000", null, null, null, null, null);
        int heapSize = 0;
        for (Drug drug:data){
            heapSize += 1;
            insert(heap,drug,heapSize);
        }
        writer = new BufferedWriter(oFile);
        while (true){
            Drug sortedDrug = removeMin(heap,heapSize);
            heapSize-=1;
            if (sortedDrug != null){
                writeDrug(sortedDrug);
            }else {
                break;
            }
        }
    }
    public void writeDrug(Drug drug) throws IOException {
        writer.write(drug.genericName + "\t" + drug.SMILES + "\t" + drug.drugBankID + "\t" + drug.url + "\t" + drug.drugGroups + "\t" + drug.score);
        writer.newLine();
    }
}
