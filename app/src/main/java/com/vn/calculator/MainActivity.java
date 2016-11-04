package com.vn.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    private Button[] btnNumbers;
    private Button btnDelete;
    private Button btnDivide;
    private Button btnMultiply;
    private Button btnMinus;
    private Button btnCross;
    private Button btnSolve;
    private Button btnDot;

    private TextView txtInput;
    private TextView txtResult;

    private String strInput = "";
    private String strNumber = "";

    private List<Double> listNumber;
    private List<Character> listOperator;

    private boolean checkDot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] numberID = new int[]{R.id.btn_num00, R.id.btn_num01, R.id.btn_num02, R.id.btn_num03,
                                    R.id.btn_num04, R.id.btn_num05, R.id.btn_num06, R.id.btn_num07,
                                    R.id.btn_num08, R.id.btn_num09, R.id.btn_delete, R.id.btn_divide,
                                    R.id.btn_multiply, R.id.btn_minus, R.id.btn_cross, R.id.btn_solve, R.id.btn_dot};
        btnNumbers = new Button[17];
        for (int i=0; i<btnNumbers.length; i++){
            btnNumbers[i] = (Button) findViewById(numberID[i]);
            btnNumbers[i].setOnClickListener(this);
            if(numberID[i] == R.id.btn_delete)
                btnNumbers[i].setOnLongClickListener(this);
        }

        listNumber = new ArrayList<>();
        listOperator = new ArrayList<>();

//        btnDelete = (Button) findViewById(R.id.btn_delete);
//        btnDivide = (Button) findViewById(R.id.btn_divide);
//        btnMultiply = (Button) findViewById(R.id.btn_multiply);
//        btnMinus = (Button) findViewById(R.id.btn_minus);
//        btnCross = (Button) findViewById(R.id.btn_cross);
//        btnSolve = (Button) findViewById(R.id.btn_solve);
//        btnDot = (Button) findViewById(R.id.btn_dot);



        txtInput = (TextView) findViewById(R.id.txt_input);
        txtResult = (TextView) findViewById(R.id.txt_result);


    }


    @Override
    public void onClick(View v) {
        Button buttonClick = (Button) v;
        Log.e("TextSize", ""+txtInput.getTextSize());
        float size = 0;
        if(strInput.length()>8 && strInput.length()<16 && buttonClick.getId() != R.id.btn_solve){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            txtInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, (txtInput.getTextSize()/2)-5);
        }
        char lastChar;
        double leftNumber, rightNumber, resultNumber;
        switch (buttonClick.getId()){
            case R.id.btn_num00:
            case R.id.btn_num01:
            case R.id.btn_num02:
            case R.id.btn_num03:
            case R.id.btn_num04:
            case R.id.btn_num05:
            case R.id.btn_num06:
            case R.id.btn_num07:
            case R.id.btn_num08:
            case R.id.btn_num09:{
                if(txtResult.getText()!="") {
                    strInput = "";
                    txtResult.setText("");
                }
                strInput += String.valueOf(buttonClick.getText());
                strNumber += String.valueOf(buttonClick.getText());
                break;
            }
            case R.id.btn_dot:{
                if(!checkDot) {
                    strInput += String.valueOf(buttonClick.getText());
                    strNumber += String.valueOf(buttonClick.getText());
                }
                checkDot = true;
                break;
            }
            case R.id.btn_delete:{
                if(!strInput.isEmpty()){
                    lastChar = strInput.charAt(strInput.length()-1);
                    if(lastChar == '.') {
                        checkDot = false;
                        strNumber = strNumber.substring(0, strNumber.length()-1);
                    }
                    else if(lastChar == '+' || lastChar == '-' || lastChar=='-' || lastChar=='×' || lastChar == '÷'){
                        listOperator.remove(listNumber.size());
                        listNumber.remove(listNumber.size());
                    }
                    strInput = strInput.substring(0, strInput.length() - 1);
                }
                break;
            }
            case R.id.btn_cross:
            case R.id.btn_minus:
            case R.id.btn_multiply:
            case R.id.btn_divide:{
                lastChar = strInput.charAt(strInput.length()-1);
                if(lastChar == '+' || lastChar == '-' || lastChar == '×' || lastChar == '÷'){
                    strInput = strInput.substring(0, strInput.length()-1) + String.valueOf(buttonClick.getText());
                }else {
                    checkDot = false;
                    listNumber.add(Double.parseDouble(strNumber));
                    strInput += String.valueOf(buttonClick.getText());
                    strNumber = "";
                }
                listOperator.add(strInput.charAt(strInput.length()-1));
                break;
            }
            case R.id.btn_solve:{
                //List cac so sau khi xu ly uu tien nhan chia
                //List cac toan tu sau khi xu ly uu tien
                listNumber.add(Double.parseDouble(strNumber));
                for (int i = 0; i<listOperator.size(); i++) {
                    if (listOperator.get(i) == '×' || listOperator.get(i) == '÷'){
                        leftNumber = listNumber.get(i);
                        rightNumber = listNumber.get(i+1);
                        resultNumber = listOperator.get(i) != 'x' ? leftNumber*rightNumber : leftNumber/rightNumber;
                        listNumber.add(i, resultNumber);
                        listNumber.remove(i+1);
                        listNumber.remove(i+1);
                        listOperator.remove(i);
                    }
                }
                resultNumber = listNumber.get(0);
                for(int i=0; i<listNumber.size()-1; i++) {
                    switch (listOperator.get(i)) {
                        case '+':
                            resultNumber += listNumber.get(i + 1);
                            break;
                        case '-':
                            resultNumber -= listNumber.get(i - 1);
                            break;
                    }
                }
                listNumber.clear();
                listOperator.clear();
                strNumber = "";
                txtResult.setText(resultNumber+"");
                break;
            }

        }
        txtInput.setText(strInput);
    }

    @Override
    public boolean onLongClick(View v) {
        strInput = "";
        txtInput.setText(strInput);
        return false;
    }
}
