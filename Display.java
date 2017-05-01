/** \file Display.java
 * \brief Display loads GUI and handles game flow
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * @class Display
 * @brief Display loads GUI and handles game flow
 * ver 1.2
 * 
 */

public class Display extends JFrame implements ActionListener {

  JPanel contentPane;
  //panel
  ArrayList<JPanel> allPlayerPanels = new ArrayList<JPanel>();
  JPanel topPanel=new JPanel();
  JPanel ppanel=new JPanel();
  JPanel dpanel=new JPanel();
  JPanel ppaneltwo=new JPanel();
  //person's label
  ArrayList<JLabel> allPlayerLabels = new ArrayList<JLabel>();
  JLabel lblPlayer=new JLabel();
  JLabel lblDealer=new JLabel();
  JLabel lblPlayertwo=new JLabel();
  //button
  JTextPane winner=new JTextPane();
  ArrayList<JButton> allHitButtons = new ArrayList<JButton>();
  ArrayList<JButton> allStandButtons = new ArrayList<JButton>();
  
  JButton dealButton=new JButton();
  JButton hitButton=new JButton();
  JButton standButton=new JButton();
  JButton standButtonTwo=new JButton();
  JButton resetButton=new JButton();
  JButton hitButtontwo=new JButton();

  //Card labels
  ArrayList<ArrayList<JLabel>> allCardLabels = new ArrayList<ArrayList<JLabel>>();
  ArrayList<JLabel> piCardLabels = new ArrayList<JLabel>();
  ArrayList<JLabel> dealerCardLabels = new ArrayList<JLabel>();
  
  //Player array details
  Player[] allPlayer;
  int nPlayers;
  GameLogic currentGame = new GameLogic();

  /**
   * Main
   */
  public static void main(String[] args) {
  
  }

