import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  */
object WordCount {
  def main(args: Array[String]): Unit ={
    val conf = new SparkConf().setAppName("WordCount")
    val sc = new SparkContext(conf)
    var srcFile="E:\\github\\holly\\sparklearning\\jars\\README.md"
    var resFile="E:\\github\\holly\\sparklearning\\jars\\result"
    if(args.length > 0){
      srcFile = args(0)
      resFile = args(1)
    }
    val rdd = sc.textFile(srcFile)
    val wordcount = rdd.flatMap(_.split(" ")).map(x => (x,1)).reduceByKey(_+_)
    val wordsort = wordcount.map(x=>(x._2,x._1)).sortByKey(false).map(x=>(x._2,x._1))
    wordsort.saveAsTextFile(resFile)
    sc.stop()
  }
}
