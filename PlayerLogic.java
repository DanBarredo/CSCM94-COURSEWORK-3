/** \file PlayerLogic.java
 * \brief PlayerLogic handles Card logic checks.
 */
 
/**
 * @class PlayerLogic
 * @brief PlayerLogic handles Card logic checks
 * ver 1.4
 * provides function for checking card count, to see if player can hit, stand or is above 21. \n
 * Dependant on the class GameLogic for the function sumHand. \n 
 * The classes Dealer and Player provide types.
 * 
 */
 
public class PlayerLogic {
  
  /// Checks if the cards add up to 21 \n 
  /// Input a Player type \n
  /// Outputs a true or false statement \n
  public static boolean Check21(Player p) {
    if (GameLogic.sumHand(p) == 21) {return true;}
    else return false;
  }

  /// Checks if the cards in the Dealer's hand are greater than 17 \n
  /// Input a dealer type \n
  /// Outputs a true or false statement \n
  public static boolean AllowHit(Dealer d) {
    if (GameLogic.sumHand(d) < 17) {return true;}
    else return false;
  }

  /// Checks if the cards in the Person's hand are less than 21 \n
  /// Input a Person type \n
  /// Outputs a true or false statement \n
  public static boolean AllowHit(Person q) {
    if (GameLogic.sumHand(q) < 21) {return true;}
    else return false;
  }

  
  /// Checks if the cards in the Dealer's hand are greater than 16 \n
  /// Input a Dealer type \n
  /// Outputs a true or false statement \n
  public static boolean AllowStand(Dealer d) {
    if (GameLogic.sumHand(d) > 16) {return true;}
    else return false;
  }

  /// Redundant class put in for symmetry- it just returns true
  public static boolean AllowStand(Person q) {
    return true;
  }
}