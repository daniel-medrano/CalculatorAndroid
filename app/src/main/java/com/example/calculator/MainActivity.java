package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.calculator.Models.Calculator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView lblResult;
    List<Button> btnOperands;
    List<Button> btnOperators;
    Button btnClear;
    Button btnResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    void initViews() {
        // Se inicializan los Views.
        lblResult = findViewById(R.id.lblResult);
        btnOperands = new ArrayList<>(10);
        btnOperators = new ArrayList<>(4);
        btnClear = findViewById(R.id.btnClear);
        btnResult = findViewById(R.id.btnResult);

        // Se define el evento OnClick para los botones que representan los operandos, los números del 0 al 9.
        for (int i = 0; i < 10; i++) {
            int id = getResources().getIdentifier("btnOperand" + i, "id", getPackageName());
            Button btnOperand = findViewById(id);

            btnOperand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button btnOperand = (Button) view;
                    String expression = lblResult.getText().toString();
                    if (expression.equals("0") || expression.equals("ERROR")) {
                        lblResult.setText(btnOperand.getText());
                        return;
                    }
                    if (!Calculator.isNumeric(expression.substring(expression.length() - 1))) {
                        lblResult.append(" " + btnOperand.getText());
                        return;
                    }
                    lblResult.append(btnOperand.getText());
                }
            });

            btnOperands.add(btnOperand);
        }
        Button btnAddition = findViewById(R.id.btnAddition);
        Button btnSubtraction = findViewById(R.id.btnSubtraction);
        Button btnMultiplication = findViewById(R.id.btnMultiplication);
        Button btnDivision = findViewById(R.id.btnDivision);

        btnOperators.add(btnAddition);
        btnOperators.add(btnSubtraction);
        btnOperators.add(btnMultiplication);
        btnOperators.add(btnDivision);
        // Se define el evento OnClick para los botones que representan los operadores (+,-,*,/).
        for (int i = 0; i < 4; i++) {
            btnOperators.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String operator = ((Button) view).getText().toString();
                    lblResult.append(" " + operator);
                }
            });
        }
        // Se define el evento para el boton limpiara el contenido.
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblResult.setText("0");
            }
        });
        // Se define el evento para el boton que dara el resultado.
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expression = lblResult.getText().toString();
                if (expression.equals("0") || expression.equals("ERROR")) {
                    return;
                }
                try {
                    // Se obtiene el resultado en forma de String al analizar la expresión y despues se muestra el resultado.
                    // Para ello se creo la clase Calculator con métodos para procesar la expresión.
                    String result = Calculator.calculateAsString(expression.replace("x", "*"));
                    lblResult.setText(result);
                } catch (ArithmeticException e) {
                    // Se lidia con la excepcion de la división entre 0.
                    lblResult.setText("ERROR");
                }
            }
        });
    }
}