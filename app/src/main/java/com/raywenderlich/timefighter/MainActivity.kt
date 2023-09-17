
package com.raywenderlich.timefighter

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.timefighter.R


class MainActivity : AppCompatActivity() {
  private var score = 0
  private var gameStarted = false
  private val TAG = MainActivity::class.java.simpleName

  private lateinit var countDownTimer: CountDownTimer
  private var initialCountDown: Long = 20000
  private var countDownInterval: Long = 1000
  private var timeLeft = 20

  private lateinit var gameScoreTextView: TextView
  private lateinit var timeLeftTextView: TextView
  private lateinit var tapMeButton: Button

  companion object {
    private const val SCORE_KEY = "SCORE_KEY"
    private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setContentView(R.layout.activity_main)
    Log.d(TAG, "onCreate called. Score is: $score")
    // 1
    gameScoreTextView = findViewById(R.id.game_score_text_view)

    timeLeftTextView = findViewById(R.id.time_left_text_view)

    tapMeButton = findViewById(R.id.tap_me_button)

    //2
    tapMeButton.setOnClickListener { incrementScore()}

    if (savedInstanceState != null){
      score = savedInstanceState.getInt(SCORE_KEY)
      timeLeft = savedInstanceState.getInt(TIME_LEFT_KEY)
      restoreGame()
    }
    else {
      resetGame()
    }

    // connect views to variables

}
  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    outState.putInt(SCORE_KEY, score)
    outState.putInt(TIME_LEFT_KEY, timeLeft)
    countDownTimer.cancel()

    Log.d(TAG, "onSaveInstanceState: Saving score: $score & TimeLeft: $timeLeft")
  }
  override fun onDestroy(){
    super.onDestroy()
    Log.d(TAG, "onDEstroy called.")
  }
  private fun incrementScore(){
    score++
    val newScore = getString(R.string.your_score, score)
    gameScoreTextView.text = newScore
    if (!gameStarted) {
      startGame()
    }
    // increment score logic
  }
  private fun resetGame() {
    // 1
    score = 0
    val initialScore = getString(R.string.your_score, score)
    gameScoreTextView.text = initialScore
    val initialTimeLeft = getString(R.string.time_left, 20)
    timeLeftTextView.text = initialTimeLeft

      countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval)
      {
      //3
        override fun onTick(millisUntilFinished: Long) {
          timeLeft = millisUntilFinished.toInt() / 1000

        val timeLeftString = getString(R.string.time_left, timeLeft)
          timeLeftTextView.text = timeLeftString
        }
          override fun onFinish() {
            endGame()
            // to be implemented Later
          }
      }
      gameStarted = false
    // reset game logic
  }
  private fun restoreGame() {
    val restoredScore = getString(R.string.your_score, score)
    gameScoreTextView.text = restoredScore

    val restoredTime = getString(R.string.time_left, timeLeft)
    timeLeftTextView.text = restoredTime

    countDownTimer = object: CountDownTimer((timeLeft * 1000).toLong(), countDownInterval) {
      timeLeft = millisUntilFinished.toInt() / 1000

      val timeLeftString = getString(R.string.time_left, timeLeft)
      timeLeftTextView.text = timeLeftString}

    }
  override fun onFinish()
  {
    endGame()
    countDownTimer.start()
    gameStarted = true
  }

  }
  private fun startGame() {
    countDownTimer.start()
    gameStarted = true
    // start game logic
  }
  private fun endGame(){
    Toast.makeText(this, getString(R.string.game_over_message, score), Toast.LENGTH_LONG).show()
    resetGame()
    // end game logic
  }


}