package com.thss.lunchtime.mainscreen.infopage

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thss.lunchtime.component.InfoData
import com.thss.lunchtime.data.userPreferencesStore
import com.thss.lunchtime.network.LunchTimeApi
import com.thss.lunchtime.network.toInfoData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InfoEditViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(InfoData())
    val uiState = _uiState.asStateFlow()

    fun refresh(context: Context) {
        val userData = context.userPreferencesStore
        viewModelScope.launch {
            val userName = userData.data.first().userName
            try{
                val response = LunchTimeApi.retrofitService.getUserInfo(
                    name = userName,
                    target_name = userName
                )
                if (response.status) {
                    val info = response.userInfo.toInfoData()
                    _uiState.update { state ->
                        state.copy(
                            Avatar = info.Avatar,
                            ID = userName,
                            SelfIntro = info.SelfIntro,
                            relation = info.relation
                        )
                    }
                }
            } catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun modifyUserName(context: Context, newUserName: String){
        val userData = context.userPreferencesStore
        viewModelScope.launch {
            val userName = userData.data.first().userName
            try{
                val response = LunchTimeApi.retrofitService.modifyUserName(
                    old_name = userName,
                    new_name = newUserName
                )
                if (response.status) {
                    _uiState.update { state ->
                        state.copy(
                            ID = newUserName,
                        )
                    }
                    // modify user preferences store
                    userData.updateData { userData ->
                        userData.toBuilder().setUserName(newUserName).setIsLogin(true).build()
                    }
                }
            } catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun modifyDescription(context: Context, newDescription: String){
        val userData = context.userPreferencesStore
        viewModelScope.launch {
            val userName = userData.data.first().userName
            try{
                val response = LunchTimeApi.retrofitService.modifyUserDescription(
                    name = userName,
                    description = newDescription
                )
                if (response.status) {
                    _uiState.update { state ->
                        state.copy(
                            SelfIntro = newDescription,
                        )
                    }
                }
            } catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show()
            }
        }
    }
}