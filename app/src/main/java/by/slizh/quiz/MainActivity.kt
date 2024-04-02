package by.slizh.quiz

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import by.slizh.quiz.fragments.HomeFragment
import by.slizh.quiz.fragments.RatingFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var signOutButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var ratingListButton: ImageButton

    private lateinit var fragmentManager: FragmentManager

    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        signOutButton = findViewById(R.id.signOutButton)
        signOutButton.setOnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setMessage("Do you want to sign out of your account?")
            builder.setPositiveButton("Yes") { p0, p1 ->

                googleSignInClient.signOut().addOnCompleteListener {
                    val intent = Intent(this, AuthActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                Firebase.auth.signOut()
                p0.dismiss()
            }
            builder.setNegativeButton("No") { p0, p1 ->
                p0.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }

        homeButton = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            replaceFragment(HomeFragment())
        }

        ratingListButton = findViewById(R.id.ratingListButton)
        ratingListButton.setOnClickListener {
            replaceFragment(RatingFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        fragmentManager = supportFragmentManager

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView, fragment)
        fragmentTransaction.commit()
    }
}