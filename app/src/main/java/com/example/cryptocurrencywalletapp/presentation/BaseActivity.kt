package com.example.cryptocurrencywalletapp.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.cryptocurrencywalletapp.R
import com.example.cryptocurrencywalletapp.presentation.coinList.CoinListActivity
import com.example.cryptocurrencywalletapp.presentation.userProfile.ProfileActivity
import com.example.cryptocurrencywalletapp.presentation.walletList.WalletListActivity
import com.example.cryptocurrencywalletapp.utils.setGone
import com.example.cryptocurrencywalletapp.utils.setVisible
import com.google.android.material.bottomnavigation.BottomNavigationView



abstract class BaseActivity : AppCompatActivity() {
    var mKeyboardVisible: Boolean = false
    abstract fun getBottomIcon(): Int


    abstract fun getKeyBoard(): Boolean

    private fun setKeyBoard(inView: Boolean){
        mKeyboardVisible = inView
    }



   private fun setBottomIcon(id: Int){
        getBottomNavigation().selectedItemId = id
    }
    private fun getBottomNavigation(): BottomNavigationView = findViewById(R.id.bottom_navigation)
    private fun getContentView(): View = window.decorView.findViewById(android.R.id.content)
    override fun onStart() {
        super.onStart()
        setKeyBoard(getKeyBoard())
        setBottomIcon(getBottomIcon())
        getBottomNavigation().setOnItemSelectedListener{

                when(it.itemId){
                    R.id.profileActivity->startBottomNavigationActivity("Profile", this)
                    R.id.walletActivity -> startBottomNavigationActivity("Wallet", this)
                    R.id.coinListActivity -> startBottomNavigationActivity("Coin", this)
                }
                 true;
            }
        }



    private fun startBottomNavigationActivity(strActivity: String, applicationContext: Context){
        var intent: Intent? = null
        when(strActivity) {
            "Profile" -> intent = ProfileActivity.newIntent(applicationContext)
            "Coin" -> intent = CoinListActivity.newIntent(applicationContext)
            "Wallet" -> intent = WalletListActivity.newIntent(applicationContext)
        }
        if (intent !=null)  {
            startActivity(intent)
            finish()
        }
    }

    private fun closeKeyboard(view: View){
        val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(view.windowToken, 0)
    }

     private fun  setLayoutKeyboardVisibilityListener() :ViewTreeObserver.OnGlobalLayoutListener {
            val mLayoutKeyboardVisibilityListener = ViewTreeObserver.OnGlobalLayoutListener {
                val rectangle = Rect()
                val contentView: View = getContentView()
                contentView.getWindowVisibleDisplayFrame(rectangle)
                val screenHeight = contentView.rootView.height


                val keypadHeight: Int = screenHeight - rectangle.bottom

                val isKeyboardNowVisible = keypadHeight > screenHeight * 0.15
                if (mKeyboardVisible != isKeyboardNowVisible) {
                    if (isKeyboardNowVisible) {
                        onKeyboardShown()
                    } else {
                        onKeyboardHidden()
                    }
                }
                mKeyboardVisible = isKeyboardNowVisible
            }
        return mLayoutKeyboardVisibilityListener
    }

    override fun onResume() {
        super.onResume()
        getContentView().viewTreeObserver.addOnGlobalLayoutListener {
            setLayoutKeyboardVisibilityListener()
        }
    }
    override fun onPause() {
        super.onPause()
        getContentView().viewTreeObserver.removeOnGlobalLayoutListener {
                setLayoutKeyboardVisibilityListener()
            }
    }
    private fun onKeyboardShown() {
     getBottomNavigation().setGone()
    }

    private fun onKeyboardHidden() {
        getBottomNavigation().setVisible()
    }

}






