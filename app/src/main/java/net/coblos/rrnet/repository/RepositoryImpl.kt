package net.coblos.rrnet.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.coblos.rrnet.domain.DataState
import net.coblos.rrnet.domain.model.ClientAuth
import net.coblos.rrnet.domain.model.req.PostMobileReq
import net.coblos.rrnet.domain.model.req.PostVerificationReq
import net.coblos.rrnet.domain.model.res.PostMobileRes
import net.coblos.rrnet.domain.model.res.PostVerificationRes
import net.coblos.rrnet.net.Services
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import retrofit2.HttpException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val services: Services) : Repository {

    override suspend fun postLogin(url: String, username: String, password: String): Flow<DataState<String>> = flow {
        emit(DataState.Loading)
        try {
            val postLogin = services.login(url, username, password)

            Log.e("HTML", postLogin)
            val doc: Document = Jsoup.parse(postLogin)
            val elements: Elements = doc.select("div.alert.bg-danger.text-center.mr-t-10")
            emit(DataState.Success(elements.text()))
        } catch (e: HttpException) {
            Log.e("TAGGING_ERR", e.response()?.errorBody().toString())
            emit(DataState.Error(Exception(e)))
        } catch (e: Exception) {
            Log.e("TAGGING_EXCEPTION", e.message.toString())
            emit(DataState.Error(e))
        }
    }

    override suspend fun postMobile(url: String, mobile: String): Flow<DataState<PostMobileRes>> = flow {
        emit(DataState.Loading)
        try {
            val postMobile = services.postMobile(url, PostMobileReq(mobile))
            emit(DataState.Success(postMobile))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(DataState.Error(Exception(e)))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }

    override suspend fun postVerification(
        url: String, verification: String
    ): Flow<DataState<ClientAuth?>> = flow {
        try {
            val postVerification = services.postVerification(url, PostVerificationReq(verification))
            emit(DataState.Success(postVerification.data))
        } catch (e: HttpException) {
            e.printStackTrace()
            emit(DataState.Error(Exception(e)))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(DataState.Error(e))
        }
    }
}