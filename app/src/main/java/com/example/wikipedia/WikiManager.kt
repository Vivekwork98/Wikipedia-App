package com.example.wikipedia

class WikiManager(private val provider: ArticleDataProvider,
                  private val favouritesRepository: FavouritesRepository,
                  private val historyRepository: HistoryRepository)
{
    private var favouritescache : ArrayList<Wikipage>? = null
    private var historyCache : ArrayList<Wikipage>? = null


    fun search(term : String ,skip : Int ,take : Int , handler : (result : WikiResult) -> Unit?)
    {
        return provider.search(term,skip,take,handler)
    }

    fun getRandom(take: Int , handler: (result: WikiResult) -> Unit?)
    {
        return provider.getRandom(take,handler)
    }

    fun getHistory(): ArrayList<Wikipage>?
    {
        if(historyCache == null)
        {
            historyCache = historyRepository.getAllHistory()
        }
        return historyCache
    }

    fun getFavourites(): ArrayList<Wikipage>?
    {
        if(favouritescache == null)
        {
            favouritescache = favouritesRepository.getAllFavourites()
        }
        return favouritescache
    }

    fun addFavourite(page : Wikipage)
    {
        favouritescache?.add(page)
        favouritesRepository.addFavourite(page)
    }

    fun removeFavourite(pageId : Int)
    {
        favouritesRepository.removeFavouriteById(pageId)
        favouritescache = favouritescache!!.filter { it.pageid != pageId } as ArrayList<Wikipage>
    }

    fun getIsFavourite(pageId: Int) : Boolean
    {
        return favouritesRepository.isArticleFavourite(pageId)
    }

    fun addHistory(page : Wikipage)
    {
        historyCache?.add(page)
        historyRepository.addFavourite(page)
    }

    fun clearHistory()
    {
        historyCache?.clear()
        val allHistory = historyRepository.getAllHistory()
        allHistory?.forEach { historyRepository.removePageById(it.pageid!!) }
    }
}