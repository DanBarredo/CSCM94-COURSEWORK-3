import java.util.ArrayList;
import java.util.List;

class Test {
  private static Lock lock = new Lock();
  private static ArrayList<ArrayList<Card>> hand = new ArrayList<ArrayList<Card>>();
  private static ArrayList<Card> handin = new ArrayList<Card>();
  /// This is a test function.
  public static void main(String[] args){
    handin.add(lock.dealCard());
    handin.add(lock.dealCard());
    
    hand.add(new ArrayList<Card>(handin));
    hand.add(new ArrayList<Card>(handin));
    int i=1;
    
    //System.out.println(hand.get(i).getRank());
    for (ArrayList handi:hand){
      Card temp = (Card) handi.get(i);
      
    }
    System.out.println(hand);
  }
}
