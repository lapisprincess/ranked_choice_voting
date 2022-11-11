import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Tally {
    private ArrayList<String> candidates;
    private Queue<Ballot> ballots;

    private File file;
    private Scanner sc;

    public Tally(String file_name) throws FileNotFoundException {
        file = new File(file_name);
        sc = new Scanner(file);
        this.disectFile(sc);
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
                    int curr_vote_length = curr_vote.length();
                    score = ((int) curr_vote.charAt(curr_vote_length-1)) - 48;
                }
                if (score > 0 && score <= candidates.size()) {
                    curr_ballot.assignValue(score, i);
                }
                line = line.substring(i_comma + 1, line.length());
                i++;
            }
            String curr_vote = line.substring(0, line.length());
            if (curr_vote.length() > 0 && !curr_vote.equals("No Rank")) { 
                int curr_vote_length = curr_vote.length();
                int score = ((int) curr_vote.charAt(curr_vote_length-1)) - 48;
                curr_ballot.assignValue(score, i);
            }

            ballots.add(curr_ballot);
        }
    }
}
