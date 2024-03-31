package by.slizh.quiz.adapters

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.slizh.quiz.QuizActivity
import by.slizh.quiz.R
import by.slizh.quiz.data.Topic
import by.slizh.quiz.databinding.ActivityQuizBinding
import by.slizh.quiz.databinding.TopicRowBinding
import by.slizh.quiz.fragments.QuizFragment


//class HomeAdapter(private val context: Context, private var topicList: List<Topic> ) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
//
//    //private var topicList = emptyList<Topic>()
//
//    private var startQuiz: ((Topic) -> Unit)? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
//        val itemView =
//            LayoutInflater.from(parent.context).inflate(R.layout.topic_row, parent, false)
//      //  val binding = TopicRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        return HomeViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
//        val currentItem = topicList[position]
//
//        holder.title.text = currentItem.title
//        holder.subtitle.text = currentItem.subtitle
//
//     //   holder.bind(currentItem)
//        holder.itemView.setOnClickListener {
//            startQuiz?.invoke(currentItem)
//            val intent = Intent(context, QuizActivity::class.java).apply {
//                putExtra("Title", currentItem.id.toString())
//                putExtra("Description", currentItem.questionList.toString())
//            }
//            context.startActivity(intent)
//
//
//        }
//
//
//    }
//
//    override fun getItemCount(): Int {
//        return topicList.size
//    }
//
//    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//      //  fun bind(topic: Topic) {
//            val title = itemView.findViewById<TextView>(R.id.quiz_title)
//            val subtitle = itemView.findViewById<TextView>(R.id.quiz_subtitle)
//
////            title.text = topic.title
////            subtitle.text = topic.subtitle
////
////            itemView.setOnClickListener {
////                val intent = Intent(itemView.context, QuizActivity::class.java)
////                intent.putExtra("questionList", ArrayList(topic.questionList))
////                Log.d(
////                    ContentValues.TAG,
////                    "firebaseAuthWithGoogle:" + topic.questionList.toString()
////                )
////                itemView.context.startActivity(intent)
////
////            }
////
////        }
//    }
////class HomeViewHolder(private val binding: TopicRowBinding) : RecyclerView.ViewHolder(binding.root) {
////
////    fun bind(topic: Topic) {
////        binding.apply {
////            quizTitle.text = topic.title
////            quizSubtitle.text = topic.subtitle
////            root.setOnClickListener {
////                val intent = Intent(root.context, QuizActivity::class.java)
////
////            }
////        }
////        val title = itemView.findViewById<TextView>(R.id.quiz_title)
////        val subtitle = itemView.findViewById<TextView>(R.id.quiz_subtitle)
////
////        title.text = topic.title
////        subtitle.text = topic.subtitle
////
////        itemView.setOnClickListener {
////            val intent = Intent(itemView.context, QuizActivity::class.java)
////            intent.putExtra("questionList", ArrayList(topic.questionList))
////            itemView.context.startActivity(intent)
////
////        }
////
////    }
////}
//
//    fun setData(topic: List<Topic>) {
//        this.topicList = topic
//        notifyDataSetChanged()
//    }
//
//    fun setOnStartQuizListener(callback: (Topic) -> Unit) {
//        this.startQuiz = callback
//    }
//}

class HomeAdapter(private val topicsList : List<Topic>) :
    RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    class MyViewHolder(private val binding: TopicRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model : Topic){
            binding.apply {
                quizTitle.text = model.title
                quizSubtitle.text = model.subtitle

                root.setOnClickListener {
                    val intent  = Intent(root.context,QuizActivity::class.java)
                    QuizActivity.questionsList = model.questionList
                    root.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = TopicRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return topicsList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(topicsList[position])
    }
}