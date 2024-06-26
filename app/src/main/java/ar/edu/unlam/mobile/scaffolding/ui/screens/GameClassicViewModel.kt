package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.network.CountryResponse
import ar.edu.unlam.mobile.scaffolding.data.repository.CountryRepository
import ar.edu.unlam.mobile.scaffolding.domain.models.CountryOption
import ar.edu.unlam.mobile.scaffolding.domain.models.GameQuestion
import ar.edu.unlam.mobile.scaffolding.domain.models.GameResult
import ar.edu.unlam.mobile.scaffolding.domain.services.QuizGame
import ar.edu.unlam.mobile.scaffolding.domain.usecases.GameResultUseCase
import ar.edu.unlam.mobile.scaffolding.domain.usecases.ShuffleGameLogic
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
sealed interface GameClassicUIState {
    data class Success(val question: GameQuestion) : GameClassicUIState

    data object Loading : GameClassicUIState

    data class Error(val message: String) : GameClassicUIState
}

@HiltViewModel
class GameClassicViewModel
    @Inject
    constructor(
        private val countryRepository: CountryRepository,
        private val gameResultUseCase: GameResultUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow<GameClassicUIState>(GameClassicUIState.Loading)
        val uiState = _uiState.asStateFlow()

        var showAnswer by mutableStateOf(false)
        var onCounterFinish: (() -> Unit)? = null
        var selectedCountry by mutableStateOf("")
        var pts by mutableStateOf(0)
        var actualCard by mutableStateOf(1)
        var quizGame: QuizGame? = null
        var currentQuestion: GameQuestion? by mutableStateOf(null)
        var counter by mutableStateOf(10)

        init {
            fetchCountries()
        }

        fun decrementCounter() {
            if (counter > 0) {
                counter--
                if (counter == 0) {
                    if (actualCard == 10) {
                        onGameEnd()
                        onCounterFinish?.invoke()
                    } else {
                        nextQuestion(selectedCountry)
                    }
                }
            }
        }

        fun resetCounter() {
            this.counter = 10
        }

        fun changeActualCard() {
            this.actualCard++
        }

        private fun fetchCountries() {
            viewModelScope.launch {
                try {
                    countryRepository.getAllCountries().collect { countryList ->
                        println("==test ${countryList.size}==")

                        val convertedCountries: ArrayList<CountryOption> =
                            convertToArrayList(countryList)

                        quizGame = QuizGame(convertedCountries, ShuffleGameLogic())
                        quizGame?.randomizeQuestions()
                        currentQuestion = quizGame?.getQuestion()
                        _uiState.value = currentQuestion?.let {
                            GameClassicUIState.Success(
                                it,
                            )
                        } ?: GameClassicUIState.Error("No questions avaliable")
                    }
                } catch (e: Exception) {
                    println("==Error fetching countries: ${e.message}==")
                    _uiState.value = GameClassicUIState.Error("Error fetching countries: ${e.message}")
                }
            }
        }

        private fun convertToArrayList(countryList: List<CountryResponse>): ArrayList<CountryOption> {
            val convertedCountries: ArrayList<CountryOption> = arrayListOf()

            countryList.forEach {
                convertedCountries.add(it.toCountryOption())
            }
            return convertedCountries
        }

        fun nextQuestion(answer: String) {
            showAnswer = true
            selectedCountry = answer
            quizGame?.answerQuestion(answer)
            pts = quizGame?.calculateScore() ?: 0
            evaluateOnAnswerQuestion()
        }

        fun nextFlagQuestion(answer: String) {
            showAnswer = true
            selectedCountry = answer
            quizGame?.answerFlagQuestion(answer)
            pts = quizGame?.calculateScore() ?: 0
            evaluateOnAnswerQuestion()
        }

        private fun evaluateOnAnswerQuestion() {
            // add a delay in courtine
            viewModelScope.launch {
                delay(500)
                // Verificar si hay más preguntas disponibles
                if (actualCard < 10) {
                    quizGame?.nextQuestion()
                    currentQuestion = quizGame?.getQuestion()
                    resetCounter()
                    changeActualCard()
                } else {
                    // Finalizar el juego cuando no hay más preguntas
                    onGameEnd()
                }
            }
        }

        private fun onGameEnd() {
            quizGame?.let {
                val gameResult =
                    GameResult(
                        id = null,
                        points = pts,
                        correctAnswers = quizGame?.getCorrectAnswersCount() ?: 0,
                        timestamp = System.currentTimeMillis(),
                    )
                saveGameResult(gameResult)
            }
        }

        fun saveGameResult(gameResult: GameResult) {
            viewModelScope.launch {
                try {
                    gameResultUseCase.saveGameResult(gameResult)
                } catch (e: Exception) {
                    println("==Error saving game result: ${e.message}==")
                }
            }
        }
    }
