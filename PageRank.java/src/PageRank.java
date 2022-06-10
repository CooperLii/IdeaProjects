/**
 * Project/Class Description:
 *
 *          This program reads in a file, calculates the page rank of
 *      all the urls in that file, ask the user for a input word,
 *      then find the urls that contains that input word, sort the
 *      those urls by their page rank, then print out those urls
 *      with corresponding page rank in a descending order and the
 *      number of the urls that contain that word.
 *
 * Known bugs: none
 */

// imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// class PageRank
public class PageRank {
    // urls,content
    private static HashMap<String, String> urlContent;
    // urls,[times pointing out,times being pointed]
    private static HashMap<String, int[]> urlCount;
    // p website, set of p' website that points to p
    private static HashMap<String, HashSet<String>> map;
    // url, page ranks
    private static HashMap<String, Double> result;

    /**
     * The constructor instantiates the hash maps.
     */
    public PageRank() {
        urlContent = new HashMap<>();
        urlCount   = new HashMap<>();
        map        = new HashMap<>();
        result     = new HashMap<>();
    }
    /**
     * The main method call the methods to read in the file ,
     * calculate the page rank and sort the page ranks, then
     * print out the information.
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // make an object
        PageRank pag = new PageRank();
        // call the method to read in the file
        //pag.readData("Simple.txt");
        pag.readData("CS.txt");

        // run the method 10 times and update the values
        // so that the numbers are stabilized
        double res = 0.0;
        for (int i = 0; i < 10; i++) {
            pag.calculate();
            for (String webs : result.keySet()) {
                res = result.get(webs);
                result.put(webs, res);
            }
        }

        // a scanner asks user for a input
        Scanner readWord = new Scanner(System.in);

        // heading
        System.out.println("Enter your search terms:");

        // read in the user's input
        String word = readWord.next();

        // the number of websites that contain the word user enters
        int hits = 0;

        // print out the URLs that contain user's input and the page rank
        // of those URLs.
        // count how many URLs contain the user's input
        System.out.println("Rank        URL");
        HashMap<String, Double> needSort = new HashMap<>();
        for (String urls : urlContent.keySet()) {
            if (urlContent.size() > 0 && urlContent.get(urls).contains(word)) {
                needSort.put(urls,result.get(urls));
                hits++;
            }
        }

        // print out the URLs that contain the input with corresponding page rank
        // in descending order
        int o=0;
        for (String s:pag.sort(needSort).keySet()
        ) {
            if ( o < 20)
            {
                System.out.printf("%.7f%s%s\n", pag.sort(needSort).get(s), "   ", s);
                o++;
            }
        }

        // print out how many hits there were
        System.out.println("There were " + hits + " hits");
    }

    /**
     *  This method scan and read in the file, and store the information into hashmaps
     *  for later calculation use.
     * @param filename is the file to read in.
     * @throws FileNotFoundException
     */
    public void readData(String filename) throws FileNotFoundException {
        //create the file
        File file = new File(filename);
        // a string that represents the lines that scanner read in
        String line;
        StringBuffer content = new StringBuffer();
        boolean isFirstUrl = false;
        String firstUrl = null;
        // a scanner that scans the file
        Scanner scan = new Scanner(file);
        // this loop read in the file
        // and store the information into hash maps for later calculation part
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            if (line.contains("PAGE")) {
                isFirstUrl = true;
                content = new StringBuffer();
                continue;
            } else if (!line.equals("")&&line.substring(0,4).contains("http")) {
                if (isFirstUrl) {
                    firstUrl = line;
                    isFirstUrl = false;
                }
                else {
                    urlContent.put(firstUrl,content.toString().toLowerCase());
                    if (firstUrl != null) {
                        int[] count = urlCount.get(firstUrl);
                        if (count == null) {
                            count = new int[]{1, 0};
                        } else {
                            count[0]++;
                        }
                        urlCount.put(firstUrl, count);
                    }
                    HashSet<String> hashSet = map.get(line);
                    if (hashSet == null) {
                        hashSet = new HashSet<>();
                    }
                    hashSet.add(firstUrl);
                    map.put(line, hashSet);
                    int[] count = urlCount.get(line);
                    if (count == null) {
                        count = new int[]{0, 1};
                    } else {
                        count[1]++;
                    }
                    urlCount.put(line, count);
                }
            } else {
                content.append(" " + line);
            }
        }
        scan.close();
    }

    /**
     * This method calculates the page rank of each url in the file.
     * @return the page rank of each url in the file.
     */
    public double calculate() {
        // the initial page rank
        double initialPR = (double) 1 / urlCount.size();
        // a small numeric constant
        final double d = 0.15;
        // the number of pages
        int n = urlCount.keySet().size();
        // the number of all pages that refer to p
        String keys = "";
        for (String key : urlContent.keySet()) {
            keys = key;
            int pageReferToP = urlCount.get(keys)[1];
        }

        // the page rank of p’, where p’ ϵ M(p), that is, p’ has a link to p
        double primePR = 0.0;
        // the total number of outgoing links from page p’
        int LpPrime = 0;

        // first assign every url the initial page rank, if they don't have any value
        for (String strs : urlCount.keySet()) {
            if (result.get(strs) == null) {
                result.put(strs, initialPR);
            }
        }

        // a double represent the page rank
        double pageRank = 0.0;
        // the summation part of the formula
        double sum = 0.0;
        // a temporary string
        String web = "";
        // this loop calculates the page rank of each url
        for (String url : urlCount.keySet()) {
            sum = 0.0;
            if (map.keySet().contains(url)) {
                for (String pPrimes : map.get(url)) {
                    sum += result.get(pPrimes) / urlCount.get(pPrimes)[0];
                }
            }
            pageRank = ((1 - d) / n) + d * (sum);
            pageRank = Math.round(pageRank * 10000000.0) / 10000000.0;
            result.put(url, pageRank);
            web = url;
        }
        // return the page rank of each url
        return result.get(web);
    }

    /**
     * This method sort the urls by their page rank that matches users' input word
     * and return them as a hash map.
     * @param needToSort is the hash map that has urls with the page rank that
     *                   match users' input that need to be sorted.
     * @return a new hash map that has sorted url with their page rank.
     */
    public HashMap<String, Double> sort (HashMap<String,Double>needToSort){
        // Create a list from elements of HashMap
        List<Map.Entry<String, Double> > list = new LinkedList<>(needToSort.entrySet());
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2)
            {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        // put data from sorted list to hashmap
        HashMap<String, Double> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Double> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        // return a sorted hash map
        return temp;
    }
}
