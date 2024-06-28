package pl.chatacctivities.activities;

import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JigsawActivity extends Activity {

    private String correctAnswer;
    private Player winner;

    @Override
    public void onStart() {
        this.correctAnswer = getRandomWord();
        List<String> list = Arrays.asList(correctAnswer.split(""));
        Collections.shuffle(list);
        broadcastMessage(String.format(messages.getMessage("jigsawAnnouncement"), String.join(" ", list)));
    }

    @Override
    public boolean onCommand(Player player, String argument) {
        if(!(argument.equalsIgnoreCase(correctAnswer)) || winner != null) {
            return false;
        }
        this.winner = player;
        stopActivity();
        return true;
    }

    @Override
    public void onStop() {
        if(winner == null) {
            return;
        }
        broadcastMessage(String.format(messages.getMessage("jigsawWinner"), winner.getName()));
    }

}
