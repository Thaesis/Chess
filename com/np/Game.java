package com.np;

import java.io.IOException;

public class Game {
    private final Board board;
    private final GUI gui;

    /**
     * Game instance constructor
     */
    public Game() throws IOException {
        board = new Board();
        gui = new GUI(board);
    }

    //Program insertion point; Game class is essentially deprecated and not needed for the GUI implementation
    public static void main(String[] args) throws IOException {
        Game game = new Game();
    }


}
