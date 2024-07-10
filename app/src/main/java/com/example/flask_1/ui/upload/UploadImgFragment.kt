package com.example.flask_1.ui.upload

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.flask_1.ui.login.OcrResponse
import com.example.flask_1.R
import com.example.flask_1.ui.login.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException

class UploadImgFragment : Fragment() {

    private lateinit var imgUploaded: ImageView
    private lateinit var btnUploadImage: Button
    private lateinit var btnConvertOcr: Button
    private lateinit var textGeneratedQuestions: TextView

    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_upload_img, container, false)
        imgUploaded = root.findViewById(R.id.img_uploaded)
        btnUploadImage = root.findViewById(R.id.btn_upload_image)
        btnConvertOcr = root.findViewById(R.id.btn_convert_ocr)
        textGeneratedQuestions = root.findViewById(R.id.text_generated_questions)

        btnUploadImage.setOnClickListener {
            openGalleryForImage()
        }

        btnConvertOcr.setOnClickListener {
            imageUri?.let { uri ->
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                    val base64Image = convertBitmapToBase64(bitmap)
                    navigateToLoadingFragment()
                    uploadImageToServer(base64Image)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        return root
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            imgUploaded.setImageURI(imageUri)
            imgUploaded.visibility = View.VISIBLE // Show uploaded image
            btnConvertOcr.visibility = View.VISIBLE // Show OCR button after image is selected
        }
    }

    private fun convertBitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun navigateToLoadingFragment() {
        findNavController().navigate(R.id.action_uploadImgFragment_to_loadingFragment)
    }

    private fun uploadImageToServer(base64Image: String) {
        Log.d("UploadImage", "Base64 Image: $base64Image")

        val requestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), base64Image)
        val call = RetrofitClient.apiService.uploadImage(requestBody)
        call.enqueue(object : Callback<OcrResponse> {
            override fun onResponse(call: Call<OcrResponse>, response: Response<OcrResponse>) {
                if (response.isSuccessful) {
                    val ocrResponse = response.body()
                    ocrResponse?.let {
                        navigateToProblemFragment(it.questions, it.options, it.answers)
                        Log.d("Questions", "Generated questions received: ${it.questions}")
                    }
                } else {
                    Log.e("UploadImage", "Upload failed with response code: ${response.code()}")
                    // Handle upload failure
                }
            }

            override fun onFailure(call: Call<OcrResponse>, t: Throwable) {
                Log.e("UploadImage", "Network error: ${t.message}")
                // Handle network error
            }
        })
    }

    private fun navigateToProblemFragment(questions: List<String>, options: List<List<String>>, answers: List<String>) {
        val bundle = Bundle().apply {
            putStringArrayList("questions", ArrayList(questions))
            putSerializable("options", ArrayList(options.map { ArrayList(it) }))
            putStringArrayList("answers", ArrayList(answers))
        }
        findNavController().navigate(R.id.action_loadingFragment_to_problemFragment, bundle)
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 100
    }
}
