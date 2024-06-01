package com.example.gg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EventsAdapter(private val eventsList: List<Event>) :
    RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.eventTitleTextView)
        val descriptionTextView: TextView = view.findViewById(R.id.eventDescriptionTextView)
        val dateTextView: TextView = view.findViewById(R.id.eventDateTextView)
        val timeTextView: TextView = view.findViewById(R.id.eventTimeTextView)
        val organizerTextView: TextView = view.findViewById(R.id.eventOrganizerTextView)
        val joinButton: Button = view.findViewById(R.id.joinButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = eventsList[position]
        holder.titleTextView.text = event.title
        holder.descriptionTextView.text = event.description
        holder.dateTextView.text = event.date
        holder.timeTextView.text = event.time
        holder.organizerTextView.text = event.organizer


        val isRegistered = event.registeredUsers.contains(FirebaseAuth.getInstance().currentUser?.uid)

        holder.joinButton.text = if (isRegistered) "Unregister" else "Join"

        holder.joinButton.setOnClickListener {
            val currentUserUid = FirebaseAuth.getInstance().currentUser?.uid
            if (currentUserUid != null) {
                if (isRegistered) {
                    event.registeredUsers.remove(currentUserUid)
                } else {
                    event.registeredUsers.add(currentUserUid)
                }
                updateEvent(event)
            }
        }
    }

    override fun getItemCount() = eventsList.size
    private fun updateEvent(event: Event) {
        val databaseReference = FirebaseDatabase.getInstance().reference
        val updatesMap = mutableMapOf<String, Any?>()
        updatesMap["registeredUsers"] = event.registeredUsers
        val childUpdates = hashMapOf<String, Any>(
            "/events/${event.id}/registeredUsers" to event.registeredUsers
        )

        databaseReference.updateChildren(childUpdates)
            .addOnSuccessListener {
            }
            .addOnFailureListener { exception ->

                Log.e(TAG, "Error updating event", exception)
            }
    }

    companion object {
        private const val TAG = "EventsAdapter"
    }
}