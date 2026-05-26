package com.example.SquareGame.model;

import java.time.LocalDateTime;

public class Game {
    private Long id;
    private String type;
    private Integer numberOfPlayers;
    private Integer boardSize;
    private String state;
    private LocalDateTime createdAt;
    
    // Constructeur vide
    public Game() {
    }
    
    // Constructeur avec paramètres
    public Game(Long id, String type, Integer numberOfPlayers, Integer boardSize, String state, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.numberOfPlayers = numberOfPlayers;
        this.boardSize = boardSize;
        this.state = state;
        this.createdAt = createdAt;
    }
    
    // Getters
    public Long getId() {
        return id;
    }
    
    public String getType() {
        return type;
    }
    
    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }
    
    public Integer getBoardSize() {
        return boardSize;
    }
    
    public String getState() {
        return state;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    // Setters
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
    
    public void setBoardSize(Integer boardSize) {
        this.boardSize = boardSize;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", numberOfPlayers=" + numberOfPlayers +
                ", boardSize=" + boardSize +
                ", state='" + state + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
