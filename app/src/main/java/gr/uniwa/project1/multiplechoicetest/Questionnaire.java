package gr.uniwa.project1.multiplechoicetest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Questionnaire {

    private static Questionnaire INSTANCE = new Questionnaire();
    private List<Question> questions;
    private int currentQuestionIndex;

    public Questionnaire() {

    }

    public Questionnaire(List<Question> questions, int currentQuestionIndex) {
        this.questions = questions;
        this.currentQuestionIndex = currentQuestionIndex;
    }
    public static Questionnaire getInstance() {
        return INSTANCE;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    /**
     *  Returns the total number of questions in the questionnaire.
     *
     * @return
     */
    public int getTotalQuestions() {
        return questions.size();
    }

    /**
     * Returns the number of unanswered questions in the questionnaire.
     *
     * @return
     */
    public int getUnansweredQuestions() {
        int count = 0;
        for (Question question : questions) {
            if (!question.isAnswered()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns the question at the specified index in the questionnaire.
     *
     * @param questionNumber The index of the question to retrieve.
     * @return
     */
    public Question getQuestionByNumber(int questionNumber) {
        return questions.get(questionNumber);
    }

    /**
     * Returns the index of the next unanswered question, or -1 if all questions are answered.
     *
     * @return
     */
    public int getNextUnansweredQuestion() {
        if (getUnansweredQuestions() == 0)
            return -1;
        do {
            currentQuestionIndex++;
            if (currentQuestionIndex == getTotalQuestions())
                currentQuestionIndex = 0;
        } while (currentQuestionIndex < getTotalQuestions() &&
                getQuestionByNumber(currentQuestionIndex) != null &&
                getQuestionByNumber(currentQuestionIndex).isAnswered());
        return currentQuestionIndex;
    }

    /**
     * Returns a list of randomly selected questions from the questionnaire.
     *
     * @param numberOfQuestions The number of questions to select.
     * @return
     */
    public List<Question> getRandomQuestions(int numberOfQuestions) {
        List<Question> allQuestions = getQuestions();
        if (allQuestions.size() <= numberOfQuestions) {
            return allQuestions;
        }

        List<Question> randomQuestions = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < allQuestions.size(); i++) {
            indexes.add(i);
        }
        Collections.shuffle(indexes);

        for (int i = 0; i < numberOfQuestions; i++) {
            int index = indexes.get(i);
            randomQuestions.add(allQuestions.get(index));
        }

        return randomQuestions;
    }
}