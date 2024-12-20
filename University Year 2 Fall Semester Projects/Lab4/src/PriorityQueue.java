public class PriorityQueue {
    Vertex[] heap = new Vertex[5000];
    int size = 0;
    public Vertex poll(){
        Vertex min = heap[1];
        heap[1] = null;
        swap(1,size);
        trickleDown(1);
        size--;
        return min;
    }
    public void add(Vertex vertex){
        size++;
        heap[size] = vertex;
        trickleUp(size);
    }
    private void swap(int i, int j){
        Vertex temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    private void trickleUp(int i){
        if (i/2 >0){
            if (heap[i / 2].compareTo(heap[i]) > 0){
                swap(i/2,i);
                trickleUp(i/2);
            }
        }
    }
    private void trickleDown(int i){
        if ((i*2)+1 < size){
            Vertex left = heap[i*2];
            Vertex right = heap[(i*2)+1];
            if (heap[i].compareTo(left)>0 && left.compareTo(right) <=0){
                swap(i,i*2);
            } else if (heap[i].compareTo(right)>0) {
                swap(i,(i*2)+1);
            }
        }
    }
    public boolean isEmpty(){
        return size <1;
    }
}
