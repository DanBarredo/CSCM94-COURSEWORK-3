/** \file Card.java
 * \brief Initiates a card object
 */
import java.util.ArrayList;
import java.util.List;
/**
 * @class Card
 * @brief Card stores the card's information 
 * ver1.2
 */
public class Card{
    
    private int rank;
    private int suit;
    private int value;
    private String[] Ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King","Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    private String[] Suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
    
    ///Empty constructor 
    public Card(){
      rank=(int)(Math.random()*52);
      suit=(int)(Math.random()*4);
    }
    
    ///Constructs a card with rank r and suit s
    public Card(int r, int s) {
        rank = r;
        suit = s;
    }
    
    ///Returns the filename of the card's picture as a string
    public String getPic(){
      return (Ranks[rank]+Suits[suit].charAt(0)).toLowerCase()+".jpg";
    }
    
    ///Returns the rank of the card object
    public int getRank() {
        return rank;
    }
    
    ///Returns the suit of the card object
    public int getSuit() {
        return suit;
    }

    ///Returns the game value of the card object
    public int getValue() {
        if ((rank >= 9 && rank < 13) || (rank >= 22 && rank < 26) || (rank >= 35 && rank < 39) || (rank >= 48 && rank <=51))
            value = 10;
        else value = (rank+1)%13;
        if (rank == 0 || rank == 13 || rank == 26 || rank == 39)
            value = 11;
        return value;
    }
    
    ///Prints out a statement defining the card
    public String toString() {
        return Ranks[rank] + " of " + Suits[suit];
    }
    
    ///Test
    public static void main(String[] args) {
        
        Card x = new Card(51, 3);
        System.out.println(x.getRank());
        System.out.println(x.getSuit());
        System.out.println(x.toString());
        
    }
}