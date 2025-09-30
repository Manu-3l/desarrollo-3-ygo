package dueling.GUI;

import dueling.model.Card;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class RoundResultFrame extends JFrame {
    public RoundResultFrame(Card playerCard, boolean playerAttack, Card aiCard, String winner, DuelFrame parent) {
        setTitle("Resultado de la ronda");
        setSize(500, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);
        try {
            JLabel aiLabel = new JLabel(escalarImagen(aiCard.getImageUrl(), 200, 300));
            aiLabel.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel playerLabel = new JLabel(escalarImagen(playerCard.getImageUrl(), 200, 300));
            playerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel resultLabel = new JLabel("Ganador: " + winner, SwingConstants.CENTER);
            resultLabel.setFont(new Font("Arial", Font.BOLD, 20));
            resultLabel.setOpaque(true);
            resultLabel.setBackground(Color.LIGHT_GRAY);
            JButton continueButton = new JButton("Continuar");
            continueButton.addActionListener(e -> dispose());
            JPanel bottomPanel = new JPanel(new BorderLayout());
            bottomPanel.add(playerLabel, BorderLayout.CENTER);
            bottomPanel.add(continueButton, BorderLayout.SOUTH);
            add(aiLabel, BorderLayout.NORTH);
            add(resultLabel, BorderLayout.CENTER);
            add(bottomPanel, BorderLayout.SOUTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private ImageIcon escalarImagen(String url, int width, int height) throws Exception {
        ImageIcon original = new ImageIcon(new URL(url));
        Image scaled = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
