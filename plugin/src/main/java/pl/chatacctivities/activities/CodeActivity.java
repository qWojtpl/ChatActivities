package pl.chatacctivities.activities;

import org.bukkit.entity.Player;

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
        stopActivity();
        return true;
    }

    @Override
    public void onStop() {
        if(winner == null) {
            return;
        }
        broadcastMessage(String.format(messages.getMessage("codeWinner"), winner.getName()));
        invokeRandomReward(winner);
    }

    private String getRandomChars() {
        int rdm = getRandomNumber(4) + 2;
        StringBuilder randomCharsBuilder = new StringBuilder();
        for(int i = 0; i < rdm; i++) {
            randomCharsBuilder.append(getRandomColor());
            randomCharsBuilder.append(CHARS[getRandomNumber(CHARS.length)]);
        }
        return randomCharsBuilder.toString();
    }

}
