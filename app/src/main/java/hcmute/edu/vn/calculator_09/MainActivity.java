package hcmute.edu.vn.calculator_09;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnDot,btnC,btnResult,btnAdd,btnSub,btnMulti,btnDiv;
    TextView textView;
    double a=0,b=0,result=0;
    boolean Calculating=false;
    String method = "";

    private static final DecimalFormat df = new DecimalFormat("0.0000000000");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControls();
        setEvent();
    }
    private void setControls(){
        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btnDot = (Button) findViewById(R.id.btnDot);
        btnC = (Button) findViewById(R.id.btnC);
        btnResult = (Button) findViewById(R.id.btnResult);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMulti = (Button) findViewById(R.id.btnMulti);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        textView = (TextView) findViewById(R.id.textView);
    }
    private void setEvent(){
        btn0.setOnClickListener(new HandleClickNum());
        btn1.setOnClickListener(new HandleClickNum());
        btn2.setOnClickListener(new HandleClickNum());
        btn3.setOnClickListener(new HandleClickNum());
        btn4.setOnClickListener(new HandleClickNum());
        btn5.setOnClickListener(new HandleClickNum());
        btn6.setOnClickListener(new HandleClickNum());
        btn7.setOnClickListener(new HandleClickNum());
        btn8.setOnClickListener(new HandleClickNum());
        btn9.setOnClickListener(new HandleClickNum());
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText("");
                method = "";
                a=0;
                result=0;
            }
        });
        btnAdd.setOnClickListener(new handleClickCalculator());
        btnSub.setOnClickListener(new handleClickCalculator());
        btnMulti.setOnClickListener(new handleClickCalculator());
        btnDiv.setOnClickListener(new handleClickCalculator());
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator();
                String tmp = Double.toString(result);
                if(tmp.length()>10){
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,80 - tmp.length() * 2);
                }
                else {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,72);
                }
                textView.setText(tmp);

            }
        });
        btnDot.setOnClickListener(new HandleClickNum());

    }

    private class HandleClickNum implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Button btn =(Button) findViewById(view.getId());
            String tmp = textView.getText().toString();
            tmp = tmp + btn.getText().toString();
            if(tmp.length()>10){
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,80 - tmp.length() * 2);
            }
            else {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,72);
            }
            textView.setText(tmp);
            handleCalculator();
        }
    }

    private void handleCalculator(){
        String tmp = textView.getText().toString();
        try {
            if(method.compareTo("")!=0) {
                b = Double.parseDouble(tmp);
            }
            else {
                a = Double.parseDouble(tmp);
            }
        }
        catch (Exception e){
            result = 0;
            a=0;b=0;
        }
    }

    private class handleClickCalculator implements  View.OnClickListener{
        @Override
        public void onClick(View view) {
            method = ((Button)findViewById(view.getId())).getText().toString();

            textView.setText("");


        }
    }
    private void calculator(){
        switch (method){
            case "+":
                result = a+b;
                a = result;
                method ="";
                break;
            case "-":
                result = a-b;
                a = result;
                method ="";
                break;
            case "X":
                result = a*b;
                a = result;
                method ="";
                break;
            case "÷":
                result = a/b;
                a = result;
                method ="";
                break;
            default:
                return;
        }

    }

}