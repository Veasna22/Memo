package kh.edu.rupp.ite.memo.api.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.memo.models.NoteResponse
import java.util.*
import kh.edu.rupp.ite.memo.databinding.ViewHolderNoteBinding


class ProductAdapter : ListAdapter<NoteResponse, ProductAdapter.ProductViewHolder>(
    object : DiffUtil.ItemCallback<NoteResponse>() {
        override fun areItemsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return Objects.equals(oldItem.id, newItem.id)
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderNoteBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ProductViewHolder(private val itemBinding: ViewHolderNoteBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(note: NoteResponse) {
            itemBinding.txtTitle.text = note.title;
            itemBinding.txtContent.text = note.content
        }
    }
}
