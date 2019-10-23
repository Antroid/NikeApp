package app.dictionary.com.nikeapp.data.local



data class EntryData(
    val id : Long=0,
    val definition : String="",
    val thumbUp : Int = 0,
    val thumbDown : Int = 0
)
{
    class CompareThumbsDownAsc : Comparator<EntryData> {
        override fun compare(obj1: EntryData, obj2: EntryData): Int {
            return obj1.thumbDown-obj2.thumbDown
        }
    }

    class CompareThumbsDownDesc : Comparator<EntryData> {
        override fun compare(obj1: EntryData, obj2: EntryData): Int {
            return obj2.thumbDown-obj1.thumbDown
        }
    }

    class CompareThumbsUpAsc : Comparator<EntryData> {
        override fun compare(obj1: EntryData, obj2: EntryData): Int {
            return obj1.thumbUp-obj2.thumbUp
        }
    }

    class CompareThumbsUpDesc : Comparator<EntryData> {
        override fun compare(obj1: EntryData, obj2: EntryData): Int {
            return obj2.thumbUp-obj1.thumbUp
        }
    }

}