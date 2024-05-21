package com.np;

import com.np.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;

/**
 * GUI class to display the chess game elements.
 */

public class GUI {

    //JWindowPane dimensions
    private static final int HEIGHT = 581;
    private static final int WIDTH = 528;

    //Filepath elements
    private static final File IMG = new File("./com/np/textures/pieces/chess.png");
    private static final String ICON_PATH = "./com/np/textures/icon/icon.png";
    private static final String PROMO_ICON_PATH = "./com/np/textures/icon/queen.png";
    private static final String WIN_ICON_PATH = "./com/np/textures/icon/king.png";

    //UI Color elements
    private static final Color CREAM = new Color(255, 239, 219);
    private static final Color BROWN = new Color(189, 149, 81);
    private static final Color HIGHLIGHT = new Color(102, 102, 102, 170);

    //attributes
    private int[] tempLoc = {};
    private ArrayList<int[]> heldLegalMoves;


    /**
     * Game GUI constructor that initializes the JFrame and all JPanel elements to be used in the UI.
     */

    public GUI(Board board) throws IOException {
        BufferedImage all = ImageIO.read(IMG);
        Image[] largePieces;
        Image[] capturedPieces;

        final Piece[] currentPiece = {null};

        //Large Piece Buffering
        largePieces = convertImages(all, 64, 64);

        //Small Piece Buffering
        capturedPieces = convertImages(all, 24, 24);


        JFrame frame = new JFrame("Chess Game");
        frame.setIconImage(new ImageIcon(ICON_PATH).getImage());
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(false);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel whiteTakenElem = getWhiteTakenElem(board, capturedPieces);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.33; c.ipady = 16;
        c.gridx = 2; c.gridy = 0;
        frame.add(whiteTakenElem, c);

        JPanel infoBoard = new JPanel();
        JLabel infoLabel = new JLabel("White's Turn to Move");
        infoBoard.add(infoLabel);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20; c.weightx = 0.33;
        c.gridx = 1; c.gridy = 0;
        frame.add(infoBoard, c);

        JPanel blackTakenElem = getBlackTakenElem(board, capturedPieces);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.33;
        c.gridx = 0; c.gridy = 0;
        frame.add(blackTakenElem, c);

        JPanel chessBoard = getjPanel(board, largePieces);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 512; c.ipadx = 512; c.weightx = 0.0; c.gridwidth = 3;
        c.gridx = 0; c.gridy = 1;
        frame.add(chessBoard, c);

        //Chess board listeners
        chessBoard.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(currentPiece[0] != null) {
                    currentPiece[0].x = e.getX() - 32;
                    currentPiece[0].y = e.getY() - 32;

                    //Prevent piece from leaving bounds
                    currentPiece[0].x = Math.max(0, Math.min(WIDTH - 64, currentPiece[0].x));
                    currentPiece[0].y = Math.max(0, Math.min(HEIGHT - 64, currentPiece[0].y));

                    //Repaint the frame
                    chessBoard.repaint();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {}
        });

        chessBoard.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                char currentTeam = (board.getTurn() % 2 == 0) ? 'w' : 'b';

