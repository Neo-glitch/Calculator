package learnprogramming.academy.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // creates the fields for the widgets that display results,newNumber and the display operation
    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    // fields to hold the operands and type of calculations
    // Double indicates that we are using class Double and not the primitive dtype double.
    private Double operand1 = null;
    private String pendingOperation = "=";

    private static final String STATE_PENDING_OPERATIONS = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        assigns the widget type to the fields specified in class.
        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);

        // assigns the button widgets to the button var defined below
        Button button0 = (Button) findViewById(R.id.button0);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button button3 = (Button) findViewById(R.id.button3);
        Button button4 = (Button) findViewById(R.id.button4);
        Button button5 = (Button) findViewById(R.id.button5);
        Button button6 = (Button) findViewById(R.id.button6);
        Button button7 = (Button) findViewById(R.id.button7);
        Button button8 = (Button) findViewById(R.id.button8);
        Button button9 = (Button) findViewById(R.id.button9);
        Button buttonDot = (Button) findViewById(R.id.buttonDot);

        Button buttonEquals = (Button) findViewById(R.id.buttonEquals);
        Button buttonDivide = (Button) findViewById(R.id.buttonDivide);
        Button buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        Button buttonMinus = (Button) findViewById(R.id.buttonMinus);
        Button buttonPlus= (Button) findViewById(R.id.buttonPlus);

//      code for the creating the onclicklistener for the Button 0- dot widgets.
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // not all views have text, but the button widget has text and that text on the button widget
                // is appended to the newNumber EditText widget.
                Button b = (Button) view;
                newNumber.append(b.getText().toString());
            }
        };

        // calls the setOnclickListener method that uses the listener def to detect clicks on our app.
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b = (Button) view;
                // reads the text of our button widget and assigns it to a string var "op".
                String op = b.getText().toString();
                // gets the text in the newNumber field(EditText widget) and assigns it to a string var named value.
                String value = newNumber.getText().toString();

//                This try, catch block is used to handle the decimal bug fix.
                try{
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                }
                catch(NumberFormatException e){
                    newNumber.setText("");
                }
                // assigns the button text to field pendingOperation
                pendingOperation = op;
                // sets the displayOperation(TextView) field to what we have in pending op var
                displayOperation.setText(pendingOperation);
            }
        };

        // sets the listener that will be used for this buttons.
        buttonEquals.setOnClickListener(opListener);
        buttonDivide.setOnClickListener(opListener);
        buttonMultiply.setOnClickListener(opListener);
        buttonMinus.setOnClickListener(opListener);
        buttonPlus.setOnClickListener(opListener);

        // creates a new listener for the Neg button.
        // we could have used the listeners for the operations also to do this, rather than creating a new one
        Button buttonNeg = (Button) findViewById(R.id.buttonNeg);
        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = newNumber.getText().toString();
                if(value.length() == 0){
                    newNumber.setText("-");
                }else{
                    try{
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNumber.setText(doubleValue.toString());
                    }
//                  The catch block is used when newNumber is "-" or ".", so clear it
                    catch(NumberFormatException e){
                        newNumber.setText("");
                    }
                }
            }
        });
    }


    // Both the onsave and the onrestore method helps us in keeping our layout stable irrespective of the rotation of phone.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // how this works is that it saves the value of pending operation and stores it in the constant
        // saved in the OutState bundle
        outState.putString(STATE_PENDING_OPERATIONS, pendingOperation);

        // if statement checks if operand1 is not null, else app crashes if we rotate dev without entering any values.
        if(operand1 != null){
            outState.putDouble(STATE_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // .getString and .getDouble gets the values saved in the constants found in the savedInstanceState bundles.
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATIONS);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOperation);
    }

    private void performOperation(double value, String operation){
        // checks if operand1 is null, and assigns to it num value of value passed to it as the 1st params.
        if (null == operand1){
            operand1 = value;
        }
        else{
            if (pendingOperation.equals("=")){
                pendingOperation = operation;
            }
            switch(pendingOperation){
                // if operation is "=" we assign the value of operand 2 to operand 1.
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if(value == 0){
                        operand1 = 0.0;
                    }
                    else{
                        // divide operand 1 by operand 2 ansd store the result in operand1
                        operand1 /= value;
                    }
                    break;
                case "*":
                    operand1 *= value;
                    break;
                case "+":
                    operand1 += value;
                    break;
            }
        }

        result.setText(operand1.toString());
        newNumber.setText("");
    }


}
