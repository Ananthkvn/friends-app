package com.example.friends

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.example.friends.databinding.ActivityMainBinding
import android.graphics.Bitmap
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.friends.Data.MainActivityViewModel
import com.example.friends.Data.User
import com.example.friends.validationField_Text.FormFIeldTextDobCalender
import com.example.friends.validationField_Text.FormFieldTextAge
import com.example.friends.validationFields.FormFieldTextAdress
import com.example.friends.validationFields.FormFieldTextEmail
import com.example.friends.validationFields.FormFieldTextName
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.view.clicks
import java.util.*

class MainActivity : AppCompatActivity() {
    var players = arrayOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-", "A1B+", "A1B-")
    private val nameValidation: TextView
        get() = findViewById(R.id.nameEditText)

    private val save_btn: Button get() = findViewById(R.id.btn_submit)

    lateinit var viewModel: MainActivityViewModel

    //    private lateinit var binding: ActivityMainBinding
    val REQUEST_IMAGE_CAPTURE = 1

    //NEW VALIDATIONS
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val fieldUsername by lazy {
        FormFieldTextName(
            scope = lifecycleScope,
            textInputLayout = binding.nameContainer,
            textInputEditText = binding.nameEditText,
            validation = { value ->
                when {
                    value.isNullOrBlank() -> "Username is required."
                    else -> null
                }
            }
        )
    }
    private val fieldUserAge by lazy {
        FormFieldTextAge(scope = lifecycleScope,
            textInputLayout = binding.AgeContainer,
            textInputEditText = binding.ageEditText,
            validation = { value ->
                when {
                    value.isNullOrBlank() -> "Age is Required"
                    else -> null
                }
            }
        )
    }
    private val fieldUserEmail by lazy {
        FormFieldTextEmail(
            scope = lifecycleScope,
            textInputLayout = binding.emailContainer,
            textInputEditText = binding.emailEditText,
            validation = { value ->
                when {
                    value.isNullOrBlank() -> "Email is required"
                    else -> null
                }
            }
        )
    }
    private val fieldUserMobileNo by lazy {
        FormFieldTextEmail(
            scope = lifecycleScope,
            textInputLayout = binding.numberContainer,
            textInputEditText = binding.numberEditText,
            validation = { value ->
                when {
                    value.isNullOrBlank() -> "Mobile Number is required"
                    else -> null
                }
            }
        )
    }
    private val fieldUserAddress by lazy {
        FormFieldTextAdress(
            scope = lifecycleScope,
            textInputLayout = binding.addressContainer,
            textInputEditText = binding.addressEditText,
            validation = { value ->
                when {
                    value.isNullOrBlank() -> "Address is required"
                    else -> null
                }
            }
        )
    }
    private val fieldUserDobCalender by lazy {
        FormFIeldTextDobCalender(
            scope = lifecycleScope,
            textInputLayout = binding.datetv,
            textInputEditText = binding.etDobmm,
            validation = { value ->
                when {
                    value.isNullOrBlank() -> "Dob is Required"
                    else -> null
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val view = binding.root
        setContentView(view)
        spinnerRetrofit()
        onDate()
        initViewModel()

        binding.btnSubmit.clicks().onEach {
//            var mapCLick = Intent(this, MapsActivity::class.java)
//            startActivity(mapCLick)
//            startActivity(Intent(this, MapsActivity::class.java))
            submit()
        }.launchIn(lifecycleScope)
    }

    private fun submit() = lifecycleScope.launch {
//        showToast("Submitted Successfully")
//            startActivity(Intent(this@MainActivity, HomeActivity2::class.java))

        createUser()
        binding.btnSubmit.isEnabled = false
        fieldUsername.disable()
        fieldUserAge.disable()
        fieldUserEmail.disable()
        fieldUserMobileNo.disable()
        fieldUserAddress.disable()
        fieldUserDobCalender.disable()
        if (fieldUsername.validate()) {
            fieldUsername.value
            println(fieldUsername.value)
//            showToast("Submit Successful!")
        }
        if (fieldUserAge.validate()) {
            fieldUserAge.value
            println("fieldUserAge")
            println(fieldUserAge.value)
        }
        if (fieldUserEmail.validate()) {
            fieldUserEmail.value
            println(fieldUserEmail.value)
        }
        if (fieldUserMobileNo.validate()) {
            fieldUserMobileNo.value
            println(fieldUserMobileNo.value)
        }
        if (fieldUserAddress.validate()) {
            fieldUserAddress.value
            println(fieldUserAddress.value)
        }
        if (fieldUserDobCalender.validate()) {
            fieldUserDobCalender.value
            println(fieldUserDobCalender.value)
        }
//        if (fieldUsername.validate() && fieldUserAge.validate() && fieldUserDobCalender.validate() && fieldUserMobileNo.validate() && fieldUserAddress.validate() && fieldUserEmail.validate()) {
//            showToast("Submited Successfully")
//        }

        fieldUsername.enable()
        fieldUserAge.enable()
        fieldUserEmail.enable()
        fieldUserMobileNo.enable()
        fieldUserAddress.enable()
        fieldUserDobCalender.enable()
        binding.btnSubmit.isEnabled = true
        showToast("Submitted Successfully")
    }

    //SUBMIT BUTTON CREATE USER
    fun createUser() {
        val user = User(
            nameEditText.text.toString(),
            ageEditText.text.toString(),
            etDobmm.text.toString(),
            emailEditText.text.toString(),
            numberEditText.text.toString(),
            addressEditText.text.toString(),
            sp_dropdown.id.toString()
        )
        viewModel.createNewUser(user)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getCreateNewUserObserver().observe(this, androidx.lifecycle.Observer<User?> {
            if (it == null) {
                Toast.makeText(this@MainActivity, "Failed to create user", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this@MainActivity, "Successfully create user", Toast.LENGTH_SHORT)
                    .show()

            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }


    //SPINNER DROPDOWN
    private fun spinnerRetrofit() {

        binding.imgPickBtn.setOnClickListener {
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission already granted
                    pickImageFromGallery()
                }
            } else {
                //system OS is < Marshmallow
                pickImageFromGallery()
            }
        }
        binding.btnCamera.setOnClickListener {
            dispatchTakePictureIntent()
        }
        val spinner = findViewById<Spinner>(R.id.sp_dropdown)
        val arrayAdapter =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, players)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                println()
                Toast.makeText(
                    applicationContext,
                    "Selected item = " + players[position],
                    Toast.LENGTH_SHORT
                ).show()
                println("ppppppppppp")
                println(players[position])

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }

    }

    //Gallery Function
    private fun pickImageFromGallery() {
        println("Gallery image picked")
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        //Permission code
        private val PERMISSION_CODE = 1001
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        println("Permission wanted page......")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        println("Where..........")
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.imageView.setImageURI(data?.data)
            println("image captured by gallery")
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imageView.setImageBitmap(imageBitmap)
        }
    }

    fun onDate() {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val days = calender.get(Calendar.DAY_OF_MONTH)

        val btn = findViewById<Button>(R.id.pickDateBtn)
        btn.setOnClickListener {
            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                    val tvChange = findViewById<TextView>(R.id.etDobmm)
                    tvChange?.setText("" + mYear + "/" + mMonth + "/" + mDay + "T00:00:00Z")
                },
                year,
                month,
                days
            )
            dpd.show()
        }
    }


}