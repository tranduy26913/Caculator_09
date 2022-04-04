package hcmute.edu.vn.calculator_09;

import java.math.BigDecimal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.MathContext;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDot, btnC, btnResult, btnAdd, btnSub, btnMulti, btnDiv;
    TextView textView;


    double newNumber = 0, result = 0;//các toán tử và kết quả
    boolean calculating = false;//flag cho biết phép tính đang được thực hiện
    String method = "";//phép tính
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControls();//viết các control
        setEvent();//cài đặt các sự kiện
    }

    private void setControls() {
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
        textView.setText("");
        //textView.setSelected(true);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }

    private void setEvent() {
        ///Sự kiện các nút số
        btn0.setOnClickListener(new HandleClickNumber());
        btn1.setOnClickListener(new HandleClickNumber());
        btn2.setOnClickListener(new HandleClickNumber());
        btn3.setOnClickListener(new HandleClickNumber());
        btn4.setOnClickListener(new HandleClickNumber());
        btn5.setOnClickListener(new HandleClickNumber());
        btn6.setOnClickListener(new HandleClickNumber());
        btn7.setOnClickListener(new HandleClickNumber());
        btn8.setOnClickListener(new HandleClickNumber());
        btn9.setOnClickListener(new HandleClickNumber());
        btnDot.setOnClickListener(new HandleClickNumber());
        ///
        btnC.setOnClickListener(new View.OnClickListener() {//xử lý sự kiện bấm C
            @Override
            public void onClick(View view) {
                textView.setText("");
                method = "";
                newNumber = 0;
                result = 0;
                calculating = false;
            }
        });

        //Sự kiện các nút phép tính
        btnAdd.setOnClickListener(new handleClickCalculator());
        btnSub.setOnClickListener(new handleClickCalculator());
        btnMulti.setOnClickListener(new handleClickCalculator());
        btnDiv.setOnClickListener(new handleClickCalculator());

        //Sự kiện nút =
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!handleParseNumber())//nếu parse lỗi sẽ dừng thực hiện phép tính
                    return;
                if (calculator()) {
                    String tmp = BigDecimal.valueOf(result).toPlainString(); //hiển thị giá trị dạng thập phân

                    if (tmp.length() > 10) { // điều chỉnh kích thước chữ để vừa khung nhìn
                        tmp = fixedDecimal(tmp);
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80 -  Math.round(tmp.length() * 1.2));
                    } else {
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 72);
                    }
                    textView.setText(tmp); // gắn kết quả vào text view
                    calculating = false;
                }
            }
        });
    }

    private class HandleClickNumber implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            Button btn = (Button) findViewById(view.getId());
            String tmp = textView.getText().toString();
            tmp = tmp + btn.getText().toString();
            if (tmp.length() > 10) {// điều chỉnh kích thước chữ để vừa khung nhìn
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80 - Math.round(tmp.length() * 1.2));
            } else {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 72);
            }
            textView.setText(tmp);
        }
    }

    private boolean handleParseNumber() {//xử lý ép kiểu sang số thực
        String tmp = textView.getText().toString();
        try {
            if (!method.equals("")) {//Nếu có bấm phép tính thì số gán vào newNumber
                newNumber = Double.parseDouble(tmp);
            } else {//Nếu chưa gán phép tính thì số trên text view gán vào result
                result = Double.parseDouble(tmp);
            }
            return true;
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Lỗi nhập số. Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
            textView.setText("");
            return false;
        }
    }

    private class handleClickCalculator implements View.OnClickListener {
        @Override
        public void onClick(View view) {//Xử lý phép tính liên tiếp cho tới khi bấm = mới kết thúc
            if(!handleParseNumber())//nếu parse lỗi sẽ dừng thực hiện phép tính
                return;
            if (calculating) {//nếu chưa bấm '='
                if(!calculator())//thực hiện phép tính cũ
                    return;
            } else//nếu đã bấm = hoặc app bấm phép tính lần đầu thì gán true để những lần sau thực hiện phép tính cũ
                calculating = true;
            method = ((Button) findViewById(view.getId())).getText().toString();//lưu phép tính mới
            textView.setText("");//clear text view

        }
    }

    private boolean calculator() {
//        if(!handleParseNumber())//parse số
//            return false;
        switch (method) {
            case "+":
                result += newNumber;
                break;
            case "-":
                result -= newNumber;
                break;
            case "X":
                result *= newNumber;
                break;
            case "÷":
                if (newNumber == 0) {
                    Toast.makeText(MainActivity.this, "Không thể chia cho 0. Vui lòng nhập lại số", Toast.LENGTH_SHORT).show();
                    textView.setText("");
                    return false;
                }
                result /= newNumber;
                break;
            default:
                return false;
        }
        method = "";
        return true;

    }

    private String fixedDecimal(String str){
        int index = str.indexOf('.');
        if(index!=-1){
            String tmp = str.substring(index);
            if(tmp.length()>10) {
                return str.substring(0, index + 11);
            }
        }
        return str;
    }

}