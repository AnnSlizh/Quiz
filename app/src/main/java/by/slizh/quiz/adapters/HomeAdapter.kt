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

    private var startQuiz: ((Topic) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.HomeViewHolder {
        val binding = TopicRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapter.HomeViewHolder, position: Int) {
        holder.bind(topicList[position])



        //   holder.bind(currentItem)



    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    fun updateUserList(topicList : List<Topic>){

        this.topicList.clear()
        this.topicList.addAll(topicList)
        notifyDataSetChanged()

    }


    class HomeViewHolder(private val binding: TopicRowBinding) : RecyclerView.ViewHolder(binding.root) {

      //  fun bind(topic: Topic) {
//            val title = itemView.findViewById<TextView>(R.id.quiz_title)
//            val subtitle = itemView.findViewById<TextView>(R.id.quiz_subtitle)

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
//




//class HomeAdapter(private var topicsList : List<Topic>) :
//    RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
//
//    class MyViewHolder(private val binding: TopicRowBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(model : Topic){
//            binding.apply {
//                quizTitle.text = model.title
//                quizSubtitle.text = model.subtitle
//
//                root.setOnClickListener {
//                    val intent  = Intent(root.context,QuizActivity::class.java)
//                    QuizActivity.questionsList = model.questionList
//                    root.context.startActivity(intent)
//                }
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val binding = TopicRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
//        return MyViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int {
//        return topicsList.size
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.bind(topicsList[position])
//    }
//}