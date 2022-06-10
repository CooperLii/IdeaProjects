/**
 * Project/Class Description:
 * This program let users see Wisconsin election results in the map.
 * Known bugs: none
 */

// imports
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

// class MapPanel inherits from JPanel
public class MapPanel extends JPanel {

    // declare an instance variable of type MapData.
    MapData data;

    // declare an hash map that takes a string as the key and Election results
    // object as the value.
    private HashMap<String, ElectionResults> results;

    /**
     * MapPanel constructor
     *  Instantiate the hash map.
     *  Instantiate the instance variable of type MapData.
     *  Call readMapData to read in the file.
     *  Call readElectionResult to read in the election result in the file.
     */

    public MapPanel() {
        results = new HashMap<>();

        // Instantiate the instance variable of type MapData
        // in MapPanel constructor
        data = new MapData();

        readMapData("WI.txt");
        readElectionResult("WI2016.txt");
    }

    /**
     * The main creates a frame and add a MapPanel object to the frame
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("WI Election Result");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MapPanel obj = new MapPanel();
        frame.getContentPane().add(obj);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }


    /**
     * Inner class MapData contains the name, minimum longitude,
     * minimum latitude, maximum longitude, maximum latitude, and
     * an array list that contains a list of the sub regions of the map.
     */

    private class MapData {
        // the name of the map
        private String name;
        // the minimum longitude used on this map
        private double minLongitude;
        // the minimum latitude used on this map
        private double minLatitude;
        // the maximum longitude used on this map
        private double maxLongitude;
        // the maximum latitude used  on this map
        private double maxLatitude;
        // contains a list of the sub regions of the map
        private ArrayList<SubRegion> subRegions;

        // the constructor of class MapData
        // instantiate the array list
        MapData() {
            this.subRegions = new ArrayList<>();
        }
    }


    /**
     * Inner class SubRegion contains the number of points in the list,
     * the name of the subRegion, and an array list with the points which
     * makes up the boundary of the sub regions.
     */

    private class SubRegion {
        // the number of points in the list
        private int pointCount;
        // the name of the subRegion
        private String name;
        // the longitude and latitude points making up the boundary of the subregion.
        private ArrayList<Point.Double> points;
        // the constructor of class SubRegion
        // instantiate the array list
        SubRegion() {
            this.points = new ArrayList<>();
        }
    }


    /**
     * This method reads in the data for the map in the format of the file.
     * @param filename is the name of the file to read in.
     */

    public void readMapData(String filename) {
        Scanner sc;
        try {
            File file = new File(filename);
            sc = new Scanner(file);
            data.minLongitude = sc.nextDouble();
            data.minLatitude = sc.nextDouble();
            data.maxLongitude = sc.nextDouble();
            data.maxLatitude = sc.nextDouble();
            int numOfSubregions = sc.nextInt();

            for (int i = 0; i < numOfSubregions; i++) {
                sc.nextLine();
                sc.nextLine();
                SubRegion sub = new SubRegion();
                sub.name = sc.nextLine();
                sc.nextLine();
                sub.pointCount = sc.nextInt();
                for (int l = 0; l < sub.pointCount; l++) {
                    double x = sc.nextDouble();
                    double y = sc.nextDouble();
                    Point.Double poi = new Point.Double(x, y);
                    sub.points.add(poi);
                }
                data.subRegions.add(sub);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Override JPanel’s paint method
     * This method create a polygon for each sub region.
     * Translate each longitude and latitude pair of the sub region to pixels
     * and store them in the polygons.
     * And draw the polygon with colors depending on the election results.
     * (use colors in between red,blue and green based on the percentage
     *  of votes each party received).
     * @param g Graphics
     */

    @Override
    public void paint(Graphics g) {
        int mX = (int) ((this.getWidth() - 21) / (data.maxLongitude - data.minLongitude));
        int bX = (int) (-mX * data.minLongitude + 10);
        int mY = (int) ((this.getHeight() - 21) / (data.minLatitude - data.maxLatitude));
        int bY = (int) (-mY * data.maxLatitude + 10);

        data.subRegions.forEach(subRegion -> {
            Polygon poly = new Polygon();
            subRegion.points.forEach(pt -> {
                int pixelX = (int) (mX * pt.x + bX);
                int pixelY = (int) (mY * pt.y + bY);
                poly.addPoint(pixelX, pixelY);
            });

            g.setColor(Color.white);

            if (results.containsKey(subRegion.name)){
                ElectionResults er = results.get(subRegion.name);

                double sum = (double)(er.demVotes+er.repVotes+er.other);
                double repPer = (double)(er.repVotes)/sum;
                double otherPer = (double)(er.other)/sum;
                double demPer = (double)(er.demVotes)/sum;
                int colornum =(int)(demPer *255) ;
                int colornum2 =(int)(repPer *255) ;
                int colornum3 =(int)(otherPer *255) ;

                Color colo1 = new Color(colornum2,colornum3,colornum);

                g.setColor(colo1);

                //if ((er.demVotes > er.repVotes) && (er.demVotes > er.other)) {
                //g.setColor(Color.red);
                //} else if ((er.repVotes > er.demVotes) && (er.repVotes > er.other)) {
                //Color colo2 = new Color((int)(255*demPer),(int)(255*repPer),(int)(255*otherPer));
                //g.setColor(Color.blue);
                //}else{
                //Color colo3 = new Color((int)(255*demPer),(int)(255*repPer),(int)(255*otherPer));
                //g.setColor(Color.green);}
            }
            g.fillPolygon(poly);
            g.setColor(Color.BLACK);
            g.drawPolygon(poly);
        });
    }


    /**
     * Inner class ElectionResults
     * Takes the name of sub regions,
     * the number of votes for the Republican candidate,
     * the number of votes for the Democratic candidate,
     * and the number of votes for all other candidates.
     */

    private class ElectionResults {
        private String name;
        private int repVotes;
        private int demVotes;
        private int other;

        // Constructor for the inner class ElectionResults
        ElectionResults() { }
    }


    /**
     * This method reads in the name of sub regions
     * and three different kinds of voting results
     * in the format of the file.
     * @param filename is the name of the file to read in.
     */

    public void readElectionResult(String filename) {
        Scanner sc;
        try {
            File file = new File(filename);
            sc = new Scanner(file);
            sc.useDelimiter(",");
            //the first line should be read and ignored
            sc.nextLine();

            ElectionResults res;
            while (sc.hasNextLine()) {
                res = new ElectionResults();
                res.name = sc.next();
                res.repVotes = sc.nextInt();
                res.demVotes = sc.nextInt();
                res.other = sc.nextInt();
                sc.nextLine();
                results.put(res.name, res);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
