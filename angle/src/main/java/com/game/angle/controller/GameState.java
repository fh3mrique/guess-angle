package com.game.angle.controller;

public class GameState {

    private int correctAngle; // O ângulo correto
    private int attempts; // Número de tentativas feitas pelo jogador

    // Construtor que reseta o jogo ao iniciar
    public GameState() {
        resetGame();
    }

    // Retorna o ângulo correto
    public int getCorrectAngle() {
        return correctAngle;
    }

    // Retorna o número de tentativas já feitas
    public int getAttempts() {
        return attempts;
    }

    // Incrementa o número de tentativas
    public void incrementAttempts() {
        this.attempts++;
    }

    // Reseta o estado do jogo, gerando um novo ângulo aleatório e zerando as tentativas
    public void resetGame() {
        this.correctAngle = (int) (Math.random() * 360); // Gera um ângulo aleatório entre 0 e 360
        this.attempts = 0; // Zera as tentativas
    }
}
