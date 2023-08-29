package com.insu.tripmoto_compose.screen.main.chat

import android.content.Context
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.insu.tripmoto_compose.model.ChatList
import com.insu.tripmoto_compose.model.Trip
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

//class ChatNotificationWorker(
//    private val appContext: Context,
//    workerParams: WorkerParameters
//): Worker(appContext, workerParams) {
//    //val tripId = inputData.getString("TripId") ?: ""
//    private val uid = inputData.getString("Uid") ?: ""
//
//    private val db = FirebaseFirestore.getInstance()
//    private val _dataFlow: MutableStateFlow<List<ChatList>> = MutableStateFlow(emptyList())
//    var isFully = false
//
//    init {
//        CoroutineScope(Dispatchers.IO).launch {
//            val tripList = db.collection("trip").whereArrayContains("member", uid).dataObjects<Trip>()
//            tripList
//                .onCompletion {
//
//                }
//                .collect {
//
//                }
//
//            chatList
//                .onCompletion {
//                    if(_dataFlow.value.isNotEmpty()) {
//                        isFully = true
//                    }
//                }
//                .collect { newData ->
//                    _dataFlow.value = newData
//                }
//        }
//    }
//
//    override fun doWork(): Result {
//        return if(isFully) {
//            // Do the work here--in this case, upload the images.
//            val title = inputData.getString("title") ?: ""
//            val body = inputData.getString("body") ?: ""
//
//            callNotification(appContext, title, body)
//
//            // Indicate whether the work finished successfully with the Result
//            Result.success()
//        } else {
//            Result.failure()
//        }
//    }
//
//    private fun callNotification(appContext: Context, title: String, body: String) {
//        NotificationService(appContext).showBasicNotification(title, body)
//    }
//}