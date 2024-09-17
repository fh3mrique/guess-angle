package com.game.angle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/angle")
@CrossOrigin(origins = "*")
public class AngleController {

    private GameState gameState = new GameState();

    @GetMapping("/start")
    public ResponseEntity<Map<String, Integer>> startGame() {
        gameState.resetGame(); // Reseta o estado do jogo
        Map<String, Integer> response = new HashMap<>();
        response.put("correctAngle", gameState.getCorrectAngle()); // Envia o ângulo correto para o frontend
        return ResponseEntity.ok(response);
    }

    @PostMapping("/guess")
    public ResponseEntity<Map<String, String>> guessAngle(@RequestBody Map<String, Integer> requestBody) {
        int userGuess = requestBody.get("angleGuess");
        Map<String, String> response = new HashMap<>();

        if (gameState.getAttempts() >= 4) {
            response.put("status", "gameOver");
            response.put("message", "O jogo acabou. O ângulo correto era: " + gameState.getCorrectAngle());
            return ResponseEntity.ok(response);
        }

        gameState.incrementAttempts();

        if (userGuess == gameState.getCorrectAngle()) {
            response.put("status", "correct");
            response.put("message", "Acertou! O ângulo correto é " + gameState.getCorrectAngle());
        } else {
            String hint;
            int difference = Math.abs(userGuess - gameState.getCorrectAngle());

            if (difference < 5) {
                hint = "Fervendo!";
            } else if (difference < 10) {
                hint = "Quente!";
            } else if (difference < 20) {
                hint = "Ficando quente";
            } else if (difference < 50) {
                hint = "Morno";
            } else if (difference < 100) {
                hint = "Frio!";
            } else {
                hint = "Congelando!";
            }

            response.put("status", "incorrect");
            response.put("hint", hint);
            response.put("arrow", userGuess > gameState.getCorrectAngle() ? "↑" : "↓");
        }

        response.put("attemptsLeft", String.valueOf(4 - gameState.getAttempts()));
        return ResponseEntity.ok(response);
    }

    private static class GameState {
        private int correctAngle;
        private int attempts;

        public GameState() {
            resetGame();
        }

        public int getCorrectAngle() {
            return correctAngle;
        }

        public int getAttempts() {
            return attempts;
        }

        public void incrementAttempts() {
            this.attempts++;
        }

        public void resetGame() {
            this.correctAngle = (int) (Math.random() * 360);
            this.attempts = 0;
        }
    }
}


