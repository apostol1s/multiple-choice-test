package gr.uniwa.project1.multiplechoicetest;

public class Score {
    private int id;
    private int score;
    private int userId;

    public Score(int id, int score, int userId) {
        this.id = id;
        this.score = score;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
