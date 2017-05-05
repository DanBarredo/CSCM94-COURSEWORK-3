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
import java.io.*;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @class Display
 * @brief Display loads GUI and handles game flow
 * ver 1.4
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
  JButton saveButton = new JButton("Save Game"); /// Button to save game.

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
    setBounds(100, 100, 800, 400+100*(nPlayers-1));
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
    
    saveButton.addActionListener(this);
    saveButton.setEnabled(false);
    
    topPanel.add(dealButton);
    topPanel.add(resetButton);
    topPanel.add(winner);
    topPanel.add(saveButton);
    
    for (int i=1; i<nPlayers; i++){ //Adds a panel and hit and stand button for each player
      allHitButtons.add(new JButton());
      allStandButtons.add(new JButton());
      allCardLabels.add(new ArrayList<JLabel>(piCardLabels));
      allPlayerPanels.add(new JPanel());
      allPlayerLabels.add(new JLabel(allPlayer[i].getName()));
    }
    int count = 0;
    Dimension d = new Dimension(100,26);
    for (JButton hitButton:allHitButtons){  //Sets information for each hit button
      count++;
      hitButton.setText(allPlayer[count].getName() + ": Hit");
      hitButton.setPreferredSize(d);
      hitButton.addActionListener(this);
      hitButton.setEnabled(false);
    }
    updateLabels();
    count = 0;
    for (JButton standButton:allStandButtons){//Sets information for each stand button
      count++;
      standButton.setText("Stand");
      standButton.addActionListener(this);
      standButton.setEnabled(false); 
    }
    count =0;
    for (JPanel playerPanel:allPlayerPanels){//Adds buttons to each panel
      playerPanel.add(allHitButtons.get(count));
      playerPanel.add(allStandButtons.get(count));
      playerPanel.add(allPlayerLabels.get(count));
      count++;
    }
    
    lblDealer.setText(allPlayer[0].getName());
    dpanel.add(lblDealer);
            
    setLayout(new BorderLayout());
    contentPane.add(dpanel, BorderLayout.SOUTH);
    contentPane.add(topPanel, BorderLayout.NORTH);
    
    JPanel tempPanel = new JPanel();
    tempPanel.setLayout(new GridLayout(count,1,0,10));
    
    count =0;
    for (JPanel playerPanel:allPlayerPanels){///Sets up panels
      tempPanel.add(playerPanel);
      count++;
    }
    contentPane.add(tempPanel, BorderLayout.WEST);
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
    
    i=0;
    for (ArrayList pCardLabels:allCardLabels){
      
      allPlayerPanels.get(i).add(allPlayerLabels.get(i));
      for (int j=0; j<pCardLabels.size(); j++){        //set icons for all dealer cards
        JLabel temp = (JLabel) pCardLabels.get(j);
        System.out.println("Player " + i + ". Setting card icon " + j + ". " + allPlayer[i+1].getCard(j)); ///TEST LINE
        temp.setIcon(new ImageIcon(allPlayer[i+1].getCard(j).getPic()));
        allPlayerPanels.get(i).add(temp);
      }
      i++;
    }
  }
    /// Action listener: checks if buttons are pressed
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(saveButton)) { 
      saveButton.setEnabled(false);
      saveGame(allPlayer);
    }
    if (e.getSource().equals(dealButton)) { ///Deal Button
      
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
      
      for (JButton hitButton:allHitButtons){  //Enables each hit button
        hitButton.setEnabled(true);
      }
      for (JButton standButton:allStandButtons){//Enables each stand button
        standButton.setEnabled(true);
      }
      resetButton.setEnabled(true);
      
      dpanel.repaint();
      
      count=0;
      for (JPanel playerPanel:allPlayerPanels){//Adds buttons to each panel
        playerPanel.repaint();
        playerPanel.add(allHitButtons.get(count));
        playerPanel.add(allStandButtons.get(count));
        playerPanel.add(allPlayerLabels.get(count));
        count++;
      }
      
      setAllIcons(false);
      
      revalidate();
      
      dealButton.setEnabled(false);
      saveButton.setEnabled(false);
      
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
        allCardLabels.get(count-1).add(new JLabel());
        setAllIcons(false);
        revalidate();
        
        if (currentGame.checkHit(count)==false) {
          hitButton.setEnabled(false);
          allStandButtons.get(count-1).setEnabled(false);
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
    count =0;
    for (JButton standButton:allStandButtons){//Checks each stand button
      if (e.getSource().equals(standButton)) { 
        standButton.setEnabled(false);
        allHitButtons.get(count).setEnabled(false);
        
        checkEndGame();
      }
      count++;
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
      saveButton.setEnabled(true);
    }
  }
    
    /// Awards appropriate amount of points to winner player(s)
  public void checkEndGame(){
    if (checkGameOver()) {
      dealerDraw();
      Player[] winners = currentGame.matchOutcome();
      if (winners==null) displayWinner(true);
      else if (winners.length>1) {//more than two people win
        endGame();
        for (Player pi:winners) pi.increaseScore(1/(float) winners.length);
        displayWinner(winners);
      }
      else {
        endGame();
        System.out.println(currentGame.sumHand(winners[0]));
        System.out.println(winners[0]);
        winners[0].increaseScore(1);
        displayWinner(winners[0]);
      }
      updateLabels();
    }
  }
  
    /// Displays the winning player in the winner label
  public void displayWinner(Player p){  
     if (p==null) winner.setText("No winners!");
     else winner.setText("Winner: " + p.getName());
  }
  
    /// Displays no winners in the winner label
  public void displayWinner(boolean noWinners){  
     winner.setText("No winners!");
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
      if (hitButton.isEnabled()) {
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
    currentGame.increaseMatchCount();
    if (currentGame.getMatchCount()>=21){
      
    }
  
    for (JButton hitButton:allHitButtons) hitButton.setEnabled(false);
    for (JButton standButton:allStandButtons) standButton.setEnabled(false);
    resetButton.setEnabled(true);
  }
  
    /// Writes the scores and names to two textfiles
  public void saveGame(Player[] Players) {
    try {
      PrintWriter writeNames = new PrintWriter("savednames.txt");
      PrintWriter writeScores = new PrintWriter("savedscores.txt");
      for (int i=0;i<Players.length;i++) {
        writeNames.println(Players[i].getName());
      }
      writeNames.close();
      for (int i=0;i<Players.length;i++) {
        writeScores.println(Players[i].getScoreString());
      }
      writeScores.close();
    }
    catch (IOException e) {e.printStackTrace();}
  }
}