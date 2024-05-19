package ar.edu.unlam.mobile.scaffolding.domain

import java.util.ArrayList
import java.util.Random

class QuizHelper {
    fun selectRandomStringQuestion(questions: ArrayList<String>): String? {
        if (questions.isEmpty()) {
            return null
        }
        val random = Random()
        val index = random.nextInt(questions.size)
        return questions.removeAt(index)
    }

    fun selectRandomQuestion(questions: ArrayList<CountryQuestion>): CountryQuestion? {
        if (questions.isEmpty()) {
            return null
        }
        val random = Random()
        val index = random.nextInt(questions.size)
        return questions.removeAt(index)
    }

}