  /**
   * Create the frame.
   */
  public Display(Player[] allP, GameLogic logic) {
    allPlayer = allP.clone();
    nPlayers = allPlayer.length;
    currentGame = logic;
    
    setTitle("BLACKJACK");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 1000, 500);
    setResizable(false);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);

    topPanel.setLayout(new FlowLayout());
    
    dealButton.setText(" Deal");  //Deal button
    dealButton.addActionListener(this);
    
    resetButton.setText(" Reset");  //Reset button
    resetButton.addActionListener(this);
    resetButton.setEnabled(false); 
    
    topPanel.add(dealButton);
    topPanel.add(resetButton);
    topPanel.add(winner);
    
    for (int i=1; i<nPlayers; i++){ //Adds a panel and hit and stand button for each player
      allCardLabels.add(new ArrayList<JLabel>(piCardLabels));
      allHitButtons.add(new JButton());
      allStandButtons.add(new JButton());
      allPlayerPanels.add(new JPanel());
      allPlayerLabels.add(new JLabel(allPlayer[i].getName()));
    }
    int count = 0;
    for (JButton hitButton:allHitButtons){  //Sets information for each hit button
      count++;
      hitButton.setText(" Player " + count + ": Hit");
      hitButton.addActionListener(this);
      hitButton.setEnabled(false);
    }
    count = 0;
    for (JButton standButton:allStandButtons){//Sets information for each stand button
      count++;
      standButton.setText(" Player " + count + ": Stand");
      standButton.addActionListener(this);
      standButton.setEnabled(false); 
    }
    count =0;
    for (JPanel playerPanel:allPlayerPanels){//Adds buttons to each panel
      count++;
      playerPanel.add(allHitButtons.get(count));
      playerPanel.add(allStandButtons.get(count));
      playerPanel.add(allPlayerLabels.get(count));
    }
    
    lblDealer.setText(allPlayer[0].getName());
    dpanel.add(lblDealer);
            
    setLayout(new BorderLayout());
    contentPane.add(dpanel, BorderLayout.SOUTH);
    contentPane.add(topPanel, BorderLayout.NORTH);
    count =0;
    for (JPanel playerPanel:allPlayerPanels){//Adds buttons to each panel
      if (count%2==0) contentPane.add(playerPanel, BorderLayout.WEST);
      else contentPane.add(playerPanel, BorderLayout.EAST);
      count++;
    }
  }
  
  public void updateLabels(){
    float score = allPlayer[0].getScore();
    if (score-Math.floor(score)>0) lblDealer.setText(allPlayer[0].getName() + ": " + Float.toString(score));
    else lblDealer.setText(allPlayer[0].getName() + ": " + Integer.toString((int) score));
    
    int count = 0;
    for (JLabel pLabel:allPlayerLabels){
      count++;
      score = allPlayer[count].getScore();
      if (score-Math.floor(score)>0)  pLabel.setText(allPlayer[count].getName() + ": " + Float.toString(score));
      else pLabel.setText(allPlayer[count].getName() + ": " + Integer.toString((int) score));
    }
  }
  
    /// Sets all player's cards icons
  public void setAllIcons(boolean showDCard){
    int i = 0;
    dpanel.add(lblDealer);
    for (JLabel dCardi:dealerCardLabels){        //set icons for all dealer cards
      if (i==0&&!showDCard) dCardi.setIcon(new ImageIcon("BACK.jpg"));
      else dCardi.setIcon(new ImageIcon(allPlayer[0].getCard(i).getPic()));    //go through hand and get picture of each card
      dpanel.add(dCardi);
      i++;
    }
    
    for (ArrayList pCardLabels:allCardLabels){
      i=1;
      allPlayerPanels.get(i).add(allPlayerLabels.get(i));
      for (int j=0; j<pCardLabels.size(); j++){        //set icons for all dealer cards
        JLabel temp = (JLabel) pCardLabels.get(j);
        temp.setIcon(new ImageIcon(allPlayer[i].getCard(j).getPic()));
        allPlayerPanels.get(j).add(temp);
      }
      i++;
    }
  }
    /// Action listener: checks if buttons are pressed
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(dealButton)) {
      
      for (int i=0; i<allPlayer[0].getHandLength(); i++){
        dealerCardLabels.add(new JLabel());
      }
      int count = 0;
      for (ArrayList pCardLabels:allCardLabels){
        count++;
        for (int i=0; i<allPlayer[count].getHandLength(); i++){
          pCardLabels.add(new JLabel());
        }
      }
      
      setAllIcons(false);
      
      for (JButton hitButton:allHitButtons){  //Enables each hit button
        hitButton.setEnabled(true);
      }
      for (JButton standButton:allStandButtons){//Enables each stand button
        standButton.setEnabled(true);
      }
      resetButton.setEnabled(true);
      
      dpanel.repaint();
      
      for (JPanel playerPanel:allPlayerPanels){//Adds buttons to each panel
        count++;
        playerPanel.repaint();
        playerPanel.add(allStandButtons.get(count));
        playerPanel.add(allPlayerLabels.get(count));
      }
      
      revalidate();
      
      dealButton.setEnabled(false);
      
      for (int i=0; i<nPlayers; i++){
        if (PlayerLogic.Check21(allPlayer[i])==true) {  //Checks for a premature win with instant 21
          displayWinner(allPlayer[i]);
          allPlayer[i].increaseScore(1);
          updateLabels();
          endGame();
        }
      }
    }
    int count = 0;
    for (JButton hitButton:allHitButtons){  //Checks each hit button
      count++;
      if (e.getSource().equals(hitButton)) {
        if (currentGame.checkHit(count)==true) {
          allPlayer[count].hit();
        }
        allCardLabels.get(count).add(new JLabel());
        setAllIcons(false);
        revalidate();
        
        if (currentGame.checkHit(count)==false) {
          hitButton.setEnabled(false);
          standButton.setEnabled(false);
        }
        
        checkEndGame();
        
        if (PlayerLogic.Check21(allPlayer[count])==true) {
          endGame();
          displayWinner(allPlayer[count]);
          allPlayer[count].increaseScore(1);
          updateLabels();
        }
      }
    }
    for (JButton standButton:allStandButtons){//Checks each stand button
      if (e.getSource().equals(standButton)) { 
        standButton.setEnabled(false);
        hitButton.setEnabled(false);
        
        checkEndGame();
      }
    }
    if (e.getSource().equals(resetButton)) {
      dpanel.removeAll(); //Resets all panels
      for (JPanel playerPanel:allPlayerPanels){
        playerPanel.removeAll();
      }
      
      dealButton.setEnabled(true);
      for (JButton hitButton:allHitButtons){  //Disables each hit button
        hitButton.setEnabled(false);
      }
      for (JButton standButton:allStandButtons){//Disables each stand button
        standButton.setEnabled(false);
      }
      resetButton.setEnabled(false);
      
      dealerCardLabels.clear();
      for (ArrayList pCardLabels:allCardLabels){
        pCardLabels.clear();
      }
      
      setAllIcons(false);
      revalidate();
      
      currentGame.newGame();
      winner.setText("");
      
      allPlayer = currentGame.getPlayers().clone();
    }
  }
    
    /// Awards appropriate amount of points to winner player(s)
  public void checkEndGame(){
    if (checkGameOver()) {
      dealerDraw();
      Player[] winners = currentGame.matchOutcome();
      if (winners.length>1) {//more than two people win
        endGame();
        for (Player pi:winners) pi.increaseScore(1/(float) winners.length);
        displayWinner(winners);
      }
      else {
        endGame();
        winners[0].increaseScore(1);
        displayWinner(winners[0]);
      }
      updateLabels();
    }
  }
  
    /// Displays the winning player in the winner label
  public void displayWinner(Player p){  
     winner.setText("Winner: " + p.getName());
  }
    
    /// Displays the winning players in the winner label
  public void displayWinner(Player[] p){
    String outStr;
    outStr = "Winners: ";
    for (int i=0; i<p.length; i++){
      if (i<p.length-1) outStr+=p[i].getName() + ", ";
      else outStr+=p[i].getName();
    }
    winner.setText(outStr);
  }
    
    /// Checks if the game is over by checking all hit buttons
    /// Outputs a boolean whether game is over
  public boolean checkGameOver(){
    for (JButton hitButton:allHitButtons){  //If any hitButtons are enabled, game is not over
      if (hitButton.isEnabled()==true) {
        return false;
      }
    }
    return true;
  }
  
    /// Dealer draws until he can't hit, then show his cards
  public void dealerDraw(){
    while (currentGame.checkHit(0)){ 
        allPlayer[0].hit();
        dealerCardLabels.add(new JLabel());
        setAllIcons(true);
        revalidate();
      }
  }
    
    /// Resets GUI after game end.
  public void endGame(){
    setAllIcons(true);
    revalidate();
    dpanel.repaint();
  
    standButton.setEnabled(false);
    standButtonTwo.setEnabled(false);
    hitButton.setEnabled(false);
    resetButton.setEnabled(true);
    hitButtontwo.setEnabled(false);
  }
}