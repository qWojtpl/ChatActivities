package pl.chatacctivities.activities;

import org.bukkit.entity.Player;

import java.util.Random;

public class CodeActivity extends Activity {

    private final String[] CHARS = new String[]{"!", "@", "#", "$", "%", "^", "&", "*"};
    private String correctAnswer;
    private Player winner;

    @Override
    public void onStart() {
        this.correctAnswer = getRandomWord();
        String[] split = correctAnswer.split("");
        StringBuilder codeBuilder = new StringBuilder();
        for(String c : split) {
            codeBuilder.append(getRandomColor());
            codeBuilder.append(c);
            codeBuilder.append(getRandomChars());
        }
        broadcastMessage(String.format(messages.getMessage("codeAnnouncement"), codeBuilder));
    }

    @Override
    public boolean onCommand(Player player, String argument) {
        if(!argument.equalsIgnoreCase(correctAnswer) || winner != null) {
            return false;
        }
        this.winner = player;
        plugin.getGameManager().stopActivity(this);
        return true;
    }

    @Override
    public void onStop() {
        broadcastMessage(String.format(messages.getMessage("codeWinner"), winner.getName()));
    }

    private String getRandomChars() {
        Random random = new Random();
        int rdm = random.nextInt(4) + 2;
        StringBuilder randomCharsBuilder = new StringBuilder();
        for(int i = 0; i < rdm; i++) {
            randomCharsBuilder.append(getRandomColor());
            randomCharsBuilder.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return randomCharsBuilder.toString();
    }

}
