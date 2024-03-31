package by.slizh.quiz.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.slizh.quiz.data.UserResult
import by.slizh.quiz.databinding.UserRowBinding



class RatingAdapter(private val userResultList : List<UserResult>) :
    RecyclerView.Adapter<RatingAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: UserRowBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model : UserResult){
            binding.apply {
                userNameRating.text = model.userName
                bestResultText.text = model.bestResult.toString()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = UserRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userResultList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(userResultList[position])
    }
}