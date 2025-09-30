package dueling.logic;

import dueling.model.Card;

import java.util.*;

public class Duel {
    private List<Card> playerCards;
    private List<Card> aiCards;
    private int playerScore = 0;
    private int aiScore = 0;
    private Battle_Listener listener;
    private Random rand = new Random();

    public Duel(List<Card> playerCards, List<Card> aiCards, Battle_Listener listener) {
        this.playerCards = new ArrayList<>(playerCards);
        this.aiCards = new ArrayList<>(aiCards);
        this.listener = listener;
    }


    public Card getRandomAiCard() {
        return aiCards.get(rand.nextInt(aiCards.size()));
    }

    public String playTurn(Card playerCard, boolean playerAttack, Card aiCard) {
        String winner;
        boolean aiAttack = rand.nextBoolean();
        if (playerAttack && aiAttack) {
            if (playerCard.getAtk() > aiCard.getAtk()) {
                playerScore++;
                winner = "Jugador";
            } else if (aiCard.getAtk() > playerCard.getAtk()) {
                aiScore++;
                winner = "IA";
            } else {
                winner = "Empate";
            }
        } else if (playerAttack && !aiAttack) {
            if (playerCard.getAtk() > aiCard.getDef()) {
                playerScore++;
                winner = "Jugador";
            } else if (aiCard.getDef() > playerCard.getAtk()) {
                aiScore++;
                winner = "IA";
            } else {
                winner = "Empate";
            }
        } else if (!playerAttack && aiAttack) {
            if (aiCard.getAtk() > playerCard.getDef()) {
                aiScore++;
                winner = "IA";
            } else if (playerCard.getDef() > aiCard.getAtk()) {
                playerScore++;
                winner = "Jugador";
            } else {
                winner = "Empate";
            }
        } else {
            winner = "Empate";
        }


        listener.onTurn(playerCard.toString(), aiCard.toString(), winner);
        listener.onScoreChanged(playerScore, aiScore);
        if (playerScore == 2 || aiScore == 2) {
            String duelWinner = (playerScore > aiScore) ? "Jugador" : "IA";
            listener.onDuelEnded(duelWinner);
        }

        return winner;
    }
}
