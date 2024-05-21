package com.np.pieces;

import com.np.Board;

import java.util.ArrayList;

/**
 * Pawn child-class of the Piece class.
 */

public class Pawn extends Piece{
    private boolean promotable;

    public Pawn(int x, int y, char t){
        xPos = x;
        yPos = y;
        this.x = x * 64;
        this.y = y * 64;
        team = t;
        promotable = false;
    }

    /**
     * Method to generate valid/legal moves for the Pawn piece.
     * @param board current board-state to calculate legal moves.
     * @return the ArrayList consisting of [x, y] coordinates of possible legal moves.
     */

    //TODO: En Passant

    @Override
    public ArrayList<int[]> generateValidMoves(Board board) {

        validMoves.clear();

        if(team == 'w') {

            if(yPos > 0 && board.getPiece(xPos, yPos - 1) == null) {
                //default add the space in front of the pawn
                validMoves.add(new int[]{xPos, yPos - 1});
            }

            //if it is the first move of the pawn; it can move two spaces
            if (moves < 1 && board.getPiece(xPos, yPos - 2) == null) {
                validMoves.add(new int[]{xPos, yPos - 2});
            }

            if(xPos > 0 && yPos > 0) {
                //Checking for enemy pieces to capture
                if (board.getPiece(xPos - 1, yPos - 1) != null && board.getPiece(xPos - 1, yPos - 1).getTeam() != 'w') {
                    validMoves.add(new int[]{xPos - 1, yPos - 1});
                }
            }

            if(xPos < 7 && yPos > 0) {
                if (board.getPiece(xPos + 1, yPos - 1) != null && board.getPiece(xPos + 1, yPos - 1).getTeam() != 'w') {
                    validMoves.add(new int[]{xPos + 1, yPos - 1});
                }
            }

        }
        //mirrored for black elements
         else {
             if(yPos < 7 && board.getPiece(xPos, yPos + 1) == null) {
                 validMoves.add(new int[]{xPos, yPos + 1});
             }

            if (moves < 1 && board.getPiece(xPos, yPos + 2) == null) {
                validMoves.add(new int[]{xPos, yPos + 2});
            }

            if(xPos > 0 && yPos < 7) {
                if (board.getPiece(xPos - 1, yPos + 1) != null && board.getPiece(xPos - 1, yPos + 1).getTeam() != 'b') {
                    validMoves.add(new int[]{xPos - 1, yPos + 1});
                }
            }

            if(xPos < 7 && yPos < 7) {
                if (board.getPiece(xPos + 1, yPos + 1) != null && board.getPiece(xPos + 1, yPos + 1).getTeam() != 'b') {
                    validMoves.add(new int[]{xPos + 1, yPos + 1});
                }
            }
        }

        return validMoves;
    }

    /**
     * Method to promote the current Pawn piece.
     * @param board current board-state to remove the pawn and add the newly chosen promotion piece.
     * @param type type element to be passed by the JOptionPane when a promotion occurs. This is converted into a
     *             valid Piece object.
     */

    public void Promote(Board board, int type) {

            Piece piece;

            switch (type) {
                case 0 -> piece = new Queen(xPos, yPos, team);
                case 1 -> piece = new Rook(xPos, yPos, team);
                case 2 -> piece = new Knight(xPos, yPos, team);
                case 3 -> piece = new Bishop(xPos, yPos, team);
                default -> piece = new Pawn(xPos, yPos, team);
            }

            board.removePiece(xPos, yPos);
            board.addPiece(xPos, yPos, piece);

    }

    /**
     * Accessor method of Pawn
     * @return boolean of whether the current pawn piece is in a promotable position on the board.
     */

    public boolean isPromotable() {

        if(team == 'w' && yPos == 0) {
            promotable = true;
        }

        else if(team == 'b' && yPos == 7) {
            promotable = true;
        }

        else {
            promotable = false;
        }

        return promotable;
    }
}
