package com.amirudev.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListUserAdapter(
    private val listUser: List<UserFollowResponseItem>,
    private val listener: onItemClickListener): RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    interface onItemClickListener {
        fun onItemClick(data: UserFollowResponseItem)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserUsernameItem: TextView = itemView.findViewById(R.id.tv_user_username_item)
        val tvUserUrlItem: TextView = itemView.findViewById(R.id.tv_user_url_item)
        val imgUserPhotoItem: ImageView = itemView.findViewById(R.id.img_user_photo_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (avatarUrl, id, login, url) = listUser[position]

        holder.tvUserUsernameItem.text = login
        holder.tvUserUrlItem.text = url.toString()

        val imageView: ImageView = holder.imgUserPhotoItem
        Glide.with(holder.itemView.context).load(avatarUrl).into(imageView)

        holder.itemView.setOnClickListener {
            listener.onItemClick(listUser[position])
        }
    }
}