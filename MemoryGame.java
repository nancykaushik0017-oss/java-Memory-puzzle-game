import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MemoryGame extends JFrame implements ActionListener {

    JButton[] buttons = new JButton[16];
    String[] values = {
            "A","A","B","B","C","C","D","D",
            "E","E","F","F","G","G","H","H"
    };

    boolean[] matched = new boolean[16];

    int firstIndex = -1;
    int secondIndex = -1;

  javax.swing.Timer timer;

    public MemoryGame() {

        setTitle("Memory Puzzle Game");
        setSize(500,500);
        setLayout(new GridLayout(4,4));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        shuffleArray(values);

        for(int i=0;i<16;i++){
            buttons[i] = new JButton("?");
            buttons[i].setFont(new Font("Arial",Font.BOLD,24));
            buttons[i].addActionListener(this);
            add(buttons[i]);
        }

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){

        JButton clicked = (JButton)e.getSource();
        int index = -1;

        for(int i=0;i<16;i++){
            if(buttons[i] == clicked){
                index = i;
                break;
            }
        }

        if(matched[index] || index == firstIndex) return;

        buttons[index].setText(values[index]);

        if(firstIndex == -1){
            firstIndex = index;
        }
        else{
            secondIndex = index;
            checkMatch();
        }
    }

    void checkMatch(){

        if(values[firstIndex].equals(values[secondIndex])){
            matched[firstIndex] = true;
            matched[secondIndex] = true;

            firstIndex = -1;
            secondIndex = -1;

            checkGameEnd();
        }
        else{

            timer = new javax.swing.Timer(1000, new ActionListener(){
                public void actionPerformed(ActionEvent e){

                    buttons[firstIndex].setText("?");
                    buttons[secondIndex].setText("?");

                    firstIndex = -1;
                    secondIndex = -1;

                    timer.stop();
                }
            });

            timer.setRepeats(false);
            timer.start();
        }
    }

    void checkGameEnd(){

        for(boolean m : matched){
            if(!m) return;
        }

        JOptionPane.showMessageDialog(this,"Congratulations! You matched all pairs.");
    }

    void shuffleArray(String[] array){

        Random rand = new Random();

        for(int i=array.length-1;i>0;i--){
            int j = rand.nextInt(i+1);

            String temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public static void main(String[] args){
        new MemoryGame();
    }
}