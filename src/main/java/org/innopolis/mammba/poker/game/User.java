package org.innopolis.mammba.poker.game;

public class User {
    private String nickname;
    private int money;
    private static Long userCounter = 0L;

    /**
     * Default constructor for an anonymous user.
     * Use UserProvider.loadUser() to create a registered user.
     */
    public User() {
        nickname = "Пользователь "+userCounter.toString();
        userCounter++;
    }
    public String getNickname() {
        return nickname;
    }
    public int getMoney() {
        return money;
    }

    /**
     * We need setters in order to convert JSON to objects via reflection
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setMoney(int money) {
        this.money = money;
    }
}
