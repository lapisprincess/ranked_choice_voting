import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Tally {
    private ArrayList<String> candidates;
    private Stack<String> ballots;

    private File file;
    private Scanner sc;

    public Tally(String file_name) throws FileNotFoundException {
        candidates = new ArrayList<>();
        ballots = new Stack<>();

        file = new File(file_name);
        sc = new Scanner(file);
        this.disectFile(sc);

        System.out.println(candidates.toString());
    }

    public void disectFile(Scanner sc) {
        // register candidates
        {
            String line = sc.nextLine();
            while (line.contains(",")) {
                int i_comma = line.indexOf(',');
                String curr_word = line.substring(0, i_comma);
                candidates.add(curr_word);
                line = line.substring(i_comma + 1, line.length());
            }
            String curr_word = line.substring(0, line.length());
            if (curr_word.length() > 0) { candidates.add(curr_word); }
        }

        // register ballots
        while (sc.hasNext()) {
            String line = sc.nextLine();
            int num_candidates = candidates.size();
            Ballot curr_ballot = Ballot(num_candidates);
            

            if (curr_ballot.checkValidity()) {
                ballots.add(curr_ballot);
            }
        }
    }
}
