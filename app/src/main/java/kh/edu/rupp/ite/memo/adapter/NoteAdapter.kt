
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.memo.R
import kh.edu.rupp.ite.memo.databinding.ViewHolderNoteBinding
import kh.edu.rupp.ite.memo.models.NoteResponse

class NoteAdapter(
    private val onNoteClicked: (NoteResponse) -> Unit) :
    ListAdapter<NoteResponse, NoteAdapter.NoteViewHolder>(ComparatorDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val orientation = parent.context.resources.configuration.orientation
        val layoutRes = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            R.layout.view_holder_note // For Horizontal layout
        } else {
            R.layout.view_holder_note2 // For Vertical Layout
        }
        val binding = ViewHolderNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        note?.let {
            holder.bind(it)
        }
    }

    inner class NoteViewHolder(private val binding: ViewHolderNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteResponse) {
            binding.title.text = note.title
            binding.desc.text = note.description
            binding.root.setOnClickListener {
                onNoteClicked(note)
            }
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<NoteResponse>() {
        override fun areItemsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem == newItem
        }
    }
}
