// Import the necessary libraries
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.note_item.*

// Create a class to represent a note
data class Note(val id: Int, val text: String)

// Create a class that extends SQLiteOpenHelper to manage the database
class NotesDBHelper(context: Context) : SQLiteOpenHelper(context, "Notes.db", null, 1) {

    // Override the onCreate() method to create the notes table
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE notes (id INTEGER PRIMARY KEY, text TEXT)")
    }

    // Override the onUpgrade() method to handle database upgrades
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS notes")
        onCreate(db)
    }

    // Create a function to save a new note to the database
    fun addNote(note: Note) {
        // Open a writable database
        val db = writableDatabase

        // Create a ContentValues object to store the note's data
        val values = ContentValues()
        values.put("text", note.text)

        // Insert the note into the database
        db.insert("notes", null, values)

        // Close the database
        db.close()
    }

    // Create a function to get all notes from the database
    fun getAllNotes(): List<Note> {
        // Open a readable database
        val db = readableDatabase

        // Create a list to store the notes
        val notes = mutableListOf<Note>()

        // Query the database for all notes
        val cursor = db.query("notes", arrayOf("id", "text"), null, null, null, null, null)
        while (cursor.moveToNext()) {
            // Add each note to the list
            notes.add(Note(cursor.getInt(0), cursor.getString(1)))
        }

        // Close the cursor and database
        cursor.close()
        db.close()

        // Return the list of notes
        return notes
    }

}

// Create a class that extends LayoutContainer to make it easier to work with the note item layout
class NoteItemHolder(override val containerView: View) : LayoutContainer {

    // Create a function to bind a note to the layout
    fun bind(note: Note) {
        // Set the text of the TextView to the note's text
        note_text.text = note.text
    }

}

// Create an adapter class to display the list
