import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        System.out.println("==============================="); 
        System.out.println("Rank-Choice Vote Ballot Counter");
        System.out.println("By Riley and Tilda :)");
        System.out.println("===============================");

        String file_name = "RCVRaw.csv";
        try {
            Tally t = new Tally(file_name);
            t.doTheThing();
        } catch (FileNotFoundException E) {
            System.out.println("File not found! " + E.toString());
        } catch (Exception E) {
            System.out.println("Something went wrong! " + E.toString());
        }
    }
}
