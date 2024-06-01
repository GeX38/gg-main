package com.example.gg

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventsAdapter(private val eventsList: List<Event>) :
    RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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

        holder.joinButton.setOnClickListener {
            // Handle join event action
        }
    }

    override fun getItemCount() = eventsList.size
}
