import java.util.Random;

/**
 * Test program for the StreamMedian class Output from this program should be:
 * 60.0 54.0 48.0 47.5 47.0 47.5 48.0 50.5 48.0 50.5 53.0 53.5 54.0 57.0 60.0
 * 57.0 60.0 60.5 60.0 57.0  *
 * Note that your instructor will supply a different testing program when he
 * grades your programs.
 *
 * @author Stuart Hansen
 * @version February 11, 2015
 */
public class TestStreamMedian {

    public static void main(String[] args) {
        StreamMedian sm = new StreamMedian();
        Random random = new Random(0);
        for (int i = 0; i < 20; i++) {
            int r = random.nextInt(100);
            sm.insert(r);
            double x = sm.getMedian();
            System.out.print(x + " ");
        }

        System.out.println();
    }
}
