package app.dictionary.com.nikeapp.data.pojo

import com.google.gson.annotations.SerializedName


data class DictionaryResponse(

	@field:SerializedName("list")
	val list: List<ListItem> = ArrayList()
)