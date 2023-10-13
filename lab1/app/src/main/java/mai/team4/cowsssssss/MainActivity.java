package mai.team4.cowsssssss;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity  extends AppCompatActivity {

    private Random random;
    private List<Integer> secretNumber;
    private int attemptsCount;
    private Button guessButton;
    private TextView attemptsTextView;
    private TextView bandcTextView;
    private EditText guessEditText;
    private Boolean isNew = true;
    private String guessString;
    private static final int MAX_ATTEMPTS = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();
        secretNumber = generateSecretNumber();

        //Toast.makeText(this, "Please enter a number without repeating digits.", Toast.LENGTH_SHORT).show();

        attemptsCount = 0;

        attemptsTextView = findViewById(R.id.attemptsTextView);
        guessEditText = findViewById(R.id.guessEditText);
        bandcTextView = findViewById(R.id.bandcTextView);
        guessButton = findViewById(R.id.guessButton);

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
            }
        });
        updateAttempts();
    }

    private void checkGuess() {
        guessString = guessEditText.getText().toString().trim();

        if ((guessString.length() == 4)&&(guessString.charAt(0)!='0')) {
            if(digitsInString(guessString)) {
                Toast.makeText(this, "Please enter a number without repeating digits.", Toast.LENGTH_SHORT).show();
                guessEditText.setText("");
            }
            List<Integer> guessList = convertStringToList(guessString);

            int bulls = 0;
            int cows = 0;
            for (int i = 0; i < secretNumber.size(); i++) {
                if (guessList.get(i).equals(secretNumber.get(i))) {
                    bulls++;
                } else if (secretNumber.contains(guessList.get(i))) {
                    cows++;
                }
            }

            bandcTextView.setText("Bulls: " + bulls + ", Cows: " + cows);

            if (bulls == 4) {
                Toast.makeText(this, "Congratulations! You won!", Toast.LENGTH_LONG).show();
                restartGame();
            }

            attemptsCount++;
            updateAttempts();
            guessEditText.setText("");
        }
        else {
            Toast.makeText(this, "Please enter a 4-digit number.", Toast.LENGTH_SHORT).show();
            guessEditText.setText("");
        }
        if (attemptsCount >= MAX_ATTEMPTS) {
            Toast.makeText(this, "You have reached the maximum number of attempts.", Toast.LENGTH_SHORT).show();
            restartGame();
            return;
        }

    }

    private List<Integer> generateSecretNumber() {
        List<Integer> secretNumber = new ArrayList<>();
        while (secretNumber.size() < 4) {
            int digit = random.nextInt(10);
            if (!secretNumber.contains(digit)) {
                secretNumber.add(digit);
            }
        }
        return secretNumber;
    }

    private void updateAttempts() {

        attemptsTextView.setText(String.format("Attempts: %d/%d", attemptsCount, MAX_ATTEMPTS));
    }

    private List<Integer> convertStringToList(String numberString) {
        List<Integer> numberList = new ArrayList<>();
        for (char digitChar : numberString.toCharArray()) {
            int digit = Character.getNumericValue(digitChar);
            numberList.add(digit);
        }
        return numberList;
    }

    private void restartGame() {
        secretNumber = generateSecretNumber();
        attemptsCount = 0;
        updateAttempts();

    }

    public void onNumberButtonClick(View view) {

        if(isNew)
            guessEditText.setText("");
        isNew = false;

        String number = guessEditText.getText().toString();

        if(view.getId()==R.id.button7)
            number = number + "7";
        else if(view.getId()==R.id.button6)
            number = number + "6";
        else if(view.getId()==R.id.button5)
            number = number + "5";
        else if(view.getId()==R.id.button4)
            number = number + "4";
        else if(view.getId()==R.id.button3)
            number = number + "3";
        else if(view.getId()==R.id.button2)
            number = number + "2";
        else if(view.getId()==R.id.button1)
            number = number + "1";
        else if(view.getId()==R.id.button9)
            number = number + "9";
        else if(view.getId()==R.id.button8)
            number = number + "8";
        else
            number = number + "0";

        guessEditText.setText(number);
    }

    private boolean digitsInString(String string) {
        boolean repeat = false;
        int i = 0;
        while (i < string.length()) {
            if(string.contains(String.valueOf(string.charAt(i))))
                repeat = true;
            ++i;
        }

        return repeat;
    }
}