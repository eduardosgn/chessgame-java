package application;

import boardgame.Board;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMatch match = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while (!match.getCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(match, captured);
                System.out.println();

                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(sc);

                boolean[][] possibleMoves = match.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(match.getPieces(), possibleMoves);

                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(sc);

                ChessPiece capturedPiece = match.performChessMove(source, target);

                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                }

                if (match.getPromoted() != null) {
                    System.out.print("Enter piece for promotion (B,H,R,Q): ");
                    String type = sc.nextLine();
                    match.replacePromotedPiece(type);
                }
            }
            catch (ChessException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }

        UI.clearScreen();
        UI.printMatch(match, captured);
    }
}
