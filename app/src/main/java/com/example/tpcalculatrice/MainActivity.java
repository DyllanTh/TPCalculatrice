package com.example.tpcalculatrice;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public enum Ops {
        PLUS("+"),
        MOINS("-"),
        FOIS("*"),
        DIV("/");

        private final String name;

        Ops(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private TextView screen;
    private int op1 = 0;
    private int op2 = 0;
    private Ops operator = null;
    private boolean isOp1 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = findViewById(R.id.screen);

        Button btnEgal = findViewById(R.id.btnEgal);
        btnEgal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
            }
        });

        Button btnClear = findViewById(R.id.btnReset);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });
    }

    private void updateDisplay() {
        int v = isOp1 ? op1 : op2;
        screen.setText(String.format("%9d", v));
    }

    public void compute() {
        if (isOp1) {
            // Ne rien faire
        } else {
            switch (operator) {
                case PLUS:
                    op1 = op1 + op2;
                    break;
                case MOINS:
                    op1 = op1 - op2;
                    break;
                case FOIS:
                    op1 = op1 * op2;
                    break;
                case DIV:
                    if (op2 != 0)
                        op1 = op1 / op2;
                    else {
                        Toast.makeText(this, "Division par zéro!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
                default:
                    return; // Ne rien faire si aucun opérateur
            }

            op2 = 0;
            isOp1 = true;
            updateDisplay();
        }
    }

    private void clear() {
        op1 = 0;
        op2 = 0;
        operator = null;
        isOp1 = true;
        updateDisplay();
    }

    public void setOperator(View v) {
        int id = v.getId();
        if (id == R.id.btnPlus) {
            operator = Ops.PLUS;
        } else if (id == R.id.btnMoins) {
            operator = Ops.MOINS;
        } else if (id == R.id.btnFois) {
            operator = Ops.FOIS;
        } else if (id == R.id.btnDiv) {
            operator = Ops.DIV;
        } else {
            Toast.makeText(this, "Opérateur non reconnu", Toast.LENGTH_LONG).show();
            return; // Ne rien faire si aucun opérateur
        }
        isOp1 = false;
        updateDisplay();
    }

    public void addNumber(View v) {
        try {
            int val = Integer.parseInt(((Button) v).getText().toString());
            if (isOp1) {
                op1 = op1 * 10 + val;
            } else {
                op2 = op2 * 10 + val;
            }
            updateDisplay();
        } catch (NumberFormatException | ClassCastException e) {
            Toast.makeText(this, "Valeur erronée", Toast.LENGTH_LONG).show();
        }
    }
}
