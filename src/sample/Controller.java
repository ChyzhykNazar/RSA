package sample;

import java.math.BigInteger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private Button encrypt_btn;

    @FXML
    private TextArea input_textarea;

    @FXML
    private Button decipher_btn;

    @FXML
    private Label output;

    @FXML
    private TextField pField;

    @FXML
    private TextField qField;

    @FXML
    private TextField dField;

    @FXML
    private TextField nField;

    @FXML
    void initialize() {

        encrypt_btn.setOnAction(actionEvent -> {
            int p = Integer.parseInt(pField.getText());
            int q = Integer.parseInt(qField.getText());
            String text = input_textarea.getText();

            long  n = p * q;
            long  m = (p-1)*(q-1);

            long d = calculateD(m);
            long e = calculateE(d, m);

            dField.setText(String.valueOf(d));
            nField.setText(String.valueOf(n));

            String result = RSA_Encode(text, e, n);

            output.setText(result);
        });

        decipher_btn.setOnAction(actionEvent -> {
            long d = Long.parseLong(dField.getText());
            long n = Long.parseLong(nField.getText());

            String string = input_textarea.getText();

            String result = RSA_Decode(string, d, n);

            output.setText(result);
        });
    }

    private long calculateE(long d, long m) {
        long e = 3;

        while (true)
        {
            if ((e * d) % m == 1)
                break;
            else
                e++;
        }
        return e;

    }

    private long calculateD(long m) {
        long d = m - 1;

        for(long i = 2; i <= m; i++){
            if ((m % i == 0) && (d % i == 0))
            {
                d--;
                i = 1;
            }
        }
        return d;
    }

    private String RSA_Encode(String s, long e, long n)
    {
        String result = "";

        BigInteger bi;

        String characterStr = "#ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz 1234567890";

        for (int i = 0; i < s.length(); i++)
        {
            int index = characterStr.indexOf(s.charAt(i));

            bi = new BigInteger(String.valueOf(index));

            bi = bi.pow(Math.toIntExact(e));

            BigInteger n_ = new BigInteger(String.valueOf(n));

            bi = bi.mod(n_);

            System.out.print(bi + " ");

            result += String.valueOf(bi) + " ";
        }
        return result;
    }
    private String RSA_Decode(String input, long d, long n)
    {
        String result = "";

        String characterStr = "#ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz 1234567890";

        BigInteger bi;

        String[] string;

        string = input.split(" ");


        for(int i = 0; i < string.length; i++){
            bi = new BigInteger(String.valueOf(Integer.parseInt(string[i])));

            bi = bi.pow(Math.toIntExact(d));

            BigInteger n_ = new BigInteger(String.valueOf(n));

            bi = bi.mod(n_);

            int index = bi.intValue();

            result += characterStr.charAt(index);
        }
        return result;
    }


}