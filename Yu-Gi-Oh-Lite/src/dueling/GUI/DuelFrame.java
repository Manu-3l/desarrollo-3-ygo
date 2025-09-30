package dueling.GUI;

import dueling.model.Card;
import dueling.api.YGO_Api;
import dueling.logic.*;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DuelFrame extends JFrame implements Battle_Listener {
    private JTextArea logArea;
    private Duel duel;
    private List<Card> playerCards;
    private List<Card> aiCards;
    private JPanel aiPanel, playerPanel;
    private JButton[] aiButtons;
    private JButton[] playerButtons;

    public DuelFrame() {
        setTitle("Yu-Gi-Oh! Duel Lite");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        aiPanel = new JPanel(new FlowLayout());
        add(aiPanel, BorderLayout.NORTH);
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.EAST);
        logArea.append("Preparado para un duelo?! Presiona el botón de inicio!\n");
        playerPanel = new JPanel(new FlowLayout());
        add(playerPanel, BorderLayout.SOUTH);


        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton startButton = new JButton("Iniciar duelo");
        startButton.addActionListener(e -> iniciarDuelo());
        controlPanel.add(startButton);
        add(controlPanel, BorderLayout.WEST);
    }


    private void iniciarDuelo() {
        logArea.setText("Cargando cartas...\n");
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try {
                    playerCards = new ArrayList<>();
                    aiCards = new ArrayList<>();
                    for (int i = 0; i < 3; i++) {
                        playerCards.add(YGO_Api.getRandomMonsterCard());
                        aiCards.add(YGO_Api.getRandomMonsterCard());
                    }
                } catch (Exception ex) {
                    logArea.append("Error cargando cartas: " + ex.getMessage() + "\n");
                }
                return null;
            }


            @Override
            protected void done() {
                duel = new Duel(playerCards, aiCards, DuelFrame.this);
                aiPanel.removeAll();
                aiButtons = new JButton[aiCards.size()];
                for (int i = 0; i < aiCards.size(); i++) {
                    aiButtons[i] = crearBotonReverso(); // siempre boca abajo
                    aiPanel.add(aiButtons[i]);
                }
                playerPanel.removeAll();
                playerButtons = new JButton[playerCards.size()];
                for (int i = 0; i < playerCards.size(); i++) {
                    Card card = playerCards.get(i);
                    playerButtons[i] = crearBotonCarta(card, i);
                    playerPanel.add(playerButtons[i]);
                }
                aiPanel.revalidate();
                aiPanel.repaint();
                playerPanel.revalidate();
                playerPanel.repaint();
                logArea.append("Cartas cargadas. ¡Elige tu carta!\n");
            }
        };
        worker.execute();
    }


    private JButton crearBotonReverso() {
        JButton button = new JButton();
        try {
            java.net.URL url = getClass().getResource("/resource/back.png");
            if (url != null) {
                ImageIcon original = new ImageIcon(url);
                Image scaled = original.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaled));
            } else {
                button.setText("Reverso");
            }
        } catch (Exception e) {
            button.setText("Reverso");
        }
        button.setEnabled(false);
        return button;
    }


    private JButton crearBotonCarta(Card card, int index) {
        JButton button = new JButton();

        try {
            URL url = new URL(card.getImageUrl());
            ImageIcon originalIcon = new ImageIcon(url);
            Image scaledImage = originalIcon.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            button.setText(card.toString());
        }


        button.setText("<html><center>" + card.getName() +
                "<br>ATK: " + card.getAtk() +
                " / DEF: " + card.getDef() + "</center></html>");
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.addActionListener(e -> {
            String[] options = {"Ataque", "Defensa"};
            int choice = JOptionPane.showOptionDialog(this,
                    "¿Cómo quieres usar esta carta?",
                    "Modo de batalla",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            boolean ataque = (choice == 0);

            Card aiCard = duel.getRandomAiCard();
            String winner = duel.playTurn(card, ataque, aiCard);
            int aiIndex = aiCards.indexOf(aiCard);
            if (aiIndex >= 0) {
                aiButtons[aiIndex].setIcon(escalarImagen(aiCard.getImageUrl(), 120, 180));
            }
            RoundResultFrame resultFrame = new RoundResultFrame(card, ataque, aiCard, winner, this);
            resultFrame.setVisible(true);
            button.setEnabled(false);
        });
        return button;
    }


    private ImageIcon escalarImagen(String url, int width, int height) {
        try {
            ImageIcon original = new ImageIcon(new URL(url));
            Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            return null;
        }
    }


    @Override
    public void onTurn(String playerCard, String aiCard, String winner) {
        logArea.append("Jugador: " + playerCard + " VS IA: " + aiCard + " => Gana: " + winner + "\n");
    }


    @Override
    public void onScoreChanged(int playerScore, int aiScore) {
        logArea.append("Puntaje -> Jugador: " + playerScore + " | IA: " + aiScore + "\n");
    }


    @Override
    public void onDuelEnded(String winner) {
        logArea.append("🏆 El ganador del duelo es: " + winner + "\n");
        JOptionPane.showMessageDialog(this, "¡Ganador del duelo: " + winner + "!");
        for (JButton b : playerButtons) b.setEnabled(false);
    }
}
