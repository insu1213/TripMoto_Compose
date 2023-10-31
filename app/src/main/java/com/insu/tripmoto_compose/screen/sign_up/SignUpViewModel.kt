package com.insu.tripmoto_compose.screen.sign_up

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.insu.tripmoto_compose.common.ext.isValidEmail
import com.insu.tripmoto_compose.common.ext.isValidPassword
import com.insu.tripmoto_compose.common.ext.passwordMatches
import com.insu.tripmoto_compose.common.snackbar.SnackbarManager
import com.insu.tripmoto_compose.model.UserInfo
import com.insu.tripmoto_compose.model.WishList
import com.insu.tripmoto_compose.model.service.AccountService
import com.insu.tripmoto_compose.model.service.LogService
import com.insu.tripmoto_compose.model.service.StorageService
import com.insu.tripmoto_compose.model.service.uploadImageToFirebaseStorage
import com.insu.tripmoto_compose.screen.MyViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import com.insu.tripmoto_compose.R.string as AppText

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService,
    logService: LogService
): MyViewModel(logService) {

    var uiState = mutableStateOf(SignUpUiState())
        private set

    private var imageUri: Uri? = null
    private var zipImageUri: Uri? = null

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    private val nickName
        get() = uiState.value.nickName

    private val userImage
        get() = uiState.value.isImage

    fun onNickNameChange(newValue: String) {
        uiState.value = uiState.value.copy(nickName = newValue)
    }

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onImageResourceChange(newImage: Uri) {
        uiState.value = uiState.value.copy(isImage = true)
        imageUri = newImage
    }

    fun formatImage(context: Context, bitmap: (Bitmap) -> Unit) {
        viewModelScope.launch {
            try {
                val inputStream = context.contentResolver.openInputStream(imageUri!!)
                val options = BitmapFactory.Options()
                options.inSampleSize = 10 // 이미지 크기를 줄이기 위한 샘플링 비율 설정
                val loadedBitmap = BitmapFactory.decodeStream(inputStream, null, options)
                inputStream?.close()

                // 압축된 이미지를 비트맵 콜백으로 전달
                if (loadedBitmap != null) {
                    val matrix = Matrix()
                    //matrix.postRotate(90f)
                    val rotatedBitmap = Bitmap.createBitmap(loadedBitmap, 0, 0, loadedBitmap.width, loadedBitmap.height, matrix, true)
//                    val outputStream = ByteArrayOutputStream()
//                    loadedBitmap.compress(Bitmap.CompressFormat.JPEG, 30, outputStream)
//                    val path = MediaStore.Images.Media.insertImage(context.contentResolver, loadedBitmap, "Title", null);
//                    zipImageUri = Uri.parse(path)
                    val cacheDir = context.cacheDir // 캐시 디렉토리 가져오기
                    val imageFile = File.createTempFile("image_", ".jpeg", cacheDir)
                    val outputStream = FileOutputStream(imageFile)
                    rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
                    outputStream.close()


                    zipImageUri = Uri.fromFile(imageFile)
                    bitmap(rotatedBitmap)
                } else {
                    Log.d("[Error]", "이미지 압축 오류")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if(nickName.isBlank()) {
            SnackbarManager.showMessage(AppText.nickname_error)
            return
        }

        if(!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }
        if(!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }
        if(!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        launchCatching {
            Log.d(TAG, "실행됨1")
            accountService.linkAccount(email, password)

            val userUid = accountService.currentUserId
            Log.d(TAG, "nickName: $nickName")
            storageService.saveUserInfo(userUid, UserInfo(nickName = nickName, email = email))

            if(zipImageUri != null) {
                uploadImageToFirebaseStorage(
                    zipImageUri!!,
                    userUid,
                    onSuccess = { openAndPopUp("LoginScreen", "SignUpScreen") },
                    onFailure = { Log.d(TAG, "결과: 실패") },
                )
            } else {
                openAndPopUp("LoginScreen", "SignUpScreen")
            }
        }
    }

    fun goSignIn(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp("LoginScreen", "SignUpScreen")
    }
}