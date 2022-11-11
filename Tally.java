import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Tally {
    private ArrayList<String> candidates;
    private Stack<Ballot> ballots;

    private File file;
    private Scanner sc;

    public Tally(String file_name) throws FileNotFoundException {
        file = new File(file_name);
        sc = new Scanner(file);
        this.disectFile(sc);

        System.out.println(candidates.toString());
    }

    public void disectFile(Scanner sc) {
        // register candidates
        this.candidates = new ArrayList<>();
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
        this.ballots = new Stack<>();
        while (sc.hasNext()) {
            String line = sc.nextLine();
            Ballot curr_ballot = new Ballot(candidates.size());
            int i = 0;
            while (line.contains(",")) {
                int i_comma = line.indexOf(',');
                String curr_vote = line.substring(0, i_comma);
                int score = -1;
                if (!curr_vote.equals("No Rank")) {
                    score = ((int) curr_vote.charAt(8)) - 48;
                }
                if (score > 0 && score <= candidates.size()) {
                    curr_ballot.assignValue(score, i);
                }
                line = line.substring(i_comma + 1, line.length());
                i++;
            }

            System.out.println(curr_ballot.toString());
            if (curr_ballot.checkValidity()) {
                ballots.add(curr_ballot);
            }
        }
    }
}
