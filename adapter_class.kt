class NotesAdapter(context: Context, notes: List<Note>) : ArrayAdapter<Note>(context, 0, notes) {

    // Override the getView() method to return a custom view for each note
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get the note for the current position
        val note = getItem(position)

        // Check if a recycled view is available
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)

        // Get the view holder for the view
        val viewHolder = view.tag as? NoteItemHolder ?: NoteItemHolder(view)
        view.tag = viewHolder

        // Bind the note to the view
        viewHolder.bind(note)

        // Return the view
        return view
    }

}
