/** \file Player.java
 * \brief Player handles .
 */
 import java.util.ArrayList;
/**
 * @class Player
 * @brief
 * ver 1.4
 */

class Player {
  
  private Lock lock = new Lock();
  private ArrayList<Card> hand = new ArrayList<Card>();
  private String name;
  private float score;
  
    ///Empty constructor
  public Player(){}
  
    ///Constructor
  public Player(String n, Lock l){  
    name = n;
    this.lock = l;     //Make sure all objects are using the same lock
    this.createHand();
  }
  
  public Player(String n, Lock l, String sc){  
    name = n;
	score = Float.parseFloat(sc);
    this.lock = l;     //Make sure all objects are using the same lock
    this.createHand();
  }
  
    ///Creates hand with two cards
  public void createHand(){ 
    hand.add(lock.dealCard());
    hand.add(lock.dealCard());
  }
  
    ///Clears hand
  public void resetPlayer(){
    hand.clear();
  }
  
    ///Returns the name variable
  public String getName(){
    return name;
  }
  
    ///Returns the score variable
  public float getScore(){
    return score;
  }
  
  public String getScoreString(){
	String scr = String.valueOf(score);
    return scr;
  }
  
    ///Returns the length of the hand variable
  public int getHandLength(){
    return hand.size();
  }
  
    ///Returns the value of the i'th card in hand
  public int getCardValue(int i){
    int j=0;
    int val=0;
    for (Card iCard:hand) {
      if (j==i) val=iCard.getValue();
      j++;
    }
    return val;
  }
  
    ///Returns the card object in the i'th place of hand
  public Card getCard(int i){
    int j=0;
    Card getCard = new Card(0,0);
    for (Card iCard:hand) {
      if (j==i) getCard = iCard;
      j++;
    }
    return getCard;
  }
  
    ///Adds a card from the lock to the hand
  public void hit(){
    hand.add(lock.dealCard());
  }
  
    ///Sets the lock to the passed lock
  public void setLock(Lock l){
    lock = l;
  }
  
    ///Resets the handd
  public void clearHand(){
    hand.clear();
    createHand();
  }
  
    ///Increases score by the passed amount
  public void increaseScore(float inc){
    score+=inc;
  }
  
    ///Returns information about the player as a string
  public String toString(){
    String outStr = ("Name: " + name + ". Hand: ");
    
    int i=0;
    for (Card iCard:hand) {
      if (i==0) outStr += iCard.toString();     //Initialise string with first card
      else outStr += " and " + iCard.toString(); //add other hands after "and"
      
      i++;
    }
    return outStr;
  }
  
  
  public static void main(String[] args){ 
    
  }
}