                if(board.getPiece(e.getX() / 64 , e.getY() / 64) != null) {
                    Piece p = board.getPiece(e.getX() / 64, e.getY() / 64);

                    tempLoc = p.getPos();

                    if (p.getTeam() == currentTeam) {
                        currentPiece[0] = p;
                        heldLegalMoves = p.generateValidMoves(board);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                heldLegalMoves = null;

                if (currentPiece[0] != null) {

                    //snap to grid elements
                    currentPiece[0].updatePos(tempLoc[0], tempLoc[1]);

                    board.movePiece(currentPiece[0], e.getX() / 64, e.getY() / 64);

                    if(!Arrays.equals(currentPiece[0].getPos(), tempLoc)) {
                        currentPiece[0].incMoves();
                    }

                    //initiate promo prompt
                    if (currentPiece[0] instanceof Pawn) {
                        if (((Pawn) currentPiece[0]).isPromotable()) {

                            ((Pawn) currentPiece[0]).Promote(board, showPromotionMenu());

                        }
                    }

                    currentPiece[0] = null;
                    frame.repaint();

                    if (board.winConditionMet()) {

                        String winner = (board.getTurn() % 2 != 0) ? "White" : "Black";

                        try {
                            showWinMenu(winner, frame);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                    }

                    //Check for current player
                    char currentTeam = (board.getTurn() % 2 == 0) ? 'w' : 'b';

                    if (currentTeam == 'b') {
                        infoLabel.setText("Black's Turn to Move");
                    } else {
                        infoLabel.setText("White's Turn to Move");
                    }

                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * This method creates a JPanel with the required attributes for drawing the chessboard.
     * @param board is the current state of the game board that permits the proper display of chess pieces on the board.
     * @param imgs is the array of chess piece images.
     * @return the JPanel configured element.
     */

    private JPanel getjPanel(Board board, Image[] imgs) {
        JPanel chessBoard = new JPanel() {
            @Override
            public void paint (Graphics g) {
                boolean white = true;
                boolean moveToHightlight = false;

                for (int y = 0; y < 8; y++) {
                    for(int x = 0; x < 8; x++) {

                        try {
                            for (int[] i : heldLegalMoves) {
                                moveToHightlight = Arrays.equals(i, new int[]{x, y});
                                if(moveToHightlight) {break;}
                            }
                        }
                        catch(NullPointerException ignored) {}

                        if(white) {
                            g.setColor(CREAM);
                        } else {
                            g.setColor(BROWN);
                        }

                        g.fillRect(x * 64, (y * 64) , 64, 64);

                        if (moveToHightlight) {
                            g.setColor(HIGHLIGHT);
                            g.fillOval((x * 64) + 20, (y * 64) + 20, 24, 24);
                        }

                        white = !white;
                    }
                    white = !white;
                }

                for (int i = 0; i < 8; i++) {
                    for(int j = 0; j < 8; j++) {

                        Piece p = board.getPiece(i, j);

                        if(p == null) continue;

                        int index = getImgIndex(p);

                        g.drawImage(imgs[index], p.x, p.y, this);
                    }
                }
            }
        };
        return chessBoard;
    }

    /**
     * This method creates the captured piece display for white-elements only.
     * @param board the current state of hte game board to access the capture piece ArrayList elements attributes of
     *              the Board class.
     * @param imgs is the array of chess piece images.
     * @return the JPanel element to display the captured white elements
     */

    private JPanel getWhiteTakenElem(Board board, Image[] imgs) {
                JPanel elem = new JPanel() {
                    @Override
                    public void paint(Graphics g) {
                        int index;
                        int spacing = JPanel.WIDTH + 100;

                        for(Piece p : board.getWhiteTaken()) {

                            if(p == null) continue;

                            index = getImgIndex(p);

                            g.drawImage(imgs[index], spacing, 0, this);
                            spacing -= 16;
                        }
                    }
                };
                return elem;
    }

    /**
     * This method creates the captured piece display for black-elements only.
     * @param board the current state of hte game board to access the capture piece ArrayList elements attributes of
     *              the Board class.
     * @param imgs is the array of chess piece images.
     * @return the JPanel element to display the captured black elements
     */

    private JPanel getBlackTakenElem(Board board, Image[] imgs) {
        JPanel elem = new JPanel() {
            @Override
            public void paint(Graphics g) {
                int index;
                int spacing = 0;

                for(Piece p : board.getBlackTaken()) {

                    if(p == null) continue;

                    index = getImgIndex(p);

                    g.drawImage(imgs[index], spacing, 0, this);
                    spacing += 16;
                }
            }
        };
        return elem;
    }

    /**
     * This method returns the index of the image corresponding to the piece parameter.
     * @param p is the piece element for which an index will be returned
     * @return the index of the corresponding instanceof piece for the image.
     */

    private int getImgIndex(Piece p) {
        int i;

        i = switch (p) {
            case King ignored -> 0;
            case Queen ignored -> 1;
            case Bishop ignored -> 2;
            case Knight ignored -> 3;
            case Rook ignored -> 4;
            default -> 5;
        };

        i += (p.getTeam() != 'w') ? 6 : 0;

        return i;
    }

    /**
     * This method converts the all-in-one chess piece image file into individually accessible piece images to be
     * displayed on the chessboard.
     * @param all is the image file to be split.
     * @param width the desired width of the returning images.
     * @param height the desired height of the returning images.
     * @return the array of images to be drawn in the JFrame.
     */

    private Image[] convertImages(BufferedImage all, int width, int height) {
        int index = 0;
        Image[] imgs = new Image[12];

        for(int y = 0; y < 400; y += 200) {
            for (int x = 0; x < 1200; x += 200) {
                imgs[index] = all.getSubimage(x, y, 200, 200).getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
                index ++;
            }
        }

        return imgs;
    }

    /**
     * Creates the JOptionPane to be displayed when a promotion is required.
     * @return the selected option from the JOptionPane.
     * 0 -> Queen
     * 1 -> Rook
     * 2 -> Knight
     * 3 -> Bishop
     */

    private int showPromotionMenu() {
        String[] options = {"Queen", "Rook", "Knight", "Bishop"};
        ImageIcon paneIcon = new ImageIcon(new ImageIcon(PROMO_ICON_PATH).getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH));

        return JOptionPane.showOptionDialog(null,
                "Your pawn was promoted! Choose the piece to be promoted to.",
                "Choose a Piece", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                paneIcon, options, null);
    }

    /**
     * Creates the JOptionPane to be displayed upon game win-condition encountered.
     * @param winner the current player when the game ended.
     * @param frame the JFrame to delete the original game window when the game is quit.
     * @throws IOException error finding the icon path
     */

    private void showWinMenu(String winner, JFrame frame) throws IOException {
        String[] options = {"New Game", "Quit"};
        ImageIcon winIcon = new ImageIcon(new ImageIcon(WIN_ICON_PATH).getImage().getScaledInstance(125, 125, Image.SCALE_SMOOTH));

        String winMessage = winner + " Wins!";

        int index = JOptionPane.showOptionDialog(null,
                winMessage, "Game Over!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                winIcon, options, options[0]);

        if (index == 0) {
            frame.dispose();
            Game game = new Game();
        }

        else {
            frame.dispose();
        }
    }
}
