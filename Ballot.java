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
        ArrayList<Integer> disect = new ArrayList<>(votes.size());
        for (int i = 0; i < votes.size(); i++){
            disect.add(votes.get(i));
            //System.out.println(votes.get(i));
        }
        ArrayList<Integer> removable = new ArrayList<>();
        removable.add(-1);
        if (disect.indexOf(1) == disect.lastIndexOf(1) && disect.indexOf(1) != -1){
            disect.set(disect.indexOf(1), -1);
            int j = 2;
            while (disect.indexOf(j) != -1 && j <= disect.size()){
                disect.set(disect.indexOf(j), -1);
                j++;
            }
            disect.removeAll(removable);
            if (disect.isEmpty()){
                return true;
            }
        }
        return false;
    }
    
    public void assignValue(int value, int index){
        this.votes.set(index, value);
    }
    
    public void removeCandidate(int index) {
        for (int i = 0; i < votes.size(); i++){
            if (votes.get(i) > votes.get(index)){
                votes.set(i, (votes.get(i) - 1));
            }
        }
        this.votes.set(index, -1);
    }
    
    public String toString(){
        return this.votes.toString();
    }
}
