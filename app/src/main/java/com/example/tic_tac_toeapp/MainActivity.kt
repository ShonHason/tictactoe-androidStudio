package com.example.tic_tac_toeapp
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import android.os.Handler
import android.os.Looper


class MainActivity : AppCompatActivity() {

    private var isXTurn = true // מתחילים עם X
    private lateinit var buttons: Array<Button>  // מערך של כפתורים

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // הגדרת כפתור Start
        val startButton = findViewById<Button>(R.id.StartBtn)
        startButton.setOnClickListener {
            startGame()
        }

        // הגדרת כפתורים במערך
        buttons = arrayOf(
            findViewById(R.id.btn1),
            findViewById(R.id.btn2),
            findViewById(R.id.btn3),
            findViewById(R.id.btn4),
            findViewById(R.id.btn5),
            findViewById(R.id.btn6),
            findViewById(R.id.btn7),
            findViewById(R.id.btn8),
            findViewById(R.id.btn9)
        )

        // הגדרת מאזינים לכפתורים
        buttons.forEachIndexed { index, button ->
            button.setOnClickListener { selectedBtn(index) }
        }

        setupButtonListeners()
    }

    // פונקציה שמאפס את המשחק
    private fun startGame() {
        val gameLayout = findViewById<LinearLayout>(R.id.gameLayout)
        gameLayout.visibility = View.VISIBLE

        // הסתרת כפתור ה-Start
        val startButton = findViewById<Button>(R.id.StartBtn)
        startButton.visibility = View.GONE

        // איפוס כל כפתורי המשחק
            buttons.forEach { it.text = ""
                it.setBackgroundColor(Color.LTGRAY)  // X יהיה אדום
            }
    }

    // פונקציה לעדכון תור המשחק
    private fun selectedBtn(buttonIndex: Int) {
        val button = buttons[buttonIndex]

        // אם הכפתור לא לחוץ עדיין, הוסף את הטקסט X או O
        if (button.text.isEmpty()) {
            if(isXTurn){
                button.text = "X"
                button.setBackgroundColor(Color.RED)  // X
            }
            else{
                button.text = "O"
                button.setBackgroundColor(Color.BLUE)  // X
            }

            if (checkWinner()) {
                // הצגת הודעה על ניצחון
                val winner = if (isXTurn) "X" else "O"
                Toast.makeText(this, "שחקן $winner ניצח!", Toast.LENGTH_SHORT).show()

                Handler(Looper.getMainLooper()).postDelayed({
                    startGame() // איפוס המשחק לאחר השהייה
                }, 1000) // השהייה של 1000 מילישניות (שנייה אחת)

            }
            if (checkTie()){
                Toast.makeText(this,"יש לנו תיקו, חייב משחק חוזר!", Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    startGame() // איפוס המשחק לאחר השהייה
                }, 1000) // השהייה של 1000 מילישניות (שנייה אחת)

            }

            isXTurn = !isXTurn
        }
    }

    // הגדרת כפתורים לריסטרט ויציאה
    private fun setupButtonListeners() {
        val restart = findViewById<Button>(R.id.restart)
        restart.setOnClickListener {
            isXTurn = true
            Toast.makeText(this, "מאפס משחק", Toast.LENGTH_SHORT).show()
            startGame()
        }

        val exit = findViewById<Button>(R.id.exit)
        exit.setOnClickListener {
            Toast.makeText(this, "יוצא מהמשחק", Toast.LENGTH_SHORT).show()
            finish()
            startActivity(intent)
        }
    }
    private fun checkTie(): Boolean{
        for (i in 0..8){
            if(buttons[i].text=="")
                return false
                }
      return true
    }


    private fun checkWinner(): Boolean {
        // לבדוק שורות
        for (i in 0..2) {
            if (buttons[i * 3].text == buttons[i * 3 + 1].text && buttons[i * 3 + 1].text == buttons[i * 3 + 2].text && buttons[i * 3].text.isNotEmpty()) {
                return true
            }
        }
        // לבדוק עמודות
        for (i in 0..2) {
            if (buttons[i].text == buttons[i + 3].text && buttons[i + 3].text == buttons[i + 6].text && buttons[i].text.isNotEmpty()) {
                return true
            }
        }
        // לבדוק אלכסונים
        if (buttons[0].text == buttons[4].text && buttons[4].text == buttons[8].text && buttons[0].text.isNotEmpty()) {
            return true
        }
        if (buttons[2].text == buttons[4].text && buttons[4].text == buttons[6].text && buttons[2].text.isNotEmpty()) {
            return true
        }

        return false
    }
}

