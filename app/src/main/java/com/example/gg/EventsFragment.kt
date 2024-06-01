package com.example.gg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EventsFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var eventsRecyclerView: RecyclerView
    private lateinit var adapter: EventsAdapter
    private val eventsList: ArrayList<Event> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("events")

        eventsRecyclerView = view.findViewById(R.id.eventsRecyclerView)
        eventsRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = EventsAdapter(eventsList)
        eventsRecyclerView.adapter = adapter

        loadEvents()

        return view
    }

    private fun loadEvents() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                eventsList.clear()
                for (dataSnapshot in snapshot.children) {
                    val event = dataSnapshot.getValue(Event::class.java)
                    event?.let { eventsList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to load events: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
