/** \file Lock.java
 * \brief Lock handles Card decks including the actions of shuffle and deal.
 */
 import java.util.Arrays;
/**
 * @class Lock
 * @brief Lock handles Card decks including the actions of shuffle and deal
 * 
 * Lock handles 4 decks of Cards- shuffles them and deals them out. It keeps track of them and the remaining Cards left available to play.
 * ver 1.4
 */
public class Lock {
    
  private Card[] lock; /// Create an array of Cards for lock.
  private int lockSize = 208; /// Create 4 decks of Cards.
  private int count = 0; /// This is the current top of deck in the array of Cards.
  private int CardsUsed; /// This is the number of Cards that have been used and disCarded.
  
    /// Constructor of lock. \n
    /// Creates 4 decks of Cards. 
  public Lock() {
      int index = 0;
      lock = new Card[lockSize];
      for (int i = 0; i < 52; ++i) {
          for (int j = 0; j < 4; ++j) {
              lock[index] = new Card(i, j);
              index++;
          }
      }
      CardsUsed = 0;
  }
  
    /// Shuffle randomises the Cards.
  public void shuffle() {
      for (int i = 0; i < lockSize; ++i) {
          int r = i + (int)(Math.random() * (lockSize-i));
          Card temp = lock[i];
          lock[i] = lock[r];
          lock[r] = temp;
      }
  }
  
    /// Takes the current top Card and returns it.
  public Card dealCard() {
      Card curr = lock[count]; //current Card
      lock[count] = null;
      count++;
      CardsUsed++;
      lockSize--;
      return curr;
  }
  
    /// The Cards remaining. \n
    /// Update this function if changing the number of decks used.
  public int CardsLeft() {
      return 208 - CardsUsed;
  }
  
    /// Returns the private variable lockSize.
  public int getSize() {
      return lockSize;
  }
  
    /// Returns the Cards in lock.
  @Override
  public String toString() {
      return Arrays.toString(lock);
  }
  
  public static void main(String[] args) {
  }
}