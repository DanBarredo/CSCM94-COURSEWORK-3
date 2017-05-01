/** \file Dealer.java
 * \brief Dealer handles Player who is also a Dealer.
 */
 
/**
 * @class Player
 * @brief Dealer deals Cards and is the competition.
 * ver1.4
 */
class Dealer extends Player{
  /// Creates a Dealer
  public Dealer(){} ;
  
  /// Dealer's constructor is inherited from Player with the parameter Dealer.
  public Dealer(Lock l){
    super("Dealer", l);
  }
  
  /// This is a test function.
  public static void main(String[] args){ 
    ;
  }
}
