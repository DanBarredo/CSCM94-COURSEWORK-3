/** \file Interface.java
 * \brief Interface handles the main GUI.
 */
 


import java.awt.BorderLayout;
import java.awt.*;
import java.awt.event.*;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JComponent;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.util.ArrayList;

/**
 * @class Interface
 * @brief Interface handles the GUI.
 * ver1.4
 */
class Interface extends JFrame implements ActionListener {

private JPanel contentPane; /// Main content window

    /// Initialise window 
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Interface frame = new Interface();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  JButton Go = new JButton("Continue"); /// Button to continue game
  JButton Play = new JButton("Play Blackjack"); /// Button to start game
  private int numberofplayers=1; /// Number of people playing the game
  String[] allNames; /// Array of player names
  Player[] allPlayers; /// Array of players
  String[] allScores;  /// Array of loaded scores
  ArrayList<JTextField> allPlayerTxts = new ArrayList<JTextField>();  /// Output player one text field
  JTextField p2 = new JTextField(15); /// Output player two text field
  ArrayList<JLabel> allPlayerLabels = new ArrayList<JLabel>();///Array of labels describing text fields

    /// Update window between:
    /// + Beginning the game yea 
    /// + Update player information
    /// + Choose player
  public void actionPerformed(ActionEvent e) {
    if(e.getSource().equals(Go)) {
      this.dispose(); 
      createFrame(numberofplayers);
    } else if(e.getSource().equals(Play)) {
      allNames = new String[numberofplayers];
      for (int i=0; i<numberofplayers; i++){
        allNames[i] = allPlayerTxts.get(i).getText();
      }
      this.dispose(); 
      initGUI();
    } else {
      JComboBox cb = (JComboBox)e.getSource();
      String playeramount = (String)cb.getSelectedItem();
      
      updatePlayers(playeramount);
    }
  }
    
    /// Change number of players to current number of players playing
  public void updatePlayers(String amount) {
    numberofplayers = Integer.parseInt(amount);
  }

    /// Initialise game user interface
  public void initGUI() {
    GameLogic logic;
    if (allScores==null) logic = new GameLogic(allNames);
    else logic = new GameLogic(allNames, allScores);
    allPlayers = logic.getPlayers().clone();
    Display runGame = new Display(allPlayers, logic);
    runGame.setVisible(true);
  }
    
    /// Setup window layout. Constructor no parameters.
  public Interface() {
    setTitle("Blackjack");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100,100,700,250);
    setResizable(false);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);

    JPanel background = new JPanel();
    background.setBackground(new Color(34, 139, 34));
    contentPane.add(background, BorderLayout.NORTH);

    JPanel dropdown = new JPanel();
    dropdown.setBackground(new Color(34, 139, 34));
    contentPane.add(dropdown, BorderLayout.CENTER);

    JPanel content = new JPanel();
    content.setBackground(new Color(34, 139, 34));
    contentPane.add(content, BorderLayout.SOUTH);

    JPanel logo = new JPanel();
    logo.setBackground(new Color(34, 139, 34));
    contentPane.add(logo, BorderLayout.NORTH);

    JLabel image = new JLabel("");
    image.setIcon(new ImageIcon("Blackjack.png"));
    logo.add(image);

    JLabel dropDown = new JLabel("Select number of players: ");
    dropdown.add(dropDown);
  
    String[] PlayerNumber = {"1", "2", "3", "4"};
    JComboBox NumberOfPlayers = new JComboBox(PlayerNumber);
    NumberOfPlayers.setSelectedIndex(0);
    NumberOfPlayers.addActionListener(this);
    dropdown.add(NumberOfPlayers);

    Go.addActionListener(this);
    dropdown.add(Go);
    
  }

    /// Setup window layout. Constructor with parameters.
    /// Parameter integer "nPlayers" is number of players. Range one to four.
  public Interface(int nPlayers) {
    updatePlayers(String.valueOf(nPlayers));
    setTitle("Blackjack");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100,100,600,300);
    setResizable(false);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);

    JPanel background = new JPanel();
    background.setBackground(new Color(34, 139, 34));
    contentPane.add(background, BorderLayout.NORTH);

    JPanel dropdown = new JPanel();
    dropdown.setBackground(new Color(34, 139, 34));
    contentPane.add(dropdown, BorderLayout.CENTER);

    JPanel content = new JPanel();
    content.setBackground(new Color(34, 139, 34));
    contentPane.add(content, BorderLayout.SOUTH);

    JPanel logo = new JPanel();
    logo.setBackground(new Color(34, 139, 34));
    contentPane.add(logo, BorderLayout.NORTH);
 
    JPanel Instance = new JPanel();
    Instance.setBackground(new Color(34, 139, 34));
    contentPane.add(Instance, BorderLayout.SOUTH);

    JLabel image = new JLabel("");
    image.setIcon(new ImageIcon("Blackjack.png"));
    logo.add(image);
    
    for (int i=1; i<=nPlayers; i++){
      allPlayerTxts.add(new JTextField(15));
      allPlayerLabels.add(new JLabel());
    }
    
    int count = 0;
    for (JLabel pLbl:allPlayerLabels){
      count++;
      pLbl.setText("Player " + count + "'s name: ");
    }
    for (JTextField p:allPlayerTxts){
      p.setPreferredSize(new Dimension(100, 25));
      p.addActionListener(this); 
    }
    for (int i=0; i<nPlayers; i++){
      dropdown.add((JLabel) allPlayerLabels.get(i));
      dropdown.add((JTextField) allPlayerTxts.get(i)); 
    }
    
    Play.addActionListener(this);
    Instance.add(Play);
  }

    /// Place window in operating system or throw error.
  public static void createFrame(final int bb) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          System.out.println("Frame parameter: " + bb);  ///TESTLINE
          Interface frame2 = new Interface(bb);
          frame2.setVisible(true);
        } catch (Exception e) {}
      }
    });
  }
}