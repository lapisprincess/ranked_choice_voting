import java.util.ArrayList;

public class Ballot{
    private ArrayList<Integer> votes;
    
    public Ballot(int candidateSize) {
        this.votes = new ArrayList<>();
        for (int i = 0; i < candidateSize; i++) {
            this.votes.add(-1);
        }
    }
    
    public boolean checkValidity() {
        boolean sequence = true;
        for (int i = 1; i <= votes.size(); i++) {
            int curr_index = votes.indexOf(i);
            int last_index = votes.lastIndexOf(i);
            if (curr_index < 0 && sequence == true) {
                sequence = false;
            } else if (curr_index < 0 && sequence == false) {
                return false;
            }

            if (curr_index != last_index) {
                return false;
            }
        }
        return true;
    }
    
    public void assignValue(int value, int index){
        this.votes.add(index, value);
    }
    
    public void removeCandidate(int index) {
        for (int i = 0; i < votes.size(); i++){
            if (votes.get(i) > votes.get(index)){
                votes.set(i, (votes.get(i) - 1));
            }
        }
        this.votes.add(index, -1);
    }
    
    public String toString(){
        return this.votes.toString();
    }
}
