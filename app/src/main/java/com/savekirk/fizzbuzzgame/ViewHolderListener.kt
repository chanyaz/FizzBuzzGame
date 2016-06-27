package com.savekirk.fizzbuzzgame

import android.view.View

interface ViewHolderListener {
    fun gameOver(shareElement : Boolean = false, sharedElement : View? = null)
    fun newGame()
}

