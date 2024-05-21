package com.np.pieces;

import com.np.Board;
import java.util.ArrayList;

/**
 * Bishop child-class of the Piece class.
 */

public class Bishop extends Piece{
    public Bishop(int x, int y, char t) {
        xPos = x;
        yPos = y;
        this.x = x * 64;
        this.y = y * 64;
        team = t;
    }

    /**
     * Method to generate valid/legal moves for the Bishop piece.
     * @param board current board-state to calculate legal moves.
     * @return the ArrayList consisting of [x, y] coordinates of possible legal moves.
     */

    @Override
    public ArrayList<int[]> generateValidMoves(Board board) {
        validMoves.clear();

        //top-left
        for(int i = 1; xPos - i >= 0 && yPos - i >= 0; i++) {

            //optimization for checking teammates blocking moves
            if(board.getPiece(xPos - i, yPos - i) != null && board.getPiece(xPos - i, yPos - i).getTeam() == team) break;

            validMoves.add(new int[]{xPos - i, yPos - i});

            //out-of-bounds check or for further enemy pieces
            if(xPos - i == 0 || yPos - i == 0) break;

            if(board.getPiece(xPos - i, yPos - i) != null && board.getPiece(xPos - i, yPos - i) instanceof King) {
                ((King) board.getPiece(xPos - i, yPos - i)).threatened();
            }

            if(board.getPiece(xPos - i, yPos - i) != null && board.getPiece(xPos - i, yPos - i).getTeam() != team) break;
        }

        //top-right
        for(int i = 1; xPos - i >= 0 && yPos + i <= 7; i++) {

            if(board.getPiece(xPos - i, yPos + i) != null && board.getPiece(xPos - i, yPos + i).getTeam() == team) break;

            validMoves.add(new int[]{xPos - i, yPos + i});
            //OOB
            if(xPos - i == 0 || yPos + i == 0) break;

            if(board.getPiece(xPos - i, yPos + i) != null && board.getPiece(xPos - i, yPos + i) instanceof King) {
                ((King) board.getPiece(xPos - i, yPos - i)).threatened();
            }

            if(board.getPiece(xPos - i, yPos + i) != null && board.getPiece(xPos - i, yPos + i).getTeam() != team) break;
        }

        //bottom-left
        for(int i = 1; xPos + i <= 7 && yPos - i >= 0; i++) {

            if(board.getPiece(xPos + i, yPos - i) != null && board.getPiece(xPos + i, yPos - i).getTeam() == team) break;

            validMoves.add(new int[]{xPos + i, yPos - i});
            //OOB
            if(xPos + i == 0 || yPos - i == 0) break;

            if(board.getPiece(xPos + i, yPos - i) != null && board.getPiece(xPos + i, yPos - i) instanceof King) {
                ((King) board.getPiece(xPos - i, yPos - i)).threatened();
            }

            if(board.getPiece(xPos + i, yPos - i) != null && board.getPiece(xPos + i, yPos - i).getTeam() != team) break;
        }

        //bottom-right
        for(int i = 1; xPos + i <= 7 && yPos + i <= 7; i++) {

            if(board.getPiece(xPos + i, yPos + i) != null && board.getPiece(xPos + i, yPos + i).getTeam() == team) break;

            validMoves.add(new int[]{xPos + i, yPos + i});
            //OOB
            if(xPos + i == 0 || yPos + i == 0) break;

            if(board.getPiece(xPos + i, yPos + i) != null && board.getPiece(xPos + i, yPos + i) instanceof King) {
                ((King) board.getPiece(xPos - i, yPos - i)).threatened();
            }

            if(board.getPiece(xPos + i, yPos + i) != null && board.getPiece(xPos + i, yPos + i).getTeam() != team) break;
        }


        return validMoves;
    }
}
