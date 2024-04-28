package gr.uniwa.project1.multiplechoicetest;

public class Question {

    private int id;
    private String questionText;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String correctAnswer;
    private String imageName;
    private String userAnswer;

    public Question(int id, String questionText, String optionA, String optionB, String optionC, String optionD, String correctAnswer, String imagePath) {
        this.id = id;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.imageName = imagePath;
        userAnswer = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    /**
     * Checks if the question has been answered.
     *
     * @return True if the question has been answered, false otherwise.
     */
    public boolean isAnswered() {
        return userAnswer != null && !userAnswer.isEmpty();
    }

    /**
     * Checks if the user's answer matches the correct answer for the question.
     *
     * @return True if the user's answer is correct, false otherwise.
     */
    public boolean isCorrect() {
        return userAnswer != null && userAnswer.equals(correctAnswer);
    }
}