package com.example.sqliteimplementation

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqliteimplementation.databinding.ActivityMainBinding
import com.example.sqliteimplementation.databinding.DialogUpdateBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmailId.text.toString()

            val databaseHandler = DatabaseHandler(this)

            if (name.isNotEmpty() && email.isNotEmpty()) {
                val status = databaseHandler.addEmployee(EmpModelClass(0, name, email))

                if (status > -1){
                    Toast.makeText(applicationContext, "Record Saved", Toast.LENGTH_LONG).show()
                    binding.etName.text.clear()
                    binding.etEmailId.text.clear()

                    setUpListOfDataIntoRecyclerView()
                }
            } else {
                Toast.makeText(applicationContext, "Name or Email Cannot Be Blank", Toast.LENGTH_LONG).show()
            }
        }

        setUpListOfDataIntoRecyclerView()

    }

    /*private fun addRecord(it: View?) {

        val name = binding.etName.text.toString()
        val email = binding.etEmailId.text.toString()

        val databaseHandler = DatabaseHandler(this)

        if (!name.isEmpty() && !email.isEmpty()) {
            val status = databaseHandler.addEmployee(EmpModelClass(0, name, email))

            if (status > -1){
                Toast.makeText(applicationContext, "Record Saved", Toast.LENGTH_LONG).show()
                binding.etName.text.clear()
                binding.etEmailId.text.clear()

                setUpListOfDataIntoRecyclerView()
            }
        } else {
            Toast.makeText(applicationContext, "Name or Email Cannot Be Blank", Toast.LENGTH_LONG).show()
        }


    }*/

    private fun setUpListOfDataIntoRecyclerView() {

        if (getItemsList().size > 0){
            binding.rvItemsList.visibility = View.VISIBLE
            binding.tvNoRecordsAvailable.visibility = View.GONE

            binding.rvItemsList.layoutManager = LinearLayoutManager(this)

            val  itemAdapter = ItemAdapter(this, getItemsList())

            binding.rvItemsList.adapter = itemAdapter

        } else {

            binding.rvItemsList.visibility = View.GONE
            binding.tvNoRecordsAvailable.visibility = View.VISIBLE
        }

    }

    private fun getItemsList() : ArrayList<EmpModelClass> {

        val databaseHandler = DatabaseHandler(this)

        return databaseHandler.viewEmployee()
    }

    fun updateRecordDialog(empModelClass : EmpModelClass) {
        val updateDialogBinding = DialogUpdateBinding.inflate(LayoutInflater.from(this))
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)

        updateDialog.setContentView(updateDialogBinding.root)
        updateDialogBinding.etUpdateName.setText(empModelClass.name)
        updateDialogBinding.etUpdateEmailId.setText(empModelClass.email)

        updateDialogBinding.tvUpdate.setOnClickListener(View.OnClickListener {
            val name = updateDialogBinding.etUpdateName.text.toString()
            val email = updateDialogBinding.etUpdateEmailId.text.toString()
            val databaseHandler = DatabaseHandler(this)

            if (!name.isEmpty() && !email.isEmpty()){

                val status = databaseHandler.updateEmployee(EmpModelClass(empModelClass.id, name, email))
                if(status > -1){
                    Toast.makeText(applicationContext, "Record Updated", Toast.LENGTH_LONG).show()
                    setUpListOfDataIntoRecyclerView()
                    updateDialog.dismiss()
                }
            } else {
                Toast.makeText(applicationContext, "Name or Email Cannot Be Blank", Toast.LENGTH_LONG).show()

            }

        })

        updateDialogBinding.tvCancel.setOnClickListener(View.OnClickListener {
            updateDialog.dismiss()
        })

        updateDialog.show()
    }


    fun deleteRecordAlertDialog(empModelClass: EmpModelClass) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Record")
        builder.setMessage("Are you sure want to delete ${empModelClass.id} ?")

        builder.setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->

            val databaseHandler = DatabaseHandler(this)
            val status = databaseHandler.deleteEmployee(EmpModelClass(empModelClass.id, "", ""))

            if (status > -1){

                Toast.makeText(
                    applicationContext,
                    "Record deleted successfully",
                    Toast.LENGTH_LONG
                ).show()

                setUpListOfDataIntoRecyclerView()


            } else{

                Toast.makeText(applicationContext, "Record deleted successfully", Toast.LENGTH_LONG).show()
                setUpListOfDataIntoRecyclerView()


            }

            dialogInterface.dismiss()

        }

        builder.setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int ->

            dialogInterface.dismiss()

        }

        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()

    }



}






















