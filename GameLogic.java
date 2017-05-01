/** \file GameLogic.java
 * \brief GameLogic handles .
 */
 
/**
 * @class GameLogic
 * @brief GameLogic deals cards and is the competition.
 * ver1.2
 */
class GameLogic {
  private int matchCount;   /// How many games 
  private int turnCount; /// Round of the game
  private Lock lock = new Lock(); /// 4 decks of cards
  private static Dealer dealer = new Dealer(); /// Opposition for players
  private static Person[] allPersons = new Person[2]; /// Player characters
  private static Player[] allPlayers = new Player[3]; /// Players and Dealer
  private String[] allNames = new String[2]; /// Player names
  
    ///Empty Constructor
  public GameLogic(){}
  
    ///Constructor
  public GameLogic(String[] names){
    allNames = names.clone();
    matchCount = 0;
    turnCount = 0;
    
    lock = createLock();
    dealer = new Dealer(lock); //Initialises players/dealers
    createPersons(names, lock);
    createPlayerArray();
    
  }
  
    ///Initialises the lock
  public Lock createLock(){
    Lock lock = new Lock(); //Create lock 
    lock.shuffle();         //shuffle it
    return lock;
  }
  
    ///Begins a new game
  public void newGame(){
    if (lock.CardsLeft()<100) lock = createLock();  //Checks if lock should be reinitialised
    
    dealer.setLock(lock);
    dealer.clearHand();
    for (Person p:allPersons) {
      p.setLock(lock);
      p.clearHand();
    }
  }
  
    ///Initialises n Person instances
  public void createPersons(String[] n, Lock lock){ //Initialise person players
    for (int i=0; i<n.length; i++) {      //set players
      allPersons[i] = new Person(n[i], lock);
    }
  }
  
    ///Creates a Player object array to hold all players and the dealer
  public void createPlayerArray(){ //Create array of all players including dealer
    allPlayers[0] = dealer;
    
    for (int i=1; i<=allPersons.length; i++) {      //set players
      allPlayers[i] = allPersons[i-1];
    }
  }
  
    ///Returns allPlayers array
  public Player[] getPlayers(){
    return allPlayers;
  }
  
    ///Determines the winning player(s) of a match
  public Player[] matchOutcome(){
    matchCount++;
    Player[] sortedPlayer = new Player[3];
    sortedPlayer = sortHands().clone();    //sort players highest hand first
    
    Player[] winningPlayers = new Player[2];    //first check for draw
    if (sumHand(sortedPlayer[0])==sumHand(sortedPlayer[1])){  //if top two hands are equal
      if (sumHand(sortedPlayer[1])==sumHand(sortedPlayer[2])) {  //if all hands are equal
        if (sumHand(sortedPlayer[0])<22) return sortedPlayer;  //if all equal hands<22, everyone wins
        else return null;                             //else everyone loses
      } else if (sumHand(sortedPlayer[0])<22) {
        winningPlayers[0] = sortedPlayer[0];
        winningPlayers[1] = sortedPlayer[1];
        return winningPlayers;
      }
    }
    
    Player[] winningPlayer = new Player[1];
    for (Player p:sortedPlayer) {         //first hand under 22 is the winner
      if (sumHand(p)<22) {  
        winningPlayer[0] = p;
        return winningPlayer;
      }
    }
    return null;
  }
  
    ///Returns the sum of a player's hand
  public static int sumHand(Player p){
    int sumHand=0;
    int ace=0;
    for (int i=0; i<p.getHandLength(); i++) {
      if (p.getCardValue(i)==11) { //value of ace is set to 11
        ace++;
        sumHand+=11;
      } else sumHand+=p.getCardValue(i);
    }
    if (ace>=1&&sumHand>21) sumHand-=ace*10; //if there was an ace and hand is over 21, effectively change ace value to 1
    return sumHand;
  }
  
    ///Returns an array containing players sorted by hand size
  public Player[] sortHands(){
    int n = allPlayers.length;
    Player[] outP = new Player[n];
    outP = allPlayers.clone();
    int k;
    
    for (int m=0; m<n; m++) { //sorts array using bubble sort
      for (int i=0; i<n-1; i++){
        k = i+1;
        if (sumHand(outP[i])<sumHand(outP[k])) {
          Player tempP = outP[i];
          outP[i] = outP[k];
          outP[k] = tempP;
        }
      }
    }
    return outP;
  }
  
    ///Checks if a Player can hit
  public boolean checkHit(int i) {
    if (i==0) return PlayerLogic.AllowHit(dealer);
    return PlayerLogic.AllowHit(allPersons[i-1]);
  }
  
    ///Returns player index if they get 21. returns -1 if turn over. returns -2 if match over.
  public int runTurn(){
    turnCount++;
    boolean allBust = true; //assume all bust at first
    boolean allStand = true;
    
    for (int i=0; i<allPersons.length; i++) {   //cycle through players
      if (PlayerLogic.AllowHit(allPersons[i])==true) {      //check bust
        allBust = false;
        System.out.print("Player: " + allPersons[i].getName() + ". Hit? "); //check input for hit
        String inp = System.console().readLine();
        if (inp.equals("y")) {
          allStand = false;
          allPersons[i].hit();
        }
        if (sumHand(allPersons[i])==21) return i; //return premature winner if 21
        if (sumHand(allPersons[i])>21) System.out.println(allPersons[i].getName() + " went bust!");
      }
    }
    
    if (allBust||allStand) return -2;         //if all are bust or all stand end match
    
    if (PlayerLogic.AllowHit(dealer)==true) dealer.hit(); //dealer gets extra card
    
    return -1;        //turn over
  }
  
  public static void main(String[] args){
  }
}