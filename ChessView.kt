package com.example.finalproject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ChessView : AppCompatActivity() {
    lateinit var relativeLayout: RelativeLayout
    private lateinit var currContext : Context
    private var chessLogic : ChessLogic = ChessLogic()
    private lateinit var buttons : Array<Array<ImageButton>>
    private lateinit var activity : MainActivity
    private var activePiece : Int = -1
    var currPlayer : String = "White"
    private var whitePiecesSet : ArrayList<Int> = arrayListOf<Int>()
    private var blackPiecesSet : ArrayList<Int> = arrayListOf<Int>()
    private var activePiecei : Int = 0
    private var activePiecej : Int = 0
    lateinit var status : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        whitePiecesSet.add(R.id.Wpawn)
        // ... Add other initialization here or in buildBoard() method
        whitePiecesSet.add(R.id.Wpawn)
        whitePiecesSet.add(R.id.Wknight)
        whitePiecesSet.add(R.id.Wbishop)
        whitePiecesSet.add(R.id.Wqueen)
        whitePiecesSet.add(R.id.Wking)
        whitePiecesSet.add(R.id.Wrook)
        blackPiecesSet.add(R.id.Bpawn)
        blackPiecesSet.add(R.id.Bknight)
        blackPiecesSet.add(R.id.Bbishop)
        blackPiecesSet.add(R.id.Bqueen)
        blackPiecesSet.add(R.id.Bking)
        blackPiecesSet.add(R.id.Brook)

        currContext = this
        buildBoard(CONTEXT)
    }

    fun buildBoard(context: Context) {
        var width : Int  = Resources.getSystem().displayMetrics.widthPixels
        var w : Int = width / 8



        var gridLayout = GridLayout(context)
        gridLayout.columnCount = 8
        gridLayout.rowCount = 8
        var rowSpec : GridLayout.Spec = GridLayout.spec( 0, 8 )
        var columnSpec : GridLayout.Spec = GridLayout.spec( 0, 8 )
        var lp : GridLayout.LayoutParams = GridLayout.LayoutParams( rowSpec, columnSpec )
        status = TextView( context )
        status.setLayoutParams(lp)
        status.width = width
        status.height = 200
        status.gravity = Gravity.CENTER
        status.setBackgroundColor(Color.parseColor("#D2B48C"))
        status.textSize = 35.0f
        status.text = "$currPlayer's Turn"

        if(currPlayer == "White") status.setTextColor(Color.WHITE) else status.setTextColor(Color.BLACK)

        gridLayout.addView(status)


        buttons = Array<Array<ImageButton>>( 8, { i -> Array<ImageButton> ( 8, { j -> ImageButton( context ) } ) } )

        var handler : ButtonHandler = ButtonHandler()

        for( i in 0 .. buttons.size - 1 ) {
            for( j in 0 .. buttons[i].size - 1 ) {
                gridLayout.addView( buttons[i][j], w, w)
                if ((i + j)%2 == 1) buttons[i][j].setBackgroundColor(Color.BLACK) else buttons[i][j].setBackgroundColor(Color.WHITE)
                if ((i == 0 && j == 0) || (i == 0 && j == 7)) {
                    buttons[i][j].setImageResource(R.drawable.b_rook_1x)
                    buttons[i][j].id = R.id.Brook
                } else if ((i == 7 && j == 0) || (i == 7 && j == 7)) {
                    buttons[i][j].setImageResource(R.drawable.w_rook_1x)
                    buttons[i][j].id = R.id.Wrook
                } else if ((i == 0 && j == 1) || (i == 0 && j == 6)) {
                    buttons[i][j].setImageResource(R.drawable.b_knight_1x)
                    buttons[i][j].id = R.id.Bknight
                } else if ((i == 7 && j == 1) || (i == 7 && j == 6)) {
                    buttons[i][j].setImageResource(R.drawable.w_knight_1x)
                    buttons[i][j].id = R.id.Wknight
                } else if ((i == 0 && j == 2) || (i == 0 && j == 5)) {
                    buttons[i][j].setImageResource(R.drawable.b_bishop_1x)
                    buttons[i][j].id = R.id.Bbishop
                } else if ((i == 7 && j == 2) || (i == 7 && j == 5)) {
                    buttons[i][j].setImageResource(R.drawable.w_bishop_1x)
                    buttons[i][j].id = R.id.Wbishop
                } else if (i == 0 && j == 3) {
                    buttons[i][j].setImageResource(R.drawable.b_king_1x)
                    buttons[i][j].id = R.id.Bking
                } else if (i == 7 && j == 4) {
                    buttons[i][j].setImageResource(R.drawable.w_king_1x)
                    buttons[i][j].id = R.id.Wking
                } else if (i == 0 && j == 4) {
                    buttons[i][j].setImageResource(R.drawable.b_queen_1x)
                    buttons[i][j].id = R.id.Bqueen
                } else if (i == 7 && j == 3) {
                    buttons[i][j].setImageResource(R.drawable.w_queen_1x)
                    buttons[i][j].id = R.id.Wqueen
                } else if (i == 1) {
                    buttons[i][j].setImageResource(R.drawable.b_pawn_1x)
                    buttons[i][j].id = R.id.Bpawn
                } else if (i == 6) {
                    buttons[i][j].setImageResource(R.drawable.w_pawn_1x)
                    buttons[i][j].id = R.id.Wpawn
                } else {
                    buttons[i][j].id = R.id.empty
                }
                buttons[i][j].scaleType = ImageView.ScaleType.FIT_XY
                buttons[i][j].setOnClickListener( handler )
            }
        }

        val layoutParams = GridLayout.LayoutParams()
        layoutParams.leftMargin = 45
        layoutParams.rightMargin = 45
        layoutParams.topMargin = 45
        layoutParams.bottomMargin = 45

        val button = Button(context)
        val button2 = Button(context)
        button.layoutParams = layoutParams
        button2.layoutParams = layoutParams
        button.text = "Game Rules"
        button2.text = "Statistics"

        relativeLayout = RelativeLayout(context)
        relativeLayout.setBackgroundColor(Color.parseColor("#D2B48C"))

        relativeLayout.addView(gridLayout)

        val buttonParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        buttonParams.addRule(RelativeLayout.BELOW, gridLayout.id) // Position below GridLayout
        buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL) // Center horizontally
        buttonParams.topMargin = 1800

        val buttonParams2 = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        buttonParams2.addRule(RelativeLayout.BELOW, gridLayout.id) // Position below GridLayout
        buttonParams2.addRule(RelativeLayout.CENTER_HORIZONTAL) // Center horizontally
        buttonParams2.topMargin = 1600

        button.layoutParams = buttonParams
        button2.layoutParams = buttonParams2
        var handler2 : ButtonHandler2 = ButtonHandler2()
        var handler3 : ButtonHandler3 = ButtonHandler3()
        button.setOnClickListener(handler2)
        button2.setOnClickListener(handler3)

        relativeLayout.addView(button)
        relativeLayout.addView(button2)

        setContentView( relativeLayout )
    }

    fun getWhiteTaken() : Int {
        return chessLogic.getNumberWhitePiecesTaken()
    }

    fun getBlackTaken() : Int {
        return chessLogic.getNumberBlackPiecesTaken()
    }

    inner class ButtonHandler2 : View.OnClickListener {
        override fun onClick(v: View?) {
            setContentView(R.layout.chess_directions)
        }
    }

    inner class ButtonHandler3 : View.OnClickListener {
        override fun onClick(v: View?) {
            setContentView(R.layout.statistics_page)
        }
    }

    fun startGame(v: View?) {
        var intent: Intent = Intent(this, ChessView::class.java)
        startActivity( intent )
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_from_right)
    }

    fun checkDirections(v: View?) {
        setContentView(R.layout.chess_directions)
    }

    fun findStats(v: View?) {
        setContentView(R.layout.statistics_page)

        var pref : SharedPreferences = this.getSharedPreferences( this.packageName + "_preferences",
            Context.MODE_PRIVATE )
        var whiteText : TextView = findViewById(R.id.secondMessageValue)
        whiteText.text = "" + pref.getInt(ChessView.WHITE_PIECES_TAKEN, 0)
        var blackText : TextView = findViewById(R.id.thirdMessageValue)
        blackText.text = "" + pref.getInt(ChessView.BLACK_PIECES_TAKEN, 0)
        var whiteVicText : TextView = findViewById(R.id.fourthMessageValue)
        whiteVicText.text = "" + pref.getInt(ChessView.STORED_WHITE_WINS, 0)
        var blackVicText : TextView = findViewById(R.id.fifthMessageValue)
        blackVicText.text = "" + pref.getInt(ChessView.STORED_BLACK_WINS, 0)
    }

    inner class ButtonHandler : View.OnClickListener {
        override fun onClick(v: View?) {
            if (v != null) {
                first@ for( i in 0 .. buttons.size - 1 ) {
                    for( j in 0 .. buttons[i].size - 1 ) {
                        if( v == buttons[i][j] ) {
                            Log.w("Button Found", "processing")
                            if (activePiece == -1){
                                Log.w("First Click", "processing")
                                activePiece = chessLogic.processFirstClick(buttons[i][j], currPlayer, whitePiecesSet, blackPiecesSet)
                                if (activePiece != -1) {
                                    activePiecei = i
                                    activePiecej = j
                                    buttons[i][j].setBackgroundColor(Color.YELLOW)
                                }
                            } else {
                                Log.w("Second Click", "processing")
                                activePiece = chessLogic.processSecondClick(currContext, activePiece, currPlayer, buttons, i, j, whitePiecesSet, blackPiecesSet, activePiecei, activePiecej)

                                if (activePiece == 0) {
                                    currPlayer = if(currPlayer == "White") "Black" else "White"
                                    status.text = "$currPlayer's Turn"
                                    if(currPlayer == "White") status.setTextColor(Color.WHITE) else status.setTextColor(Color.BLACK)
                                    activePiece = -1
                                    activePiecei = 0
                                    activePiecej = 0
//                                    updatePiecesTaken(currContext, chessLogic.getNumberWhitePiecesTaken(), chessLogic.getNumberBlackPiecesTaken())
                                }
                            }
                            break@first
                        }
                    }
                }
            }
        }
    }

    companion object {
        lateinit var CONTEXT: Context
        lateinit var ACTIVITY : MainActivity
        const val STORED_WHITE_WINS : String = "white_victories"
        const val STORED_BLACK_WINS : String = "black_victories"
        const val WHITE_PIECES_TAKEN : String = "white_pieces_taken"
        const val BLACK_PIECES_TAKEN : String = "black_pieces_taken"
    }


}