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

public class BullsAndCowsActivity extends AppCompatActivity {

    private Random random;
    private List<Integer> secretNumber;
    private int attemptsCount;
    private TextView attemptsTextView;
    private EditText guessEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();
        secretNumber = generateSecretNumber();
        attemptsCount = 0;

        attemptsTextView = findViewById(R.id.attemptsTextView);
        guessEditText = findViewById(R.id.guessEditText);

        Button guessButton = findViewById(R.id.guessButton);
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
            }
        });

        updateAttempts();
    }

    private void checkGuess() {
        String guessString = guessEditText.getText().toString().trim();

        if (guessString.length() == 4) {
            List<Integer> guessList = convertStringToList(guessString);

// Calculate bulls and cows
            int bulls = 0;
            int cows = 0;
            for (int i = 0; i < secretNumber.size(); i++) {
                if (guessList.get(i).equals(secretNumber.get(i))) {
                    bulls++;
                } else if (secretNumber.contains(guessList.get(i))) {
                    cows++;
                }
            }

// Show feedback to the player
            Toast.makeText(this, "Bulls: " + bulls + ", Cows: " + cows, Toast.LENGTH_SHORT).show();

// Check if the player has won
            if (bulls == 4) {
                Toast.makeText(this, "Congratulations! You won!", Toast.LENGTH_LONG).show();
                restartGame();
            }

// Update attempts count and clear the input field
            attemptsCount++;
            updateAttempts();
            guessEditText.setText("");
        } else {
            Toast.makeText(this, "Please enter a 4-digit number.", Toast.LENGTH_SHORT).show();
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
        attemptsTextView.setText("Attempts: " + attemptsCount);
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
}