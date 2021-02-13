package com.example.deneme;

import android.content.Intent;


import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle; // this is becouse I turn to the side but also I closed turning sides

import java.util.Random;
// Puzzle Activity
public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons;

    private int linenumber;//it means y-axis
    private int x;
    private int hint_id;//this is for hint

    public MainActivity2() {
        buttons= new Button[9][9];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent(); // getting intent

        String first = intent.getStringExtra("firstnumber");
        String second =intent.getStringExtra("secondnumber");
        TextView f = findViewById(R.id.textView2);
        TextView s =findViewById(R.id.textView3);
       f.setText(first);
        s.setText(second);
         linenumber=Integer.parseInt(second);
         x=Integer.parseInt(first);
        GridLayout ll = findViewById(R.id.grid);
        ll.setColumnCount(x);
        ll.setRowCount(linenumber);
        for(int i=1;i<linenumber*x;i++)
            addButton(i);
        addButton(0);

        fillButtons();
        shuffle();

        for(int i=0;i<linenumber;i++){
            for(int j=0;j<x;j++){
                buttons[i][j].setOnClickListener(this);
            }
        }

        hint_id=0;
        final Button hint=findViewById(R.id.button_hint);
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    calculateDistance();
                    move(hint_id);
                    if(controlWin()){
                        Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
                        startActivity(intent);
                    }
                }
                catch (Exception e){
                    Toast.makeText(MainActivity2.this,"You can solve yourself :)",Toast.LENGTH_SHORT).show();
                }

            }
        });





    }

    boolean checkingTruePosition(int y_pos,int x_pos){			/*I wrote the keyword const here!! becouse value shouldn't change*/
        if(y_pos==linenumber-1 && x_pos==x-1){
            return buttons[y_pos][x_pos].getId() == 0;
        }
        return buttons[y_pos][x_pos].getId() == x * y_pos + x_pos + 1;							/*This is checking the number is true position or not*/
    }
    int absoluteValue(int number){
        if(number<0)
            return -1 * number;
        else
            return number;
    }
    void calculateDistance(){
        int[] position= new int[2];			/*This is inital position of blank*/
        int x_cur,y_cur;					/*This is for below algorithm*/
        int size_square=linenumber*x-1;				/*just for assigning maximum number becouse I want to find the least distance*/
        int left_block,right_block,up_block,down_block;
        left_block=size_square; right_block=size_square; up_block=size_square; down_block=size_square;
        findPosition(position,0);
        y_cur=position[0];
        x_cur=position[1];

        if(x_cur!=x-1){
            if(!checkingTruePosition(y_cur, x_cur + 1))
                right_block=absoluteValue((y_cur*linenumber+x_cur+1)-buttons[y_cur][x_cur+1].getId()); 		/*This is calculating distance for right*/

        }
        if(x_cur!=0){
            if(!checkingTruePosition(y_cur, x_cur - 1))			/*This is calculating distance for left*/
                left_block=absoluteValue((y_cur*linenumber+x_cur+1)-buttons[y_cur][x_cur-1].getId());

        }
        if(y_cur!=0){
            if(!checkingTruePosition(y_cur - 1, x_cur))				/*This is calculating distance for up*/
                up_block=absoluteValue((y_cur*linenumber+x_cur+1)-buttons[y_cur-1][x_cur].getId());
        }
        if(y_cur!=linenumber-1){
            if(!checkingTruePosition(y_cur + 1, x_cur))			/*This is calculating distance for down*/
                down_block=absoluteValue((y_cur*linenumber+x_cur+1)-buttons[y_cur+1][x_cur].getId());
        }
        if(right_block==0) {                        /*If minimum distance is 0 then I needn't to know which is less*/
            hint_id = buttons[y_cur][x_cur + 1].getId();
            return;
        }
        if(left_block==0){			/*So directly I moved the blanks*/
            hint_id=buttons[y_cur][x_cur-1].getId();
            return;
        }
        if(up_block==0) {
            hint_id = buttons[y_cur - 1][x_cur].getId();
            return;
        }
        if(down_block==0) {
            hint_id = buttons[y_cur + 1][x_cur].getId();
            return;
        }

        int min=0;
        hint_id=buttons[y_cur][x_cur].getId();
        if(right_block<=left_block){
            min=right_block;
            hint_id=buttons[y_cur][x_cur+1].getId();

        }
        else{
            min=left_block;
            hint_id=buttons[y_cur][x_cur-1].getId();

        }

        if(up_block<=down_block){
            if(up_block<=min){
                hint_id=buttons[y_cur-1][x_cur].getId();

            }
        }
        else{
            if(down_block<=min){

                hint_id=buttons[y_cur+1][x_cur].getId();
            }
        }

    }

    private void fillButtons(){
        for(int i=0;i<linenumber;i++){
            for(int j=0;j<x;j++){
                if(x*i+j+1==linenumber*x) {
                    buttons[i][j] = findViewById(0);
                    buttons[i][j].setVisibility(View.INVISIBLE);
                }
                else
                    buttons[i][j]=findViewById(x*i+j+1);
            }
        }
    }

    private void swap(Button a,Button b){
        int tempid= a.getId();
        String temptext = a.getText().toString();
        int visibility=a.getVisibility();
        a.setId(b.getId());
        a.setText(b.getText());
        a.setVisibility(b.getVisibility());
        b.setText(temptext);
        b.setId(tempid);
        b.setVisibility(visibility);
    }
    private void addButton(Integer number) {
        Button myButton = new Button(this);




        if(x==3) {
            //myButton.setWidth(150);
            myButton.setHeight(150);
            myButton.setTextSize(20);
        }
        else if (x==4) {
            //myButton.setWidth(200);
            //myButton.setHeight(20);
            //myButton.setTextSize(20);
        }


        myButton.setBackgroundColor(R.color.greenlight);


        GridLayout ll = findViewById(R.id.grid);
        ll.addView(myButton);
        myButton.setText(number.toString());
        myButton.setId(number);
    }

    @Override
    public void onClick(View view) {
        int id= view.getId();
        move(id);

        if(controlWin()){
            Intent intent = new Intent(this,MainActivity3.class);
            startActivity(intent);
        }

    }
    private void move(int id){

        int[] butonposition = new int[2];
        findPosition(butonposition,id);
        int y_cur=butonposition[0];
        int x_cur=butonposition[1];
        if(y_cur!=0){
            if(buttons[y_cur-1][x_cur].getId()==0)
                swap(buttons[y_cur-1][x_cur],buttons[y_cur][x_cur]);
        }
        if(x_cur!=0){
            if(buttons[y_cur][x_cur-1].getId()==0)
                swap(buttons[y_cur][x_cur-1],buttons[y_cur][x_cur]);
        }
        if(y_cur!=linenumber-1){
            if(buttons[y_cur+1][x_cur].getId()==0)
                swap(buttons[y_cur+1][x_cur],buttons[y_cur][x_cur]);
        }
        if(x_cur!=x-1){
            if(buttons[y_cur][x_cur+1].getId()==0)
                swap(buttons[y_cur][x_cur+1],buttons[y_cur][x_cur]);
        }


    }
    private void findPosition(int[] butonposition,int id){
        for(int i=0;i<linenumber;i++){
            for(int j=0;j<x;j++){
                if(buttons[i][j].getId()==id) {
                    butonposition[0] = i;
                    butonposition[1] = j;
                }
            }
        }

    }
    private boolean controlWin(){
        for(int i=0;i<linenumber;i++){
            for(int j=0;j<x;j++){
                if(buttons[i][j].getId()!=0) {
                    if (buttons[i][j].getId() != i * x + j+1)
                        return false;
                }
            }
        }
       return true;
    }
    private void shuffle(){
        int[] blankposition = new int[2];

        for(int i=0;i<40;i++) {
            findPosition(blankposition, 0);
            int y_cur = blankposition[0];
            int x_cur = blankposition[1];
            if(!moveRandomly(y_cur,x_cur))
                i--;
        }
    }

    private boolean moveRandomly(int y_cur,int x_cur){
        Random rand= new Random();
            if(rand.nextInt(4)==0){
                if(y_cur!=0){
                        swap(buttons[y_cur-1][x_cur],buttons[y_cur][x_cur]);
                        return true;
                }
                else
                    return false;
            }
            if(rand.nextInt(4)==1){
                if(x_cur!=0){
                    swap(buttons[y_cur][x_cur-1],buttons[y_cur][x_cur]);
                    return true;
                }
                else
                    return false;
            }
            if(rand.nextInt(4)==2){
                if(y_cur!=linenumber-1){
                    swap(buttons[y_cur+1][x_cur],buttons[y_cur][x_cur]);
                    return true;
                }
                else
                    return false;
            }
            if(rand.nextInt(4)==3){
                if(x_cur!=x-1){
                    swap(buttons[y_cur][x_cur+1],buttons[y_cur][x_cur]);
                    return true;
                }
                else
                    return false;
            }
            return false;
    }


}