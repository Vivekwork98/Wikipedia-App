package com.example.wikipedia

object Urls
{
    val BaseUrl = "https://en.wikipedia.org/w/api.php"

    fun getSearchUrl(term:String , skip:Int , take:Int ) : String
    {
        return BaseUrl + "?action=query" +
                "&formatversion=2" +
                "&generator=prefixsearch" +
                "&gpssearch=$term" +
                "&gpslimit=$take" +
                "&gpsoffset=$skip" +
                "&prop=pageimages|info" +
                "&piprop=thumbnail|url" +
                "&pithumbsize=200" +
                "&pilimit=$take" +
                "&wbptterms=description" +
                "&format=json" +
                "&inprop=url"
    }

    fun getRandomUrl(take:Int) : String
    {
        return BaseUrl + "?action=query" +
                "&format=json" +
                "&formatversion=2" +
                "&generator=random" +
                "&grnnamesapce=0" +
                "&prop=pageimages|info" +
                "&grnlimit=$take" +
                "&inprop=url" +
                "&pithumbsize=200"
    }
}