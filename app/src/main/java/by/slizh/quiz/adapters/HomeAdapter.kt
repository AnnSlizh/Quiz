package by.slizh.quiz.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.slizh.quiz.QuizActivity
import by.slizh.quiz.data.Topic
import by.slizh.quiz.databinding.TopicRowBinding


class HomeAdapter() : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    private val topicList = ArrayList<Topic>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        val binding = TopicRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        holder.bind(topicList[position])
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    fun setTopicsData(topicList: List<Topic>) {
        this.topicList.clear()
        this.topicList.addAll(topicList)
        notifyDataSetChanged()
    }


    class HomeViewHolder(private val binding: TopicRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Topic) {
            binding.apply {
                quizTitle.text = model.title
                quizSubtitle.text = model.subtitle

                root.setOnClickListener {
                    val intent = Intent(root.context, QuizActivity::class.java)
                    QuizActivity.questionsList = model.questionList
                    root.context.startActivity(intent)
                }
            }
        }
    }
}