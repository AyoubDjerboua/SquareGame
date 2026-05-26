package com.example.SquareGame.model;

public class GameCreationParams {
    private String gameType;
    private Integer numberOfPlayers;
    private Integer boardSize;
    
    // Constructeur vide (nécessaire pour Spring)
    public GameCreationParams() {
    }
    
    // Constructeur avec paramètres
    public GameCreationParams(String gameType, Integer numberOfPlayers, Integer boardSize) {
        this.gameType = gameType;
        this.numberOfPlayers = numberOfPlayers;
        this.boardSize = boardSize;
    }
    
    // Getters
    public String getGameType() {
        return gameType;
    }
    
    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }
    
    public Integer getBoardSize() {
        return boardSize;
    }
    
    // Setters
    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
    
    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
    
    public void setBoardSize(Integer boardSize) {
        this.boardSize = boardSize;
    }
}
