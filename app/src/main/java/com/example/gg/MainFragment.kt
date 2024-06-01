package com.example.gg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class MainFragment : Fragment() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        mAuth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        val textView = view.findViewById<TextView>(R.id.name)
        val user = mAuth.currentUser

        user?.let {
            val userName = it.displayName
            textView.text = "Welcome, $userName"
        }

        val signOutButton = view.findViewById<Button>(R.id.logout_button)
        signOutButton.setOnClickListener {
            signOutAndStartSignInFragment()
        }

        val eventsButton = view.findViewById<Button>(R.id.events_button)
        eventsButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, EventsFragment())
                .addToBackStack(null)  // Добавляем транзакцию в back stack
                .commit()
        }

        return view
    }

    private fun signOutAndStartSignInFragment() {
        mAuth.signOut()
        mGoogleSignInClient.signOut().addOnCompleteListener(requireActivity()) {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SignInFragment())
                .commit()
        }
    }
}
