import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

import java.util.Scanner;
import java.io.File;

public class Tally {
    ArrayList<String> candidates;
    Stack<String> ballots;
    File file;

    public Tally(String file_name) {
        candidates = new ArrayList<>();
        ballots = new Stack<>();
        file = new File(file_name);
    }
}
