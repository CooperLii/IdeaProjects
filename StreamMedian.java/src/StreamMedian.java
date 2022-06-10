/**
 * Project/Class Description:
 *
 *              This program takes in integers and put them seperately
 *          into 2 different priority queues, then find the median of these
 *          integers by using those two priority queues.
 *
 * Known bugs: none
 */

// import
import java.util.*;

// class StreamMedian
public class StreamMedian {
    // a min heap containing the larger half
    // of the data encountered so far.
    public static PriorityQueue<Integer> bigger;
    // a max heap containing the smaller half
    // of the data encountered so far
    public static PriorityQueue<Integer> smaller;

    // the constructor instantiates the priority queues,
    // and make the smaller priority queue a max heap.
    public StreamMedian() {
         bigger = new PriorityQueue<> ();
         smaller = new PriorityQueue<>(
                new Comparator<Integer>() {
                    @Override
                    public int compare(Integer a, Integer b) {
                        return b - a;
                    }
                });
    }

    // This program will be tested against professor's main class
    public static void main(String[] args) { }

    /**
     * This method add the next element of the stream to the data
     * used for calculating the median.
     * @param i is the element of the stream.
     */
    public void insert(Integer i) {
        if (smaller.size() == bigger.size()) {
            smaller.add(i);
            if (!smaller.isEmpty()&&!bigger.isEmpty())
            {
                if (smaller.peek()>bigger.peek()){
                int temp = smaller.poll();
                int temp2 = bigger.poll();
                smaller.add(temp2);
                bigger.add(temp);}
            }
        } else if (!(smaller.peek() < i)) {
            bigger.add(smaller.poll());
            smaller.add(i);
        } else
            bigger.add(i);
    }


    /**
     * This method find and return the median of
     * all the data inserted.
     *
     *  If two queues have a different number of size, take the biggest number from
     * the smaller priority queue, and that's the median.
     *  If the queues have a same number of size, take the biggest number from
     * the smaller priority queue and take the smallest number from the
     * bigger priority queue, then sum them and divide the result by 2 to
     * get the median.
     * @return the median
     */
    public double getMedian() {
        double median=0.0;
        if (!smaller.isEmpty() || !bigger.isEmpty()) {
            if (bigger.size() == smaller.size()) {
                median = ((double) (smaller.peek() + bigger.peek())) / 2;
            } else
                median = (double)smaller.peek();
        }

        // return the median.
        return median;
    }
}
