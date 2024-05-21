package com.np.pieces;

import com.np.Board;

import java.util.ArrayList;

/**
 * Knight child-class of the Piece class.
 */

public class Knight extends Piece{

    public Knight(int x, int y, char t) {
        xPos = x;
        yPos = y;
        this.x = x * 64;
        this.y = y * 64;
        team = t;
    }

    /**
     * Method to generate valid/legal moves for the Knight piece.
     * @param board current board-state to calculate legal moves.
     * @return the ArrayList consisting of [x, y] coordinates of possible legal moves.
     */

    @Override
    public ArrayList<int[]> generateValidMoves(Board board) {

        validMoves.clear();

        for(int i = xPos - 1; i <= xPos + 1; i += 2)  {
            for(int j = yPos - 2; j <= yPos + 2; j += 4) {

                int[] index = {i, j};

                if(index[0] > 7 || index[0] < 0 || index[1] > 7 || index[1] < 0) continue;

                if (board.getPiece(i,j) != null && board.getPiece(i, j).getTeam() == team) continue;

                validMoves.add(new int[]{i, j});
            }
        }

        for(int i = xPos - 2; i <= xPos + 2; i += 4) {
            for(int j = yPos - 1; j <= yPos + 1; j += 2) {

                int[] index = {i, j};

                if(index[0] > 7 || index[0] < 0 || index[1] > 7 || index[1] < 0) continue;

                if (board.getPiece(i,j) != null && board.getPiece(i, j).getTeam() == team) continue;

                validMoves.add(new int[]{i, j});
            }
        }

        return validMoves;
    }
}
