package com.amirudev.githubuser.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amirudev.githubuser.R
import com.amirudev.githubuser.UserFollowResponseItem
import com.amirudev.githubuser.models.User
import com.bumptech.glide.Glide

class ListUserAdapter(
    private val listUser: List<User>,
    private val listener: onItemClickListener
): RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    interface onItemClickListener {
        fun onItemClick(data: User)
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
        val user: User = listUser[position]

        holder.tvUserUsernameItem.text = user.login
        holder.tvUserUrlItem.text = user.html_url.toString()

        val imageView: ImageView = holder.imgUserPhotoItem
        Glide.with(holder.itemView.context).load(user.avatar_url).into(imageView)

        holder.itemView.setOnClickListener {
            listener.onItemClick(listUser[position])
        }
    }
}