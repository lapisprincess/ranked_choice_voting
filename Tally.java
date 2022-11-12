import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

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

    private void removeCandidate(int candidate) {
        for (int i = 0; i < ballots.size(); i++) {
            ballots.peek().removeCandidate(candidate);
            ballots.offer(ballots.poll());
        }
    }

    private void disectFile(Scanner sc) {
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
        this.ballots = new LinkedList<>();
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

    public String toString() {
        String out = "[\n";
        for (int i = 0; i < ballots.size(); i++) {
            Ballot curr_ballot = ballots.poll();
            out += curr_ballot.toString() + "\n";
            ballots.offer(curr_ballot);
        }
        out += "]";
        return out;
    }
    
    public int totalValidBallots() {
        int out = 0;
        for (int i = 0; i < ballots.size(); i++) {
            if (ballots.peek().checkValidity()) {
                out++;
            }
            ballots.offer(ballots.poll());
        }
        return out;
    }

    public int totalBallots() {
        return ballots.size();
    }

    public int getCandidateVote(int candidate) {
        int total = 0;
        for (int i = 0; i < ballots.size(); i++) {
            Ballot curr_ballot = ballots.poll();
            if (curr_ballot.checkValidity() && curr_ballot.getFirstVote() == candidate) {
                total++;
            }
            ballots.offer(curr_ballot);
        }
        return total;
    }

    public int getCandidatePercentage(int candidate) {
        double fraction = (double)
            this.getCandidateVote(candidate) / this.totalValidBallots();
        return (int) (fraction * 100);
    }

    public void doTheThing() {
        System.out.println();
        System.out.println("--------NEXT ROUND---------");
        System.out.println("Valid ballots: " + totalValidBallots());
        int winner = -1;
        int loser = 0;
        while (getCandidateVote(loser) == 0) {
            loser++;
        }
        for (int i = 0; i < candidates.size(); i++) {
            if (getCandidateVote(i) > 0) {
                if (getCandidatePercentage(i) > 50) {
                    winner = i;
                }
                if (getCandidateVote(i) < getCandidateVote(loser)) {
                    loser = i;
                }
                System.out.print(candidates.get(i) + ": ");
                System.out.print(getCandidateVote(i) + " votes ");
                System.out.println("(" + getCandidatePercentage(i) + "%)");
            }
        }
        if (winner < 0) {
            System.out.println(candidates.get(loser) + " eliminated");
            removeCandidate(loser);
            doTheThing();
        } else {
            System.out.println("---------RESULTS-----------");
            System.out.println("WINNER BY MAJORITY: " + candidates.get(winner));
            System.out.println("---------------------------");
        }
    }
}
