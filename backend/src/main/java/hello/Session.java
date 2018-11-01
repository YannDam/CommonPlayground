package hello;

import java.util.ArrayList;
import java.util.List;

public class Session {
    private String name;
    private String game;
    private String place;
    private String date;
    private int numberOfPlayers;
    private List players  = new ArrayList();

    public Session(String name, String game, String place, String date, int numberOfPlayers) {
        this.name = name;
        this.game = game;
        this.place = place;
        this.date = date;
        this.numberOfPlayers = numberOfPlayers;

        players.add("Host");
        // players.size() == numberOfPlayers
    }

    public String getName() {
        return name;
    }

    public String getGame() {
        return game;
    }

    public String getPlace() {
        return place;
    }

    public String getDate() {
        return date;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public List getPlayers() {
        return players;
    }

    @Override
    public String toString() {
        return "Session name=" + name + " game=" + game + " place=" + place + " date=" + date + " numberOfPlayers=" + numberOfPlayers + " players=" + players;
    }
}